/**
 * Danta Core Bundle
 * (danta.core)
 *
 * Copyright (C) 2017 Tikal Technologies, Inc. All rights reserved.
 *
 * Licensed under GNU Affero General Public License, Version v3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/agpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied;
 * without even the implied warranty of MERCHANTABILITY.
 * See the License for more details.
 */

package danta.core.concurrency.tasks;

import danta.core.concurrency.locks.Locks;
import danta.core.util.OSGiUtils;
import org.apache.felix.scr.annotations.*;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

import static danta.core.Constants.ERROR;
import static danta.core.concurrency.locks.Locks.Lock;
import static danta.core.concurrency.tasks.EmptyCacheBehavior.WAIT;
import static org.apache.sling.commons.scheduler.Scheduler.PROPERTY_SCHEDULER_CONCURRENT;

/**
 * Abstract Asynchronous Executor
 *
 * @author      joshuaoransky
 * @version     1.0.0
 * @since       2014-05-16
 */
@Component(componentAbstract = true, metatype = true, immediate = true)
@Service({Runnable.class, AsynchronousExecutor.class})
@Property(name = PROPERTY_SCHEDULER_CONCURRENT, boolValue = false)
public abstract class AbstractAsynchronousExecutor<Result>
        implements AsynchronousExecutor<Result> {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private final Locks locks = new Locks();
    protected final Locks.ReadLock readLock = locks.readLock();
    protected final Locks.WriteLock writeLock = locks.writeLock();
    protected final Semaphore available = new Semaphore(1, true);

    private volatile Result cachedResult;

    /**
     * Override this to do specific, implementation dependant activation work before the process is started.
     */
    protected void doActivate() {

    }

    /**
     * This is where the specific implementation logic goes to generate the Result.
     *
     * @return
     * @throws Exception
     */
    protected abstract Result computeResult()
            throws Exception;

    /**
     * This is where logic to determine if the cached value is in a state that's considered workable.
     *
     * @return
     */
    protected abstract boolean isCacheValid();

    protected EmptyCacheBehavior emptyCacheBehavior() {
        return WAIT;
    }

    /**
     * Override this to do specific, implementation dependant deactivation work before the process is started.
     */
    protected void doDeactivate() {

    }


    protected final boolean isCacheInvalid() {
        return !isCacheValid();
    }


    protected final Result cachedResult() {
        try (Lock l = readLock.lock()) {
            return cachedResult;
        }
    }

    private final Result updateCache(final Result result) {
        try (Lock l = writeLock.lock()) {
            return cachedResult = result;
        }
    }

    protected final Result blockForResult()
            throws Exception {
        try (Lock l = readLock.lock()) {
            return currentTask().get();
        }
    }

    public final Result result()
            throws Exception {
        Result result = cachedResult();
        if (isCacheInvalid()) {
            switch (emptyCacheBehavior()) {
                case BARF:
                    throw new IllegalStateException("No Result has been fully calculated.");
                case WAIT:
                    result = blockForResult();
                case BLANK:
                default:
            }
        }
        return result;
    }

    protected ComponentContext componentContext;

    @Activate
    protected final void activate(ComponentContext componentContext)
            throws Exception {
        this.componentContext = componentContext;
        OSGiUtils.activate(this, componentContext);
        doActivate();
        run();
    }

    private volatile FutureTask<Result> currentTask;

    protected final FutureTask<Result> currentTask() {
        try (Lock l = readLock.lock()) {
            return currentTask;
        }
    }

    protected final FutureTask<Result> currentTask(FutureTask<Result> task) {
        try (Lock l = writeLock.lock()) {
            return currentTask = task;
        }
    }

    private final class AsyncProcessTask
            extends FutureTask<Result> {

        private AsyncProcessTask() {
            super(new Callable<Result>() {
                @Override
                public Result call()
                        throws Exception {
                    return computeResult();
                }
            });
            run();
        }

        @Override
        protected void done() {
            try {
                if (!isCancelled()) {
                    Result result = get();
                    updateCache(result);
                    available.release();
                }
            } catch (Exception ew) {
                log.error(ERROR, ew);
            }
        }
    }

    public final void run() {
        try {
            available.acquire();
            FutureTask<Result> task = currentTask(new AsyncProcessTask());
        } catch (Exception ew) {
            log.error(ERROR, ew);
            available.release();
        }
    }

    @Deactivate
    protected final void deactivate(final ComponentContext context) {
        try (Lock l = readLock.lock()) {
            if (currentTask() != null && !currentTask().isCancelled())
                currentTask().cancel(true);
            doDeactivate();
        }
    }
}
