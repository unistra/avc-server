#!/bin/bash
# Script qui reencode un fichier video wmv en rm
# Prend en argument le repertoire du fichier video
cd $1
mencoder enregistrement-video.wmv -ovc copy -oac pcm -o Tmp.avi 
ffmpeg -vcodec rawvideo -acodec pcm_s16le -i Tmp.avi -y tmp.avi
producer -i tmp.avi -o enregistrement-video.rm
rm *.avi
