grammar ArrayInit;

// grammar rules

init : '{' value (',' value)* '}' ;

value : init
    |   INT
    ;

// lexing rules

INT : '-'?[0-9]+ ;

WS  : [ \t\r\n]+ -> skip ;
