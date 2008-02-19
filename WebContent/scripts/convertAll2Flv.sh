#!/bin/bash
# Script which converts a video file into .flv format
# First argument: the video folder
# Second argument: the input video filename (with extension)
# Third argument: the output video name (without extension)

cd $1
#Verification of Microsoft ASF file
file "$2" > type.txt
TYPE=$(cat type.txt | sed s/"$2: "//)
rm type.txt

# Padding calculation
mplayer -nosound -vo null -ss 03:00:00 -really-quiet -identify "$2" | grep ID_VIDEO_HEIGHT= > lh.txt
HAUTEUR=$(cat lh.txt | sed s/ID_VIDEO_HEIGHT=//)
mplayer -nosound -vo null -ss 03:00:00 -really-quiet -identify "$2" | grep ID_VIDEO_WIDTH= > lh.txt
LARGEUR=$(cat lh.txt | sed s/ID_VIDEO_WIDTH=//)
HCALC=$(echo $((320*($HAUTEUR)/($LARGEUR))))
PAD=$(echo $(((240-($HCALC))/2)))
RESTE=$(echo $(($PAD % 2)))
rm lh.txt
if (( $RESTE != 0 ))
then
	PADF=$(echo $(($PAD + 1)))
else
        PADF=$(echo $(($PAD)))
fi

#Apply conversion
if [ "$TYPE" = "Microsoft ASF" ]
	then
	if (( $PADF > 0 ))
	then
        	/usr/bin/ffmpeg -i "$2" -ac 2 -ar 44100 -b 400000 -s 320x$HCALC -padbottom $PADFWMV -padtop $PADFWMV -y "$3".flv
	else
        	/usr/bin/ffmpeg -i "$2" -ac 2 -ar 44100 -b 400000 -s 320x$HCALC -y "$3".flv
	fi
else
	/usr/bin/mencoder "$2" -quiet -oac lavc -ovc lavc -lavcopts acodec=mp2:abitrate=64  -vf scale=320:-3 -o TTmp.avi
	if (( $PADF > 0 ))
	then
        	/usr/bin/ffmpeg -i TTmp.avi -ac 2 -ar 44100 -b 400000 -padbottom $PADF -padtop $PADF -y "$3".flv
	else
        	/usr/bin/ffmpeg -i TTmp.avi -ac 2 -ar 44100 -b 400000 -y "$3".flv
	fi
	rm TTmp.avi
fi

echo "fin" > fin.txt
