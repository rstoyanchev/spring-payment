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

package org.springframework.payment.creditcard;

import org.springframework.payment.common.money.MonetaryAmount;
import org.springframework.payment.common.order.Order;
import org.springframework.payment.core.PaymentOperations;
import org.springframework.payment.core.PaymentServiceRequest;
import org.springframework.payment.core.PaymentTransaction;

/**
 * A default implementation of {@link CreditCardService}.
 * 
 * @author Rossen Stoyanchev
 */
public class DefaultCreditCardService implements CreditCardService {

	private PaymentOperations paymentOperations;

	public DefaultCreditCardService(PaymentOperations paymentOperations) {
		this.paymentOperations = paymentOperations;
	}

	public PaymentTransaction authorize(MonetaryAmount total, Order order, String requestId) {
		CreditCardAuthRequest request = new CreditCardAuthRequest(order);
		request.setRequestId(requestId);
		request.setTotal(total);
		return paymentOperations.execute(request);
	}

	public PaymentTransaction purchase(MonetaryAmount total, Order order, String requestId) {
		CreditCardAuthRequest ccAuthRequest = new CreditCardAuthRequest(order);
		ccAuthRequest.setRequestId(requestId);
		ccAuthRequest.setTotal(total);
		PaymentServiceRequest request = new CreditCardAuthCaptureRequest(ccAuthRequest);
		return paymentOperations.execute(request);
	}

	public PaymentTransaction capture(MonetaryAmount total, PaymentTransaction authTransaction) {
		CreditCardFollowOnCaptureRequest request = new CreditCardFollowOnCaptureRequest(authTransaction);
		request.setTotal(total);
		return paymentOperations.execute(request);
	}
}
