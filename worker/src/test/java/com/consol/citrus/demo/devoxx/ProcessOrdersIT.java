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

import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.dsl.functions.Functions;
import com.consol.citrus.dsl.testng.TestNGCitrusTestDesigner;
import com.consol.citrus.http.server.HttpServer;
import com.consol.citrus.jms.endpoint.JmsEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

/**
 * @author Christoph Deppisch
 * @since 2.4
 */
@Test
public class ProcessOrdersIT extends TestNGCitrusTestDesigner {

    @Autowired
    @Qualifier("factoryOrderEndpoint")
    private JmsEndpoint factoryOrderEndpoint;

    @Autowired
    @Qualifier("reportingServer")
    private HttpServer reportingServer;

    @CitrusTest
    public void processOrderWithReporting() {
        variable("orderId", Functions.randomNumber(10L));
        send(factoryOrderEndpoint)
            .payload("<order>" +
                        "<type>chocolate</type>" +
                        "<id>${orderId}</id>" +
                        "<amount>1</amount>" +
                    "</order>");

        http().server(reportingServer)
        	.put("/report/services/reporting")
            .header("id", "${orderId}")
            .header("name", "chocolate")
            .header("amount", "1")
            .timeout(10000L);
        
        http().server(reportingServer)
            .respond(HttpStatus.OK);
    }
}
