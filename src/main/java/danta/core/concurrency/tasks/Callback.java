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

/**
 * User: joshuaoransky
 * Date: 5/7/12
 * Time: 12:37 PM
 * Location:
 * Purpose:
 */
public interface Callback<V> {
    /**
     *
     *
     * @param result
     * @throws Exception
     */
    public void invoke(V result)
            throws Exception;
}
