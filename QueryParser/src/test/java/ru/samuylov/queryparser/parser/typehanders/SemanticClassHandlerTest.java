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
public class SemanticClassHandlerTest {
  private SemanticClassHandler subj = new SemanticClassHandler();

  @Test
  public void testCanAdd() {
    assertTrue(subj.canAdd("GHFHG"));
    assertTrue(subj.canAdd("GHFH335"));
    assertFalse(subj.canAdd("ААОП_"));
    assertFalse(subj.canAdd("!@%#^$%)$%^"));
    assertTrue(subj.canAdd("GHFH33____5"));
    assertTrue(subj.canAdd("GHFH3???35"));
    assertTrue(subj.canAdd("GHFH*3*335"));
    assertFalse(subj.canAdd(">?FDZ"));
    assertFalse(subj.canAdd("GHFH33____5 FFGG"));

  }
}
