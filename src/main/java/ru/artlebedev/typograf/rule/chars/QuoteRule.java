package ru.artlebedev.typograf.rule.chars;

import ru.artlebedev.typograf.info.MainInfo;
import ru.artlebedev.typograf.info.CharsInfo;
import ru.artlebedev.typograf.util.CommonUtil;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 27.05.2009
 * Time: 16:28:27
 */
public class QuoteRule extends AbstractCharRule implements CharRule {

  private boolean isInFirstLevel = false;
  public int style = MainInfo.ruRU;

  // режимы
  private static final int LEFT = 0;
  private static final int RIGHT = 1;

  private static int mode = LEFT;
  private static int currentLevel = 1;

  public QuoteRule()
  {
  }


  public void process() {
    if (
        p.c != '"'
            && p.c != '\''
            && p.c != CharsInfo.ru1Left
            && p.c != CharsInfo.en1Left
            && p.c != CharsInfo.ru1Right
      //&& p.c != '”' // HACK
      //&& p.c != '„' // HACK?
        ) {
      return;
    }
    if (
        p.isInScript
            || p.isInTag
            || p.isInStyle
            || p.isInNoTypograf
        ) {
      return;
    }
//!    ct = new CharContext(p.Chars, p.CurrentIndex);

    // TODO возможно так можно будет решить проблему с JSON
    //if (
    //       (ct.prevChar != ' ' && ct.prevChar != (char)160 && ct.prevChar != ';' && ct.prevChar != '>')
    //    && (ct.nextChar != ' ' && ct.nextChar != (char)160
    //        && ct.nextChar != '»'
    //        && ct.nextChar != '&'
    //        && ct.nextChar != ';'
    //        && ct.nextChar != '.'
    //        && ct.nextChar != '?'
    //        && ct.nextChar != '!'
    //        )
    //   ) return;
    if (style == MainInfo.enEN && p.c == '\'') {
      return;
    } // HACK фикс для английской типографики

    // Проблема с "Международное Standard & Poor's подтвердило" в русском тексте
    if (p.c == '\''
        && CommonUtil.isInLatLetter(p.nextChar)
        && CommonUtil.isInLatLetter(p.prevChar)
        ) {
      return;
    }
//    log.debug("prev '" + p.prevChar + "' char '" + p.c + "' next char '" + p.nextChar + "'");
    // открывающая кавычка
    if (
        p.hasNextChar &&
            (
                p.prevChar == CharsInfo.space
                    || p.prevChar == CharsInfo.noBreakSpace
                    || p.prevChar == ';'
                    || p.prevChar == '>'
            )
            &&
            (p.nextChar != '<'
                && p.nextChar != CharsInfo.space
                && p.nextChar != ',')
        ) {
      mode = LEFT;
      // типа первый раз, понятно, что внутри
      if (isInFirstLevel) {
        currentLevel = 2;
      } else {
        // открылся
        isInFirstLevel = true;
      }
    }

    // закрывающая кавычка
    if (//p.hasNextChar &&
        p.nextChar == CharsInfo.space
            || p.nextChar == CharsInfo.noBreakSpace
            || p.nextChar == ','
            || p.nextChar == ';'
            || p.nextChar == '.'
            || p.nextChar == '?'
            || p.nextChar == '!'
            || p.nextChar == ':'
            || p.nextChar == '<'
            || p.nextChar == '"'
            || p.nextChar == CharsInfo.ru1Right
            || p.nextChar == CharsInfo.ru2Right
            || p.nextChar == ')'
        ) {
      mode = RIGHT;
      if (isInFirstLevel && p.hasNextChar) {
        isInFirstLevel = false;
      } else {
        currentLevel = 1;
      }
    }

    if (p.c == CharsInfo.ru1Left  || p.c == CharsInfo.ru2Left)  { mode = LEFT; }
    if (p.c == CharsInfo.ru1Right || p.c == CharsInfo.ru2Right) {
      mode = RIGHT;
      // Фиксим ошибки вводящих
      if (p.nextChar != CharsInfo.ru1Right || p.c == CharsInfo.ru2Right) { currentLevel = 1; }
    }

//!    p.Chars.RemoveAt(p.CurrentIndex);
    int length = 0;
    if (mode == LEFT) {
      if (style == MainInfo.ruRU) {
        if (currentLevel == 1) {
          p.source[p.charIndex] = CharsInfo.ru1Left;
        } else if (currentLevel == 2) {
          p.source[p.charIndex] = CharsInfo.ru2Left;
        }
      }
      //mode = right;
    } else {
      if (style == MainInfo.ruRU) {
        if (currentLevel == 1) {
          p.source[p.charIndex] = CharsInfo.ru1Right;
        } else if (currentLevel == 2) {
          p.source[p.charIndex] = CharsInfo.ru2Right;
        }
      }
      //mode = left;
    }
//!    p.charIndex = p.charIndex + length - 1;
    p.updateChar();
  }
}
