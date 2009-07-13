package ru.artlebedev.typograf.rule.chars;

import ru.artlebedev.typograf.info.CharsInfo;
import ru.artlebedev.typograf.model.Word;
import ru.artlebedev.typograf.rule.Rule;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 28.05.2009
 * Time: 12:27:50
 */
public class ParseWordRule extends Rule implements CharRule {

  private Word word = new Word();

  public void process() {
    if (p.c == '.' && word.value.length() == 1 && Character.isUpperCase(word.charAt(0))) {
      if (p.hasNextChar && p.nextChar == ' ') {
        return;
      }
//        int nextChar = p.charIndex + 1;
      // TODO вероятно пропущено добавление неразрывного пробела
      p.setCurrentWord(word);
      word = new Word();
//      p.charIndex += 1;
      p.updateChar(); // обновляем текущий char
      return;
    }

    if (
        p.c == '\r' || p.c == '\n'
            || p.c == '<' || p.c == '>'
            || p.c == ';' || p.c == ',' || p.c == ':'
            || p.c == '(' || p.c == ')'
            || p.c == CharsInfo.en1Left // TODO должна быть группа символов
            || p.c == '&'
            || p.c == CharsInfo.mdash // '—'
            //|| (p.c == '-' && word.Length == 1) //
            || p.c == CharsInfo.space
            || p.c == CharsInfo.noBreakSpace
            || p.c == '"'
            || p.c == CharsInfo.ru1Left || p.c == CharsInfo.ru1Right
            || p.charIndex + 1 == p.source.length // не захватывает последний символ
        ) {
      //int lengthBefore = word.Length;
      p.setCurrentWord(word);
      word = new Word(); // обнуляем "внутреннее" слово
    } else {
      word.append(p.c);
    }
  }
}
