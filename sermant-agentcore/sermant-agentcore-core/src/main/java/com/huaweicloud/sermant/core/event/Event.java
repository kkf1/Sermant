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

package com.huaweicloud.sermant.core.event;

import com.huaweicloud.sermant.core.common.BootArgsIndexer;

/**
 * Event definition
 *
 * @author luanwenfei
 * @since 2023-03-02
 */
public class Event {
    private String metaHash;

    private long time;

    private String scope;

    private EventLevel eventLevel;

    private EventType eventType;

    private EventInfo eventInfo;

    private LogInfo logInfo;

    /**
     * Constructor for event
     *
     * @param scope scope
     * @param eventLevel level
     * @param eventType type
     * @param eventInfo information
     */
    public Event(String scope, EventLevel eventLevel, EventType eventType, EventInfo eventInfo) {
        // use InstanceId provisionally
        this.metaHash = BootArgsIndexer.getInstanceId();
        this.time = System.currentTimeMillis();
        this.scope = scope;
        this.eventLevel = eventLevel;
        this.eventType = eventType;
        this.eventInfo = eventInfo;
    }

    /**
     * Constructor for the log event (log message cannot get the print log scope, can be accurate to the method of log
     * occurrence)
     *
     * @param eventLevel level
     * @param eventType type
     * @param logInfo information
     */
    public Event(EventLevel eventLevel, EventType eventType, LogInfo logInfo) {
        this.metaHash = BootArgsIndexer.getInstanceId();
        this.time = System.currentTimeMillis();
        this.eventLevel = eventLevel;
        this.eventType = eventType;
        this.logInfo = logInfo;
    }

    public String getMetaHash() {
        return metaHash;
    }

    public void setMetaHash(String metaHash) {
        this.metaHash = metaHash;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public EventLevel getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(EventLevel eventLevel) {
        this.eventLevel = eventLevel;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventInfo getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(EventInfo eventInfo) {
        this.eventInfo = eventInfo;
    }

    public LogInfo getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(LogInfo logInfo) {
        this.logInfo = logInfo;
    }

    @Override
    public String toString() {
        return "Event{" + "metaHash='" + metaHash + '\'' + ", time=" + time + ", scope='" + scope + '\''
                + ", eventLevel=" + eventLevel + ", eventType=" + eventType + ", eventInfo=" + eventInfo + ", logInfo="
                + logInfo + '}';
    }
}
