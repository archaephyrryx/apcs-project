grammar Schema;

schema :
      ( block '\n\n' )*
    ;

block :
    LET ID proplist (PERIOD|SEMI)		# LetType
  ;

proplist :
    prop					#AddFirstProp
  | proplist COMMA prop				#AddNextProp
  ;

prop :
    ptype ID					#DeclareProp
  ; 

ptype :	
    t=INT					# Getsym
  | t=BOOL					# Getsym
  | t=STR					# Getsym
  | t=ID					# Getsym
  ;

INT	: 'int' ;
BOOL	: 'bool' ;
STR	: 'string' ;
LET	: 'let' ;
COLON	: ':' ;
SEMI	: ';' ;
PERIOD	: '.' ;
COMMA	: ',' ;

ID : [A-Za-z] [A-Za-z0-9]* ;
WS : [ \t\n\r]+ -> skip ;
