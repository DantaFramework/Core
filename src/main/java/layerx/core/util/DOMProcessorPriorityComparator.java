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

package layerx.core.util;

import layerx.api.DOMProcessor;

import java.util.Comparator;


/**
 * This class is used to sort a list of {@link DOMProcessor}. The list
 * is ordered in descending order based on the priority.
 * <p/>
 * Note that this class is not intended to be used to order a sorted collection,
 * such as TreeSet.
 *
 * @author jabarca
 */
public class DOMProcessorPriorityComparator
        implements Comparator<DOMProcessor> {

    /**
     * Compares two {@code DOMProcessor} based on their priority, and is used mainly to sort a list of
     * {@link DOMProcessor} in descending order based on the priority.
     *
     * @param  cp1 the first {@code DOMProcessor} to compare
     * @param  cp2 the second {@code DOMProcessor} to compare
     * @return the value {@code 0} if {@code cp1's priority == cp2's priority};
     *         a value less than {@code 0} if {@code cp1's priority > cp2's priority}; and
     *         a value greater than {@code 0} if {@code cp1's priority < cp1's priority}
     */
    @Override
    public int compare(DOMProcessor cp1, DOMProcessor cp2) {
        return Integer.compare(cp2.priority(), cp1.priority());
    }
}
