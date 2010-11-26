package ru.artlebedev.typograf.rule.chars;

import ru.artlebedev.typograf.info.CharsInfo;
import ru.artlebedev.typograf.info.MainInfo;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 18.04.2009
 * Time: 14:37:45
 */
public class DashRule extends AbstractCharRule implements CharRule {

  public void process() {

    if (
        p.c != CharsInfo.dash && p.c != CharsInfo.mdash && p.c != CharsInfo.ndash
            || p.isInTag
            || p.isInScript
            || p.isInStyle
            || p.isInNoTypograf
        ) {
      return;
    }
    if (p.hasPrevChar && p.hasNextChar) {
      if (
          p.prevChar == CharsInfo.space
              || p.prevChar == CharsInfo.noBreakSpace
              || p.prevChar == ';'
              || p.prevChar == '>'
              || p.prevChar == '\r'
              || p.prevChar == '\n'
              || p.prevChar == '\t'
              && p.nextChar == CharsInfo.space
          ) {
        if (p.style.equals(MainInfo.Lang.RU)) {
          p.source[p.charIndex] = CharsInfo.mdash;
        } else if (p.style.equals(MainInfo.Lang.EN)) {
          p.source[p.charIndex] = CharsInfo.ndash;
        }
        // привяжем к предыдущему слову
        if (p.hasPrevChar && p.prevChar == ' ') {
          p.source[p.charIndex - 1] = CharsInfo.noBreakSpace;
        }
      }
    }
  }
}
