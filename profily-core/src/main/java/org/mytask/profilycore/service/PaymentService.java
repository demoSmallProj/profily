package org.mytask.profilycore.service;

import org.apache.commons.lang3.tuple.Pair;
import org.mytask.profilycore.entity.Payment;
import org.mytask.profilycore.repository.PaymentRepository;
import org.mytask.profilycore.resource.Classification;
import org.mytask.profilycore.resource.Customer;
import org.mytask.profilycore.resource.Transaction;
import org.mytask.profilycore.type.ClassificationType;
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
        //TODO: fix
        if (monthDate == null) {
            logger.error("monthDate cannot be null");
            return null;
            //TODO: throw exception
//            throw new Exception()
        }
        Date balanceTime = balanceTimeSpecified != null ? balanceTimeSpecified : new Date();

        Pair<Date, Date> wholeMonthDates = wholeMonthDates(monthDate);
        List<Payment> payments = paymentRepository
                .findPaymentsByCustomerIdAndDateGreaterThanEqualAndDateBeforeOrderByDateAsc(customerId, wholeMonthDates.getLeft(), wholeMonthDates.getRight());
        Long balance = paymentRepository.sumAmountCents(customerId, balanceTime);
        logger.info("balance {}", balance);

        List<ClassificationType> classificationTypes = getClassifications(payments);
        List<Classification> classifications = toClassifications(classificationTypes);
        List<Transaction> transactions = toTransactions(payments);

        Customer customer = new Customer(customerId, classifications, balanceTime, balance, monthDate, transactions);
        return customer;
    }

    private List<Classification> toClassifications(List<ClassificationType> classificationTypes) {
        return classificationTypes.stream().map(t -> new Classification(t.name(), t.getDescription())).collect(Collectors.toList());
    }

    private List<Transaction> toTransactions(List<Payment> payments) {
        if (payments == null) {
            return Collections.emptyList();
        }
        return payments.stream().map(Transaction::fromPayment).collect(Collectors.toList());
    }

    private List<ClassificationType> getClassifications(List<Payment> payments) {
        List<ClassificationType> classifications = new ArrayList<>();
        if (payments == null) {
            return Collections.emptyList();
        }

        ClassificationType personType = getPersonType(payments);
        if (personType != null) {
            classifications.add(personType);
        }

        boolean bigSpender = isBigSpender(payments);
        boolean fastSpender = isFastSpender(payments);
        classifications.addAll(getBigSpenderFastSpenderPotentialLoan(bigSpender, fastSpender));

        if (isBigTicketSpender(payments)) {
            classifications.add(ClassificationType.BIG_TICKET_SPENDER);
        }
        if (isPotentialSaver(payments)) {
            classifications.add(ClassificationType.POTENTIAL_SAVER);
        }
        return classifications;

//        strictlyAfterMiddayCount =

//        classifications.add(isMorningPerson(payments) ? ClassificationType.MORNING_PERSON : ClassificationType.AFTERNOON_PERSON);
    }

    //Big Spender	Spends over 80% of their deposits every month ($ value of deposits)
    private boolean isBigSpender(List<Payment> payments) {
        if (payments == null) {
            return false;
        }
        long[] spendingsAndDeposits = getSpendingsAndDeposits(payments);
        long spendings = spendingsAndDeposits[0];
        long deposits = spendingsAndDeposits[1];
        boolean result;
        if (deposits == 0L) {
            result = Math.abs(spendings) > 0L;
        } else {
            double ratio = ((double) Math.abs(spendings)) / deposits;
            result = ratio > 0.8;
        }
        return result;
    }

    private boolean isPotentialSaver(List<Payment> payments) {
        if (payments == null) {
            return false;
        }
        long[] spendingsAndDeposits = getSpendingsAndDeposits(payments);
        long spendings = spendingsAndDeposits[0];
        long deposits = spendingsAndDeposits[1];
        boolean result;
        if (deposits == 0L) {
            result = Math.abs(spendings) == 0L;
        } else {
            double ratio = ((double) Math.abs(spendings)) / deposits;
            result = ratio < 0.25;
        }
        return result;
    }

    private long[] getSpendingsAndDeposits(List<Payment> payments) {
        List<Long> amounts = payments.stream()
                .filter(payment -> payment != null && payment.getAmountCents() != null)
                .map(Payment::getAmountCents)
                .collect(Collectors.toList());
        long spendingsTotal = amounts.stream().mapToLong(Long::longValue).filter(amount -> amount < 0).sum();
        long depositsTotal = amounts.stream().mapToLong(Long::longValue).filter(amount -> amount > 0).sum();

        return new long[]{spendingsTotal, depositsTotal};
    }


    private boolean isBigTicketSpender(List<Payment> payments) {
        if (payments == null) {
            return false;
        }
        long centsIn1000Dollars = 1000 * 100;
        long bigTransactionsCount = payments.stream()
                .filter(payment -> payment != null && payment.getAmountCents() != null)
                .map(Payment::getAmountCents)
                .filter(amount -> amount > centsIn1000Dollars).count();
        return bigTransactionsCount > 0;
    }

    private List<ClassificationType> getBigSpenderFastSpenderPotentialLoan(boolean bigSpender, boolean fastSpender) {
        List<ClassificationType> result = new ArrayList<>();
        if (bigSpender && fastSpender) {
            result.add(ClassificationType.POTENTIAL_LOAN);
        } else {
            if (bigSpender) {
                result.add(ClassificationType.BIG_SPENDER);
            }
            if (fastSpender) {
                result.add(ClassificationType.FAST_SPENDER);
            }
        }
        return result;
    }

    //        Fast Spender	Spends over 75% of any deposit within 7 days of making it
    private boolean isFastSpender(List<Payment> payments) {
        if (payments == null) {
            return false;
        }
        List<Pair<Long, Long>> depositToClearSpendings = new ArrayList<>();
        for (int i = 0; i < payments.size(); i++) {
            Payment payment = payments.get(i);
            if (payment.getAmountCents() > 0) {
                Date dateTo = plus7Days(payment.getDate());
                List<Payment> nextPaymentsInRange = new ArrayList<>();
                for (int j = i + 1; i < payments.size() && payments.get(j).getDate().before(dateTo); i++) {
                    nextPaymentsInRange.add(payments.get(j));
                }
                long cleanSpendingsInRange = nextPaymentsInRange.stream().map(Payment::getAmountCents).mapToLong(Long::longValue).sum();
                depositToClearSpendings.add(Pair.of(payment.getAmountCents(), cleanSpendingsInRange));
            }
        }

        OptionalDouble smallRatio = depositToClearSpendings.stream().mapToDouble(pair -> pair.getLeft() == 0L ? 1D : ((double) pair.getRight()) / pair.getLeft()).filter(ratio -> ratio <= 0.75).findFirst();
        return smallRatio.isPresent();
    }

    private Date plus7Days(Date date) {
        Instant instant = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().plusDays(7)
                .atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }


    private ClassificationType getPersonType(List<Payment> payments) {
        List<LocalTime> localTimes = payments.stream()
                .filter(payment -> payment != null && payment.getDate() != null)
                .map(Payment::getDate)
                .map(Date::toInstant)
                .map(instant -> instant.atZone(ZoneId.systemDefault()))
                .map(LocalTime::from).collect(Collectors.toList());

        int validPaymentsCount = localTimes.size();
        long beforeMiddayCount = localTimes.stream().filter(localTime -> localTime.isBefore(LocalTime.NOON)).count();
        long strictlyAfterMiddayCount = localTimes.stream().filter(localTime -> localTime.isAfter(LocalTime.NOON)).count();

        ClassificationType personType = getClassificationType(validPaymentsCount, beforeMiddayCount, strictlyAfterMiddayCount);
        return personType;
    }

    ClassificationType getClassificationType(int validPaymentsCount, long beforeMiddayCount, long strictlyAfterMiddayCount) {
        if (validPaymentsCount == 0) {
            return null;
        }
        double beforeRatio = (double) beforeMiddayCount / validPaymentsCount;
        double afterRatio = (double) strictlyAfterMiddayCount / validPaymentsCount;
        return beforeRatio > 0.5 ? ClassificationType.MORNING_PERSON :
                afterRatio > 0.5 ? ClassificationType.AFTERNOON_PERSON : null;
    }


    private Pair<Date, Date> wholeMonthDates(Date monthDate) {
        LocalDate localDate = LocalDate.from(monthDate.toInstant().atZone(ZoneId.systemDefault()));
        Date dateFrom = toJulDate(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay());
        Date dateTo = toJulDate(localDate.with(TemporalAdjusters.firstDayOfNextMonth()).atStartOfDay());
        return Pair.of(dateFrom, dateTo);
    }

    private Date toJulDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }
}
