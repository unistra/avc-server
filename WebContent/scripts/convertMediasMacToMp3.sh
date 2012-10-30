#!/bin/bash
# Convert medias from client MAC to MP3
# First argument: the course folder
# Second argument: the name of the file to create (with extension)
# Third argument: the name of the initial flv file (with extension)
# Fourth argument: the name of the initial aac file (with extension)

cd $1

if [ -f $3 ]
then

	/usr/bin/ffmpeg -v -1 -i $3 -vn -ar 48000 -ac 1 -ab 64k -f mp3 -threads 0 -y $2 &> /dev/null

elif [ -f $4 ]
then
	/usr/bin/ffmpeg -v -1 -i $4 -vn -ar 48000 -ac 1 -ab 64k -f mp3 -threads 0 -y $2 &> /dev/null
	
fi