grammar Expr ;

s : e ;
e   : e (MUL | DIV) e   # Mult
    | e (ADD | SUB) e    # Add
    | INT        # Int
    ;

MUL  : '*' ;
DIV  : '/' ;
ADD  : '+' ;
SUB  : '-' ;
INT  : '-'?[0-9]+ ;
WS : [ \t\r\n]+ -> skip ;
