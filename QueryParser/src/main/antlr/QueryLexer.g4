lexer grammar QueryLexer;

/////////////////////////////////////////////////// LEXER //////////////////////////////////////////////////////////////
Identifier
	:   ESCAPED_WITH_OPERATOR? (Digit | Letter  | SPECIAL_CHAR | ESCAPED_CHAR)+
		(MINUS | PLUS | Digit | Letter | ESCAPED_CHAR | SPECIAL_CHAR )*
	;

WildcardOperator
	:  '?'+
	|  '*'
	;

WildcardExpression
	:   WildcardOperator Identifier (WildcardOperator Identifier)* WildcardOperator?
	|   Identifier (WildcardOperator Identifier)* WildcardOperator
	|   Identifier (WildcardOperator Identifier)+
	;

ExactMatchExpression
	:
    	'"' ('\\"' | '\\\\' | ~["\\])+ '"'
	;

ESCAPED_CHAR    :   '\\'[(){}\\<>|"~?*] ;
ESCAPED_WITH_OPERATOR : '\\'[^(){}<>\\|":?*~+-];
PLUS            :   '+' ;
MINUS           :   '-' ;
COMMA           :   ','	;
DOT             :   '.'	;
LPAREN          :   '(' ;
RPAREN          :   ')' ;
SYNONYM         :   '|' ;
TILDE           :   '~';
SPECIAL_CHAR    :   [!@#$%&=[\],.:^;/'] | '\u2116' | '\u0060' ; // / и ^ (){} \ | " : ? *  через escape


Digit
    :   [0-9]
    ;

Letter
    :   [a-zA-Z_] |
        A | Be | Ve | Ghe | De | Ie | Iee | Zhe | Ze | I | I_ | Ka | El | Em | En |
        O | Pe | Er | Es | Te | U | Ef | Ha | Tse | Che | Sha | Shcha | Hs | Yeru |
        Ss | E | Yu | Ya | Io
    ;

fragment
A		: '\u0430' | '\u0410';

fragment
Be		: '\u0431' | '\u0411';

fragment
Ve		: '\u0432' | '\u0412';

fragment
Ghe		: '\u0433' | '\u0413';

fragment
De		: '\u0434' | '\u0414';

fragment
Ie		: '\u0435' | '\u0415';

fragment
Iee		: '\u0451' | '\u0401';

fragment
Zhe		: '\u0436' | '\u0416';

fragment
Ze		: '\u0437' | '\u0417';

fragment
I		: '\u0438' | '\u0418';

fragment
I_		: '\u0439' | '\u0419';

fragment
Ka		: '\u043A' | '\u041A';

fragment
El		: '\u043B' | '\u041B';

fragment
Em		: '\u043C' | '\u041C';

fragment
En		: '\u043D' | '\u041D';

fragment
O		: '\u043E' | '\u041E';

fragment
Pe		: '\u043f' | '\u041f';

fragment
Er		: '\u0440' | '\u0420';

fragment
Es		: '\u0441' | '\u0421';

fragment
Te		: '\u0442' | '\u0422';

fragment
U		: '\u0443' | '\u0423';

fragment
Ef		: '\u0444' | '\u0424';

fragment
Ha	    : '\u0445' | '\u0425';

fragment
Tse		: '\u0446' | '\u0426';

fragment
Che		: '\u0447' | '\u0427';

fragment
Sha		: '\u0448' | '\u0428';

fragment
Shcha	: '\u0449' | '\u0429';

fragment
Hs		: '\u044A' | '\u042A';

fragment
Yeru    : '\u044B' | '\u042B';

fragment
Ss		: '\u044C' | '\u042C';

fragment
E		: '\u044D' | '\u042D';

fragment
Yu		: '\u044E' | '\u042E';

fragment
Ya		: '\u044F' | '\u042F';

fragment
Io		: '\u0451';

// English letters
fragment EA
	:	'A' | 'a';

fragment EB
	:	'B' | 'b';

fragment EC
	:	'C' | 'c';

fragment ED
	:	'D' | 'd';

fragment EE
	:	'E' | 'e';

fragment EF
	:	'F' | 'f';

fragment EG
	:	'G' | 'g';

fragment EH
	:	'H' | 'h';

fragment EI
	:	'I' | 'i';

fragment EJ
	:	'J' | 'j';

fragment EK
	:	'K' | 'k';

fragment EL
	:	'L' | 'l';

fragment EM
	:	'M' | 'm';

fragment EN
	:	'N' | 'n';

fragment EO
	:	'O' | 'o';

fragment EP
	:	'P' | 'p';

fragment EQ
	:	'Q' | 'q';

fragment ER
	:	'R' | 'r';

fragment ES
	:	'S' | 's';

fragment ET
	:	'T' | 't';

fragment EU
	:	'U' | 'u';

fragment EV
	:	'V' | 'v';

fragment EW
	:	'W' | 'w';

fragment EX
	:	'X' | 'x';

fragment EY
	:	'Y' | 'y';

fragment EZ
	:	'Z' | 'z';


WS  :  [ \t\r\n\u000C\u00A0]+ -> channel(HIDDEN)
    ;

UNKNOWN:
	.
	;