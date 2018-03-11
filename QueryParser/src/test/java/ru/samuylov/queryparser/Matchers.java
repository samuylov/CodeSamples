// ©  2017 ABBYY Production LLC. ABBYY and Compreno are either registered trademarks or trademarks of ABBYY Software Ltd.

package ru.samuylov.queryparser;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;
import ru.samuylov.queryparser.expressions.atomic.*;
import ru.samuylov.queryparser.expressions.combined.CombinedQueryExpression;
import ru.samuylov.queryparser.expressions.combined.GroupingExpression;
import ru.samuylov.queryparser.expressions.combined.SynonymExpression;

import java.util.Arrays;

/**
 * матчеры для проверки {@link IQueryExpression}, {@link IQueryItem}
 */
final class Matchers {
    private Matchers() {
    }

    ////////////////////////////////////////// Operations ///////////////////////////////////////////////////

    @SafeVarargs
    static @NotNull Matcher<IQueryExpression> group(@NotNull Matcher<IQueryExpression>... branches) {
        return new CombinedQueryExpressionMatcher(GroupingExpression.class, branches);
    }

    @SafeVarargs
    static @NotNull Matcher<IQueryExpression> synonym(@NotNull Matcher<IQueryExpression>... branches) {
        return new CombinedQueryExpressionMatcher(SynonymExpression.class, branches);
    }

    static @NotNull Matcher<IQueryExpression> exact(@NotNull String value) {
        return new AtomicQueryExpressionMatcher(ExactMatchExpression.class, value);
    }

    static @NotNull Matcher<IQueryExpression> term(@NotNull String value) {
        return new AtomicQueryExpressionMatcher(TermExpression.class, value);
    }

    static @NotNull Matcher<IQueryExpression> fuzzy(@NotNull String value) {
        return new AtomicQueryExpressionMatcher(FuzzyTermExpression.class, value);
    }

    static @NotNull Matcher<IQueryExpression> wildcard(@NotNull String value) {
        return new AtomicQueryExpressionMatcher(WildcardExpression.class, value);
    }

    static @NotNull Matcher<IQueryItem> item(@NotNull Matcher<IQueryExpression> expressionMatcher) {
        return new QueryItemMatcher(QueryOperator.None, expressionMatcher);
    }

    static @NotNull Matcher<IQueryItem> itemMustNot(@NotNull Matcher<IQueryExpression> expressionMatcher) {
        return new QueryItemMatcher(QueryOperator.MustNot, expressionMatcher);
    }

    static @NotNull Matcher<IQueryItem> itemMust(@NotNull Matcher<IQueryExpression> expressionMatcher) {
        return new QueryItemMatcher(QueryOperator.Must, expressionMatcher);
    }

    static @NotNull Matcher<SearchQuery> query(@NotNull Matcher<IQueryItem>... itemsMatchers) {
        return new QueryMatcher(itemsMatchers);
    }

    /////////////////////////////////////// Implementation /////////////////////////////////////////////////////////
    private static class QueryMatcher extends BaseMatcher<SearchQuery> {

        private final Matcher<IQueryItem>[] itemsMatchers;

        private QueryMatcher(
                @NotNull Matcher<IQueryItem>[] itemsMatchers) {
            this.itemsMatchers = itemsMatchers;
        }

        @Override
        public void describeTo(Description description) {
            boolean first = true;
            for (Matcher<IQueryItem> matcher : itemsMatchers) {
                if (first)
                    first = false;
                else
                    description.appendText(", ");

                description.appendDescriptionOf(matcher);
            }
        }

        @Override
        public boolean matches(Object item) {
            if (!item.getClass().equals(SearchQuery.class))
                return false;

            IQuery query = (IQuery) item;

            if (itemsMatchers.length != query.getQueryItems().size())
                return false;

            int i = 0;
            for (IQueryItem queryItem : query.getQueryItems()) {
                if (!itemsMatchers[i].matches(queryItem))
                    return false;
                i++;
            }

            return true;
        }
    }

    private static class QueryItemMatcher extends BaseMatcher<IQueryItem> {

        private final Matcher<IQueryExpression> expressionMatcher;
        private final QueryOperator operator;

        private QueryItemMatcher(
                @NotNull QueryOperator operator,
                @NotNull Matcher<IQueryExpression> expressionMatcher) {
            this.expressionMatcher = expressionMatcher;
            this.operator = operator;
        }

        @Override
        public void describeTo(Description description) {
            description.appendValue(operator.getCharacter());
            description.appendDescriptionOf(expressionMatcher);
        }

        @Override
        public boolean matches(Object item) {
            if (!item.getClass().equals(QueryItem.class))
                return false;

            IQueryItem queryItem = (IQueryItem) item;
            return expressionMatcher.matches(queryItem.getItemExpression());
        }
    }

    private static class AtomicQueryExpressionMatcher extends BaseMatcher<IQueryExpression> {
        private AtomicQueryExpressionMatcher(
                @NotNull Class<? extends AtomicQueryExpression> clazz, @NotNull String value) {
            this.clazz = clazz;
            this.value = value;
        }

        private final Class<? extends AtomicQueryExpression> clazz;
        private final String value;

        @Override
        public void describeTo(Description description) {
            description.appendValue(clazz.getSimpleName());
            description.appendText("[");
            description.appendValue(value);
            description.appendText(" ]");
        }

        @Override
        public boolean matches(Object item) {
            if (!item.getClass().equals(clazz))
                return false;
            AtomicQueryExpression expression = (AtomicQueryExpression) item;

            return expression.getText().equals(value);
        }
    }


    private static class CombinedQueryExpressionMatcher extends BaseMatcher<IQueryExpression> {
        private final Class<? extends CombinedQueryExpression> clazz;
        private final Matcher<IQueryExpression>[] matchersForBranches;

        private CombinedQueryExpressionMatcher(@NotNull Class<? extends CombinedQueryExpression> clazz,
                                               @NotNull Matcher<IQueryExpression>[] matchers) {
            this.clazz = clazz;
            matchersForBranches = Arrays.copyOf(matchers, matchers.length);
        }

        @Override
        public void describeTo(Description description) {
            description.appendValue(clazz.getSimpleName());
            description.appendText("[");

            boolean first = true;
            for (Matcher<IQueryExpression> matcher : matchersForBranches) {
                if (first)
                    first = false;
                else
                    description.appendText(", ");

                description.appendDescriptionOf(matcher);
            }

            description.appendText("]");
        }

        @Override
        public boolean matches(Object item) {
            if (!item.getClass().equals(clazz))
                return false;
            CombinedQueryExpression expression = (CombinedQueryExpression) item;

            if (matchersForBranches.length != expression.getBranches().size())
                return false;

            int i = 0;
            for (IQueryExpression branch : expression.getBranches()) {
                if (!matchersForBranches[i].matches(branch))
                    return false;
                i++;
            }

            return true;
        }
    }
}
