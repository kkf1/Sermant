/*
 * Copyright (C) 2022-2022 Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.huawei.registry.auto.sc.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon automatic configuration
 *
 * @author zhouss
 * @since 2022-05-19
 */
@Configuration
@ConditionalOnClass(name = "org.springframework.cloud.netflix.ribbon.SpringClientFactory")
@AutoConfigureAfter(name = "org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration")
@RibbonClients(defaultConfiguration = ServiceCombRibbonConfiguration.class)
public class ServiceCombRibbonAutoConfiguration {
}
