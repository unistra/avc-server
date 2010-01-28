#!/bin/bash
# Script which converts a video file into .mp4 format
# First argument: the video folder
# Second argument: the input video filename (with extension)
# Third argument: the output video name (without extension)

cd $1

nice -n 9 /usr/bin/mencoder -ofps 25 -of lavf -lavfopts format=mp4 -af lavcresample=44100 -vf-add harddup -vf-add scale=-11:720,expand=1280:720 -oac lavc -ovc lavc -lavcopts aglobal=1:vglobal=1:acodec=libfaac:abitrate=64:vcodec=libx264:vbitrate=1200:keyint=25 -o $3.mp4 "$2" > /dev/null