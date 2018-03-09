/**
 *
 */
package ru.samuylov.queryparser.parser.typehanders;

/**
 * обработчик для типа подзапроса "Семантический запрос"
 *
 * @author samuylov
 *
 */
public class SemanticHandler implements SubQueryTypeHandler {
  private static final String START = "{";
  private static final String END = "}";

  private static final String MIDDLE_PATTERN = "^[a-zA-Z0-9а-яА-Я]+$";
  private static final String STARTING_PATTERN = "^\\{{1}[a-zA-Z0-9а-яА-Я]+\\}?$";
  private static final String ENDING_PATTERN = "^\\{?[a-zA-Z0-9а-яА-Я]+\\}{1}$";

  /*
   * (non-Javadoc)
   *
   * @see ru.samuylov.queryparser.parser.typehanders.SubQueryTypeHandler#transformFirst(java.lang.String)
   */
  @Override
  public String transformFirst(String first) {
    return first.substring(first.indexOf(START) + START.length());
  }

  /*
   * (non-Javadoc)
   *
   * @see ru.samuylov.queryparser.parser.typehanders.SubQueryTypeHandler#transformLast(java.lang.String)
   */
  @Override
  public String transformLast(String last) {
    return last.substring(0, last.lastIndexOf(END));
  }

  /*
   * (non-Javadoc)
   *
   * @see ru.samuylov.queryparser.parser.typehanders.SubQueryTypeHandler#isClosingToken(java.lang.String)
   */
  @Override
  public boolean isClosingToken(String token) {
    return token.matches(ENDING_PATTERN);
  }

  /*
   * (non-Javadoc)
   *
   * @see ru.samuylov.queryparser.parser.typehanders.SubQueryTypeHandler#canAdd(java.lang.String)
   */
  @Override
  public boolean canAdd(String token) {
    return token.matches(MIDDLE_PATTERN);
  }


  /*
   * (non-Javadoc)
   *
   * @see ru.samuylov.queryparser.parser.typehanders.SubQueryTypeHandler#isStartingToken(java.lang.String)
   */
  @Override
  public boolean isStartingToken(String token) {
    return token.matches(STARTING_PATTERN);
  }


}
