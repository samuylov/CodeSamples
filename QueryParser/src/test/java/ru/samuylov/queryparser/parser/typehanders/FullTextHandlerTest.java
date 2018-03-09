/**
 *
 */
package ru.samuylov.queryparser.parser.typehanders;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author samuylov
 *
 */
public class FullTextHandlerTest {
  private FullTextHandler subj = new FullTextHandler();


  @Test
  public void testCanAdd() {
    assertTrue(subj.canAdd("GHFHG"));
    assertTrue(subj.canAdd("GHFH335"));
    assertTrue(subj.canAdd("sdfsfd34353"));
    assertTrue(subj.canAdd("sdfsdfываываыААЫы3sdfs"));
    assertFalse(subj.canAdd("ААОП_"));
    assertFalse(subj.canAdd("!@%#^$%)$%^"));
    assertFalse(subj.canAdd("GHFH33____5"));
    assertFalse(subj.canAdd("GHFH3???35"));
    assertFalse(subj.canAdd("GHFH*3*335"));
    assertFalse(subj.canAdd(">?FDZ"));
  }
}
