This project is a simple database query language that allows users to search a database while specifying conditions.

Two implementations were developed in parallel, one with a more advanced
query interface in the main directory, and another with a more flexible
grammar based on the ANTLR parser generator in the "antlr/" subdirectory.
There was not time to combine the two approaches into a single program
that has a rich query interface and a flexible grammar, for the antlr
implementation, see the readme.txt file in that directory.

To run the project, run Interface.java and enter the sample queries specified in the file.
Sample queries are also specified in queries.txt.

**Using the Language**
The query language searches databases of a given format. The standard format for a database is a txt file of comma-separated rows, with one entry in each row.
The first row contains titles of columns, and the second row contains the type of each column, either String or Num. For example:
Title,Author,Year,Genre
String,String,Num,String

The format for a query is:
retrieve <returnType> from <database> ;
OR
retrieve <returnType> from <database> where <columnTitle> <operator> <searchObj> ;

ex. retrieve Title from Book ;
OR
retrieve Artist from Song where Genre = Pop ;

(Note that there is a space between the last word and the semicolon.)

See Interface.java or queries.txt for more sample queries.

**Breakdown by Keyword**
There are three choices for returnType:
"all": returns the full row of information for an entry satisfying a given condition
"count": returns the number of entries satisfying a given condition
<columnTitle> (ex. "Author"): returns all rows under the specified columnTitle satisfying a given condition. If it has multiple words, it must be contained in double quotes.

<database>: the name of the database. Included databases are Book and Song.

<columnTitle>: the name of the column involved in the condition the user wants to search for. If it has multiple words, it must be contained in double quotes.

<operator>: for Strings, = or !=
            for Nums, =, !=, <, >, <=, or >=
            
<searchObj> (ex. 1960 or "The Beatles"): the String or number the user wants to search for. If it has multiple words, it must be contained in double quotes.

; is the standard format for ending queries.


**Limits of the Query Language**
Currently, the query language can return all, count, or exactly one column title, with the user having specified at most one condition. This language can be expanded in the future to make it possible to return multiple column titles and to allow specification of multiple conditions.
For example, a future query might look like: retrieve Title,Artist from Song where Time > 4 && Genre = Rock

Note that Time in the Song database is currently written as something like "3.21". This is a bit misleading, as this refers to 3 minutes 21 seconds, not 3.21 minutes. 

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



