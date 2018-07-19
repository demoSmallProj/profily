package org.mytask.profilycore.controller;

import org.mytask.profilycore.resource.Customer;
import org.mytask.profilycore.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class CustomerController {
    public static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    PaymentService paymentService;

    @GetMapping("/customer/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Customer getCustomer(@PathVariable("id") long customerId,
                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                @RequestParam("month") Date month,
                                @DateTimeFormat(pattern = "yyyy-MM-ddThh:mm:ss")
                                @RequestParam(value = "balanceTime", required = false) Date balanceTime) {
        logger.info("request received for customerId: {}, month: {}, balanceTime: {}", customerId, month, balanceTime);
        Customer customer = paymentService.getCustomer(customerId, month, balanceTime);
        return customer;
    }
}
