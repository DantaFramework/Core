/**
 * LayerX Core Bundle
 * (layerx.core)
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

package layerx.core.concurrency.tasks;

import java.util.concurrent.Callable;

/**
 * Created by IntelliJ IDEA.
 * User: joshuaoransky
 * Date: 5/7/12
 * Time: 12:39 PM
 * Location:
 * Purpose:
 */

/**
 * This class allows a Task to be started that runs synchronously, then passed the result of the processing
 * to any Callbacks passed.
 * <p/>
 * Usage:<br/>
 * <pre>
 *  public class ExampleUsage {
 *
 *      private static Number countUpTo(Number number)
 *              throws InterruptedException {
 *          int i = 0;
 *          while (i <= number.intValue()) {
 *              System.out.println("Counting " + i);
 *              Thread.sleep(1000);
 *              i++;
 *          }
 *          return i;
 *      }
 *
 *      private static void processWorkResult(Number result) {
 *          System.out.println("Counted to: " + result);
 *      }
 *
 *      public static void main(String... args) {
 *          final Integer number = Integer.parseInt(args[0]);
 *          Callable<Number> work = new Callable<Number>() {
 *              public Number call()
 *                      throws Exception {
 *                  return countUpTo(number);
 *              }
 *          };
 *          Callback<Number> callback = new Callback<Number>() {
 *              public void invoke(final Number result) {
 *                  processWorkResult(result);
 *              }
 *          };
 *          CallbackTask<Number> task = new SynchronousCallbackTask<>(work, callback);
 *          System.out.println("Started counting, and when I'm done I printed this statement!");
 *      }
 *  }
 * </pre>
 */
public final class SynchronousCallbackTask<Result>
        extends AbstractCallbackTask<Result> {

    /**
     * Runs a Task asynchronously, defined in Callable that returns type of Result.
     *
     * @param task
     * @param callbacks
     */
    public SynchronousCallbackTask(String id, Callable<Result> task, Callback<Result>... callbacks)
            throws Exception {
        super(id, task, callbacks);
    }

    public SynchronousCallbackTask(Callable<Result> task, Callback<Result>... callbacks)
            throws Exception {
        super(task, callbacks);
        Result value = this.get();
        for (Callback<Result> callback : callbacks)
            callback.invoke(value);
    }
}
