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

package com.huawei.discovery.declarers.httpconnection;

import com.huawei.discovery.declarers.BaseDeclarer;
import com.huawei.discovery.interceptors.httpconnection.HttpUrlConnectionConnectInterceptor;
import com.huawei.discovery.interceptors.httpconnection.HttpUrlConnectionDisconnectCodeInterceptor;

import com.huaweicloud.sermant.core.plugin.agent.declarer.InterceptDeclarer;
import com.huaweicloud.sermant.core.plugin.agent.matcher.ClassMatcher;
import com.huaweicloud.sermant.core.plugin.agent.matcher.MethodMatcher;

/**
 * Blocking HttpUrlConnection connections
 *
 * @author zhouss
 * @since 2022-10-20
 */
public class HttpUrlConnectionConnectDeclarer extends BaseDeclarer {
    private static final String INTERCEPT_CLASS = HttpUrlConnectionConnectInterceptor.class.getCanonicalName();

    private static final String INTERCEPT_DISCONNECT_CLASS =
            HttpUrlConnectionDisconnectCodeInterceptor.class.getCanonicalName();

    private static final String METHOD_NAME = "connect";

    private static final String METHOD_DISCONNECT_NAME = "disconnect";

    @Override
    public ClassMatcher getClassMatcher() {
        return ClassMatcher.isExtendedFrom("java.net.HttpURLConnection");
    }

    @Override
    public InterceptDeclarer[] getInterceptDeclarers(ClassLoader classLoader) {
        return new InterceptDeclarer[]{
                InterceptDeclarer.build(MethodMatcher.nameEquals(METHOD_NAME), INTERCEPT_CLASS),
                InterceptDeclarer.build(MethodMatcher.nameEquals(METHOD_DISCONNECT_NAME), INTERCEPT_DISCONNECT_CLASS)
        };
    }
}
