package ru.samuylov.queryparser;

import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static ru.samuylov.queryparser.Matchers.*;


public class SearchQueryTest {

    @Test
    public void testQueryParsing() throws RequestParsingException {
        assertMatched("foo bar", item(term("foo")), item(term("bar")));
        assertMatched("+foo bar", itemMust(term("foo")), item(term("bar")));
        assertMatched("foo -bar", item(term("foo")), itemMustNot(term("bar")));
        assertMatched("red brown|grey fox", item(term("red")), item(synonym(term("brown"),term("grey"))),item(term("fox")));
        assertMatched("-red brown|grey fox", itemMustNot(term("red")), item(synonym(term("brown"),term("grey"))),item(term("fox")));
        assertMatched("red brown|grey (fox wolf)", item(term("red")), item(synonym(term("brown"),term("grey"))),item(group(term("fox"),term("wolf"))));
    }

    private static void assertMatched(@NotNull String queryString,
                                      @NotNull Matcher<IQueryItem>... itemsMatchers) throws RequestParsingException {
        assertThat( new SearchQuery(queryString), query(itemsMatchers) );
    }
}