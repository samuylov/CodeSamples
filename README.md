# CodeSamples

Parser of human-readable search request to elastic search queries syntax.

Supported experssions:
1) before top-level expression MUST/MUST NOT operators are supported. According to operators top-level expression will be transformed to:
- no-operator: to should queries of bool query
- MUST (+ operator): to must queries of bool query
- MUST NOT (- operator): to must not operator of bool query

Supported expressions:
1) usual term - consists of characters and numbers
2) wildcard - term with ? and *
3) fuzzy - term with ~ prefix. This expression means that term can have some errors. see https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-fuzzy-query.html
4) exact phrase - starts and ends with double quotes("). transforms to exact phrase. see https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-match-query-phrase.html
5) synonyms - subexpressions, divided by |. transforms to dismax query. see https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-dis-max-query.html
6) grouping - subexpressions, surrounded by (). subexpressions transfroms to should queries fo new bool query.


examples of search requests:
+fox browh|red -wolf 
+fox (browh grey)|(red green) -wolf 
+fox (browh grey)|(red green) -~wilf 
