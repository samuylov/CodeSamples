parser grammar QueryParser;
options {tokenVocab = QueryLexer;}
@header {
  import ru.samuylov.queryparser.*;
  import ru.samuylov.queryparser.expressions.*;
  import ru.samuylov.queryparser.expressions.atomic.*;
  import ru.samuylov.queryparser.expressions.combined.*;
  import org.apache.commons.lang3.text.translate.LookupTranslator;
  import org.apache.commons.lang3.StringUtils;
}
@parser::members {
	private static final LookupTranslator exactTranslator =
								new LookupTranslator(new String[][] { {"\\\\", "\\"},
                                                                      {"\\\"", "\""},
                                                            		});
	private static final LookupTranslator termTranslator =
                         		new LookupTranslator(new String[][] { {"\\(", "("},
                                                                      {"\\)", ")"},
                                                                      {"\\{", "{"},
                                                                      {"\\}", "}"},
                                                                      {"\\|", "|"},
                                                                      {"\\\"", "\""},
                                                                      {"\\:", ":"},
                                                                      {"\\?", "?"},
                                                                      {"\\*", "*"},
                                                                      {"\\~", "~"},
                                                                      {"\\\\", "\\"},
                                                                      {"\\<", "<"},
                                                                      {"\\>", ">"},
                                                                      {"\\^", "^"}
                                                                    });

	private static String unescapeExact(CharSequence input)
	{
		return exactTranslator.translate( input );
	}

	private static String unescapeTerm(String input)
	{
		//в обычных выражениях допустимо искейпить операторы только в начале
		if(input.startsWith("\\+") || input.startsWith("\\-"))
			input = input.substring(1,input.length());
		return termTranslator.translate( input );
	}

    private String cutFirstLastSymbols(String text)
    {
        return text.substring(1,text.length()-1);
    }
}

query returns [ List<IQueryItem> items ]
@init { $items = new ArrayList<>();}
:
	 (queryItem {$items.add( $queryItem.value );})+
;

queryItem returns [IQueryItem value]
@init
{
	QueryOperator operator = QueryOperator.None;
	int operatorOffset = -1;
}
:

    (operatorPrefix
        {
            operator = $operatorPrefix.value;
            operatorOffset = $operatorPrefix.start.getStartIndex();
        }
    )?
    queryExpression {$value = new QueryItem($queryExpression.value, operator,operatorOffset );}
;

queryExpression returns [AbstractQueryExpression value]
@init
{
	List<IQueryExpression> synonymExpressions = new ArrayList<>();
}
:
	(
		firstExpression = expression_WithoutSynonym
        (SYNONYM expression_WithoutSynonym { synonymExpressions.add($expression_WithoutSynonym.value);} )*
    )
	{
        if(synonymExpressions.isEmpty())
        {
            //синонимов нет проставляем певому выражению данные об операторе
            $value = $firstExpression.value;
        } else {
           // найдено перечисление синонимов.
           List<IQueryExpression> expressions = new ArrayList<>();
           expressions.add($firstExpression.value);
           expressions.addAll(synonymExpressions);
           $value = new SynonymExpression(expressions, $firstExpression.start.getStartIndex());
       }
	}
;

expression_WithoutSynonym returns [AbstractQueryExpression value]
@init { List<IQueryExpression> groupExpressions = new ArrayList<>();}
:

   exactMatchExpression {$value = $exactMatchExpression.value; }
 	|	WildcardExpression  {$value = new WildcardExpression($WildcardExpression.text, $WildcardExpression.getStartIndex() ); }
 	|	term {$value = new TermExpression($term.value, $term.start.getStartIndex() ); }
 	|   fuzzyTerm {$value = $fuzzyTerm.expression; }
    | (
	       LPAREN
	       (queryExpression {groupExpressions.add($queryExpression.value);}  )+
	       RPAREN
       ){ $value = new GroupingExpression(groupExpressions, $LPAREN.getStartIndex());}
;

operatorPrefix returns [QueryOperator value]
:
	PLUS {$value = QueryOperator.Must;}
	|   MINUS {$value = QueryOperator.MustNot;}
;

exactMatchExpression returns [ExactMatchExpression value]
:
	ExactMatchExpression {$value = new ExactMatchExpression(
								unescapeExact(cutFirstLastSymbols($ExactMatchExpression.text)),
								 $ExactMatchExpression.getStartIndex()); }
;

term returns [String value]
:
	Identifier {$value = unescapeTerm($Identifier.text);}
;

fuzzyTerm returns [FuzzyTermExpression expression]
@init
    {
        String text = null;
        int textStart = -1;
    }
:
	TILDE
    Identifier
    {
        text = unescapeTerm($Identifier.text);
        textStart = $Identifier.getStartIndex();
        $expression = new FuzzyTermExpression(unescapeTerm($Identifier.text), $Identifier.getStartIndex() );
    }
;