/*
 * Copyright 2006-2015 the original author or authors.
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

package com.consol.citrus.demo.devoxx;

import org.apache.camel.spring.Main;

/**
 * @author Christoph Deppisch
 * @since 2.4
 */
public class WorkerApp extends Main {

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            System.setProperty("FACTORY_TYPE", args[0]);
        }

        if (args.length > 1) {
            System.setProperty("FACTORY_COSTS", args[1]);
        }

        WorkerApp application = new WorkerApp();
        application.run();
    }
}
