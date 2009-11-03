package ru.artlebedev.typograf.rule.word;

import ru.artlebedev.typograf.info.CharsInfo;
import ru.artlebedev.typograf.rule.Rule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 29.05.2009
 * Time: 11:47:42
 */
public class HyphenWordRule extends Rule implements WordRule {
  protected final transient Log log = LogFactory.getLog(getClass());

  public void process()
  {
    if (p.word.hyphenCount == 0 && p.word.value.length() <= 1) { return; } // если нет дефисов
    if (p.isInScript || p.isInTag || p.isInStyle || p.isInNoTypograf || p.isInAttribute) { return; } // если не в правильном месте
    if (p.word.hyphenCount == 1)
    {
      int hyphenIndex = p.charIndex - p.word.value.length() + p.word.value.toString().indexOf('-');
      // TODO проверка, а может таких символов нет? (маловероятно на html-странице)
      if (Character.isDigit(p.source[hyphenIndex - 1]) && Character.isDigit(p.source[hyphenIndex + 1])) {
        p.source[hyphenIndex] = CharsInfo.ndash;
      }
    }
  }
}
