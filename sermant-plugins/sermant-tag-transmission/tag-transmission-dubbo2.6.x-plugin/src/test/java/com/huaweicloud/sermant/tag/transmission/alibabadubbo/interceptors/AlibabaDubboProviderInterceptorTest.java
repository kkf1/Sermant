/*
 * Copyright (C) 2023-2023 Huawei Technologies Co., Ltd. All rights reserved.
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

package com.huaweicloud.sermant.tag.transmission.alibabadubbo.interceptors;

import com.huaweicloud.sermant.core.plugin.agent.entity.ExecuteContext;
import com.huaweicloud.sermant.core.utils.tag.TrafficTag;
import com.huaweicloud.sermant.core.utils.tag.TrafficUtils;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcInvocation;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboInvoker;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * AlibabaDubboProviderInterceptorTest
 *
 * @author daizhenyu
 * @since 2023-08-29
 **/
public class AlibabaDubboProviderInterceptorTest extends AbstractRpcInterceptorTest {
    private final AlibabaDubboProviderInterceptor interceptor = new AlibabaDubboProviderInterceptor();

    public AlibabaDubboProviderInterceptorTest() {
    }

    @Override
    public void doBefore(TrafficTag trafficTag) {
    }

    @Test
    public void testAlibabaDubboProvider() {
        // defineParameter
        ExecuteContext context;
        ExecuteContext returnContext;
        Map<String, String> attachments;
        Map<String, List<String>> expectTag;

        // invoker is consumer
        context = buildContext(new RpcInvocation(), new HashMap<>(), "consumer");
        interceptor.before(context);
        Assert.assertNull(TrafficUtils.getTrafficTag());

        // Subsequent tests are all on the provider side. Invocation is null
        context = buildContext(null, new HashMap<>(), "provider");
        interceptor.before(context);
        Assert.assertNull(TrafficUtils.getTrafficTag());

        // The traffic tag transmission switch is turned off
        tagTransmissionConfig.setEnabled(false);
        context = buildContext(new RpcInvocation(), new HashMap<>(), "provider");
        interceptor.before(context);
        Assert.assertNull(TrafficUtils.getTrafficTag());
        tagTransmissionConfig.setEnabled(true);

        // The attachments of the Invocation object contain all keys
        attachments = new HashMap<>();
        attachments.put("id", "001");
        attachments.put("name", "test001");
        context = buildContext(new RpcInvocation(), attachments, "provider");
        returnContext = interceptor.before(context);
        expectTag = buildExpectTrafficTag("id", "name");
        Assert.assertEquals(TrafficUtils.getTrafficTag().getTag(), expectTag);
        interceptor.after(returnContext);

        // The attachments of the Invocation object contain partial traffic labels
        attachments = new HashMap<>();
        attachments.put("id", "001");
        context = buildContext(new RpcInvocation(), attachments, "provider");
        returnContext = interceptor.before(context);
        expectTag = buildExpectTrafficTag("id");
        Assert.assertEquals(TrafficUtils.getTrafficTag().getTag(), expectTag);
        interceptor.after(returnContext);
    }

    private ExecuteContext buildContext(RpcInvocation rpcInvocation, Map<String, String> headers, String side) {
        URL url = new URL("http", "127.0.0.1", 8080);
        url = url.addParameter("side", side);
        Invoker invoker = new DubboInvoker<>(String.class, url, null);
        if (rpcInvocation != null) {
            rpcInvocation.setAttachments(headers);
        }
        Object[] arguments = new Object[]{invoker, rpcInvocation};
        return ExecuteContext.forMemberMethod(new Object(), null, arguments, null, null);
    }
}