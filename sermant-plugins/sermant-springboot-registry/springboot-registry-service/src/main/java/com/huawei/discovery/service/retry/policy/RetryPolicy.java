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

package com.huawei.discovery.service.retry.policy;

import com.huawei.discovery.entity.ServiceInstance;

import java.util.Optional;

/**
 * Retry policy
 *
 * @author zhouss
 * @since 2022-09-30
 */
public interface RetryPolicy {
    /**
     * Select a service and select the retry logic based on the retry policy
     *
     * @param serviceName Service name
     * @param policyContext Retry the context
     * @return Selected instances
     */
    Optional<ServiceInstance> select(String serviceName, PolicyContext policyContext);

    /**
     * The name of the retry policy
     *
     * @return retryPolicy
     */
    String name();
}
