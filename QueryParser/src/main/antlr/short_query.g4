parser grammar short_query;
options {tokenVocab = QueryLexer;}

query
	:  queryItem+
	;

queryItem
	:  operatorPrefix?  queryExpression
	;

queryExpression
	:
    expression_WithoutSynonym
            (SYNONYM expression_WithoutSynonym)*
	;

expression_WithoutSynonym
    :   exactMatchExpression
 	|	WildcardExpression
 	|	term
 	|   fuzzyTerm
    | LPAREN (queryExpression)+ RPAREN
    ;

operatorPrefix
	:   PLUS
	|   MINUS
	;

exactMatchExpression
	:   ExactMatchExpression
	;

term
	:   Identifier
	;

fuzzyTerm
	:
	TILDE Identifier
;
