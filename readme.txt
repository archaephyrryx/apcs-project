This project is a simple database query language that allows users to search a database by specifying conditions.
To run the project, run Interface.java and enter the sample queries specified in the file.
Sample queries are also specified in queries.txt.

**Using the Language**
The query language searches databases of a given format. The standard format for a database is a txt file of comma-separated rows, with one entry on each row:
The first row contains titles of columns, and the second row contains the type of each column, so far, either String or Num. For example:
Title,Author,Year,Genre
String,String,Num,String

The format for a query is:
retrieve <returnType> from <database> ;
OR
retrieve <returnType> from <database> where <columnTitle> <operator> <searchObj> ;

ex. retrieve Title from Book ;
OR
retrieve Artist from Song where Genre = Pop ;

See Interface.java or queries.txt for more sammple queries.

**Breakdown by Keyword**
There are three choices for returnType:

"all": returns the full row for an entry satisfying a given condition
"count": returns the number of entries satisfying a given condition
<columnTitle> (ex. "Author"): returns all rows under the specified columnTitle satisfying a given condition. If it has multiple words, it must be contained in double quotes.

<database>: the name of the database. Ones included are Book and Song.

<columnTitle>: the name of the column involving the condition the user wants to search for. If it has multiple words, it must be contained in double quotes.

<operator>: for Strings, = or !=
            for Nums, =, !=, <, >, <=, or >=
<searchObj> (ex. 1960 or "The Beatles"): the String or number the user wants to search for. If it has multiple words, it must be contained in double quotes.

; is the standard format for ending queries.


**Limits on the Query Language**
Currently, the query language allows the user to return all, count, or exactly one column title to return, and exactly one condition. This language can be expanded in the future to allow the return of multiple column titles and the specificiation of multiple conditions: for example: retrieve Title,Artist from Song where Time > 4 && Genre = Rock

**Java Files**

Database.java: stores database from txt file in 2D array

Interface.java: takes user queries, sends to Parser

Parser.java: interprets user queries from interface, sends to Engine for execution

EngineGen.java: runs general queries from Parser (i.e. no "where" clause); prints results

EngineString.java: runs queries from Parser involving conditions dealing with Strings; prints results

EngineNum.java: runs queries from Parser involving conditions dealing with numbers; prints results

**Txt Files**

bookData.txt: sample database with column titles Title, Author, Year, Genre

sampleData.txt: sample database with column titles Title, Time, Artist, Album, Genre

queries.txt: sample queries (also found in Interface.java). 
Can be run all at once from the command line with "cat queries.txt | java Interface"

**Other**
cs1: provides Keyboard functionality



