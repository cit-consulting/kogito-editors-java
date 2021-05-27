/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.bpmn.client.dataproviders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.jboss.errai.ui.client.local.spi.TranslationService;
import org.kie.workbench.common.forms.dynamic.model.config.SelectorData;
import org.kie.workbench.common.forms.dynamic.model.config.SelectorDataProvider;
import org.kie.workbench.common.forms.dynamic.service.shared.FormRenderingContext;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.CashType;
import org.kie.workbench.common.stunner.core.util.SafeComparator;

@Dependent
public class CacheProvider implements SelectorDataProvider {

    private enum CACHE_TYPE {

        DEFAULT(CashType.DEFAULT, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.DEFAULT"),
        NONE(CashType.NONE, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.NONE"),
        CACHE_ONLY(CashType.CACHE_ONLY, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.CACHE_ONLY"),
        FORCE_CACHE(CashType.FORCE_CACHE, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.FORCE_CACHE");

        private final String value;

        private final String i18nKey;

        CACHE_TYPE(String value, String i18nKey) {
            this.value = value;
            this.i18nKey = i18nKey;
        }

        public String value() {
            return value;
        }

        public String i18nKey() {
            return i18nKey;
        }
    }

    private static Map<Object, Integer> valuePosition;

    private final TranslationService translationService;

    @Inject
    public CacheProvider(final TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostConstruct
    protected void init() {
        valuePosition = new HashMap<>();
        valuePosition.put(CACHE_TYPE.DEFAULT.value(), 0);
        valuePosition.put(CACHE_TYPE.NONE.value(), 1);
        valuePosition.put(CACHE_TYPE.CACHE_ONLY.value(), 2);
        valuePosition.put(CACHE_TYPE.FORCE_CACHE.value(), 3);
    }

    private SafeComparator<Object> getComparator() {
        return SafeComparator.TO_STRING_REVERSE_COMPARATOR;
    }

    @Override
    public String getProviderName() {
        return getClass().getSimpleName();
    }

    @Override
    @SuppressWarnings("unchecked")
    public SelectorData getSelectorData(final FormRenderingContext context) {
        Map<Object, String> values = new TreeMap<>(SafeComparator.of(this::getComparator));
        Arrays.stream(CACHE_TYPE.values())
                .forEach(ruleLanguage -> values.put(ruleLanguage.value(),
                        translationService.getTranslation(ruleLanguage.i18nKey())));

        return new SelectorData(values, CACHE_TYPE.DEFAULT.value());
    }
}