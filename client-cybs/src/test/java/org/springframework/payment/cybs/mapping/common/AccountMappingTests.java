/*
 * Copyright 2004-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.payment.cybs.mapping.common;

import static junit.framework.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.springframework.payment.common.account.Address;
import org.springframework.payment.common.account.CardDate;
import org.springframework.payment.common.account.PaymentCard;
import org.springframework.payment.common.account.PaymentMethod;
import org.springframework.payment.gateway.GatewayRequest;
import org.springframework.payment.test.beans.CommonTestBeans;

public class AccountMappingTests {

	@Test
	public void testPaymentMethod() throws Exception {
		PaymentMethod source = CommonTestBeans.paymentMethod();
		GatewayRequest target = new GatewayRequest();
		new PaymentMethodMapper().map(source, target);
		assertPaymentMethod(source, target);
	}

	@Test
	public void testPaymentCard() throws Exception {
		GatewayRequest target = new GatewayRequest();
		PaymentCard paymentCard = CommonTestBeans.paymentCard();
		new PaymentCardMapper().map(paymentCard, target);
		assertPaymentCard(paymentCard, target);
	}

	@Test
	public void testAddress() throws Exception {
		GatewayRequest target = new GatewayRequest();
		Address address = CommonTestBeans.address();
		new AddressMapper("billTo_").map(address, target);
		assertAddress(address, target);
	}

	public static void assertPaymentMethod(PaymentMethod paymentMethod, GatewayRequest target) {
		Map<String, String> requestFields = target.getRequestFields();
		assertEquals(requestFields.get("card_accountNumber"),paymentMethod.getPaymentCard().getNumber());
		assertEquals(requestFields.get("billTo_street1"), paymentMethod.getAddress().getLine1());
	}

	public static void assertPaymentCard(PaymentCard paymentCard, GatewayRequest target) {
		Map<String, String> requestFields = target.getRequestFields();
		assertEquals(requestFields.get("card_cardType"), "001");
		assertEquals(requestFields.get("card_accountNumber"), paymentCard.getNumber());
		CardDate expiration = paymentCard.getExpiration();
		assertEquals(requestFields.get("card_expirationMonth"), expiration.getMonthTwoDigit());
		assertEquals(requestFields.get("card_expirationYear"), String.valueOf(expiration.getYear()));
	}

	public static void assertAddress(Address address, GatewayRequest target) {
		Map<String, String> requestFields = target.getRequestFields();
		assertEquals(requestFields.get("billTo_street1"), address.getLine1());
		assertEquals(requestFields.get("billTo_city"), address.getCity());
		assertEquals(requestFields.get("billTo_postalCode"), address.getPostalCode());
		assertEquals(requestFields.get("billTo_state"), address.getState().getCode());
		assertEquals(requestFields.get("billTo_country"), address.getCountry().getIsoCode());
	}

}
