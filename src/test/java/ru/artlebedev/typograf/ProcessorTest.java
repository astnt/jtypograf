package ru.artlebedev.typograf;

import junit.framework.TestCase;

import ru.artlebedev.typograf.rule.chars.DashRule;
import ru.artlebedev.typograf.rule.chars.QuoteRule;
import ru.artlebedev.typograf.rule.chars.ParseWordRule;
import ru.artlebedev.typograf.rule.chars.ModeRule;
import ru.artlebedev.typograf.rule.word.ShortWordRule;
import ru.artlebedev.typograf.rule.word.HyphenWordRule;
import ru.artlebedev.typograf.info.CharsInfo;
import ru.artlebedev.typograf.info.MainInfo;

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
public class ProcessorTest extends TestCase {

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
    Typograf p = new Typograf("test - test");
    p.addRule(new DashRule());
    if (p.process()) {
      assertWith("test — test", p.getSource());
    } else {
      fail();
    }
  }

  public void testQuotes() throws IOException {
    Typograf p = new Typograf("test is \"test\" test");
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    if (p.process()) {
      assertWith("test is «test» test", p.getSource());
    } else {
      fail();
    }
  }

  public void testQuotesSecondLevel() throws IOException {
    Typograf p = new Typograf("test is \"test in \"somewhere\"\" test");
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    if (p.process()) {
      assertWith("test is «test in „somewhere”» test", p.getSource());
    } else {
      fail();
    }
  }

  public void testParseWorldRule() throws IOException {
//    setUpLog();
    Typograf p = new Typograf("one two three - four six, nine and \"left\" to the ");
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
    Typograf p = createProcessor("<style type=\"text/css\"> background: url(\"test.jpg\") </style> test - test ");
    if (p.process()) {
      assertWith("<style type=\"text/css\"> background: url(\"test.jpg\") </style> test — test ", p.getSource());
    } else {
      fail();
    }
  }

  public void testSafity1() throws IOException {
    setUpLog();
    final File file = new File(getClass().getResource("/source.txt").getFile());
    final String source = FileUtils.readFileToString(file);
    Typograf p = createProcessor(source);
    if (p.process()) {
      log.debug(String.valueOf(p.getSource()));
    }
//      assertWith("one two three — four six, nine and «left» to the ", p.getSource());
//    } else {
//      fail();
//    }
  }

  public void testShortWords() {
    final Typograf p = createProcessor("тест к предлога");
    if (p.process()) {
      final String message = String.valueOf(p.getSource());
      log.debug(message);
      assertTrue(p.source[6] == CharsInfo.noBreakSpace);
      assertTrue(p.source[4] == CharsInfo.space);
    } else {
      fail();
    }
  }

  public void testShortWordsComplex() {
    final Typograf p = createProcessor("как бы то ни было"); // как&nbsp;бы&nbsp;то ни&nbsp;было
    if (p.process()) {
      assertTrue(p.source[3] == CharsInfo.noBreakSpace);
      assertTrue(p.source[6] == CharsInfo.noBreakSpace);
      assertTrue(p.source[9] == CharsInfo.space);
      assertTrue(p.source[12] == CharsInfo.noBreakSpace);
    } else {
      fail();
    }
  }

  public void testHyphenWordRule() {
    final Typograf p = createProcessor("текст это просто 123-456 по тел. (452) 456-45-544 и т.д."); // как&nbsp;бы&nbsp;то ни&nbsp;было
    if (p.process()) {
      assertWith("текст это просто 123–456 по тел. (452) 456-45-544 и т.д.", p.getSource());
      assertTrue(p.source[20] == CharsInfo.ndash);
    } else {
      fail();
    }
  }

  public void testDigitsWordRule() {
    final Typograf p = createProcessor("слон весит 215 кг., а может и больше"); // как&nbsp;бы&nbsp;то ни&nbsp;было
    if (p.process()) {
      log.debug("result:" + String.valueOf(p.getSource()));
      assertTrue(p.source[14] == CharsInfo.noBreakSpace);
      assertTrue(p.source[21] == CharsInfo.noBreakSpace);
      assertTrue(p.source[27] == CharsInfo.space);
      assertTrue(p.source[29] == CharsInfo.noBreakSpace);
    } else {
      fail();
    }
  }

  public void testQuoteRuleEn() {
    final Typograf p = createProcessor("Snoovel allows you to create and view \"Google Earth \"tours\" in your\" browser.");
    p.style = MainInfo.Lang.EN;
    if (p.process()) {
      assertWith("Snoovel allows you to create and view “Google Earth ‘tours’ in your” browser.", p.getSource());
    } else {
      fail();
    }
  }

  public void testDashRuleEn() {
    final Typograf p = createProcessor("Test - dash test in en.");
    p.style = MainInfo.Lang.EN;
    if (p.process()) {
      assertWith("Test – dash test in en.", p.getSource()); // ndash
    } else {
      fail();
    }
  }

  private Typograf createProcessor(String source) {
    Typograf p = new Typograf(source);
    p.addRule(new ParseWordRule());
    p.addRule(new ModeRule());
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());

    p.addRule(new ShortWordRule());
    p.addRule(new HyphenWordRule());
    return p;
  }

  private void assertWith(String expected, char[] actual) {
//      log.info("result'" + String.valueOf(actual) + "'");
      assertEquals(expected, String.valueOf(actual));
  }

}
