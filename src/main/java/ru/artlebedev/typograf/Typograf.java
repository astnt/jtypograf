package ru.artlebedev.typograf;

import ru.artlebedev.typograf.info.MainInfo;
import ru.artlebedev.typograf.model.Word;
import ru.artlebedev.typograf.rule.Rule;
import ru.artlebedev.typograf.rule.chars.CharRule;
import ru.artlebedev.typograf.rule.word.WordRule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 18.04.2009
 * Time: 14:09:53
 */
public class Typograf {
  public char[] source;

  public boolean isInText;
  public boolean isInTitle;
  public boolean isInTag;
  public boolean isInScript;
  public boolean isInStyle;
  public boolean isInNoTypograf;
  public boolean isInAttribute;

  public char prevChar;
  public boolean hasPrevChar;
  public char c;
  public char nextChar;

  public boolean hasNextChar;

  public int charIndex;
  private List<CharRule> charRules = new ArrayList<CharRule>();
  private List<WordRule> wordRules = new ArrayList<WordRule>();
  private Word currentWord;
  public Word prevWord;

  public Word word;
  public MainInfo.Lang style = MainInfo.Lang.RU;

  public void setAndHandleCurrentWord(Word value) {
    word = value;
    if (isInTag) {
      if (value.equals(scriptStart)) { isInScript = true; }
      if (value.equals(scriptEnd)) { isInScript = false; }

      if (value.equals(styleStart)) { isInStyle = true; }
      if (value.equals(styleEnd)) { isInStyle = false; }

      if (value.equals(titleStart)) { isInTitle = true; }
      if (value.equals(titleEnd)) { isInTitle = false; }

      if (value.equals(noTypografStart)) { isInNoTypograf = true; }
      if (value.equals(noTypografEnd)) { isInNoTypograf = false; }
    }
    if (wordRules.isEmpty()) {
      //Console.WriteLine("OnWordChange is empty");
    } else {
      if (!isInTag // Аттрибуты?
          && !isInScript
          && !isInStyle
          && !isInNoTypograf
          ) {
        if (currentWord != null && !currentWord.equals(nbsp)) {
          prevWord = currentWord;
        }
        // TODO
        for (WordRule rule : wordRules) {
          rule.process();
          updateChar();
        }
      }
    }
    currentWord = value;
  }

  public char[] getSource() {
    return source;
  }

  public Typograf(String source) {
    this.source = source.toCharArray();
  }

  public void addRule(CharRule charRule) {
    charRules.add(charRule);
    ((Rule) charRule).p = this;
  }

  public void addRule(WordRule wordRule) {
    wordRules.add(wordRule);
    ((Rule) wordRule).p = this;
  }

  public boolean process() {
    for (int i = 0; i < source.length; i++) {
      charIndex = i;
      updateChars(i);
      for (CharRule charRule : charRules) {
        charRule.process();
      }
    }
    return true;
  }

  private void updateChars(int i) {
    if (hasPrevChar = i - 1 > 0) {
      prevChar = source[i - 1];
    }
    c = source[i];
    if (hasNextChar = i + 1 < source.length - 1) {
      nextChar = source[i + 1];
    }
  }

  public void updateChar() {
    updateChars(charIndex);
  }

  private static final Word scriptStart = new Word("script");
  private static final Word scriptEnd = new Word("/script");

  private static final Word styleStart = new Word("style");
  private static final Word styleEnd = new Word("/style");

  private static final Word titleStart = new Word("title");
  private static final Word titleEnd = new Word("/title");

  private static final Word noTypografStart = new Word("notypograf");
  private static final Word noTypografEnd = new Word("/notypograf");

  private static final Word nbsp = new Word("nbsp");
}
