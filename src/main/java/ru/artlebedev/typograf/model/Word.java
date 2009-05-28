package ru.artlebedev.typograf.model;

import ru.artlebedev.typograf.util.CommonUtil;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 28.05.2009
 * Time: 12:29:33
 */
public class Word {
  public StringBuilder value;
  public int hyphenCount = 0;
  public boolean hasDigit = false;

  public Word() {
    value = new StringBuilder();
  }

  public Word(StringBuilder word) {
    value = word;
  }

  public Word(String word) {
    value = new StringBuilder(word);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Word)) return false;

    Word word = (Word) o;

    if (!value.toString().equals(word.value.toString())) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  public void append(char c) {
    if (c == '-') { hyphenCount += 1; }
    if (CommonUtil.isDigitChar(c)) { hasDigit = true; }
    value.append(c);
  }

  public char getLastChar() {
    int lastIndex = value.length() - 1;
    if (lastIndex < 0) {
      lastIndex = 0;
    }
    return value.charAt(lastIndex);
  }

  public char charAt(int position) {
    return value.charAt(position);
  }

}
