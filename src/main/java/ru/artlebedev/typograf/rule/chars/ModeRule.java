package ru.artlebedev.typograf.rule.chars;

import ru.artlebedev.typograf.rule.Rule;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 28.05.2009
 * Time: 12:19:03
 */
public class ModeRule extends Rule implements CharRule {

  public void process() {
    //Console.WriteLine(p.ModeType + " char [" + p.c + "]");
    if (p.source.length <= p.charIndex) { return; } // TODO ?
    // "Тупая проверка" для производительности
    if (p.c == '<' && !p.isInScript
        || p.c == '<' && p.isInScript && p.source[p.charIndex + 1] == '/') // ФИКС: <script> if(a<script){ fuck; }</script> test - test
    {
      if (p.isInText) {
        p.isInText = false;
      }
      p.isInTag = true;
    }
    if (p.c == '>') {
      if (p.isInTag) {
        p.isInTag = false;
      }
      p.isInText = true;
    }
  }


}
