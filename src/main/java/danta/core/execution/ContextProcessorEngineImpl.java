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
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Context Processor Implementor
 *
 * @author      joshuaoransky
 * @version     1.0.0
 * @since       2016-07-14
 */
@Component
@Service
public class ContextProcessorEngineImpl
        implements ContextProcessorEngine {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Reference(cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, bind = "bindProcessor", unbind = "unbindProcessor", referenceInterface = ContextProcessor.class, policy = ReferencePolicy.DYNAMIC)
    private SortedMap<Integer, List<ContextProcessor>> contextProcessors = new TreeMap<>(Comparator.reverseOrder());

    @Override
    public List<String> execute(ExecutionContext executionContext, ContentModel contentModel)
            throws AcceptsException, ProcessException {
        List<String> currentProcessorChain = Collections.synchronizedList(new ArrayList<>());
        for (List<ContextProcessor> processorsSamePriority : contextProcessors.values()) {
            processorsSamePriority.parallelStream().forEach(processor -> {
                try {
                    if (processor.accepts(executionContext)) {
                        processor.process(executionContext, contentModel);
                        currentProcessorChain.add(processor.getClass().getName());
                    }
                } catch (Exception e) {
                    LOG.error("Exception in parallel stream execution", e);
                    throw new RuntimeException(e);
                }
            });
        }
        return currentProcessorChain;
    }

    public void bindProcessor(ContextProcessor processor) {
        List<ContextProcessor> contextProcessorsSamePriority = this.contextProcessors.get(processor.priority());
        if (contextProcessorsSamePriority == null) {
            contextProcessorsSamePriority = new ArrayList<>();
        }
        contextProcessorsSamePriority.add(processor);
        contextProcessors.put(processor.priority(), contextProcessorsSamePriority);
    }

    public void unbindProcessor(ContextProcessor processor) {
        List<ContextProcessor> contextProcessorsSamePriority = this.contextProcessors.get(processor.priority());
        contextProcessorsSamePriority.remove(processor);
    }

}