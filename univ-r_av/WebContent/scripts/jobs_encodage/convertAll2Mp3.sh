#!/bin/bash
# Script which creates MP3 podcasts from RM files
# First argument: the RM folder
# Second argument: the name of the file to create (without extension)
# Third argument: the extension of the initial file

cd $1

# Transformation du fichier flv en fichier mp3
/usr/bin/ffmpeg -v -1 -i $2.$3 -vn -ar 48000 -ac 1 -ab 64k -f mp3 -threads 0 -y $2.mp3 &> /dev/null
