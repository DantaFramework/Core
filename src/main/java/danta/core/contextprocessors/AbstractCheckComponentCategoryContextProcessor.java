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

package danta.core.contextprocessors;

import danta.api.ContentModel;
import danta.api.ExecutionContext;
import danta.api.configuration.Configuration;
import danta.api.configuration.ConfigurationProvider;
import danta.api.configuration.Mode;
import danta.api.exceptions.AcceptsException;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static danta.Constants.ENGINE_RESOURCE;
import static danta.core.Constants.XK_COMPONENT_CATEGORY;

/**
 * Author:  joshuaoransky
 * Date:    5/3/16
 * Purpose:
 * Location:
 */
@Component(componentAbstract = true)
public abstract class AbstractCheckComponentCategoryContextProcessor<C extends ContentModel>
        extends AbstractContextProcessor<C> {

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.STATIC)
    protected ConfigurationProvider configurationProvider;

    @Override
    public boolean accepts(final ExecutionContext executionContext)
            throws AcceptsException {
        boolean accepts = false;
        try {
            Object resource = executionContext.get(ENGINE_RESOURCE);

            if(resource != null) {
                Configuration configuration = configurationProvider.getFor(resource);
                if (configuration != null ) {
                    Collection<String> compCategories = configuration.asStrings(XK_COMPONENT_CATEGORY, Mode.MERGE);

                    if (noneOf().isEmpty() || Collections.disjoint(compCategories, noneOf())) {
                        if (allOf().isEmpty() || compCategories.containsAll(allOf())) {
                            if (anyOf().isEmpty()) {
                                accepts = true;
                            } else {
                                if (!compCategories.isEmpty()) {
                                    for (String category : anyOf()) {
                                        if (compCategories.contains(category)) {
                                            accepts = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new AcceptsException(e);
        }
        return accepts;
    }

    public Set<String> anyOf() {
        return Collections.emptySet();
    }

    public Set<String> allOf() {
        return Collections.emptySet();
    }

    public Set<String> noneOf() {
        return Collections.emptySet();
    }
}
