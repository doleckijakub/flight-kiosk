JAVAC = javac
JAVAC_FLAGS = -g -d bin -sourcepath src -cp bin

SOURCES = $(shell find src -name *.java)

all: compile

compile:
	mkdir -p bin
	$(JAVAC) $(JAVAC_FLAGS) $(SOURCES)

run: compile
	java -cp bin Main

clean:
	rm -rf bin/*

.PHONY: all compile run clean