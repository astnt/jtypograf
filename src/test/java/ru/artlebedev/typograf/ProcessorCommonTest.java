package ru.artlebedev.typograf;

import junit.framework.TestCase;

import ru.artlebedev.typograf.rule.chars.DashRule;
import ru.artlebedev.typograf.rule.chars.QuoteRule;
import ru.artlebedev.typograf.info.CharsInfo;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 12.04.2009
 * Time: 17:00:20
 */
public class ProcessorCommonTest extends TestCase {

  protected final transient Log log = LogFactory.getLog(getClass());

  public ProcessorCommonTest() {
    BasicConfigurator.configure();
    final Level level = Level.OFF;
    final Logger logger = Logger.getRootLogger();
    logger.setLevel(level);
  }

  @Override
  protected void setUp() throws Exception {

  }

  public void testFirst() throws IOException {
    Processor p = new Processor("test - test");
    p.addRule(new DashRule());
    if (p.process()) {
      assertWith("test — test", p.getSource());
    } else {
      fail();
    }
  }

  public void testQuotes() throws IOException {
    Processor p = new Processor("test is \"test\" test");
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    if (p.process()) {
      assertWith("test is «test» test", p.getSource());
    } else {
      fail();
    }
  }

  public void testQuotesSecondLevel() throws IOException {
    Processor p = new Processor("test is \"test in \"somewhere\"\" test");
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    if (p.process()) {
      assertWith("test is «test in „somewhere”» test", p.getSource());
    } else {
      fail();
    }
  }


  private void assertWith(String expected, char[] actual) {
      log.info("result'" + String.valueOf(actual) + "'");
      assertEquals(expected, String.valueOf(actual));
  }

}
