all:
	javac -encoding UTF-8 -cp . `find . -name *.java`

clean:
	rm -rf *.class
	rm -rf `find . -name *.class`

cleanall:	clean
	rm aa aaa saved01

release:	all
	jar cf poof.jar poof
	jar cf pt.jar pt

run:	all
	java poof.textui.Shell

test:	all
	./tests/runtests.sh

testshort:	all
	./tests/runtestsshort.sh

testfail:	all
	./tests/runtestsfail.sh

testluis:	all
	./tests/runtestsluis.sh