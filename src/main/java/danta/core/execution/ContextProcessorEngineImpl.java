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

package danta.core.execution;

import danta.api.ContentModel;
import danta.api.ContextProcessor;
import danta.api.ContextProcessorEngine;
import danta.api.ExecutionContext;
import danta.api.exceptions.AcceptsException;
import danta.api.exceptions.ProcessException;
import danta.core.util.ContextProcessorPriorityComparator;
import org.apache.felix.scr.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static danta.Constants.*;

/**
 * Author:  joshuaoransky
 * Date:    7/14/16
 * Purpose:
 * Location:
 */
@Component
@Service
public class ContextProcessorEngineImpl
        implements ContextProcessorEngine {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    static final ContextProcessorPriorityComparator PRIORITY_ORDER = new ContextProcessorPriorityComparator();

    @Reference(cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, bind = "bindProcessor", unbind = "unbindProcessor", referenceInterface = ContextProcessor.class, policy = ReferencePolicy.DYNAMIC)
    private List<ContextProcessor> contextProcessors = new ArrayList<>();

    @Override
    public List<String> execute(ExecutionContext executionContext, ContentModel contentModel)
            throws AcceptsException, ProcessException {
        List<String> currentProcessorChain = new ArrayList<>();
        for (ContextProcessor processor : contextProcessors) {
            if (processor.accepts(executionContext)) {
                processor.process(executionContext, contentModel);
                currentProcessorChain.add(processor.getClass().getName());
            }
        }
        return currentProcessorChain;
    }

    public void bindProcessor(ContextProcessor processor) {
        contextProcessors.add(processor);
        Collections.sort(contextProcessors, PRIORITY_ORDER);

    }

    public void unbindProcessor(ContextProcessor processor) {
        contextProcessors.remove(processor);
    }
}
