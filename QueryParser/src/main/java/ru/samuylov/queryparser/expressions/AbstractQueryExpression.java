// ©  2017 ABBYY Production LLC. ABBYY and Compreno are either registered trademarks or trademarks of ABBYY Software Ltd.

package ru.samuylov.queryparser.expressions;

import ru.samuylov.queryparser.IQueryExpression;

public abstract class AbstractQueryExpression implements IQueryExpression {
    ////////////////////////////////////////////// Construction ////////////////////////////////////////////////////////
    protected AbstractQueryExpression(int expressionOffset) {
        this.expressionOffset = expressionOffset;
    }

    ////////////////////////////////////////////// Attributes ////////////////////////////////////////////////////////
    @Override
    public int getExpressionStartOffset() {
        return expressionOffset;
    }

    private final int expressionOffset;
}