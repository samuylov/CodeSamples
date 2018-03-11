// ©  2017 ABBYY Production LLC. ABBYY and Compreno are either registered trademarks or trademarks of ABBYY Software Ltd.

package ru.samuylov.queryparser.expressions.atomic;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.jetbrains.annotations.NotNull;

/**
 * Simple term for search
 */
public final class TermExpression extends AtomicQueryExpression {
    ////////////////////////////////////////////// Construction ////////////////////////////////////////////////////////
    public TermExpression(String text, int expressionOffset) {
        super(text, expressionOffset);
    }

    //////////////////////////////////////////////// Overrides /////////////////////////////////////////////////////////

    @Override
    public String toString() {
        return getText();
    }

    @Override
    public @NotNull QueryBuilder toQueryBuilder(@NotNull String fieldName) {
        return QueryBuilders.termQuery(fieldName, getText());
    }
}