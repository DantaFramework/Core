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

package layerx.core.commons.collections;

import net.sf.juffrou.reflect.JuffrouBeanWrapper;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by joshuaoransky on 6/27/14.
 */
public class POJOBackedMap extends AbstractMap<String, Object> {

    //TODO: Add lazy loading, caching of BeanContexts, support for nested beans, and asynchronous processing

    private final Object object;
    private final Map<String, Object> rawObjectMap;

    private POJOBackedMap(final Object object) {
        this.object = object;
        rawObjectMap = new HashMap<>();
        JuffrouBeanWrapper objWrapper = new JuffrouBeanWrapper(object);
        for(String name : objWrapper.getPropertyNames()) {
            rawObjectMap.put(name, objWrapper.getValue(name));
        }
    }

    public static POJOBackedMap toMap(final Object object) {
        return new POJOBackedMap(object);
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return rawObjectMap.entrySet();
    }
}
