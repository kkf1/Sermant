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

package com.huawei.discovery.service;

import com.huawei.discovery.entity.ServiceInstance;

import com.huaweicloud.sermant.core.plugin.service.PluginService;

import java.util.Optional;

/**
 * Load balancing
 *
 * @author zhouss
 * @since 2022-09-26
 */
public interface LbService extends PluginService {
    /**
     * Select an instance
     *
     * @param serviceName 服务名
     * @return ServiceInstance
     */
    Optional<ServiceInstance> choose(String serviceName);
}
