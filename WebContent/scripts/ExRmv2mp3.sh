#!/bin/bash
# Script qui créé des podcasts MP3 de fichiers RM
# Prend en argument le répertoire et le nom du fichier à créer

cd $1
# Enregistrement intermédiaire en avi
/usr/bin/mencoder $2.rm -quiet -oac pcm -ovc frameno -o $2.avi
# Transformation du fichier avi en mp3
/usr/bin/ffmpeg -i $2.avi -vn -ar 48000 -ac 1 -ab 64 -f mp3 $2.mp3
rm $2.avi
