/**
 *
 */
package ru.samuylov.queryparser.parser;

/**
 * Префикс перед подзапросом. может сочетаться с любым типом подзапроса
 *
 * @author samuylov
 *
 */
public enum SubQueryPrefix {
  MUST("+"), MUST_NOT("-");

  private String prefixChars;


  /**
   * @param prefixChars
   */
  private SubQueryPrefix(String prefixChars) {
    this.prefixChars = prefixChars;
  }


  /**
   * возвращает тип префикса, который имеет элемент, иначе null
   *
   * @param token
   * @return
   */
  public static SubQueryPrefix parseTokenPrefix(String token) {
    for (SubQueryPrefix prefix : SubQueryPrefix.values()) {
      if (token.startsWith(prefix.prefixChars)) {
        return prefix;
      }
    }
    return null;
  }

  /**
   * Преобразует элемент запроса, который имеет к качестве префикса текущий enum. убирает
   * prefixChars
   *
   * @return
   */
  public String transformFirst(String token) {
    return token.substring(token.indexOf(prefixChars) + prefixChars.length());
  }
}
