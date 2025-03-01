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

package com.huaweicloud.sermant.core.notification;

/**
 * Netty Notification Type
 *
 * @author zhp
 * @since 2023-06-16
 */
public enum NettyNotificationType implements NotificationType {
    /**
     * netty connected
     */
    CONNECTED("CONNECTED", "notification of netty connected"),

    /**
     * netty disconnected
     */
    DISCONNECTED("DISCONNECTED", "notification of netty disconnected");

    private final String name;

    private final String description;

    NettyNotificationType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
