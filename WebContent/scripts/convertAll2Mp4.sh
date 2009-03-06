#!/bin/bash
# Script which converts a video file into .mp4 format
# First argument: the video folder
# Second argument: the input video filename (with extension)
# Third argument: the output video name (without extension)

cd $1
nice -n 9 /usr/bin/ffmpeg -i $2 -acodec aac -ab 64k -vcodec h264 -b 1200k -s 1280x720 -y $3.mp4
#nice -n 9 /usr/bin/ffmpeg -i $2 -acodec libfaac -ab 64k -vcodec libx264 -b 1200k -s 1280x720 -y $3.mp4
#nice -n 9 /usr/bin/ffmpeg -i $2 -acodec aac -ab 128k -vcodec h264 -b 3000k -s 1920x1080 -y $3.mp4