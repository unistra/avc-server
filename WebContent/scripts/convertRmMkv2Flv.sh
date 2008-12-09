#!/bin/bash
# Script which converts a .rm,.rv, or .mkv file into .flv format
# First argument: the video folder
# Second argument: the input video filename (with extension)
# Third argument: the output video name (without extension)
cd $1
/usr/bin/mencoder "$2" -quiet -oac lavc -ovc lavc -lavcopts acodec=mp2:abitrate=64  -o $3.avi
/usr/bin/ffmpeg -i $3.avi -ac 2 -ar 44100 -s 320x240 -b 400000 -y $3.flv
rm $3.avi
