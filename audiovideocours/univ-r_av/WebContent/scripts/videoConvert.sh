#!/bin/bash
# Script which converts a video format in .rm format
# First argument: the video folder
# Second argument: the input video filename (with extension)
# Third argument: the output video name (without extension)
cd $1
mencoder $2 -ovc copy -oac pcm -o tmp_univ-r_av_1.avi
ffmpeg -vcodec rawvideo -acodec pcm_s16le -i tmp_univ-r_av_1.avi -ar 48000 -s 320x240 -y tmp_univ-r_av_2.avi
producer -i tmp_univ-r_av_2.avi -o $3.rm
rm tmp_univ-r_av_1.avi tmp_univ-r_av_2.avi
