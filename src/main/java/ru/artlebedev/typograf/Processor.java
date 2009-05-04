package ru.artlebedev.typograf;

import ru.artlebedev.typograf.rule.chars.ICharRule;
import ru.artlebedev.typograf.rule.Rule;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 18.04.2009
 * Time: 14:09:53
 */
public class Processor {
    public char[] source;

    public boolean isInTag = false;
    public boolean isInScript = false;
    public boolean isInStyle = false;
    public boolean isInNoTypograf = false;

    public char prevChar;
    public boolean hasPrevChar = false;
    public char c;
    public char nextChar;
    public boolean hasNextChar = false;

    public int charIndex = 0;

    private List<ICharRule> charRules = new ArrayList<ICharRule>();

    public char[] getSource() {
        return source;
    }

    public Processor(String source) {
        this.source = source.toCharArray();
    }

    public void addRule(ICharRule charRule) {
        charRules.add(charRule);
        ((Rule)charRule).p = this;
    }

    public boolean process() {
        for (int i = 0; i < source.length; i++) {
            charIndex = i;
            if (hasPrevChar = i - 1 > 0) {
                prevChar = source[i - 1];
            }
            c = source[i];
            if (!(hasNextChar = i + 1 < source.length)) {
                prevChar = source[i - 1];
            }
            for (ICharRule charRule : charRules) {
                charRule.process();
            }
        }
        return true;
    }
}
