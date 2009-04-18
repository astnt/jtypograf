package ru.artlebedev.typograf;

import junit.framework.TestCase;
import ru.artlebedev.typograf.rule.chars.DashRule;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 12.04.2009
 * Time: 17:00:20
 */
public class ProcessorCommonTest extends TestCase {
    public void testFirst() throws IOException {
        Processor p = new Processor("test - test");
        p.addRule(new DashRule());
        if (p.process()) {
            log("result");
            log(String.valueOf(p.getSource()));
//            log("result: " + new String(p.getSource(), 0, p.getSource().length, "UTF-8"));
//            log("result: " + new String(writer., 0, p.getSource().length, "UTF-8"));
        }
    }

    public void log(String message) {
        System.out.println(message);
    }

}
