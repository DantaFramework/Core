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

/**
 * Simple utility class to assist
 * with URL path manipulation.
 *
 * @author      dross
 * @version     1.0.0
 * @since       2016-04-20
 */
public class UrlUtils {

    private static final String SLASH = "/";

    private UrlUtils() {
    }

    /**
     * Returns the segments of a URL path, removing the extension if necessary.
     * For example: given <code>/content/dam/xumak.html</code> the returned
     * segments would be [0] content [1] dam [2] xumak.
     * This function will return a <code>String[]</code> of size
     * one if a single segment is passed into the function.
     * @param path The path to parse for segments
     * @return the parsed segments
     */
    public static String[] getPathSegments(String path) {

        path = removeExtensionFromPath(path);

        if (path.startsWith(SLASH)) {
            path = path.substring(1, path.length());
        }
        return getRelativePathSegments(path);
    }

    /**
     * Returns only the last segment of a path
     * @param path The path to parse
     * @return The last segment of a path. For example, "xumak" in <code>/content/dam/xumak</code>
     */
    public static String getLastPathSegment(String path) {
        String[] segments = getPathSegments(path);
        return segments[segments.length - 1];
    }

    private static String[] getRelativePathSegments(String path) {
        return path.split(SLASH);
    }

    private static String removeExtensionFromPath(String path) {
        if (path.contains(".")) {
            return path.substring(0, path.indexOf("."));
        } else {
            return path;
        }
    }

}
