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

package org.springframework.payment.cybs.mapping.creditcard;

import org.springframework.mapping.Mapper;
import org.springframework.payment.core.PaymentTemplate;
import org.springframework.payment.cybs.mapping.PaymentTransactionMapper;
import org.springframework.payment.gateway.GatewayRequest;

/**
 * A factory for instantiating a complete set of mappers to use to configure a 
 * {@link PaymentTemplate}.
 * 
 * @author Rossen Stoyanchev
 */
public abstract class CreditCardMapperFactory {

	@SuppressWarnings("unchecked")
	public static <S> Mapper<S, GatewayRequest>[] defaultRequestMappers() {
		return new Mapper[] { new CreditCardAuthRequestMapper(), new CreditCardAuthCaptureRequestMapper(),
				new CreditCardFollowOnCaptureRequestMapper() };
	}

	public static PaymentTransactionMapper[] defaultResponseMappers() {
		return new PaymentTransactionMapper[] { new PaymentTransactionMapper() };
	}

}
