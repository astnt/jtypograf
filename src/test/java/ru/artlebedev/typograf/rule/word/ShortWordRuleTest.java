package ru.artlebedev.typograf.rule.word;

import ru.artlebedev.typograf.AbstractTypografTest;
import ru.artlebedev.typograf.Typograf;
import ru.artlebedev.typograf.info.CharsInfo;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 11.10.2010
 * Time: 15:27:51
 */
public class ShortWordRuleTest extends AbstractTypografTest {
  public void testWithShortWord3Letters1() {
    final Typograf p = createProcessor("Предложение для теста.");
    if (p.process()) {
      assertEquals(p.source[15], CharsInfo.noBreakSpace);
    }
  }

  public void testWithShortWord3Letters2() {
    final Typograf p = createProcessor("Предложение под тестом.");
    if (p.process()) {
      assertEquals(p.source[15], CharsInfo.noBreakSpace);
    }
  }

  public void testWithShortWord3Letters3() {
    final Typograf p = createProcessor("Предложение над тестом.");
    if (p.process()) {
      assertEquals(p.source[15], CharsInfo.noBreakSpace);
    }
  }
}
