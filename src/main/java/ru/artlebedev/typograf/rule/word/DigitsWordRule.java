package ru.artlebedev.typograf.rule.word;

import ru.artlebedev.typograf.util.CommonUtil;
import ru.artlebedev.typograf.rule.Rule;
import ru.artlebedev.typograf.info.CharsInfo;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 29.05.2009
 * Time: 12:55:14
 */
public class DigitsWordRule extends Rule implements WordRule {
  public void process()
  {
    if (!p.word.hasDigit) { return; }
    if (p.charIndex >= p.source.length) { return; }
    if (CommonUtil.isDigitChar(p.word.charAt(p.word.value.length() - 1)) && p.c == CharsInfo.space)
    {
      p.source[p.charIndex] = CharsInfo.noBreakSpace;
    }
  }
}
