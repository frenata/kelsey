grammar CSV;

file : hdr row+ ;
hdr  : row ;
row  : field (',' field)* '\r'? '\n' ;

field
    : TEXT
    | STRING
    |
    ;

// lexing rules

TEXT   : ~[,\n\r"]+ ;
STRING : '"' ('""'|~'"')* '"' ;
