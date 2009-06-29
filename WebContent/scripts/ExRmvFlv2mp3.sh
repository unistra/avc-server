#!/bin/bash
# Script which creates MP3 podcasts from RM files
# First argument: the RM folder
# Second argument: the name of the file to create (without extension)
# Third argument: the extension of the initial file

cd $1
# Enregistrement interm�diaire en avi
#/usr/bin/mencoder $2.$3 -quiet -oac pcm -ovc frameno -o $2.avi
# Transformation du fichier avi en mp3
#/usr/bin/ffmpeg -i $2.avi -vn -ar 48000 -ac 1 -ab 64 -f mp3 $2.mp3
#rm $2.avi

# Transformation du fichier flv en fichier mp3
/usr/bin/mplayer $2.$3 -dumpaudio -dumpfile $2.mp3

