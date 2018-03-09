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
public class FulltextWildcardHandlerTest {
  private FulltextWildcardHandler subj = new FulltextWildcardHandler();

  @Test
  public void testCanAdd() {
    assertFalse(subj.canAdd("dffghj"));
    assertFalse(subj.canAdd("GHFH335"));
    assertFalse(subj.canAdd("АВПП"));
    assertFalse(subj.canAdd("!@%#^$%)$%^"));
    assertFalse(subj.canAdd("GHFH33____5"));
    assertTrue(subj.canAdd("GHFH3???35"));
    assertTrue(subj.canAdd("GHFH*3*335"));
    assertTrue(subj.canAdd("*"));
    assertTrue(subj.canAdd("*dsf"));
    assertTrue(subj.canAdd("dsf*"));
    assertTrue(subj.canAdd("?dsf"));
    assertTrue(subj.canAdd("d?sf"));
    assertTrue(subj.canAdd("dsf?"));
    assertTrue(subj.canAdd("ds?f?"));
    assertFalse(subj.canAdd(">?FDZ"));
  }
}
