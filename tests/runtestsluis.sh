#!/bin/bash

# Modified version of runtests.sh
# PO IST-TP 2014 2015
# Shows stuff with color :)
# Changes (c) 2014 by Luis Silva
# 2014-11-26 10:07
# This comes with absolutely no warranty.
# Do not distribute without permission.

# Args: n x
# n:	max number of tests to show output of.
#		if negative, stop output after -n errors/fails
# x:	if set (can be anything), show cat diff files.

#######################
match_number='^-?[0-9]+$'
#######################


MAX_TESTS=0
if [ $# \> 0 ]; then
	if [[ $1 =~ $match_number ]]; then
		MAX_TESTS=$1
		if [ $1 -gt 0 ]; then
			echo "Displaying result of $MAX_TESTS test(s), max."
		elif [ $1 -eq 0 ]; then
			echo "Displaying all tests."
		else
			echo "Stopping output -($MAX_TESTS) errors/fails.";
		fi
	else
		echo "max_tests must be an integer."
		exit
	fi
fi


if [ $# \> 1 ]; then
	echo "Will cat .diff files."
fi

if [ $# \> 2 ]; then
	echo "Args: max_count show_diff" >&2; exit 1
	exit
fi

#######################
# Text formating strings
red_text="\x1b[31;1m"
green_text="\x1b[32;1m"
yellow_text="\x1b[33;1m"
gray_text="\x1b[90;1m"
blue_background="\x1b[46;1m"
gray_background="\x1b[100;1m"
black_background="\x1b[40;1m"
reset_text="\x1b[0m"
#######################

FAIL=0
ERROR=0
PASS=0
TOTAL=0



for x in tests/*.in; do

	SHOW_OUT=1
	if [ $MAX_TESTS -lt "0" ] && [ $((FAIL + ERROR + MAX_TESTS)) -ge 0 ]; then
		# Hide after -MAX_TESTS errors/fails
		SHOW_OUT=0
	elif [ $MAX_TESTS -ge "1" ] && [ $TOTAL -ge $MAX_TESTS ]; then
		# Hide after max
		SHOW_OUT=0
	fi



	if [ $SHOW_OUT -eq 1 ]; then
		echo ""
		echo -ne $gray_background
		echo -e "================ TEST $TOTAL ================$reset_text"
	fi #==========================
	
	if [ -e ${x%.in}.import ]; then

		# ECHO STUFF
		if [ $SHOW_OUT -eq 1 ]; then
			echo -ne $gray_text
			echo java -cp .:poof-support/:po-uilib/ -Dimport=${x%.in}.import -Din=$x -Dout=${x%.in}.outhyp poof.textui.Shell
			echo -ne $reset_text
			
			java -cp .:poof-support/:po-uilib/ -Dimport=${x%.in}.import -Din=$x -Dout=${x%.in}.outhyp poof.textui.Shell
		else
			java -cp .:poof-support/:po-uilib/ -Dimport=${x%.in}.import -Din=$x -Dout=${x%.in}.outhyp poof.textui.Shell > /dev/null
		fi #==========================
	else
	
		# ECHO STUFF
		if [ $SHOW_OUT -eq 1 ]; then
			echo -ne $gray_text
			echo java -cp .:poof-support/:po-uilib/ -Din=$x -Dout=${x%.in}.outhyp poof.textui.Shell
			echo -ne $reset_text
			
			java -cp .:poof-support/:po-uilib/ -Din=$x -Dout=${x%.in}.outhyp poof.textui.Shell
		else
			java -cp .:poof-support/:po-uilib/ -Din=$x -Dout=${x%.in}.outhyp poof.textui.Shell > /dev/null
		fi #==========================
		
	fi

	diff -cB -w ${x%.in}.out ${x%.in}.outhyp > ${x%.in}.diff ;
	DIFF_CODE=$?
	if [ -s ${x%.in}.diff ]; then
		# .diff file exits
		
		# ECHO STUFF
		if [ $SHOW_OUT -eq 1 ]; then
			echo -ne $red_text
			echo "FAIL: $x. See file ${x%.in}.diff" ;
			echo -ne $reset_text
			if [ $# \> 1 ]; then
				echo -e "$yellow_text====== diff start ======$reset_text"
				cat ${x%.in}.diff ;
				echo -e "$yellow_text====== diff end ======$reset_text"
			fi
		else
			echo -ne "$red_text$TOTAL$reset_text "
		fi #==========================
		
		FAIL=$((FAIL+1))
		
	elif [ $DIFF_CODE \> 1 ]; then
		# .diff file does not exist, but diff returned an error
		# such as file not found.
		
		# ECHO STUFF
		if [ $SHOW_OUT -eq 1 ]; then
			echo -ne $yellow_text
			echo "ERROR: diff exited with $DIFF_CODE" ;
			echo -ne $reset_text
		else
			echo -ne "$yellow_text$TOTAL$reset_text "
		fi #==========================
		
		ERROR=$((ERROR+1))
	else
		# no .diff file and no error. Should be good.

		
		# ECHO STUFF
		if [ $SHOW_OUT -eq 1 ]; then
			echo -ne $green_text
			echo "PASS"
			echo -ne $reset_text
		else
			echo -ne "$green_text$TOTAL$reset_text "
		fi #==========================
		
		PASS=$((PASS+1))
		rm -f ${x%.in}.diff ${x%.in}.outhyp ; 
	fi
	TOTAL=$((TOTAL+1))
done

rm -f *.dat
rm -f aaa
rm -f aa
rm -f saved01

	echo ""
echo "========================================="

echo -ne $red_text
echo -e "Fail:\t$FAIL"
echo -ne "$reset_text"

echo -ne $yellow_text
echo -e "Error:\t$ERROR"
echo -ne "$reset_text"

echo -ne $green_text
echo -e "Pass:\t$PASS"
echo -ne "$reset_text"
echo -e "Total:\t$TOTAL"
echo "========================================="




