#!/bin/bash
# Script which converts a video file into .mp4 format
# First argument: the video folder
# Second argument: the input video filename (with extension)
# Third argument: the output video name (without extension)
# Four argument: the script to calculate padding

# Calculate padding
donnees=`bash $4 $1 $2 1280 720`
L=`echo $donnees | cut -d: -f1`
H=`echo $donnees | cut -d: -f2`
PL=`echo $donnees | cut -d: -f3`
PHB=`echo $donnees | cut -d: -f4`

cd $1

#nice -n 9 /usr/bin/mencoder -really-quiet -ofps 25 -of lavf -lavfopts format=mp4 -af lavcresample=44100 -vf-add harddup -vf-add scale=-11:720,expand=1280:720 -oac lavc -ovc lavc -lavcopts aglobal=1:vglobal=1:acodec=libfaac:abitrate=64:vcodec=libx264:vbitrate=1200:keyint=25 -o $3_tmp.mp4 "$2" > /dev/null
nice -n 9 /usr/bin/ffmpeg -v -1 -i "$2" -acodec libfaac -ab 64k -vcodec libx264 -s "$L"x"$H" -padleft $PL -padright $PL -padtop $PHB -padbottom $PHB -aspect 16:9 -vpre normal -crf 27 -y $3_tmp.mp4 -threads 0 > /dev/null

nice -n 9 /usr/bin/qt-faststart $3_tmp.mp4 $3.mp4
rm $3_tmp.mp4