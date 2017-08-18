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

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: joshuaoransky
 * Date: 4/20
 * Time: 0:48
 * Purpose:
 * Location:
 */
public final class ObjectUtils {

    private ObjectUtils() {
    }

    public static JSONObject wrap(final JSONObject data) {
        return data;
    }

    public static JSONObject wrap(final Map<String, Object> data) {
        return (data instanceof JSONObject) ? (JSONObject) data : new JSONMapWrapper(data);
    }

    public static JSONArray wrap(final JSONArray data) {
        return data;
    }

    public static JSONArray wrap(final List<Object> data) {
        JSONArray obj = new JSONArray();
        if(data instanceof JSONArray)
            obj = (JSONArray) data;
        else {
            obj.addAll(data);
        }
        return obj;
    }

    private final static class JSONMapWrapper
            extends JSONObject {

        private final Map<String, Object> wrappedMap;

        public JSONMapWrapper(Map<String, Object> map) {
            this.wrappedMap = map;
        }

        @Override
        public int size() {
            return wrappedMap.size();
        }

        @Override
        public boolean isEmpty() {
            return wrappedMap.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return wrappedMap.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return wrappedMap.containsValue(value);
        }

        @Override
        public Object get(Object key) {
            return wrappedMap.get(key);
        }

        @Override
        public Object put(String key, Object value) {
            return wrappedMap.put(key, value);
        }

        @Override
        public Object remove(Object key) {
            return wrappedMap.remove(key);
        }

        public void putAll(Map<? extends String, ?> m) {
            wrappedMap.putAll(m);
        }

        @Override
        public void clear() {
            wrappedMap.clear();
        }

        @Override
        public Set<String> keySet() {
            return wrappedMap.keySet();
        }

        @Override
        public Collection<Object> values() {
            return wrappedMap.values();
        }

        @Override
        public Set<Map.Entry<String, Object>> entrySet() {
            return wrappedMap.entrySet();
        }

        @Override
        public boolean equals(Object o) {
            return wrappedMap.equals(o);
        }

        @Override
        public int hashCode() {
            return wrappedMap.hashCode();
        }
    }
}
