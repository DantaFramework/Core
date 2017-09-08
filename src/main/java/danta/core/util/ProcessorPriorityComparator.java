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

package danta.core.util;

import danta.api.Processor;

import java.util.Comparator;


/**
 * This class is used to sort a list of {@link Processor}. The list
 * is ordered in descending order based on the priority.
 * <p/>
 * Note that this class is not intended to be used to order a sorted collection,
 * such as TreeSet.
 *
 * @author      dhughes
 * @version     1.0.0
 * @since       2017-09-08
 */
public class ProcessorPriorityComparator
        implements Comparator<Processor> {

    @Override
    public int compare(Processor cp1, Processor cp2) {
        return Integer.compare(cp2.priority(), cp1.priority());
    }
}
