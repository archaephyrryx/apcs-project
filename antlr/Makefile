ANTLR = java -jar /usr/local/lib/antlr-4.0-complete.jar

MOOSES	= \
	   SchemaBaseListener.java \
	   SchemaBaseVisitor.java \
	   SchemaLexer.java \
	   SchemaListener.java \
	   SchemaParser.java \
	   SchemaVisitor.java

SRCS = \
	   Load.java \
	   Symbol.java 

OBJS = $(SRCS:%.java=%.class) $(MOOSES:%.java=%.class)

.SUFFIXES: .java .class

.java.class:
	javac $<

all: Main.class $(OBJS)

Main.class: Main.java $(OBJS)
	javac Main.java

$(MOOSES): Schema.g4
	$(ANTLR) -visitor Schema.g4

clean:
	$(RM) *.class *.tokens Schema*.java
