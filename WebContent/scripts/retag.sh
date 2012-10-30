#!/bin/bash
#script which retag medias of a course
# First argument: the course folder
# Second argument: the course id
# Third argument: the mediatype

export MALLOC_CHECK_=0

cd $1

#Extraction des Metadonnées
AUTHOR=`cat "./description.txt" | grep Author: | cut -d":" -f2`
FORMATION=`cat "./description.txt" | grep Formation: | cut -d":" -f2`
TITLE=`cat "./description.txt" | grep Title: | cut -d":" -f2`
SUBJECT=`cat "./description.txt" | grep Subject: | cut -d":" -f2`
DATE=`cat "./description.txt" | grep Date: | cut -d":" -f2 | cut -d"/" -f1`
TYPE=`cat "./description.txt" | grep Type: | cut -d":" -f2`
DURATION=`cat "./description.txt" | grep Duration: | cut -d":" -f2`
COMMENT=`cat "./description.txt" | grep Comment: | cut -d":" -f2`


# MP3
if (( (2 & $3) > 0 ))
then
	/usr/bin/eyeD3 -t "$TITLE" -a "$AUTHOR" -Y "$DATE" -A "$FORMATION" -c "::$COMMENT" --to-v2.3 ./$2.mp3 &> /dev/null
fi


# OGG
if (( (4 & $3) > 0 ))
then
	/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" ./$2.ogg &> /dev/null
fi

# VIDEOSLIDE
if (( (32 & $3) > 0 ))
then
	/usr/bin/AtomicParsley ./"$2"_videoslide.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
fi

# VIDEOSLIDE IPOD
if (( (256 & $3) > 0 ))
then
	/usr/bin/AtomicParsley ./"$2"_videoslide_ipod.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
fi


# MP4 HD
if (( (128 & $3) > 0 ))
then
	/usr/bin/AtomicParsley ./$2.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
	/usr/bin/AtomicParsley ./"$2"_ipod.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
fi

