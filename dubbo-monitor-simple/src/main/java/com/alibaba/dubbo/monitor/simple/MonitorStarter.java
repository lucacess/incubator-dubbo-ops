/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.monitor.simple;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.container.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class MonitorStarter {
    private static Logger logger= LoggerFactory.getLogger(MonitorStarter.class);
    public static void main(String[] args) throws IOException {
        System.setProperty(Constants.DUBBO_PROPERTIES_KEY, "conf/dubbo.properties");
        Main.main(args);

    }

    @Deprecated
    private static void setConfig() throws IOException {
        //读取公共配置
        Properties p = new Properties();
        p.load(MonitorStarter.class.getClassLoader().getResourceAsStream("conf/dubbo.properties"));
        String  profile = p.getProperty("application.profiles.active");

        Optional.ofNullable(profile).orElseThrow(()->new RuntimeException("profile 没有指定"));

        String propPath = "conf/dubbo"+"-"+profile+".properties";

        logger.info("当前 prifile:{},使用的 properties:{}",profile,propPath);

        System.setProperty("dubbo.application.logger","slf4j");
        System.setProperty(
                Constants.DUBBO_PROPERTIES_KEY, propPath);
    }
}
