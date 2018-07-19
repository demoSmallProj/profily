package org.mytask.profilycore.service;

import org.mytask.profilycore.repository.PaymentRepository;
import org.mytask.profilycore.resource.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    public static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    public Customer getCustomer(long customerId, Date monthDate, Date balanceTimeSpecified) {
        return null;
    }

}
