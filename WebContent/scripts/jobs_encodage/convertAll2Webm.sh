#!/bin/bash
# Script to convert all to webm
# First argument: the course folder
# Second argument: the name of the file to create (without extension)
# Third argument: the extension of the initial file

cd $1

/usr/bin/ffmpeg -v -1 -i $2.$3 -threads 0 -f webm -vcodec libvpx -g 120 -level 216 -profile 0 -qmax 42 -qmin 10 -acodec libvorbis -aq 90 -ac 2 $2.webm &> /dev/null
