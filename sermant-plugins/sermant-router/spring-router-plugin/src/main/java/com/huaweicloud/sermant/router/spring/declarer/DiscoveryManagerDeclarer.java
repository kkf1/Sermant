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

package com.huaweicloud.sermant.router.spring.declarer;

import com.huaweicloud.sermant.core.plugin.agent.matcher.ClassMatcher;

/**
 * Register plugin intercept point
 *
 * @author provenceee
 * @since 2022-10-13
 */
public class DiscoveryManagerDeclarer extends AbstractDeclarer {
    private static final String ENHANCE_CLASS
            = "com.huawei.discovery.service.lb.DiscoveryManager";

    private static final String INTERCEPT_CLASS
            = "com.huaweicloud.sermant.router.spring.interceptor.DiscoveryManagerInterceptor";

    private static final String METHOD_NAME = "registry";

    /**
     * Constructor
     */
    public DiscoveryManagerDeclarer() {
        super(ENHANCE_CLASS, INTERCEPT_CLASS, METHOD_NAME);
    }

    @Override
    public ClassMatcher getClassMatcher() {
        return ClassMatcher.nameEquals(ENHANCE_CLASS);
    }
}
