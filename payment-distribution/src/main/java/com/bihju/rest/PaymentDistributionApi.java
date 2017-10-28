package com.bihju.rest;

import com.bihju.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableBinding(Source.class)
@RestController
@RequestMapping("/api")
public class PaymentDistributionApi {
    @Autowired
    private MessageChannel output;

    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    public void pay(@RequestBody Payment payment) {
        output.send(MessageBuilder.withPayload(payment).build());
    }
}
