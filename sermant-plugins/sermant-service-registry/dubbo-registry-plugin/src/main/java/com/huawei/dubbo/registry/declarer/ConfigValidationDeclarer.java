/*
 * Copyright (C) 2022-2022 Huawei Technologies Co., Ltd. All rights reserved.
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

package com.huawei.dubbo.registry.declarer;

import com.huaweicloud.sermant.core.plugin.agent.declarer.InterceptDeclarer;

/**
 * Enhance the extractRegistryType method of the ConfigValidationUtils class
 *
 * @author provenceee
 * @since 2022-01-27
 */
public class ConfigValidationDeclarer extends AbstractDeclarer {
    private static final String[] ENHANCE_CLASS = {"org.apache.dubbo.config.utils.ConfigValidationUtils"};

    private static final String INTERCEPT_CLASS = "com.huawei.dubbo.registry.interceptor.ConfigValidationInterceptor";

    private static final String METHOD_NAME = "extractRegistryType";

    /**
     * Constructor
     */
    public ConfigValidationDeclarer() {
        super(ENHANCE_CLASS);
    }

    @Override
    public InterceptDeclarer[] getInterceptDeclarers(ClassLoader classLoader) {
        return new InterceptDeclarer[]{
            InterceptDeclarer.build(getStaticMethod(METHOD_NAME), INTERCEPT_CLASS)
        };
    }
}
