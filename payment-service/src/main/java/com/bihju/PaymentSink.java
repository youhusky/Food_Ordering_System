package com.bihju;

import com.bihju.model.Payment;
import com.bihju.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

@MessageEndpoint
@EnableBinding(Sink.class)
@Slf4j
public class PaymentSink {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ObjectMapper objectMapper;

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void updatePayment(Payment payment) throws Exception {
        log.info("Payment received for orderId: " + payment.getOrderId());
        paymentService.processPayment(payment);
    }
}
