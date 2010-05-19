#!/bin/bash
# Script which creates MP3 podcasts from RM files
# First argument: the RM folder
# Second argument: the name of the file to create (without extension)
# Third argument: the extension of the initial file

cd $1

# Transformation du fichier mp3 en fichier ogg
/usr/bin/ffmpeg -i $2.$3 -acodec vorbis -aq 50 -ac 2 -y $2.ogg

