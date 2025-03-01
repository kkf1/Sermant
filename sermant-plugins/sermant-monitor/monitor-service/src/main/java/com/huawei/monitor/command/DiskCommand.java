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

package com.huawei.monitor.command;

import com.huawei.monitor.common.Constants;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * hard disk command
 *
 * @author zhp
 * @version 1.0.0
 * @since 2022-08-02
 */
public class DiskCommand extends CommonMonitorCommand<List<DiskCommand.DiskStats>> {

    private static final String COMMAND = "cat /proc/diskstats";

    private static final int DEVICE_NAME_INDEX = 3;
    private static final int SECTORS_READ_INDEX = 6;
    private static final int SECTORS_WRITTEN_INDEX = 10;
    private static final int IO_SPENT_MILLIS_INDEX = 13;

    @Override
    public String getCommand() {
        return COMMAND;
    }

    /**
     * Refactor the pan-PaaS class: com.huawei.sermant.plugin.collection.util.DiskParser parse method
     *
     * @param inputStream external process output stream
     * @return the result after analysis
     */
    @Override
    public List<DiskStats> parseResult(InputStream inputStream) {
        final List<String> lines = readLines(inputStream);
        final List<DiskStats> diskStats = new ArrayList<>(lines.size());
        for (String line : lines) {
            String[] diskStatsText = line.split(Constants.REGEX_MULTI_SPACES);
            String deviceName = diskStatsText[DEVICE_NAME_INDEX];
            long sectorsRead = Long.parseLong(diskStatsText[SECTORS_READ_INDEX]);
            long sectorWritten = Long.parseLong(diskStatsText[SECTORS_WRITTEN_INDEX]);
            long ioSpentMillis = Long.parseLong(diskStatsText[IO_SPENT_MILLIS_INDEX]);
            diskStats.add(new DiskStats(deviceName, sectorsRead, sectorWritten, ioSpentMillis));
        }
        return diskStats;
    }

    /**
     * disk information
     *
     * @since 2022-08-02
     */
    public static final class DiskStats {
        /**
         * 3. Device name
         */
        private final String deviceName;

        /**
         * 6. Number of sectors read. This is the total number of sectors read successfully.
         */
        private final long sectorsRead;

        /**
         * 10. Number of sectors written. This is the total number of sectors written successfully.
         */
        private final long sectorsWritten;

        /**
         * 13. Number of milliseconds spent doing I/Os. This field is increased so long as field 9 is nonzero.
         */
        private final long ioSpentMillis;

        /**
         * constructionMethod
         *
         * @param deviceName device name
         * @param sectorsRead read disk size
         * @param sectorsWritten write disk size
         * @param ioSpentMillis time consuming
         */
        public DiskStats(String deviceName, long sectorsRead, long sectorsWritten, long ioSpentMillis) {
            this.deviceName = deviceName;
            this.sectorsRead = sectorsRead;
            this.sectorsWritten = sectorsWritten;
            this.ioSpentMillis = ioSpentMillis;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public long getSectorsRead() {
            return sectorsRead;
        }

        public long getSectorsWritten() {
            return sectorsWritten;
        }

        public long getIoSpentMillis() {
            return ioSpentMillis;
        }
    }
}
