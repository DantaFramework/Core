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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Abstract Callback Task
 *
 * @author      joshuaoransky
 * @version     1.0.0
 * @since       2013-03-03
 */
public class AbstractCallbackTask<Result>
        extends FutureTask<Result>
        implements CallbackTask {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final List<Callback<Result>> callbacks = new ArrayList<>();
    protected String id;

    protected AbstractCallbackTask(String id, Callable<Result> task, Callback<Result>... callbacks)
            throws Exception {
        super(task);
        this.callbacks.addAll(Arrays.asList(callbacks));
        this.id = id;
    }

    protected AbstractCallbackTask(Callable<Result> task, Callback<Result>... callbacks)
            throws Exception {
        this(task.toString(), task, callbacks);
    }

    public final String id() {
        return id;
    }
}
