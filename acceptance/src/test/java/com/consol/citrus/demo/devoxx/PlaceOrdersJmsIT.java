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
import com.consol.citrus.container.IteratingConditionExpression;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.functions.Functions;
import com.consol.citrus.dsl.testng.TestNGCitrusTestDesigner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.jms.endpoint.JmsEndpoint;
import com.consol.citrus.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

/**
 * @author Christoph Deppisch
 * @since 2.4
 */
@Test
public class PlaceOrdersJmsIT extends TestNGCitrusTestDesigner {

    @Autowired
    @Qualifier("bakeryOrderEndpoint")
    private JmsEndpoint bakeryOrderEndpoint;

    @Autowired
    @Qualifier("reportingClient")
    private HttpClient reportingClient;

    @CitrusTest
    public void placeChocolateCookieOrder() {
        variable("amount", Functions.randomNumber(2L));
        variable("orderId", Functions.randomNumber(10L));

        send(bakeryOrderEndpoint)
            .payload("<order>" +
                        "<type>chocolate</type>" +
                        "<id>${orderId}</id>" +
                        "<amount>${amount}</amount>" +
                    "</order>");

        repeatOnError(
            http().client(reportingClient)
                .get("/reporting/order")
                .queryParam("id", "${orderId}"),
            http().client(reportingClient)
                .response(HttpStatus.OK)
                .messageType(MessageType.PLAINTEXT)
                .payload("true")
        ).until(new IteratingConditionExpression() {
            @Override
            public boolean evaluate(int index, TestContext context) {
                return index > 3;
            }
        }).autoSleep(3000L);
    }

    @CitrusTest
    public void placeCaramelCookieOrder() {
        variable("amount", Functions.randomNumber(2L));
        variable("orderId", Functions.randomNumber(10L));

        send(bakeryOrderEndpoint)
                .payload("<order>" +
                            "<type>caramel</type>" +
                            "<id>${orderId}</id>" +
                            "<amount>${amount}</amount>" +
                        "</order>");

        repeatOnError(
            http().client(reportingClient)
                    .get("/reporting/order")
                    .queryParam("id", "${orderId}"),
            http().client(reportingClient)
                    .response(HttpStatus.OK)
                    .messageType(MessageType.PLAINTEXT)
                    .payload("true")
        ).until(new IteratingConditionExpression() {
            @Override
            public boolean evaluate(int index, TestContext context) {
                return index > 3;
            }
        }).autoSleep(3000L);
    }

    @CitrusTest
    public void placeBlueberryCookieOrder() {
        variable("amount", Functions.randomNumber(2L));
        variable("orderId", Functions.randomNumber(10L));

        send(bakeryOrderEndpoint)
                .payload("<order>" +
                            "<type>blueberry</type>" +
                            "<id>${orderId}</id>" +
                            "<amount>${amount}</amount>" +
                        "</order>");

        repeatOnError(
            http().client(reportingClient)
                    .get("/reporting/order")
                    .queryParam("id", "${orderId}"),
            http().client(reportingClient)
                    .response(HttpStatus.OK)
                    .messageType(MessageType.PLAINTEXT)
                    .payload("true")
        ).until(new IteratingConditionExpression() {
            @Override
            public boolean evaluate(int index, TestContext context) {
                return index > 3;
            }
        }).autoSleep(3000L);
    }
}
