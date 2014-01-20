grammar Schema;

schema :
	 (block '\n\n')*
       ;

block
  : LET type=ID plist=proplist (PD|SCOL) #LetType
  ;

prop
  : type=ptype name=ID #DeclareProp
  ; 

proplist
  : first=prop #AddFirstProp
  | proplist COM next=prop #AddNextProp
  ;

ptype
  : value=BI_INT #PropBIType
  | value=BI_BOOL #PropBIType
  | value=BI_STR #PropBIType
  ;

BI_INT : 'int' ;
BI_BOOL : 'bool' ;
BI_STR : 'string' ;

LET : 'let' ;
COL : ':' ;
SCOL : ';' ;
PD : '.' ;
COM : ',' ;

ID : [A-Za-z] [A-Za-z0-9]* ;
WS : [ \t\n\r]+ -> skip ;

