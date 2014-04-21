JC = javac
JFLAGS = -g -Xlint:all -cp junit-4.11.jar:.
CHECK = java -jar checkstyle-5.7-all.jar -c jhu_checks.xml

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = SparseGraph.java \
          Kevin.java

TESTS = TestGraph.java

default: classes
classes: $(CLASSES:.java=.class) $(TESTS:.java=.class)
test:    $(TESTS:.java=.class)
clean: ; rm -f *.class
check: ; $(CHECK) $(CLASSES)
testrun: $(TESTS:.java=.class)
	java -cp junit-4.11.jar:. org.junit.runner.JUnitCore $(TESTS:.java=)
