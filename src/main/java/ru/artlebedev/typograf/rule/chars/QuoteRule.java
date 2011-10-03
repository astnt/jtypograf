package ru.artlebedev.typograf.rule.chars;

import java.util.logging.Logger;

import ru.artlebedev.typograf.info.CharsInfo;
import ru.artlebedev.typograf.info.MainInfo;
import ru.artlebedev.typograf.util.Util;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 27.05.2009
 * Time: 16:28:27
 */
public class QuoteRule extends AbstractCharRule implements CharRule {
  private static Logger logger = Logger.getLogger("ru.artlebedev.typograf");
  private static final int LEFT = 0;
  private static final int RIGHT = 1;
  private int mode;
  private int currentLevel;

  public QuoteRule()
  {
    mode = LEFT;
    currentLevel = 1;
  }

  public void process() {
    if (!CharsInfo.isQuoteSymbol(p.c)) { return; }
    if (
        p.isInScript
            || p.isInTag
            || p.isInStyle
            || p.isInNoTypograf
        ) { return; }
    if ((p.style.equals(MainInfo.Lang.EN) || p.style.equals(MainInfo.Lang.DE)) && p.c == '\'') { return; } // HACK фикс для английской типографики
    if ((p.hasPrevChar && Util.isLetter(p.prevChar)) && (p.hasNextChar  && Util.isLetter(p.nextChar)) && p.c == '\'') { return; }
    // Проблема с "Международное Standard & Poor's подтвердило" в русском тексте
    if (p.c == '\''
        && Util.isInLatLetter(p.nextChar)
        && Util.isInLatLetter(p.prevChar)
        ) { return; }
    // открывающая кавычка
    if (p.hasNextChar && (p.nextChar != '.' || (p.nextChar == '.' && p.hasPrevChar && p.prevChar == CharsInfo.space)) &&
            (
                p.prevChar == CharsInfo.space
                    || p.prevChar == CharsInfo.noBreakSpace
                    || p.prevChar == ';'
                    || (p.prevChar == '>' && p.source[p.charIndex + 2] != '.') // «Сегодня в проекте „<a>Южный поток</a>“».
                    || p.prevChar == '\r'
                    || p.prevChar == '\n'                
                    || p.prevChar == '\t'
                    || p.prevChar == '('
                    || (p.prevChar == CharsInfo.quote && p.isInAttribute)
            )
            &&
            (p.nextChar != '<' && p.prevChar != CharsInfo.space && p.prevChar != CharsInfo.noBreakSpace || true) // <p>Проект «<a href="#postId=13275">Сахалин-2</a>» начался.</p>
                && p.nextChar != CharsInfo.space
                && p.nextChar != ','
                && p.nextChar != ';'
        ) {
      mode = LEFT;
    } else
    // закрывающая кавычка
    if (p.nextChar == CharsInfo.space
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
            || p.nextChar == '\''
            || p.nextChar == ')'
            || p.nextChar == '\r'
            || p.nextChar == '\n'
            || p.nextChar == '&'
        ) {
      mode = RIGHT;
    }
    if (p.c == CharsInfo.ru1Left  || p.c == CharsInfo.ru2Left)  { mode = LEFT; }
    if (p.c == CharsInfo.ru1Right || p.c == CharsInfo.ru2Right) { mode = RIGHT; }
    if (mode == LEFT) {
      if (p.style.equals(MainInfo.Lang.RU)) {
        if (currentLevel == 1) {
          p.source[p.charIndex] = CharsInfo.ru1Left;
        } else if (currentLevel > 1) {
          p.source[p.charIndex] = CharsInfo.ru2Left;
        }
      } else if (p.style.equals(MainInfo.Lang.EN)) {
        if (currentLevel == 1) {
          p.source[p.charIndex] = CharsInfo.en1Left;
        } else if (currentLevel > 1) {
          p.source[p.charIndex] = CharsInfo.en2Left;
        }
      } else if (p.style.equals(MainInfo.Lang.DE)) {
        if (currentLevel == 1) {
          p.source[p.charIndex] = CharsInfo.de1Left;
        } else if (currentLevel > 1) {
          p.source[p.charIndex] = CharsInfo.de2Left;
        }
      }
      currentLevel += 1;
    } else if (mode == RIGHT) {
      currentLevel -= 1;
      if (p.style.equals(MainInfo.Lang.RU)) {
        if (currentLevel == 1) {
          p.source[p.charIndex] = CharsInfo.ru1Right;
        } else if (currentLevel > 1) {
          p.source[p.charIndex] = CharsInfo.ru2Right;
        }
      } else if (p.style.equals(MainInfo.Lang.EN)) {
        if (currentLevel == 1) {
          p.source[p.charIndex] = CharsInfo.en1Right;
        } else if (currentLevel > 1) {
          p.source[p.charIndex] = CharsInfo.en2Right;
        }
      } else if (p.style.equals(MainInfo.Lang.DE)) {
        if (currentLevel == 1) {
          p.source[p.charIndex] = CharsInfo.de1Right;
        } else if (currentLevel > 1) {
          p.source[p.charIndex] = CharsInfo.de2Right;
        }
      }
    }
    p.updateChar();
  }
}
