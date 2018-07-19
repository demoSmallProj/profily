package org.mytask.profilycore.controller;

import org.mytask.profilycore.resource.Customer;
import org.mytask.profilycore.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class CustomerController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/customer/{id}")
    public Customer getCustomer(@PathVariable("id") long customerId,
                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                @RequestParam("month") Date month,
                                @DateTimeFormat(pattern = "yyyy-MM-ddThh:mm:ss")
                                @RequestParam(value = "balanceTime", required = false) Date balanceTime) {
        Customer customer = paymentService.getCustomer(customerId, month, balanceTime);
        return customer;
    }

    //TODO: drop later
    @GetMapping("/tmp")
    public Customer tmp() {
        Date month = null;
        try {
            month = new SimpleDateFormat("dd/MM/yyyy").parse("29/05/2016");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Customer customer = paymentService.getCustomer(3, month, month);
        return customer;
    }
}
