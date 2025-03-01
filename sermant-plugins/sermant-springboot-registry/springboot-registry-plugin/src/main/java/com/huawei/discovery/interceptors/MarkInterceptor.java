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

package com.huawei.discovery.interceptors;

import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.plugin.agent.interceptor.Interceptor;

/**
 * Used to mark interceptor calls, and is only used to call {@link com.huawei.discovery.service.InvokerService}
 * interceptors
 *
 * @author zhouss
 * @since 2022-10-10
 */
public abstract class MarkInterceptor implements Interceptor {
    /**
     * Shared by multiple interceptors
     */
    private static final ThreadLocal<Boolean> MARK = new ThreadLocal<>();

    @Override
    public ExecuteContext before(ExecuteContext context) throws Exception {
        if (MARK.get() != null) {
            return context;
        }
        MARK.set(Boolean.TRUE);
        try {
            ready();
            return doBefore(context);
        } finally {
            MARK.remove();
        }
    }

    /**
     * Invocation logic
     *
     * @param context Context
     * @return Context
     * @throws Exception Execute an exception throw
     */
    protected abstract ExecuteContext doBefore(ExecuteContext context) throws Exception;

    /**
     * Preparation before calling
     */
    protected abstract void ready();
}
