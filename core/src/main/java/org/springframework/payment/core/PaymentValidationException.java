package org.springframework.payment.core;

import java.util.Set;

import javax.validation.ConstraintViolation;

@SuppressWarnings("serial")
public class PaymentValidationException extends RuntimeException {

	private PaymentServiceRequest paymentRequest;
	
	private Set<ConstraintViolation<PaymentServiceRequest>> violations;
	
	public PaymentValidationException(PaymentServiceRequest paymentRequest,
			Set<ConstraintViolation<PaymentServiceRequest>> violations) {
		this.paymentRequest = paymentRequest;
		this.violations = violations;
	}

	public PaymentServiceRequest getPaymentRequest() {
		return paymentRequest;
	}

	public Set<ConstraintViolation<PaymentServiceRequest>> getViolations() {
		return violations;
	}
	
}
