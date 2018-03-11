// ©  2017 ABBYY Production LLC. ABBYY and Compreno are either registered trademarks or trademarks of ABBYY Software Ltd.

package ru.samuylov.queryparser.expressions.atomic;

import org.jetbrains.annotations.NotNull;
import ru.samuylov.queryparser.expressions.AbstractQueryExpression;

import java.util.Objects;

/**
 * some expression which consists of simple text value
 */
public abstract class AtomicQueryExpression extends AbstractQueryExpression {
    ////////////////////////////////////////////// Construction ////////////////////////////////////////////////////////
    AtomicQueryExpression(@NotNull String text, int expressionOffset) {
        super(expressionOffset);
        this.text = text;
    }

    private final String text;

    /////////////////////////////////////////////// Attributes /////////////////////////////////////////////////////////
    /**
     * @return text of element.
     * if some characters where escaped in query, this method returns text in unescaped form
     * if expression implies that it is surrounded by some characters (quotes or other), then this method returns text without these chars
     */
    public @NotNull String getText() {
        return text;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null || !getClass().equals(obj.getClass()))
            return false;

        AtomicQueryExpression otherExpression = (AtomicQueryExpression) obj;
        return Objects.equals(getText(), otherExpression.getText());
    }

    @Override
    public int hashCode() {
        return getText().hashCode();
    }
}