package ru.artlebedev.typograf.rule.word;

import ru.artlebedev.typograf.info.CharsInfo;
import ru.artlebedev.typograf.rule.Rule;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 29.05.2009
 * Time: 11:47:42
 */
public class HyphenWordRule extends Rule implements WordRule {
  protected final transient Logger log = Logger.getLogger(HyphenWordRule.class.getName());

  public void process()
  {
    if (p.word.hyphenCount == 0 && p.word.value.length() <= 1) { return; } // если нет дефисов
    if (p.isInScript || p.isInTag || p.isInStyle || p.isInNoTypograf || p.isInAttribute || p.isInAttributeIgnore) { return; } // если не в правильном месте
    if (p.word.hyphenCount == 1)
    {
      int hyphenIndex = p.charIndex - p.word.value.length() + p.word.value.toString().indexOf('-');
      // TODO проверка, а может таких символов нет? (маловероятно на html-странице)
      if (p.hasPrevChar && p.hasNextChar && Character.isDigit(p.source[hyphenIndex - 1]) && Character.isDigit(p.source[hyphenIndex + 1])) {
        p.source[hyphenIndex] = CharsInfo.ndash;
      }
    }
  }
}
