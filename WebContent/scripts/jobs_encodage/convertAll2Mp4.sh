#!/bin/bash
# Script which converts a video file into .mp4 format
# First argument: the video folder
# Second argument: the input video filename (with extension)
# Third argument: the output video name (without extension)
# Four argument: the script to calculate padding
# Fifth argument: true for ipod video format. false for hd.

WIDTH=1280
HEIGHT=720
VPRE="-vpre normal"

#for ipod
if [ $5 == 'true' ]
then 
	WIDTH=640
	HEIGHT=480
	VPRE="-vpre normal -vpre ipod640"
fi

# Calculate padding
donnees=`bash $4 $1 $2 $WIDTH $HEIGHT`
L=`echo $donnees | cut -d: -f1`
H=`echo $donnees | cut -d: -f2`
PL=`echo $donnees | cut -d: -f3`
PHB=`echo $donnees | cut -d: -f4`

cd $1

#extraction du son en wav puis fabrication de l'aac
mplayer -really-quiet -vo null -vc null -ao pcm:fast:file=$3.wav $3.mp3
faac -b 64k -w -o $3.m4a $3.wav &> /dev/null

#Creation du fichier HD mp4
if [ $5 == 'true' ]
then
	/usr/bin/ffmpeg -v -1 -i $3.m4a -acodec copy -i "$2" -r 25 -vcodec libx264 -s "$L"x"$H" -padleft $PL -padright $PL -padtop $PHB -padbottom $PHB -aspect 4:3 -vpre normal -vpre ipod640 -crf 27 -threads 0 -y $3_tmp.mp4 &> /dev/null
	/usr/bin/qt-faststart $3_tmp.mp4 "$3"_ipod.mp4 &> /dev/null
else
	/usr/bin/ffmpeg -v -1 -i $3.m4a -acodec copy -i "$2" -r 25 -vcodec libx264 -s "$L"x"$H" -padleft $PL -padright $PL -padtop $PHB -padbottom $PHB -aspect 16:9 -vpre normal -crf 27 -threads 0 -y $3_tmp.mp4 &> /dev/null
	/usr/bin/qt-faststart $3_tmp.mp4 $3.mp4 &> /dev/null
fi	


#suppression des fichiers temporaires
rm $3_tmp.mp4
rm $3.wav
rm $3.m4a
