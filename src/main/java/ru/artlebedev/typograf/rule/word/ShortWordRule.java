package ru.artlebedev.typograf.rule.word;

import ru.artlebedev.typograf.info.CharsInfo;
import ru.artlebedev.typograf.model.Word;
import ru.artlebedev.typograf.rule.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 29.05.2009
 * Time: 11:10:58
 */
public class ShortWordRule extends Rule implements WordRule {
  private static final Word hyphenWord = new Word("-");

  public void process()
  {
    if (p.word.value.length() > 3) { return; }

    // связываем еденицы измерения с предыдущим словом
    int prevPrevIndex = p.charIndex - p.word.value.length() - 2;
    if(prevPrevIndex > 0)
    {
      char code = p.source[prevPrevIndex];
      if (
          Character.isDigit(code) // это цифра
           || particles.contains(p.word) // или частица
           || rightExceptions.contains(p.prevWord) // или относиться к искоючениям
        )
      {
        int nbspIndex = prevPrevIndex + 1;
        if (p.source[nbspIndex] == CharsInfo.space && !p.word.equals(hyphenWord))
        {
          p.source[nbspIndex] = CharsInfo.noBreakSpace;
          return;
        }
        // charIndex + 6;
      }
    }

    // не связывать неразрывным пробелом цепочку более 3-4 коротких слов
    if (p.word.value.length() < 3 /*&& p.ShortWordCount < 3*/ )
    {
      //p.ShortWordCount++;
//!      if (common.Exists(delegate(Model.Word sb) { return sb.Equals(p.word); }))
      if (common.contains(p.word))
      {
        shortWordCount++;
        if (shortWordCount > 2 || moreThenTwoLetters.contains(p.word))
        {
          shortWordCount = 0;
          return;
        }
      }
    }
    //else p.ShortWordCount = 0; // обнуляем

    //int prevSpace = p.CurrentIndex - p.word.Length - 1;
    //if (prevSpace < 0) prevSpace = 0;

    //int prevChar = p.CurrentIndex - 1 - 6;
    //if (prevChar < 0) prevChar = 0;

    if (p.source.length <= p.charIndex) { return; }

    //Console.WriteLine(p.PrevWord.Value);

    // свзяваем неразрывнам пробелом со следующим словом
    if (
         (p.word.value.length() < 3 || moreThenTwoLetters.contains(p.word))
      && p.word.value.length() != 0
      && p.c == ' '
//!      && !particles.Exists(delegate(Model.Word sb) { return sb.Equals(p.word); }) // не частица
      && !particles.contains(p.word) // не частица
      && !p.word.equals(dot)
      && !p.word.equals(question)
      && !p.word.equals(hyphenWord)
      //&& chars[prevSpace] != ';' // WARN типа не проставлена уже entity перед ним
      //&& !CommonUtil.IsDigitChar(chars[nex]) // и если не цифра
     )
    {
      int nextChar = p.charIndex + 1;
      //Console.WriteLine("]"+chars[prevChar]+"[");

      if (p.source.length <= p.charIndex) { return; }
      // Если следущая заглавная
      //if (CommonUtil.IsUpper(chars[nextChar]) && !CommonUtil.IsUpper(chars[prevChar])) return;
      if (p.prevWord != null && p.prevWord.value.length() != 0 && Character.isDigit(p.prevWord.getLastChar())) { return; }

      if (p.source.length > nextChar && p.source[nextChar] == '(') { return; }

      p.source[p.charIndex] = CharsInfo.noBreakSpace;
    }
  }

  private int shortWordCount;
  private static final List<Word> common = new ArrayList<Word>();
  private static final List<Word> particles = new ArrayList<Word>();
  private static final List<Word> moreThenTwoLetters = new ArrayList<Word>();
  static {
    //my $preps = "я|ты|мы|вы|не|ни|на|но|в|во|до|от|и|а|ее|".
    //      "он|с|со|о|об|ну|к|ко|за|их|из|ей|ой|ай";
    //my $posts = "ли|ль|же|ж|бы|б";
    common.add(new Word("ни"));
    common.add(new Word("не"));
    common.add(new Word("ли"));

    particles.add(new Word("то"));
    particles.add(new Word("бы"));
    particles.add(new Word("/"));
    //particles.Add(new Word("г."));

    moreThenTwoLetters.add(new Word("для"));
    moreThenTwoLetters.add(new Word("под"));
    moreThenTwoLetters.add(new Word("над"));
    moreThenTwoLetters.add(new Word("RUB"));
    moreThenTwoLetters.add(new Word("USD"));
    moreThenTwoLetters.add(new Word("ООО"));
    moreThenTwoLetters.add(new Word("ОАО"));
    moreThenTwoLetters.add(new Word("ЗАО"));
    moreThenTwoLetters.add(new Word("НК"));
    moreThenTwoLetters.add(new Word("УК"));
    moreThenTwoLetters.add(new Word("ДОАО"));
    moreThenTwoLetters.add(new Word("АО"));
    moreThenTwoLetters.add(new Word("НГК"));
    moreThenTwoLetters.add(new Word("ГУ"));
    moreThenTwoLetters.add(new Word("ФГУ"));
    moreThenTwoLetters.add(new Word("ФГУП"));
    moreThenTwoLetters.add(new Word("OAO"));
    moreThenTwoLetters.add(new Word("OOO"));
    moreThenTwoLetters.add(new Word("ZAO"));
    moreThenTwoLetters.add(new Word("ЛПУ"));
  }

  /// <summary>
  /// Искючения к которым привязывается предлоги справа.
  /// </summary>
  private static final List<Word> rightExceptions = new ArrayList<Word>();
  private static final Word dot = new Word(".");
  private static final Word question = new Word("?");
  public ShortWordRule() { }

  /// <summary>
  /// Добавляет исключения к которым привязываются короткие слов справа.
  /// </summary>
  /// <param name="exceptionWord"></param>
  public void addRightException(Word exceptionWord)
  {
    rightExceptions.add(exceptionWord);
  }


}
