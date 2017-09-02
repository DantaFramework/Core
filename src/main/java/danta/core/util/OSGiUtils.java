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

import danta.core.annotations.BindToComponentContext;
import danta.core.annotations.OptionalComponent;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

import static java.lang.String.format;
import static danta.core.Constants.ERROR;

/**
 * Helper utils for osgi
 *
 * @author      joshuaoransky
 * @version     1.0.0
 * @since       2016-04-20
 */
public class OSGiUtils {
    private static final Logger log = LoggerFactory.getLogger(OSGiUtils.class);

    /**
     * To be called from by Service Activate methods.  This will automatically
     * bind any applicable properties in the ComponentContext to any field
     * that has a @BindToComponentContext annotation.
     */
    private static <ObjType> void bindComponentProperties(ObjType obj, Dictionary properties) {
        if (obj != null && properties != null) {
            Map<String, Field> fieldsToBind = gatherBindToComponentFields(obj);
            for (String propName : fieldsToBind.keySet()) {
                Object value = properties.get(propName);
                if (value != null) {
                    bindComponentProperty(obj, fieldsToBind.get(propName).getName(), value);
                }
            }
        }
    }

    private static <ObjType> void bindComponentProperty(ObjType obj, String propertyName, Object value) {
        try {
            Field field = obj.getClass().getDeclaredField(propertyName);

            log.debug("Binding {} as {} to {}", propertyName, value.toString(), obj.getClass().getName());

            field.setAccessible(true);

            // For properties that are single element arrays, OSGI will give the element
            // itself as the value. Weird, I know. Here, we're checking if field is an
            // array and value is not, and then use reflection to create such array. For
            // more info, see http://download.oracle.com/javase/tutorial/reflect/special/index.html
            if (field.getType().isArray() && !value.getClass().isArray()) {
                log.debug("Converting value {} to a single element array", value);

                // create a single-element array
                Object array = Array.newInstance(field.getType().getComponentType(), 1);
                // set 0th element
                Array.set(array, 0, value);

                value = array;
            }

            field.set(obj, value);

        } catch (Exception e) {
            log.warn("Could not bind compontent property: " + propertyName, e);
        }
    }

    private static <ObjType> Map<String, Field> gatherBindToComponentFields(ObjType obj) {
        HashMap<String, Field> fieldsToBind = new HashMap<String, Field>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(BindToComponentContext.class)) {
                String propertyName = field.getAnnotation(BindToComponentContext.class).name();
                if (propertyName != null) {
                    fieldsToBind.put(propertyName, field);
                }
            }
        }
        return fieldsToBind;
    }

    /**
     * Disable component if property "enabled" is set to false
     *
     * @param ctx component context
     */
    private static void disableIfNeeded(Object obj, ComponentContext ctx) {
        OptionalComponent oc = obj.getClass().getAnnotation(OptionalComponent.class);

        if (oc == null) {
            return;
        }

        boolean enabled = PropertiesUtil.toBoolean(ctx.getProperties().get(oc.propertyName()), true);

        if (!enabled) {
            String pid = (String) ctx.getProperties().get(Constants.SERVICE_PID);
            log.info("disabling component {}", pid);

            // at this point this is the only way to reliably disable a component
            // it's going to show up as "unsatisfied" in Felix console.
            throw new ComponentException(format("Component %s is intentionally disabled", pid));
        }
    }


    /**
     * Syntactic sugar - all activation-related functions in one.
     */
    public static void activate(Object obj, ComponentContext ctx) {
        bindComponentProperties(obj, ctx.getProperties());
        disableIfNeeded(obj, ctx);
    }

    public static <E> List<E> getServices(BundleContext bundleContext, Class<E> serviceType, String filter)
            throws InvalidSyntaxException {
        List<E> services = null;
        final ServiceReference[] refs = bundleContext.getServiceReferences(serviceType.getName(), filter);
        if (refs != null) {
            services = new ArrayList<>(refs.length);
            if (refs != null && refs.length > 0) {
                for (ServiceReference ref : refs) {
                    E service = (E) bundleContext.getService(ref);
                    services.add(service);
                }
            }
        } else
            services = Collections.unmodifiableList(new ArrayList<E>());
        return services;
    }

    public static <E> List<E> getServices(BundleContext bundleContext, Class<E> serviceType) {
        List<E> services;
        try {
            services = getServices(bundleContext, serviceType, null);
        } catch (Exception e) {
            log.error(ERROR, e);
            services = new ArrayList(0);
        }
        return Collections.unmodifiableList(services);
    }

    public static <E> E getService(BundleContext bundleContext, Class<E> serviceType, String filter) {
        List<E> services;
        try {
            services = (List) getServices(bundleContext, serviceType, filter);
        } catch (InvalidSyntaxException e) {
            log.error(ERROR, e);
            services = new ArrayList<>();
        }
        return (services.size() > 0) ? services.get(0) : null;
    }

    public static <E> E getService(BundleContext bundleContext, Class<E> serviceType) {
        return getService(bundleContext, serviceType, null);
    }

}
