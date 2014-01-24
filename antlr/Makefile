ANTLR = java -jar /usr/local/lib/antlr-4.0-complete.jar

MOOSES	= \
	SchemaBaseListener.java \
	SchemaBaseVisitor.java \
	SchemaLexer.java \
	SchemaListener.java \
	SchemaParser.java \
	SchemaVisitor.java

SRCS = \
	QString.java \
	Symbol.java \
	AVLTree.java \
	Entry.java


OBJS = $(SRCS:%.java=%.class) $(MOOSES:%.java=%.class)

.SUFFIXES: .java .class

.java.class:
	javac $<

all: Main.class $(OBJS)

Load.class: Load.java $(OBJS)
	javac Load.java

Query.class: Query.java $(OBJS)
	javac Query.java

Main.class: Main.java Load.class Query.class
	javac Main.java


$(MOOSES): Schema.g4
	$(ANTLR) -visitor Schema.g4

clean:
	$(RM) *.class *.tokens Schema*.java
