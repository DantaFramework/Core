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

package layerx.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Field level annotation that allows for the proper
 * documentation of an LayerX configuration property.
 * Using this annotation will allow the javadoc
 * tool to properly describe the purpose and use
 * of a particular configuration that can be specified
 * by the developers of a ContextProcessor during
 * initial development, and not through use of a
 * wiki entry at a later date.
 * <p/>
 * {@code name} The name that should be put on the xk_config node
 * {@code type} The type for the value that the configuration expects (e.g. String[])
 * {@code description} A concise and informative description of how this
 * configuration works with this context processor
 * {@code exampleValues} A couple example values to help ensure proper usage
 * and generally reduce development time.
 * @author dross
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface LayerXProperty {

    public String name();
    public String description() default "";
    public String type();
    public String[] exampleValues() default {};
    public String[] usedBy() default {};
}
