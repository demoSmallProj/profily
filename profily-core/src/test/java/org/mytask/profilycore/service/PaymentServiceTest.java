package org.mytask.profilycore.service;

import org.junit.Test;
import org.mytask.profilycore.type.ClassificationType;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PaymentServiceTest {
    @Test
    public void shouldGetClassificationType() {
        PaymentService svc = new PaymentService();

        assertThat(svc.getClassificationType(10, 4, 6),is(ClassificationType.AFTERNOON_PERSON));
        assertThat(svc.getClassificationType(10, 6, 4),is(ClassificationType.MORNING_PERSON));
        //noone is > 50%
        assertThat(svc.getClassificationType(10, 5, 5),is(nullValue()));
        assertThat(svc.getClassificationType(10, 5, 3),is(nullValue()));
        assertThat(svc.getClassificationType(0, 0, 0),is(nullValue()));
        assertThat(svc.getClassificationType(10, 10, 0),is(ClassificationType.MORNING_PERSON));
        assertThat(svc.getClassificationType(10, 0, 10),is(ClassificationType.AFTERNOON_PERSON));
    }

}