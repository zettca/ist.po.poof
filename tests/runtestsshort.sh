#!/bin/bash

tests=0;
tests_passed=0;
tests_failed=0;

for x in tests/*.in; do
	let tests+=1;
	if [ -e ${x%.in}.import ]; then
		java -cp . -Dimport=${x%.in}.import -Din=$x -Dout=${x%.in}.outhyp poof.textui.Shell;
	else
		java -cp . -Din=$x -Dout=${x%.in}.outhyp poof.textui.Shell;
	fi

	diff -cB -w ${x%.in}.out ${x%.in}.outhyp > ${x%.in}.diff ;
	if [ -s ${x%.in}.diff ]; then
		let tests_failed+=1;
	else
		let tests_passed+=1;
		rm -f ${x%.in}.diff ${x%.in}.outhyp ; 
	fi
done

rm -f *.dat

echo " Results for $tests tests:"
echo -e " \e[32m✔\e[39m $tests_passed tests passed"
echo -e " \e[31mø\e[39m $tests_failed tests failed"
