package ru.artlebedev.typograf;

import junit.framework.TestCase;

import ru.artlebedev.typograf.rule.chars.DashRule;
import ru.artlebedev.typograf.rule.chars.QuoteRule;
import ru.artlebedev.typograf.rule.chars.ParseWordRule;
import ru.artlebedev.typograf.rule.chars.ModeRule;
import ru.artlebedev.typograf.info.CharsInfo;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 12.04.2009
 * Time: 17:00:20
 */
public class ProcessorCommonTest extends TestCase {

  protected final transient Log log = LogFactory.getLog(getClass());

//  public ProcessorCommonTest() {
//    BasicConfigurator.configure();
//    final Level level = Level.OFF;
//    final Logger logger = Logger.getRootLogger();
//    logger.setLevel(level);
//  }

  private void setUpLog() {
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

  public void testParseWorldRule() throws IOException {
    Processor p = new Processor("one two three - four six, nine and \"left\" to the ");
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    p.addRule(new ParseWordRule());
    if (p.process()) {
      assertWith("one two three — four six, nine and «left» to the ", p.getSource());
    } else {
      fail();
    }
  }

  public void testSafity() throws IOException {
//    setUpLog();
    final String source = "<style type=\"text/css\"> background: url(\"test.jpg\") </style> test - test ";
    Processor p = createProcessor(source);
    if (p.process()) {
      assertWith("<style type=\"text/css\"> background: url(\"test.jpg\") </style> test - test ", p.getSource());
    } else {
      fail();
    }
  }

  public void testSafity1() throws IOException {
    setUpLog();
    final File file = new File(getClass().getResource("/source.txt").getFile());
    final String source = FileUtils.readFileToString(file);
    log.debug(source);
    Processor p = createProcessor(source);
    if (p.process()) {
//      assertWith("one two three — four six, nine and «left» to the ", p.getSource());
    } else {
//      fail();
    }
  }

  private Processor createProcessor(String source) {
    Processor p = new Processor(source);
    p.addRule(new ParseWordRule());
    p.addRule(new ModeRule());
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    return p;
  }

  private void assertWith(String expected, char[] actual) {
      log.info("result'" + String.valueOf(actual) + "'");
      assertEquals(expected, String.valueOf(actual));
  }

}
