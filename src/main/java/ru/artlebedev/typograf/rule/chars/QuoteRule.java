package ru.artlebedev.typograf.rule.chars;

import ru.artlebedev.typograf.info.MainInfo;
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

  public QuoteRule()
  {
    quotesFirstLevelByLang = new Dictionary<int, String[]>();
    quotesSecondLevelByLang = new Dictionary<int, String[]>();

    quotesFirstLevelByLang.Add(RussianStyle, ru1);
    quotesFirstLevelByLang.Add(EnglishStyle, en1);

    quotesSecondLevelByLang.Add(RussianStyle, ru2);
    quotesSecondLevelByLang.Add(EnglishStyle, en2);

    currentLevel = quotesFirstLevelByLang; // по-молчанию
  }


  public void process() {
    if (
        p.c != '"'
            && p.c != '\''
            && p.c != '«'
            && p.c != '“'
            && p.c != '»'
      //&& p.c != '”' // HACK
      //&& p.c != '„' // HACK?
      //„ ”
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

    // открывающая кавычка
    if (
        p.hasNextChar &&
            (
                p.prevChar == ' '
                    || p.prevChar == (char) 160 // неразрывный пробел
                    || p.prevChar == ';'
                    || p.prevChar == '>'
            )
            &&
            (
                p.nextChar != '<'
                    && p.nextChar != ' '
                    && p.nextChar != ','
            )
        ) {
      mode = LEFT;
      // типа первый раз, понятно, что внутри
      if (isInFirstLevel) {
        currentLevel = quotesSecondLevelByLang;
      } else {
        // открылся
        isInFirstLevel = true;
      }
    }

    // закрывающая кавычка
    if (//ct.hasNextChar &&
        (
            p.nextChar == 32 // пробел
                || p.nextChar == 160 // неразрывный пробел
                || p.nextChar == ','
                || p.nextChar == ';'
                || p.nextChar == '.'
                || p.nextChar == '?'
                || p.nextChar == '!'
                || p.nextChar == ':'
                || p.nextChar == '<'
                || p.nextChar == '"'
                || p.nextChar == '»'
                || p.nextChar == '”'
                || p.nextChar == ')'
        )
        ) {
      mode = RIGHT;
      if (isInFirstLevel && p.hasNextChar) {
        isInFirstLevel = false;
      } else {
        currentLevel = quotesFirstLevelByLang;
      }
    }

    if (p.c == '«' || p.c == '“') mode = LEFT;
    if (p.c == '»' || p.c == '”') {
      mode = RIGHT;
      // Фиксим ошибки вводящих
      if (p.nextChar != '»' || p.c == '”') currentLevel = quotesFirstLevelByLang;
    }

//!    p.Chars.RemoveAt(p.CurrentIndex);
    int length;
    if (mode == LEFT) {
      p.Chars.InsertRange(p.CurrentIndex, currentLevel[style][LEFT].ToCharArray());
      length = quotesFirstLevelByLang[style][LEFT].Length;
      //mode = right;
    } else {
      p.Chars.InsertRange(p.CurrentIndex, currentLevel[style][RIGHT].ToCharArray());
      length = quotesFirstLevelByLang[style][RIGHT].Length;
      //mode = left;
    }

    p.CurrentIndex = p.CurrentIndex + length - 1;
    p.UpdateChar();
  }
}
