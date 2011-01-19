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
ASPECT="-aspect 16:9"
AUDIO="-i $3.m4a -acodec copy"
SUFFNAME=""

#for ipod
if [ $5 == 'true' ]
then 
	WIDTH=640
	HEIGHT=480
	VPRE="-vpre normal -vpre ipod640"
	ASPECT="-aspect 4:3"
	SUFFNAME="_ipod"
fi

# Calculate padding
donnees=`bash $4 $1 $2 $WIDTH $HEIGHT`
L=`echo $donnees | cut -d: -f1`
H=`echo $donnees | cut -d: -f2`
PL=`echo $donnees | cut -d: -f3`
PHB=`echo $donnees | cut -d: -f4`

cd $1

# test if no sound
if [ `stat -c '%s' $3.mp3` -eq 0 ]
then
	AUDIO=""
fi


#extraction du son en wav puis fabrication de l'aac
mplayer -really-quiet -vo null -vc null -ao pcm:fast:file=$3.wav $3.mp3 &> /dev/null
faac -b 128k -w -o $3.m4a $3.wav &> /dev/null

#Creation du fichier HD mp4
#echo "/usr/bin/ffmpeg -v -1 $AUDIO -i "$2" -r 25 -vcodec libx264 -s "$L"x"$H" -padleft $PL -padright $PL -padtop $PHB -padbottom $PHB $ASPECT $VPRE -crf 27 -g 100 -threads 0 -y $3_tmp.mp4 &> /dev/null"
/usr/bin/ffmpeg -v -1 $AUDIO -i "$2" -r 25 -vcodec libx264 -s "$L"x"$H" -padleft $PL -padright $PL -padtop $PHB -padbottom $PHB $ASPECT $VPRE -crf 27 -g 100 -threads 0 -y $3_tmp.mp4 &> /dev/null
/usr/bin/qt-faststart $3_tmp.mp4 "$3""$SUFFNAME".mp4 &> /dev/null


#suppression des fichiers temporaires
rm $3_tmp.mp4 &> /dev/null
rm $3.wav &> /dev/null
rm $3.m4a &> /dev/null
