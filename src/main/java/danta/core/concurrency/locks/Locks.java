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

package danta.core.concurrency.locks;


import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * User: joshuaoransky
 * Date: 1/3/14
 * Time: 23:18
 * Purpose:
 * Location:
 */
public class Locks {

    private final ReentrantReadWriteLock lockMaster;
    private final ReadLock readLock;
    private final WriteLock writeLock;

    public Locks() {
        lockMaster = new ReentrantReadWriteLock();
        readLock = new ReadLock(lockMaster.readLock());
        writeLock = new WriteLock(lockMaster.writeLock());
    }

    public final ReadLock readLock() {
        return readLock;
    }


    public final WriteLock writeLock() {
        return writeLock;
    }

    public static abstract class Lock<L extends java.util.concurrent.locks.Lock>
            implements AutoCloseable {

        private final L lock;

        private Lock(L lock) {
            this.lock = lock;
        }

        public Lock lock() {
            lock.lock();
            return this;
        }

        public Lock unlock() {
            lock.unlock();
            return this;
        }

        public void close()
                throws RuntimeException {
            unlock();
        }
    }

    public static final class ReadLock
            extends Lock<ReentrantReadWriteLock.ReadLock> {

        private ReadLock(ReentrantReadWriteLock.ReadLock lock) {
            super(lock);
        }
    }

    public static final class WriteLock
            extends Lock<ReentrantReadWriteLock.WriteLock> {

        private WriteLock(ReentrantReadWriteLock.WriteLock lock) {
            super(lock);
        }
    }
}
