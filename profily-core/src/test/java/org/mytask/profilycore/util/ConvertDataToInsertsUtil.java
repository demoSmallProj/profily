package org.mytask.profilycore.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class ConvertDataToInsertsUtil {
    public static void main(String[] args) {
        List<String> lines = new BufferedReader(new InputStreamReader(System.in)).lines().collect(Collectors.toList());
        ConvertDataToInsertsUtil util = new ConvertDataToInsertsUtil();
        List<String> inserts = lines.stream().map(util::convertLineToInsert).collect(Collectors.toList());
        inserts.stream().forEach(System.out::println);
    }

    String convertLineToInsert(String line) {
        String[] vals = line.split(",");
        long cents = (long) (Double.parseDouble(vals[2]) * 100);
        return String.format("insert into payment (customer_id, date, amount_cents, description) values " +
                        "(%s, parsedatetime('%s', 'dd/MM/yyyy hh:mm:ss aa'), %d, '%s');",
                vals[0],vals[1],cents,vals[3]);
    }
}
