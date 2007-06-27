#!/bin/bash
# Script qui reencode un fichier video wmv en rm
# Prend en argument le repertoire du fichier video
cd $1
mplayer "enregistrement-video.rm" -ao "pcm:file=enregistrement-micro.wav" -vc "dummy" -vo "null"
touch test2