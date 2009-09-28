package ru.artlebedev.typograf.rule.chars;

import ru.artlebedev.typograf.AbstractTypografTest;
import ru.artlebedev.typograf.Typograf;
import ru.artlebedev.typograf.info.CharsInfo;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 27.08.2009
 * Time: 16:19:03
 */
public class ModeRuleTest extends AbstractTypografTest {
  public void testImgTitle() {
    final Typograf p = createProcessor("<img title=\"Картинка - с типографикой и 'кавычкой'\" />");
    if (p.process()) {
      assertEquals(p.source[21], CharsInfo.mdash);
      assertEquals(p.source[40], CharsInfo.ru1Left);
      assertEquals(p.source[49], CharsInfo.ru1Right);
    }
  }
  public void testImgAlt() {
    final Typograf p = createProcessor("<img alt=\"Картинка - с типографикой и 'кавычкой'\" />");
    if (p.process()) {
      assertEquals(p.source[19], CharsInfo.mdash);
      assertEquals(p.source[38], CharsInfo.ru1Left);
      assertEquals(p.source[47], CharsInfo.ru1Right);
    }
  }
}