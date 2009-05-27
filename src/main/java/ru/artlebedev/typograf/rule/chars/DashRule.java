package ru.artlebedev.typograf.rule.chars;

import ru.artlebedev.typograf.info.CharsInfo;

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
          p.prevChar == ' '
              || p.prevChar == CharsInfo.noBreakSpace
              || p.prevChar == ';'
              || p.prevChar == '>'
              && p.nextChar == CharsInfo.space
          ) {
        p.source[p.charIndex] = CharsInfo.mdash;
        // привяжем к предыдущему слову
        if (p.hasPrevChar) {
          p.source[p.charIndex - 1] = CharsInfo.noBreakSpace;
        }
      }
    }
  }
}
