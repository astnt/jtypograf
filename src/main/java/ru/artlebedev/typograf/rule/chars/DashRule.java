package ru.artlebedev.typograf.rule.chars;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 18.04.2009
 * Time: 14:37:45
 */
public class DashRule extends AbstractCharRule implements ICharRule {

    public void process() {

        if (
                (p.c != '-' && p.c != (char) 151 && p.c != '\u2013') // TODO ndash
                        || p.isInTag
                        || p.isInScript
                        || p.isInStyle
                        || p.isInNoTypograf
                ) {
            return;
        }
        System.out.println("dash rule: " + p.hasPrevChar + "&&" + p.hasNextChar);
        if (p.hasPrevChar && p.hasNextChar) {
            if (
                    p.prevChar == ' '
                            || p.prevChar == (char) 160
                            || p.prevChar == ';'
                            || p.prevChar == '>'
                            && p.nextChar == ' '
                    ) {
                p.source[p.charIndex] = (char) 151;
                // привяжем к предыдущему слову
                if (p.hasPrevChar) {
                    p.source[p.charIndex - 1] = (char) 160;
                }
            }
        }
    }
}
