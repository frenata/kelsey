grammar Scheme ;

sexpr
    : '(' sexpr* ')'
    | SYMBOL
    | value
    ;


value
    : STRING
    | NUMBER
    | 'nil'
    ;

SYMBOL : [a-zA-Z_]+ ;

NUMBER : '-'?[0-9]+ ;

STRING : '"' .*? '"' ;

WS : [ \t\r\n]+ -> skip ;
