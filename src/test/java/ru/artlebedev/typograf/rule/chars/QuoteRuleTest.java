package ru.artlebedev.typograf.rule.chars;

import ru.artlebedev.typograf.AbstractTypografTest;
import ru.artlebedev.typograf.Typograf;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 13.07.2009
 * Time: 10:07:30
 */
public class QuoteRuleTest extends AbstractTypografTest {
  public void test3level() {
    final Typograf p = createProcessor("<p>\"Комплекс стандартов \"Документы нормативные для проектирования, строительства и эксплуатации объектов ОАО \"Газпром\"\"\"</p>");
    if (p.process()) {
      final String result = String.valueOf(p.getSource());
      logger.info(result);
      assertEquals("<p>«Комплекс стандартов „Документы нормативные для проектирования, строительства и эксплуатации объектов ОАО „Газпром““»</p>" , result);
    }
  }

  public void testQuotes3() {
    final Typograf p = createProcessor("<p>\"\"Газпром\" предлагает\" полностью</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>«„Газпром“ предлагает» полностью</p>", String.valueOf(p.getSource()));
    }
  }

  public void testQuotesHrefOuter() {
    final Typograf p = createProcessor("<p>Проект \"<a href=\"#postId=13275\">Сахалин-2</a>\" начался.</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>Проект «<a href=\"#postId=13275\">Сахалин-2</a>» начался.</p>", String.valueOf(p.getSource()));
    }
  }

  public void testQuotesHrefOuterWithNoBreakSpace() {
    final Typograf p = createProcessor("Совместно с ОАО \"<a href=\"\">СИБУР Холдинг</a>\" компания");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("Совместно с ОАО «<a href=\"\">СИБУР Холдинг</a>» компания", String.valueOf(p.getSource()));
    }
  }
  
  public void testQuotes4() {
    final Typograf p = createProcessor("<p>Маштоца (Республика Армения); орденом \"Достык\" (\"Дружбы\") II степени</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>Маштоца (Республика Армения); орденом «Достык» («Дружбы») II степени</p>", String.valueOf(p.getSource()));
    }
  }

  public void testQuotes5() {
    final Typograf p = createProcessor("<p>\"\"Газпром\" свою стратегию. В слова \"вылетает в трубу\"! Таким образом\".</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>«„Газпром“ свою стратегию. В слова „вылетает в трубу“! Таким образом».</p>", String.valueOf(p.getSource()));
    }
  }

  public void testQuotes6() {
    final Typograf p = createProcessor("<p>Cвою стратегию. В слова \"<a href=\"/test/\">вылетает</a>\";<b>в трубу</b>!.</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>Cвою стратегию. В слова «<a href=\"/test/\">вылетает</a>»;<b>в трубу</b>!.</p>", String.valueOf(p.getSource()));
    }
  }

  public void testQuotesEndWithDot() {
    final Typograf p = createProcessor("<p>ООО \"Кубаньгазпром\" активным коллективов \"<a href=\"/path/\">Факел</a>\".</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>ООО «Кубаньгазпром» активным коллективов «<a href=\"/path/\">Факел</a>».</p>", String.valueOf(p.getSource()));
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
      assertWith("test is «test in „somewhere“» test", p.getSource());
    } else {
      fail();
    }
  }

  public void testQuotesWithThreeDots() throws IOException {
    Typograf p = new Typograf("three dots test is \"...test in \"somewhere\" text\" test");
    p.addRule(new DashRule());
    p.addRule(new QuoteRule());
    if (p.process()) {
      logger.info(new String(p.getSource()));
      assertWith("three dots test is «...test in „somewhere“ text» test", p.getSource());
    } else {
      fail();
    }
  }
}
