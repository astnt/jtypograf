package ru.artlebedev.typograf.util;

import ru.artlebedev.typograf.info.CharsInfo;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 27.05.2009
 * Time: 16:37:28
 */
public class Util {
  private static final String LAT_LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String DIGIT = "1234567890";

  public static boolean isInLatLetter(char c) {
    return LAT_LETTERS.indexOf(c) >= 0;
  }

  public static boolean hasChar(char[] source, int i) {
    return source.length > i;
  }

  public static boolean wordEnd(char c) {
    return c == '<' || c == CharsInfo.space || c == CharsInfo.noBreakSpace || c == ')';
  }

  public static boolean isNotSpaceOrQuote(char c) {
    return c != CharsInfo.space && c != CharsInfo.noBreakSpace && !CharsInfo.isQuoteSymbol(c);
  }

  public static boolean isLetter(char c) {
      return isInLatLetter(c) || isCyrillic(c) || DIGIT.indexOf(c) > 0;
  }

  public static boolean isCyrillic(char c) {
    return (0x400 <= c && c <= 0x4ff);
  }

  public static boolean isLatin(char c) {
    return (0x000 <= c && c <= 0x080);
  }
}
