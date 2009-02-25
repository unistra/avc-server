#!/bin/bash
TAILLE=`cat $1/$2 | wc -c`
if [ $TAILLE -gt 0 ]
then
	chmod +x ./mp3cleaner
	./mp3cleaner $1 $2
else
	echo "!!!!!!!!!! Invalid MP3 file for the course $1 (size:0) !!!!!!!!!!"
fi
