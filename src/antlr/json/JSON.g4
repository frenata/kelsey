grammar JSON ;

json
    : object
    | array
    ;

object
    : '{' pair (',' pair)* '}'
    | '{' '}' // empty object
    ;

pair : STRING ':' value ;

array
    : '[' value (',' value)* ']'
    | '[' ']' // empty array
    ;

value
    : STRING
    | NUMBER
    | object
    | array
    | 'true'
    | 'false'
    | 'null'
    ;

NUMBER
    : '-'? INT '.' [0-9]+ EXP? // 1.35, 1.35E-9, 0.3, -4.5
    | '-'? INT EXP             // 1e10, -3e4
    | '-'? INT                 // -3, 54
    ;

fragment INT : '0' | [1-9] [0-9]* ;
fragment EXP : [Ee] [+\-]? INT ;

STRING : '"' (ESC | ~["\\])* '"' ;

fragment ESC : '\\' (["\\/bfnrt] | UNICODE) ;
fragment UNICODE : 'u' HEX HEX HEX HEX ;
fragment HEX : [0-9a-fA-F] ;

WS : [ \t\r\n]+ -> skip ;
