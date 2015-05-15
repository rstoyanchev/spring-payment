package org.springframework.payment.test.gateway;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.payment.test.gateway.StubGatewayResponse;

public class StubGatewayResponseTests {

	@Test
	public void testConstructorWithPropertiesFile() throws Exception {
		StubGatewayResponse response = new StubGatewayResponse("ccAuthService_run=true",
				"classpath:org/springframework/payment/test/gateway/response.properties");
		assertTrue(response.getGatewayResponse().getResponseFields().size() > 0);
	}

}
