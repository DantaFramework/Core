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

package danta.core;

import danta.core.annotations.DantaProperty;

import static danta.Constants.NS_XUMAK_AN;
import static danta.Constants.NS_XUMAK_PN;

public class Constants {

    public static final String ERROR = "ERROR";

    //Styling XK Config Stuff
    public static final String CONTAINER_CLASSES_CP = "containerClasses";
    public static final String CONTAINER_STYLING_CSSN_AV = "containerStyling";

    @DantaProperty(
            name = "xk_containerClass",
            type = "String[]"
    )
    public static final String XK_CONTAINER_CLASSES_CP = NS_XUMAK_PN + CONTAINER_CLASSES_CP;
    public static final String XK_CLIENT_ACCESSIBLE_CP = NS_XUMAK_PN + "clientAccessible";
    public static final String XK_CONTENT_ID_CP = NS_XUMAK_PN + "contentId";
    public static final String XK_CLIENT_MODEL_PROPERTIES_CP = NS_XUMAK_PN + "clientModelProperties";
    public static final String XK_COMPONENT_CATEGORY = NS_XUMAK_PN + "componentCategory";

    @DantaProperty(
            name = "xk_placeholderTriggers",
            type = "String[]"
    )
    public static final String XK_PLACEHOLDER_TRIGGERS_CP = NS_XUMAK_PN + "placeholderTriggers";
    public static final String DISPLAY_PLACEHOLDER_STYLING_PROPERTY_NAME = "displayPlaceholder";
    public static final String DISPLAY_PLACEHOLDER_CSSN = "xk-placeholder";
    @DantaProperty(
            name = "xk_deserializeJSON",
            description = "Used to deserialize multi-field values into a JSON that " +
                    "represents the exact same data model as the form it is stored in the JCR",
            type = "String[]",
            exampleValues = {"content.keyValues", "content.entries"},
            usedBy = {"DeserializeJSONPropertyValuesContextProcessor"}
    )
    public static final String XK_DESERIALIZE_JSON_PROPS_CP = "xk_deserializeJSON";

}
