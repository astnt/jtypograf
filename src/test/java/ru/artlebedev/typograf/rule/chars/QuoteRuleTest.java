package ru.artlebedev.typograf.rule.chars;

import ru.artlebedev.typograf.AbstractTypografTest;
import ru.artlebedev.typograf.Typograf;

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
      assertEquals("<p>«Комплекс стандартов „Документы нормативные для проектирования, строительства и эксплуатации объектов ОАО „Газпром””»</p>" , result);
    }
  }

  public void testQuotes3() {
    final Typograf p = createProcessor("<p>\"\"Газпром\" предлагает\" полностью</p>");
    if (p.process()) {
      logger.info(String.valueOf(p.getSource()));
      assertEquals("<p>«„Газпром” предлагает» полностью</p>", String.valueOf(p.getSource()));
    }
  }
}
