#!/bin/bash
# Script which converts a video file into HD format
# First argument: the video folder
# Second argument: the input video filename (with extension)
# Third argument: Desired width
# Fourth argument : Desired height

PADL=0
PADHB=0
FW=0
FH=0
DesiW=$3
DesiH=$4

cd $1
# Padding calculation
vidh=`mplayer -nosound -frames 0 -vo null -ss 03:00:00 -really-quiet -identify "$2" | grep ID_VIDEO_HEIGHT=`
vidw=`mplayer -nosound -frames 0 -vo null -ss 03:00:00 -really-quiet -identify "$2" | grep ID_VIDEO_WIDTH=`
HAUTEUR=$(echo $vidh | sed s/ID_VIDEO_HEIGHT=//)
LARGEUR=$(echo $vidw | sed s/ID_VIDEO_WIDTH=//)

wforH=$(echo $((DesiH*($LARGEUR)/($HAUTEUR)))) #Largeur pour la hauteur voulue
hforW=$(echo $((DesiW*($HAUTEUR)/($LARGEUR)))) #Hauteur pour la largeur voulue

if (( $wforH < $DesiW ))
then
PAD=$(echo $((($DesiW-($wforH))/2)))
RESTE=$(echo $(($PAD % 2)))
  if (( $RESTE != 0 ))
  then
        PAD=$(echo $(($PAD + 1)))
  else
        PAD=$(echo $(($PAD)))
  fi
PADL=$PAD
FW=$wforH
FH=$DesiH
#nice -n 9 /usr/bin/ffmpeg -i "$2" -acodec libfaac -ab 64k -vcodec libx264 -s "$wforH"x720 -padleft $PAD -padright $PAD -aspect 16:9 -vpre normal -crf 27 -y $3_tmp.mp4 -threads 0

else
PAD=$(echo $((($DesiH-($hforW))/2)))
RESTE=$(echo $(($PAD % 2)))
  if (( $RESTE != 0 ))
  then
        PAD=$(echo $(($PAD + 1)))
  else
        PAD=$(echo $(($PAD)))
  fi
PADHB=$PAD
FW=$DesiW
FH=$hforW
#nice -n 9 /usr/bin/ffmpeg -i "$2" -acodec libfaac -ab 64k -vcodec libx264 -s 1280x$hforW -padtop $PAD -padbottom $PAD -aspect 16:9 -vpre normal -crf 27 -y $3_tmp.mp4 -threads 0
fi

echo "$FW:$FH:$PADL:$PADHB"