#!/bin/bash
# Script which creates MP3 podcasts from RM files
# First argument: the RM folder
# Second argument: the name of the file to create (without extension)
# Third argument: the extension of the initial file

cd $1

# Ajout des meta-données dans le flv
/usr/bin/yamdi -i $2.$3 -o $2-tmp.$3
rm $2.$3
mv $2-tmp.$3 $2.$3
