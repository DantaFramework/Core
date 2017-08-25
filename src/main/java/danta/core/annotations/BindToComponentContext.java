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

package danta.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An helper annotation to ease with the coding needed for
 * discovering properties in the OSGi Component Context and applying those
 * to our service.  To use, add the SCR property, add this property, and
 * make sure you call OSGiUtils.bindComponentProperties in the activate
 * method of the service.
 *
 * Important to note, this changes the implementation of the @Property annotation some.
 * You *MUST* set the name on @Property and the default value for the property is now
 * 100% supplied by the static variable itself (so don't make it final!!)
 * Example:
 *
 * @Property(name="my.property.name", label="My Property", description="Configure, configure, configure")
 * @BindToComponentContent(name="my.property.name")  //Unfortunate that you have to put the name twice
 * protected static String PROPERTY="A Sensible Default"
 * ....
 * @Activate
 * public void activate(ComponentContext context){
 *     OSGiUtils.bindComponentProperties(this, content.getProperties)
 * ....
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindToComponentContext {
    public String name();
}
