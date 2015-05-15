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
import org.springframework.payment.core.PaymentServiceRequest;
import org.springframework.payment.core.PaymentTemplate;
import org.springframework.payment.core.PaymentTransaction;

/**
 * An abstraction providing methods for making credit card payment service requests. This is
 * a higher level abstraction than using {@link PaymentTemplate} directly. It abstracts users
 * from the details of preparing a {@link PaymentServiceRequest} and requires inputs that
 * end applications are more likely to understand and use.
 * 
 * @author Rossen Stoyanchev
 */
public interface CreditCardService {

	/**
	 * Obtain authorization for the given amount and credit card information. The resulting 
	 * PaymentTransaction can later be passed to the {@link #capture(MonetaryAmount, PaymentTransaction)}
	 * method to capture the authorized amount.
	 * 
	 * @param total the amount to authorize
	 * @param order the order with credit card and billing information
	 * @param requestId a merchant-assigned tracking number
	 */
	PaymentTransaction authorize(MonetaryAmount total, Order order, String requestId);

	/**
	 * Complete a purchase including authorization and capture for the given amount.
	 * 
	 * @param total the amount for the purchase
	 * @param order the order with credit card and billing information
	 * @param requestId a merchant-assigned tracking number
	 */
	PaymentTransaction purchase(MonetaryAmount total, Order order, String requestId);

	/**
	 * Capture a portion of an amount or the full amount from a prior authorization.
	 * 
	 * @param total the amount the amount to capture
	 * @param authTransaction a prior authorization transaction
	 */
	PaymentTransaction capture(MonetaryAmount total, PaymentTransaction authTransaction);

}