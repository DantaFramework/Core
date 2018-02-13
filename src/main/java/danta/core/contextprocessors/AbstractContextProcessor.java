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
import danta.api.ContextProcessor;
import danta.api.ExecutionContext;
import danta.api.exceptions.AcceptsException;
import danta.api.exceptions.ProcessException;
import danta.core.util.OSGiUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static danta.Constants.LOW_PRIORITY;

/**
 * Abstract Context Processor
 *
 * @author      joshuaoransky
 * @version     1.0.0
 * @since       2013-10-30
 */
public abstract class AbstractContextProcessor<C extends ContentModel>
        implements ContextProcessor<C> {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected ComponentContext componentContext;

    @Activate
    protected void activate(ComponentContext componentContext) // TODO: Make this final
            throws Exception {
        this.componentContext = componentContext;
        OSGiUtils.activate(this, componentContext);
        doActivate(componentContext);
    }

    protected void doActivate(ComponentContext componentContext)
            throws Exception {
    }

    @Override
    public boolean accepts(final ExecutionContext executionContext)
            throws AcceptsException {
        return true;
    }

    @Override
    public int priority() {
        // By default it returns the lowest priority.
        return LOW_PRIORITY;
    }

    @Override
    public abstract void process(final ExecutionContext executionContext, final C contentModel)
            throws ProcessException;

}
