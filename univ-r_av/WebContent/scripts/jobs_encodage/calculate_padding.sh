#!/bin/bash
# Script which calculate padding for a video
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
#vidh=`mplayer -nosound -frames 0 -vo null -ss 03:00:00 -really-quiet -identify "$2" | grep ID_VIDEO_HEIGHT=`
#vidw=`mplayer -nosound -frames 0 -vo null -ss 03:00:00 -really-quiet -identify "$2" | grep ID_VIDEO_WIDTH=`
vidw=`ffmpeg -i "$2" 2>&1 | grep "Video:"  | cut -d',' -f3 | cut -d' ' -f2 | cut -d'x' -f1`
vidh=`ffmpeg -i "$2" 2>&1 | grep "Video:"  | cut -d',' -f3 | cut -d' ' -f2 | cut -d'x' -f2`
#HAUTEUR=$(echo $vidh | sed s/ID_VIDEO_HEIGHT=//)
#LARGEUR=$(echo $vidw | sed s/ID_VIDEO_WIDTH=//)
HAUTEUR=$vidh
LARGEUR=$vidw

#Anamorphic pixels correction
PAR=`ffmpeg -i "$2" 2>&1 | grep "Video:" | cut -d',' -f3 | cut -d' ' -f4`
if [[ $PAR == "" ]]
then
PAR="1:1"
fi
PARX=$(echo $PAR | cut -d':' -f1)
PARY=$(echo $PAR | cut -d':' -f2)
LARGEUR=$(echo " $LARGEUR * $PARX / $PARY " | bc)

wforH=$(echo $((DesiH*($LARGEUR)/($HAUTEUR)))) #Largeur pour la hauteur voulue
hforW=$(echo $((DesiW*($HAUTEUR)/($LARGEUR)))) #Hauteur pour la largeur voulue

if (( $wforH < $DesiW )) #Padding lateral
then
PAD=$(echo $((($DesiW-($wforH))/2)))
RESTE=$(echo $(($PAD % 2)))
  if (( $RESTE != 0 ))
  then
        PAD=$(echo $(($PAD - 1)))
  fi

RESTE2=$(echo $(($wforH % 2)))
  if (( $RESTE2 != 0 ))
  then
        wforH=$(echo $(($wforH - 1)))
  fi

PADL=$PAD
FW=$wforH
FH=$DesiH

else		#Padding Haut/bas
PAD=$(echo $((($DesiH-($hforW))/2)))
RESTE=$(echo $(($PAD % 2)))
  if (( $RESTE != 0 ))
  then
        PAD=$(echo $(($PAD - 1)))
  fi

RESTE2=$(echo $(($hforW % 2)))
  if (( $RESTE2 != 0 ))
  then
        hforW=$(echo $(($hforW - 1)))
  fi

PADHB=$PAD
FW=$DesiW
FH=$hforW
fi

#renvoi du résultat
echo "$FW:$FH:$PADL:$PADHB"
