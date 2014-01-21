grammar Schema;

schema :
      ( block )*
    ;

block :
    letblock
  | clarifyblock
  ;

clarifyblock :
    CLARIFY ptype AS ID (PERIOD|SEMI)           #ClarifyType
  ;

letblock :
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

AS	: 'as' ;
INT	: 'int' ;
BOOL	: 'bool' ;
STR	: 'string' ;
LET	: 'let' ;
CLARIFY	: 'clarify' ;
COLON	: ':' ;
SEMI	: ';' ;
PERIOD	: '.' ;
COMMA	: ',' ;

ID : [A-Za-z] [A-Za-z0-9]* ;
WS : [ \t\n\r]+ -> skip ;
