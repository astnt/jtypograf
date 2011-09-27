package ru.artlebedev.typograf.info;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 27.05.2009
 * Time: 16:15:45
 */
public class CharsInfo {
  public static final char mdash = '\u2014';
  public static final char ndash = '\u2013';
  public static final char dash = '-';
  public static final char noBreakSpace = '\u00A0';
  public static final char space = ' ';
  public static final char quote = '\"';

  public static final char ru1Left = '\u00AB';
  public static final char ru1Right = '\u00BB';
  public static final char ru2Left = '\u201E';
  public static final char ru2Right = '\u201C';

  public static final char en1Left = '“';
  public static final char en1Right = '”';
  public static final char en2Left = '‘';
  public static final char en2Right = '’';

  public static final char de1Left = '„';
  public static final char de1Right = '“';
  public static final char de2Left = '„';
  public static final char de2Right = '“';

  public static boolean isQuoteSymbol(char c) {
    return c == '"'
        || c == '\''
        || c == en1Left
        || c == ru1Left
        || c == ru1Right
        || c == ru2Left
        || c == ru2Right;
  }
}
