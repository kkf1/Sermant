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

package com.huawei.dubbo.registry;

import com.huawei.dubbo.registry.alibaba.ServiceCenterRegistry;
import com.huawei.dubbo.registry.alibaba.ServiceCenterRegistryFactory;
import com.huawei.dubbo.registry.service.RegistryService;

import com.huaweicloud.sermant.core.service.BaseService;
import com.huaweicloud.sermant.core.service.ServiceManager;
import com.huaweicloud.sermant.core.utils.KeyGenerateUtils;

import com.alibaba.dubbo.common.URL;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Test RegistryFactory
 *
 * @author provenceee
 * @since 2022-02-16
 */
public class RegistryFactoryTest {
    /**
     * Constructor
     */
    public RegistryFactoryTest() throws NoSuchFieldException, IllegalAccessException {
        Field field = ServiceManager.class.getDeclaredField("SERVICES");
        field.setAccessible(true);
        Map<String, BaseService> map = (Map<String, BaseService>) field.get(null);
        map.put(KeyGenerateUtils.generateClassKeyWithClassLoader(RegistryService.class), new RegistryService() {
            @Override
            public void startRegistration() {
            }

            @Override
            public void doSubscribe(Object url, Object notifyListener) {
            }

            @Override
            public void shutdown() {
            }

            @Override
            public void addRegistryUrls(Object url) {
            }

            @Override
            public void notifyGovernanceUrl() {

            }
        });
    }

    /**
     * Test Alibaba RegistryFactory
     *
     * @throws NoSuchMethodException Can't find method
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     */
    @Test
    public void testAlibabaServiceCenterRegistryFactory()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ServiceCenterRegistryFactory registryFactory = new ServiceCenterRegistryFactory();
        Method method = registryFactory.getClass().getDeclaredMethod("createRegistry", URL.class);
        method.setAccessible(true);
        Assert.assertEquals(ServiceCenterRegistry.class, method.invoke(registryFactory,
            URL.valueOf(TestConstant.SC_ADDRESS)).getClass());
    }

    /**
     * Test Apache RegistryFactory
     *
     * @throws NoSuchMethodException Can't find method
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     */
    @Test
    public void testApacheServiceCenterRegistryFactory()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        com.huawei.dubbo.registry.apache.ServiceCenterRegistryFactory registryFactory =
            new com.huawei.dubbo.registry.apache.ServiceCenterRegistryFactory();
        Method method = registryFactory.getClass()
            .getDeclaredMethod("createRegistry", org.apache.dubbo.common.URL.class);
        method.setAccessible(true);
        Assert.assertEquals(com.huawei.dubbo.registry.apache.ServiceCenterRegistry.class, method.invoke(registryFactory,
            org.apache.dubbo.common.URL.valueOf(TestConstant.SC_ADDRESS)).getClass());
    }
}