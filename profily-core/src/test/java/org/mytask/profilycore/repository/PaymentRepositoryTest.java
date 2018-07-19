package org.mytask.profilycore.repository;


import org.mytask.profilycore.entity.Payment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PaymentRepositoryTest {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void shouldSumAmountCents() throws ParseException {
        Payment payment1 = new Payment(9999L, toDate("20/07/2018 22:40:00"), 100L, "test payment 1");
        Payment payment2 = new Payment(9999L, toDate("20/07/2018 22:45:10"), 200L, "test payment 2");
        entityManager.persist(payment1);
        entityManager.persist(payment2);
        entityManager.flush();

        Long balance = paymentRepository.sumAmountCents(9999L, toDate("20/07/2018 22:45:10"));
        assertThat(balance, is(300L));
    }

    @Test
    public void shouldSumAmountCentsEdgeConditions() throws ParseException {
        Payment normalPayment = new Payment(9999L, toDate("20/07/2018 22:40:00"), 100L, "test payment 1");
        Payment paymentAtTime = new Payment(9999L, toDate("20/07/2018 22:45:10"), 200L, "test payment 2");
        Payment paymentAfter = new Payment(9999L, toDate("20/07/2018 22:50:00"), 1L, "test payment 3");
        Payment paymentDifferentUser = new Payment(7777L, toDate("20/07/2018 22:40:00"), 2L, "test payment 4");
        entityManager.persist(normalPayment);
        entityManager.persist(paymentAtTime);
        entityManager.persist(paymentAfter);
        entityManager.persist(paymentDifferentUser);
        entityManager.flush();

        Long balance = paymentRepository.sumAmountCents(9999L, toDate("20/07/2018 22:45:10"));
        assertThat(balance, is(300L));
    }

    @Test
    public void shouldFindPayments() throws ParseException {
        Payment payment1 = new Payment(9999L, toDate("03/07/2018 10:00:00"), 100L, "test payment 1");
        Payment payment2 = new Payment(9999L, toDate("28/07/2018 10:00:00"), 200L, "test payment 2");
        entityManager.persist(payment1);
        entityManager.persist(payment2);
        entityManager.flush();
        List<Payment> payments = paymentRepository.findPaymentsByCustomerIdAndDateGreaterThanEqualAndDateBeforeOrderByDateAsc(
                9999L, toDate("01/07/2018 00:00:00"), toDate("01/08/2018 00:00:00"));
        assertThat(payments, is(Arrays.asList(payment1, payment2)));
    }

    @Test
    public void shouldFindPaymentsEdgeConditions() throws ParseException {
        Payment okPayment1 = new Payment(9999L, toDate("01/07/2018 00:00:00"), 100L, "test payment 1");
        Payment okPayment2 = new Payment(9999L, toDate("28/07/2018 10:00:00"), 200L, "test payment 2");
        Payment paymentInPreviousMonth = new Payment(9999L, toDate("30/06/2018 23:59:59"), 101L, "test payment 3");
        Payment paymentInNextMonth = new Payment(9999L, toDate("01/08/2018 00:00:00"), 102L, "test payment 4");
        Payment paymentDifferentUser = new Payment(7777L, toDate("28/07/2018 10:00:00"), 105L, "test payment 5");
        entityManager.persist(okPayment1);
        entityManager.persist(okPayment2);
        entityManager.persist(paymentInNextMonth);
        entityManager.persist(paymentInPreviousMonth);
        entityManager.persist(paymentDifferentUser);
        entityManager.flush();
        List<Payment> payments = paymentRepository.findPaymentsByCustomerIdAndDateGreaterThanEqualAndDateBeforeOrderByDateAsc(
                9999L, toDate("01/07/2018 00:00:00"), toDate("01/08/2018 00:00:00"));
        assertThat(payments, is(Arrays.asList(okPayment1, okPayment2)));
    }


    private Date toDate(String dateStr) throws ParseException {
        return simpleDateFormat.parse(dateStr);
    }
}