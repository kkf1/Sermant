/*
 * Copyright (C) 2022-2022 Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.huawei.dynamic.config;

import com.huawei.dynamic.config.sources.TestConfigSources;
import com.huawei.dynamic.config.sources.TestLowestConfigSources;

import com.huaweicloud.sermant.core.common.LoggerFactory;
import com.huaweicloud.sermant.core.operation.OperationManager;
import com.huaweicloud.sermant.core.operation.converter.api.YamlConverter;
import com.huaweicloud.sermant.core.plugin.config.PluginConfigManager;
import com.huaweicloud.sermant.core.service.dynamicconfig.common.DynamicConfigEvent;
import com.huaweicloud.sermant.implement.operation.converter.YamlConverterImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * configuration
 *
 * @author zhouss
 * @since 2022-04-16
 */
public class ConfigHolderTest {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String KEY = "test";
    private static final String CONTENT = "test: 1";

    private static final int TEST_CONFIG_SOURCES_SIZE = 3;

    private static DynamicConfigEvent event;

    private MockedStatic<PluginConfigManager> pluginConfigManagerMockedStatic;

    private MockedStatic<OperationManager> operationManagerMockedStatic;

    @Before
    public void setUp() {
        operationManagerMockedStatic = Mockito.mockStatic(OperationManager.class);
        operationManagerMockedStatic.when(() -> OperationManager.getOperation(YamlConverter.class)).thenReturn(new YamlConverterImpl());
        event = Mockito.mock(DynamicConfigEvent.class);
        DynamicConfiguration configuration = Mockito.mock(DynamicConfiguration.class);
        Mockito.when(event.getKey()).thenReturn(KEY);
        Mockito.when(event.getContent()).thenReturn(CONTENT);
        Mockito.when(configuration.getFirstRefreshDelayMs()).thenReturn(0L);
        pluginConfigManagerMockedStatic = Mockito.mockStatic(PluginConfigManager.class);
        pluginConfigManagerMockedStatic.when(() -> PluginConfigManager.getPluginConfig(DynamicConfiguration.class))
                .thenReturn(configuration);
        ConfigHolder.INSTANCE.getConfigSources().removeIf(configSource -> configSource.getClass() == TestConfigSources.class
                || configSource.getClass() == TestLowestConfigSources.class);
        ConfigHolder.INSTANCE.getConfigSources().add(new TestConfigSources());
        ConfigHolder.INSTANCE.getConfigSources().add(new TestLowestConfigSources());
        Collections.sort(ConfigHolder.INSTANCE.getConfigSources());
    }

    @After
    public void tearDown() {
        pluginConfigManagerMockedStatic.close();
        operationManagerMockedStatic.close();
        ConfigHolder.INSTANCE.getConfigSources().removeIf(configSource -> configSource.getClass() == TestConfigSources.class
                        || configSource.getClass() == TestLowestConfigSources.class);
    }

    @Test
    public void testResolveAndListener() throws InterruptedException {
        ConfigHolder.INSTANCE.addListener(event -> LOGGER.info("refresh success"));
        ConfigHolder.INSTANCE.resolve(event);
        // Because this is an asynchronous execution, it waits for the asynchronous execution to complete
        Thread.sleep(1000);
        final int test = (Integer) ConfigHolder.INSTANCE.getConfig(KEY);
        Assert.assertEquals(TestConfigSources.ORDER, test);
        final Set<String> configNames = ConfigHolder.INSTANCE.getConfigNames();
        Assert.assertTrue(configNames.contains(KEY));
    }

    @Test
    public void testConfigSourcesPriority() {
        final List<ConfigSource> configSources = ConfigHolder.INSTANCE.getConfigSources();
        if (configSources.size() == TEST_CONFIG_SOURCES_SIZE) {
            Assert.assertEquals(configSources.get(0).order(), TestConfigSources.ORDER);
            Assert.assertEquals(configSources.get(configSources.size() - 1).order(), TestLowestConfigSources.ORDER);
        }
    }
}
