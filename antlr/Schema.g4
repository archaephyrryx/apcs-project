grammar Schema;

schema :
      ( command '\n' )*
    ;

command :
    letblock
  | clarifyblock
  | loadcmd
  | querycmd
  ;

querycmd: GET ID matchlist (PERIOD|SEMI)		#PerformQuery
     ;

matchlist : match					#GetFirstMatch
	  | matchlist (COMMA) match			#GetNextMatch
	  ;

match : ID comp value					#EvalMatch
      ;

comp : EQUALS						#EqualTo
     ;

value : v=NUMBER					#EvalNumberValue
      | v=(TRUE|FALSE)					#EvalBoolValue
      | v=QSTRING					#EvalQStringValue
      ;

clarifyblock :
    CLARIFY ptype AS ID (PERIOD|SEMI)           #ClarifyType
  ;

letblock :
    LET ID proplist (PERIOD|SEMI)		# LetType
    ;

loadcmd :
    LOAD ID QSTRING (SEMI|PERIOD)		#Load
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

csvfile : (csvline)*
	;

csvline : csvrecord '\n' 			# GetCSVRecord
	;

csvrecord : csventry				# GetFirstCSVEntry
          | csvrecord COMMA csventry		# GetNextCSVEntry
	  ;

csventry : v=NUMBER				# GetCSVNumber
	 | v=(TRUE|FALSE)			# GetCSVBool
	 | v=QSTRING				# GetCSVQstr
	 ;

fragment QUOTE : '"';
fragment ESC_QUOTE : '\\"' ;
fragment ESC_BSLASH : '\\\\' ;
QSTRING : QUOTE ( ~('"'|'\\')+ | ESC_QUOTE | ESC_BSLASH )* QUOTE ;

TRUE    : 'true' ;
FALSE   : 'false' ;

NUMBER : DIGIT+ ;
fragment DIGIT : [0-9] ;

GET     : 'get' ;
AS	: 'as' ;
INT	: 'int' ;
BOOL	: 'bool' ;
STR	: 'string' ;
LET	: 'let' ;
LOAD	: 'load' ;
CLARIFY	: 'clarify' ;
COLON	: ':' ;
SEMI	: ';' ;
PERIOD	: '.' ;
COMMA	: ',' ;
EQUALS :  '=' ;

ID : [A-Za-z] [A-Za-z0-9]* ;
WS : [ \t\n\r]+ -> skip ;
