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

import danta.api.DOMProcessor;
import danta.api.DOMProcessorEngine;
import danta.api.ExecutionContext;
import danta.core.util.ProcessorPriorityComparator;
import org.apache.felix.scr.annotations.*;
import org.jsoup.nodes.Document;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DOM Processing Engine
 *
 * @author      joshuaoransky
 * @version     1.0.0
 * @since       2016-08-08
 */
@Component
@Service
public class DOMProcessorEngineImpl
        implements DOMProcessorEngine {

    private static final Logger LOG = LoggerFactory.getLogger(this.getClass());
    static final ProcessorPriorityComparator PRIORITY_COMPARATOR = new ProcessorPriorityComparator();

    @Reference(cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE, bind = "bindDOMProcessor", unbind = "unbindDOMProcessor", referenceInterface = DOMProcessor.class, policy = ReferencePolicy.DYNAMIC)
    private List<DOMProcessor> domProcessors = new ArrayList<>();

    @Override
    public List<String> execute(ExecutionContext executionContext, Document document)
            throws Exception {
        List<String> currentDomProcessorChain = new ArrayList<>();
        for (DOMProcessor processor : domProcessors) {
            processor.process(executionContext, document);
            currentDomProcessorChain.add(processor.getClass().getName());
        }
        return currentDomProcessorChain;
    }

    @Activate
    protected void activate(BundleContext bundleContext)
            throws Exception {
    }

    private void bindDOMProcessor(DOMProcessor domProcessor) {
        domProcessors.add(domProcessor);
        Collections.sort(domProcessors, PRIORITY_COMPARATOR);
    }

    private void unbindDOMProcessor(DOMProcessor domProcessor) {
        domProcessors.remove(domProcessor);
    }

}
