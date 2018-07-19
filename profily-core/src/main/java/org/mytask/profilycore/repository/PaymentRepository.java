package org.mytask.profilycore.repository;

import org.mytask.profilycore.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findPaymentsByCustomerIdAndDateGreaterThanEqualAndDateBeforeOrderByDateAsc(
            long customerId, Date periodFrom, Date periodToExclusive);


    @Query("select sum(p.amountCents) from Payment p where p.customerId = ?1 and p.date <= ?2")
    Long sumAmountCents(long customerId, Date balanceTime);
}