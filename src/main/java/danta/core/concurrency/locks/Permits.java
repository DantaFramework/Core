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


import java.util.concurrent.*;

/**
 * User: joshuaoransky
 * Date: 1/3/14
 * Time: 23:18
 * Purpose:
 * Location:
 */
public class Permits {

    private final Semaphore semaphore;

    public Permits() {
        this(1);
    }

    public Permits(int maxNumber) {
        this(maxNumber, true);
    }

    public Permits(int maxNumber, boolean fair) {
        semaphore = new Semaphore(maxNumber, fair);
    }

    public Permit obtain()
            throws InterruptedException {
        return obtain(1);
    }

    public Permit obtain(int number)
            throws InterruptedException {
        return new Permit(number);
    }

    public class Permit
            implements AutoCloseable {

        private boolean isValid = true;
        private int total;

        private Permit()
                throws InterruptedException {
            this(1);
        }

        private Permit(int number)
                throws InterruptedException {
            total = number;
            semaphore.acquire(number);
        }

        public Permit cancel() {
            isValid = false;
            semaphore.release(total);
            return this;
        }

        public Permit trim(int number) {
            if (number < total) {
                total = number;
                semaphore.release(number);
            } else if(number >= total) {
                return cancel();
            }
            return this;
        }

        public int remaining() {
            return total;
        }

        public boolean isValid() {
            return isValid;
        }

        public void close()
                throws RuntimeException {
            cancel();
        }
    }

}
