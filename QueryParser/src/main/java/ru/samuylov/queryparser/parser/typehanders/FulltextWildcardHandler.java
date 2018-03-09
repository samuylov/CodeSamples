/**
 *
 */
package ru.samuylov.queryparser.parser.typehanders;

/**
 * обработчик для типа подзапроса "Wildcard в полнотекстовом запросе"
 *
 * @author samuylov
 *
 */
public class FulltextWildcardHandler implements SubQueryTypeHandler {

  /*
   * (non-Javadoc)
   *
   * @see ru.samuylov.queryparser.parser.typehanders.SubQueryTypeHandler#transformFirst(java.lang.String)
   */
  @Override
  public String transformFirst(String first) {
    return first;
  }

  /*
   * (non-Javadoc)
   *
   * @see ru.samuylov.queryparser.parser.typehanders.SubQueryTypeHandler#transformLast(java.lang.String)
   */
  @Override
  public String transformLast(String last) {
    return last;
  }

  /*
   * (non-Javadoc)
   *
   * @see ru.samuylov.queryparser.parser.typehanders.SubQueryTypeHandler#isClosingToken(java.lang.String)
   */
  @Override
  public boolean isClosingToken(String token) {
    return canAdd(token);
  }

  /*
   * (non-Javadoc)
   *
   * @see ru.samuylov.queryparser.parser.typehanders.SubQueryTypeHandler#canAdd(java.lang.String)
   */
  @Override
  public boolean canAdd(String token) {
    return token.matches("^[a-zA-Z0-9а-яА-Я*?]*[*?][a-zA-Z0-9а-яА-Я*?]*$");
  }



  /*
   * (non-Javadoc)
   *
   * @see ru.samuylov.queryparser.parser.typehanders.SubQueryTypeHandler#isStartingToken(java.lang.String)
   */
  @Override
  public boolean isStartingToken(String token) {
    return canAdd(token);
  }

}
