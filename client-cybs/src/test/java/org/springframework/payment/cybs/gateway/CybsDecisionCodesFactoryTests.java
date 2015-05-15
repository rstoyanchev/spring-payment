package org.springframework.payment.cybs.gateway;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.payment.gateway.GatewayDecisionCodes;

public class CybsDecisionCodesFactoryTests {

	@Test
	public void testname() throws Exception {
		GatewayDecisionCodes codes = CybsDecisionCodesFactory.defaultDecisionCodes();
		assertTrue(Arrays.binarySearch(codes.getLostOrStolenCardCodes(), "205") >= 0);
	}
	
}
