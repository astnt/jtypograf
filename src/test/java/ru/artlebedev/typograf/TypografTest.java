package ru.artlebedev.typograf;

import ru.artlebedev.typograf.info.CharsInfo;
import ru.artlebedev.typograf.info.MainInfo;
import ru.artlebedev.typograf.rule.chars.DashRule;
import ru.artlebedev.typograf.rule.chars.ParseWordRule;
import ru.artlebedev.typograf.rule.chars.QuoteRule;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 12.04.2009
 * Time: 17:00:20
 */
public class TypografTest extends AbstractTypografTest {

  public void testFirst() throws IOException {
    Typograf p = new Typograf("test - test");
    p.addRule(new DashRule());
    if (p.process()) {
      assertWith("test — test", p.getSource());
    } else {
      fail();
    }
  }

  public void testParseWorldRule() throws IOException {
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
    Typograf p = createProcessor("<style type=\"text/css\"> background: url(\"test.jpg\") </style> test - test ");
    if (p.process()) {
      assertWith("<style type=\"text/css\"> background: url(\"test.jpg\") </style> test — test ", p.getSource());
    } else {
      fail();
    }
  }

  public void testSafity1() throws IOException {
    final File file = new File(getClass().getResource("/source.txt").getFile());
    final String source = FileUtils.readFileToString(file);
    Typograf p = createProcessor(source);
    if (p.process()) {
      // ?
    } else {
      fail();
    }
  }

  public void testShortWords() {
    final Typograf p = createProcessor("тест к предлога");
    if (p.process()) {
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

  public void testMeasureRule1() {
    final Typograf p = createProcessor("текст 12 212 212 млрд куб. м текст");
    if (p.process()) {
      assertTrue(p.source[16] == CharsInfo.noBreakSpace);
      assertTrue(p.source[26] == CharsInfo.noBreakSpace);
    } else {
      fail();
    }
  }

  public void testMeasureRule2() {
    final Typograf p = createProcessor("текст 121 212 212 млрд кв. м текст");
    if (p.process()) {
      assertTrue(p.source[17] == CharsInfo.noBreakSpace);
      assertTrue(p.source[26] == CharsInfo.noBreakSpace);
    } else {
      fail();
    }
  }

  public void testQuotesBreakeLine() {
    final Typograf p = createProcessor("<p>\r\n\"Газпром предлагает\"\r\n полностью</p>");
    if (p.process()) {
      assertEquals("<p>\r\n" +
          "«Газпром предлагает»\r\n" +
          " полностью</p>", String.valueOf(p.getSource()));
    }
  }

  public void testFirstStatementDash() {
    final Typograf p = createProcessor("<p>- Газпром предлагает полностью</p>");
    if (p.process()) {
      assertEquals("<p>— Газпром предлагает полностью</p>", String.valueOf(p.getSource()));
    }
  }

  public void testFirstStatementDash1() {
    final Typograf p = createProcessor("<p>рост потребления энергоресурсов\n" +
        "- это экономический рост</p>");
    if (p.process()) {
      assertEquals("<p>рост потребления энергоресурсов\n" +
          "— это экономический рост</p>", String.valueOf(p.getSource()));
    }
  }

  public void testFirstStatementDash2() {
    final Typograf p = createProcessor("<p>рост потребления энергоресурсов -\n" +
        "это экономический рост</p>");
    if (p.process()) {
      assertEquals("<p>рост потребления энергоресурсов —\n" +
          "это экономический рост</p>", String.valueOf(p.getSource()));
    }
  }
}
