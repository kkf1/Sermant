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

package com.huawei.flowcontrol.retry.cluster;

import com.huawei.flowcontrol.common.config.FlowControlConfig;

import com.huaweicloud.sermant.core.plugin.config.PluginConfigManager;

import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.Cluster;
import com.alibaba.dubbo.rpc.cluster.Directory;
import com.alibaba.dubbo.rpc.cluster.support.AbstractClusterInvoker;

/**
 * alibaba dubbo cluster
 *
 * @author zhouss
 * @since 2022-03-04
 */
public class AlibabaDubboCluster implements Cluster {
    @Override
    public <T> Invoker<T> join(Directory<T> directory) throws RpcException {
        final FlowControlConfig pluginConfig = PluginConfigManager.getPluginConfig(FlowControlConfig.class);
        AbstractClusterInvoker<T> delegate = null;
        if (pluginConfig.isUseOriginInvoker()) {
            Object curCluster = ClusterInvokerCreator.INSTANCE.buildInvoker();
            if (curCluster instanceof Cluster) {
                delegate = (AbstractClusterInvoker<T>) ((Cluster) curCluster).join(directory);
            }
        }
        if (delegate != null) {
            return new AlibabaDubboClusterInvoker<>(directory, delegate);
        }
        return new AlibabaDubboClusterInvoker<>(directory);
    }
}
