package ru.artlebedev.typograf.rule.chars;

import ru.artlebedev.typograf.rule.Rule;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 18.04.2009
 * Time: 14:35:55
 */
public abstract class AbstractCharRule extends Rule {
  protected final transient Log log = LogFactory.getLog(getClass());
}
