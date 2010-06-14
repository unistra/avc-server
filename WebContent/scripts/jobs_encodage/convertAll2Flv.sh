#!/bin/bash
# Script which converts a video file into .mp4 format
# First argument: the video folder
# Second argument: the input video filename (with extension)
# Third argument: the output video name (without extension)
# Four argument: the script to calculate padding

# Calculate padding
donnees=`bash $4 $1 $2 320 240`
L=`echo $donnees | cut -d: -f1`
H=`echo $donnees | cut -d: -f2`
PL=`echo $donnees | cut -d: -f3`
PHB=`echo $donnees | cut -d: -f4`

cd $1
/usr/bin/ffmpeg -v -1 -i "$2" -ac 2 -ar 44100 -b 400000 -s "$L"x"$H" -padleft $PL -padright $PL -padtop $PHB -padbottom $PHB -aspect 4:3 -y "$3".flv &> /dev/null
