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
import org.kie.workbench.common.stunner.bpmn.definition.property.task.IntegrationType;
import org.kie.workbench.common.stunner.core.util.SafeComparator;

@Dependent
public class IntegrationProvider implements SelectorDataProvider {

    private enum INTEGRATION_TYPE {
        AMAZON(IntegrationType.AMAZON, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.AMAZON"),
        DATA_BASE_REQUEST(IntegrationType.DATA_BASE_REQUEST, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.DATA_BASE_REQUEST"),
        SCORING(IntegrationType.SCORING, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.SCORING"),
        DRAGON_PAY(IntegrationType.DRAGON_PAY, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.DRAGON_PAY"),
        SEON(IntegrationType.SEON, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.SEON"),
        ADVANCE_AI(IntegrationType.ADVANCE_AI, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.ADVANCE_AI"),
        ADVANCE_AI_EXTENDED(IntegrationType.ADVANCE_AI_EXTENDED, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.ADVANCE_AI_EXTENDED"),
        TRUSTING_SOCIAL(IntegrationType.TRUSTING_SOCIAL, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.TRUSTING_SOCIAL"),
        SQL_ADAPTER(IntegrationType.SQL_ADAPTER, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.SQL_ADAPTER"),
        FIN_SCORE(IntegrationType.FIN_SCORE, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.FIN_SCORE"),
        TELE_SIGN(IntegrationType.TELE_SIGN, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.TELE_SIGN"),
        AMAZON_PHOTO_VALIDATION(IntegrationType.AMAZON_PHOTO_VALIDATION, "org.kie.workbench.common.stunner.bpmn.client.dataproviders.CacheProvider.AMAZON_PHOTO_VALIDATION");

        private final String value;

        private final String i18nKey;

        INTEGRATION_TYPE(String value, String i18nKey) {
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
    public IntegrationProvider(final TranslationService translationService) {
        this.translationService = translationService;
    }

    @PostConstruct
    protected void init() {
        valuePosition = new HashMap<>();
        valuePosition.put(INTEGRATION_TYPE.ADVANCE_AI.value(), 0);
        valuePosition.put(INTEGRATION_TYPE.ADVANCE_AI_EXTENDED.value(), 1);
        valuePosition.put(INTEGRATION_TYPE.AMAZON.value(), 2);
        valuePosition.put(INTEGRATION_TYPE.DATA_BASE_REQUEST.value(), 3);
        valuePosition.put(INTEGRATION_TYPE.DRAGON_PAY.value(), 4);
        valuePosition.put(INTEGRATION_TYPE.SCORING.value(), 5);
        valuePosition.put(INTEGRATION_TYPE.SEON.value(), 6);
        valuePosition.put(INTEGRATION_TYPE.SQL_ADAPTER.value(), 7);
        valuePosition.put(INTEGRATION_TYPE.TRUSTING_SOCIAL.value(), 8);
        valuePosition.put(INTEGRATION_TYPE.FIN_SCORE.value(), 9);
        valuePosition.put(INTEGRATION_TYPE.TELE_SIGN.value(), 10);
        valuePosition.put(INTEGRATION_TYPE.AMAZON_PHOTO_VALIDATION.value(), 11);
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
        Arrays.stream(INTEGRATION_TYPE.values())
                .forEach(ruleLanguage -> values.put(ruleLanguage.value(),
                        translationService.getTranslation(ruleLanguage.i18nKey())));

        return new SelectorData(values, INTEGRATION_TYPE.SCORING.value());
    }
}