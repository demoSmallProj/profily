package org.mytask.profilycore.util;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConvertDataToInsertsUtilTest {
    ConvertDataToInsertsUtil util = new ConvertDataToInsertsUtil();
    @Test
    public void shouldConvertLineToInsert() {
        assertThat(util.convertLineToInsert("3,9/05/2016 1:26:54 PM,-13.94,SIT MAURIS IPSUM SIT"),
                is("insert into payment (customer_id, date, amount_cents, description) values (3, parsedatetime('9/05/2016 1:26:54 PM', 'dd/MM/yyyy hh:mm:ss aa'), -1394, 'SIT MAURIS IPSUM SIT');"));
    }

    @Test
    public void shouldConvertRoundAmountToCentsProperly() {
        assertThat(util.convertLineToInsert("3,9/05/2016 1:26:54 PM,-13,SIT MAURIS IPSUM SIT"),
                is("insert into payment (customer_id, date, amount_cents, description) values (3, parsedatetime('9/05/2016 1:26:54 PM', 'dd/MM/yyyy hh:mm:ss aa'), -1300, 'SIT MAURIS IPSUM SIT');"));
    }
}
