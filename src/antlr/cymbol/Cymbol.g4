grammar Cymbol ;

//file : (functionDecl | varDecl)+ ;
ws : WS ;

WS : [ \t\r\n]+ -> skip ;
