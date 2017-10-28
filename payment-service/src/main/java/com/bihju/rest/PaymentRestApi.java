package com.bihju.rest;

import com.bihju.model.Payment;
import com.bihju.model.Version;
import com.bihju.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PaymentRestApi {
    private PaymentService paymentService;

    @Autowired
    public PaymentRestApi(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void pay(@RequestBody Payment payment) {
        paymentService.processPayment(payment);
    }

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Version version() {
        return new Version("1.0");
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)

    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("health", "up");
        return ResponseEntity.ok(result);
    }
}
