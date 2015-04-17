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
		echo -e " \e[31mø\e[39m $x" ;
		#cat ${x%.in}.diff ;
		#exit 0 ;
	else
		let tests_passed+=1;
		#echo -e " \e[32m✔\e[39m $x." ;
		rm -f ${x%.in}.diff ${x%.in}.outhyp ; 
	fi
done

rm -f *.dat

echo "================================"
echo " Done. Passed $tests_passed/$tests"
echo -e "   \e[32m✔\e[39m $tests_passed tests passed (hidden)"
echo -e "   \e[31mø\e[39m $tests_failed tests failed"
echo "================================"
