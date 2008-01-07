#!/bin/bash
# Script which converts a video format in .rm format
# First argument: the video folder
# Second argument: the input video filename (with extension)
# Third argument: the output video name (without extension)
cd $1
mencoder $2 -ovc raw -oac pcm -srate 48000 -vf scale=320:-3,format=i420 -o tmp_univ-r_av_video.avi 
/usr/local/bin/producer -i tmp_univ-r_av_video.avi -o $3.rm
rm tmp_univ-r_av_video.avi
