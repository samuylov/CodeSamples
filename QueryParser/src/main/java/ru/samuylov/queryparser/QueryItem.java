package ru.samuylov.queryparser;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * simple implementation of {@link IQueryItem}
 */
public final class QueryItem implements IQueryItem {

    ////////////////////////////////////////////// Construction ////////////////////////////////////////////////////////
    public QueryItem(@NotNull IQueryExpression expression, @NotNull QueryOperator operator, int operatorOffset) {
        this.expression = expression;
        this.operatorOffset = operatorOffset;
        this.operator = operator;
    }

    private final IQueryExpression expression;
    private final int operatorOffset;
    private final QueryOperator operator;

    ////////////////////////////////////////////// Overrides ////////////////////////////////////////////////////////

    @Override
    public @NotNull IQueryExpression getItemExpression() {
        return expression;
    }

    @Override
    public @NotNull QueryOperator getOperator() {
        return operator;
    }

    @Override
    public int getOperatorOffset() {
        return operatorOffset;
    }

    @Override
    public @NotNull String toString() {
        return getOperator().getCharacter() + expression;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !getClass().equals(obj.getClass()))
            return false;
        IQueryItem queryItem = (IQueryItem) obj;
        return Objects.equals(expression, queryItem.getItemExpression()) &&
                operator == queryItem.getOperator();
    }

    @Override
    public int hashCode() {
        int result = expression.hashCode();
        result = 31 * result + operatorOffset;
        result = 31 * result + operator.hashCode();
        return result;
    }
}
