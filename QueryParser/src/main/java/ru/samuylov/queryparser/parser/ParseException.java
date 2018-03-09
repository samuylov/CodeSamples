/**
 *
 */
package ru.samuylov.queryparser.parser;

/**
 * Исключение разбора поискового запроса
 *
 * @author samuylov
 *
 */
public class ParseException extends Exception {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * @param message
   * @param cause
   */
  public ParseException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param message
   */
  public ParseException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public ParseException(Throwable cause) {
    super(cause);
  }


}
