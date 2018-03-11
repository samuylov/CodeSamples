// ©  2017 ABBYY Production LLC. ABBYY and Compreno are either registered trademarks or trademarks of ABBYY Software Ltd.

package ru.samuylov.queryparser.expressions.combined;

import org.jetbrains.annotations.NotNull;
import ru.samuylov.queryparser.expressions.AbstractQueryExpression;
import ru.samuylov.queryparser.IQueryExpression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Abstract class for expression, which is container for other expressions
 */
public abstract class CombinedQueryExpression extends AbstractQueryExpression {

    ////////////////////////////////////////////// Construction ////////////////////////////////////////////////////////
    CombinedQueryExpression(@NotNull List<IQueryExpression> branches,
                            int expressionOffset) {
        super(expressionOffset);
        this.branches.addAll(branches);
    }

    private final List<IQueryExpression> branches = new ArrayList<>();

    /////////////////////////////////////////////// Operations /////////////////////////////////////////////////////////

    /**
     * @return list of subexpressions
     */
    public final @NotNull List<IQueryExpression> getBranches() {
        return Collections.unmodifiableList(branches);
    }

    /**
     * @return unwrapped expression, if combined expression consists of one sub expression, otherwise - generates runtime exception
     */
    public final @NotNull IQueryExpression getSingleBranch() {
        if (branches.size() != 1)
            throw new IllegalStateException();

        return branches.get(0);
    }

    /**
     * @return true if current expression consists of one subexpression, otherwise - returns false
     */
    public final boolean hasSingleBranch() {
        return branches.size() == 1;
    }

    ////////////////////////////////////////////// Overrides ////////////////////////////////////////////////////////
    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || !getClass().equals(obj.getClass()))
            return false;

        CombinedQueryExpression otherExpression = (CombinedQueryExpression) obj;
        return Objects.equals(getBranches(), otherExpression.getBranches());
    }

    @Override
    public final int hashCode() {
        return getBranches().hashCode();
    }
}