#!/bin/bash
# Script to encode medias
# $1 scripts directory path
# $2 audiovideocours server url
# $3 course folder
# Optional : $4 job ligne if separate medias encodage is true. Else nothing.

PTHSCR=$1
SRVURL=$2
CRSMNTPNT=$3
ligne=$4

if test -z "$1"
then
	echo "PTHSCR is needed. Ex: /audiovideocours/jobs_encodage"
	exit
fi

if test -z "$2"
then
	echo "SRVURL is needed. Ex: http://audiovideocast.unistra.fr"
	exit
fi

if test -z "$3"
then
	echo "CRSMNTPNT is needed. Ex: /audiovideocours/cours"
	exit
fi

# Lire le job et récupérer la première ligne en mode "waiting"
if test -z "$4"
then
#	sleep $(($RANDOM%60))
	ligne=`python $PTHSCR/accessbase.py`
fi


if [ ! $ligne = "" ]; then
#Couper sur les 2 points
CourseID=`echo $ligne | cut -d: -f1`
JobState=`echo $ligne | cut -d: -f2`
MediaType=`echo $ligne | cut -d: -f3`
JobType=`echo $ligne | cut -d: -f4`
MediaFolder=`echo $ligne | cut -d: -f5`
MediaFolder="$CRSMNTPNT/$MediaFolder"
Extension=`echo $ligne | cut -d: -f6`

#Extraction des Metadonnées
AUTHOR=`cat "$MediaFolder/description.txt" | grep Author: | cut -d":" -f2`
FORMATION=`cat "$MediaFolder/description.txt" | grep Formation: | cut -d":" -f2`
TITLE=`cat "$MediaFolder/description.txt" | grep Title: | cut -d":" -f2`
SUBJECT=`cat "$MediaFolder/description.txt" | grep Subject: | cut -d":" -f2`
DATE=`cat "$MediaFolder/description.txt" | grep Date: | cut -d":" -f2 | cut -d"/" -f1`
TYPE=`cat "$MediaFolder/description.txt" | grep Type: | cut -d":" -f2`
DURATION=`cat "$MediaFolder/description.txt" | grep Duration: | cut -d":" -f2`
COMMENT=`cat "$MediaFolder/description.txt" | grep Comment: | cut -d":" -f2`

# et on lance les scripts d'encodage en fonction du type et du mediatype
case $JobType in
CA)
#mp3Tag
#/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3 &> /dev/null
#mp3 tag image
/usr/bin/eyeD3 -t "$TITLE" -a "$AUTHOR" -Y "$DATE" -A "$FORMATION" -c "::$COMMENT" --to-v2.3 --add-image=$PTHSCR/cover.jpg:FRONT_COVER $MediaFolder/$CourseID.mp3 &> /dev/null
#pdfCreation $MediaFolder $CourseID
python $PTHSCR/CreatePDF.py $MediaFolder $CourseID
#mp3ToOgg
bash $PTHSCR/convertAll2Ogg.sh $MediaFolder $CourseID "mp3"
#oggTag
/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" $MediaFolder/$CourseID.ogg &> /dev/null
#videoslideCreation
bash $PTHSCR/videoslide.sh $MediaFolder $CourseID
#convert videoslide to webm
bash $PTHSCR/convertAll2Webm.sh $MediaFolder "$CourseID"_videoslide mp4
#mp4Tag
/usr/bin/AtomicParsley $MediaFolder/"$CourseID"_videoslide.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
/usr/bin/AtomicParsley $MediaFolder/"$CourseID"_videoslide_ipod.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null

# Maj du mediatype sur le serveur avc
wget --spider "$SRVURL/avc/encodagestate?courseid=$CourseID&mediatype=$MediaType&jobtype=$JobType"
;;

CV)
#convertAllToMp3
bash $PTHSCR/convertAll2Mp3.sh $MediaFolder $CourseID "flv"
#mp3Tag
#/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3 &> /dev/null
#mp3 tag image
/usr/bin/eyeD3 -t "$TITLE" -a "$AUTHOR" -Y "$DATE" -A "$FORMATION" -c "::$COMMENT" --to-v2.3 --add-image=$PTHSCR/cover.jpg:FRONT_COVER $MediaFolder/$CourseID.mp3 &> /dev/null
#pdfCreation(c.getMediaFolder(), c.getMediasFileName()); // PDF
python $PTHSCR/CreatePDF.py $MediaFolder $CourseID
#mp3ToOgg
bash $PTHSCR/convertAll2Ogg.sh $MediaFolder $CourseID "mp3"
#oggTag
/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" $MediaFolder/$CourseID.ogg &> /dev/null
#videoslideCreation
bash $PTHSCR/videoslide.sh $MediaFolder $CourseID
#convert videoslide to webm
bash $PTHSCR/convertAll2Webm.sh $MediaFolder "$CourseID"_videoslide mp4
#mp4Tag
/usr/bin/AtomicParsley $MediaFolder/"$CourseID"_videoslide.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
/usr/bin/AtomicParsley $MediaFolder/"$CourseID"_videoslide_ipod.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
# flv to mp4 for html5
bash $PTHSCR/convertAll2Mp4.sh $MediaFolder $CourseID.$Extension $CourseID $PTHSCR/calculate_padding.sh true &> /dev/null
ln -s $MediaFolder/"$CourseID"_ipod.mp4 $MediaFolder/"$CourseID".mp4
#mp4Tag for ipod
/usr/bin/AtomicParsley $MediaFolder/"$CourseID"_ipod.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
# convert flv to webm for html5
bash $PTHSCR/convertAll2Webm.sh $MediaFolder $CourseID flv

# Maj du mediatype sur le serveur avc
wget --spider "$SRVURL/avc/encodagestate?courseid=$CourseID&mediatype=$MediaType&jobtype=$JobType"
;;

MUA)
#mp3
if [ $Extension = "mp3" ]; then
#mp3Tag
#/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3 &> /dev/null
#mp3 tag image
/usr/bin/eyeD3 -t "$TITLE" -a "$AUTHOR" -Y "$DATE" -A "$FORMATION" -c "::$COMMENT" --to-v2.3 --add-image=$PTHSCR/cover.jpg:FRONT_COVER $MediaFolder/$CourseID.mp3 &> /dev/null
#mp3ToOgg
bash $PTHSCR/convertAll2Ogg.sh $MediaFolder $CourseID "mp3"
#oggTag
/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" $MediaFolder/$CourseID.ogg &> /dev/null
fi

if [ $Extension = "ogg" ]; then
#ogg
#oggTag
/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" $MediaFolder/$CourseID.ogg &> /dev/null
#convertAllToMp3
bash $PTHSCR/convertAll2Mp3.sh $MediaFolder $CourseID "ogg"
#mp3Tag
#/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3 &> /dev/null
#mp3 tag image
/usr/bin/eyeD3 -t "$TITLE" -a "$AUTHOR" -Y "$DATE" -A "$FORMATION" -c "::$COMMENT" --to-v2.3 --add-image=$PTHSCR/cover.jpg:FRONT_COVER $MediaFolder/$CourseID.mp3 &> /dev/null
fi

if [ $Extension = "wma" ]||[ $Extension = "wav" ]; then
#wma wav
#convertAllToMp3
bash $PTHSCR/convertAll2Mp3.sh $MediaFolder $CourseID $Extension
#mp3Tag
#/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3 &> /dev/null
#mp3 tag image
/usr/bin/eyeD3 -t "$TITLE" -a "$AUTHOR" -Y "$DATE" -A "$FORMATION" -c "::$COMMENT" --to-v2.3 --add-image=$PTHSCR/cover.jpg:FRONT_COVER $MediaFolder/$CourseID.mp3 &> /dev/null
#mp3ToOgg
bash $PTHSCR/convertAll2Ogg.sh $MediaFolder $CourseID "mp3"
#oggTag
/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" $MediaFolder/$CourseID.ogg &> /dev/null
fi

# Maj du mediatype sur le serveur avc
wget --spider "$SRVURL/avc/encodagestate?courseid=$CourseID&mediatype=$MediaType&jobtype=$JobType"
;;

MUV)
ORI=""
#flv
if [ ! $Extension = "flv" ]; then
ORI="ori_"
#videoConvert
bash $PTHSCR/convertAll2Flv.sh $MediaFolder $ORI$CourseID.$Extension $CourseID $PTHSCR/calculate_padding.sh
fi

#tous
#injectMetadata
bash $PTHSCR/injectMetadata.sh $MediaFolder $CourseID "flv"
#convertAllToMp3
bash $PTHSCR/convertAll2Mp3.sh $MediaFolder $CourseID "flv"

# test if no sound
if [ `stat -c '%s' $MediaFolder/$CourseID.mp3` -eq 0 ]
then
	MediaType=$(echo "$MediaType-6"|bc)
else
	#mp3Tag
	#/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3 &> /dev/null
	#mp3 tag image
	/usr/bin/eyeD3 -t "$TITLE" -a "$AUTHOR" -Y "$DATE" -A "$FORMATION" -c "::$COMMENT" --to-v2.3 --add-image=$PTHSCR/cover.jpg:FRONT_COVER $MediaFolder/$CourseID.mp3 &> /dev/null
	#mp3ToOgg
	bash $PTHSCR/convertAll2Ogg.sh $MediaFolder $CourseID "mp3"
	#oggTag
	/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" $MediaFolder/$CourseID.ogg &> /dev/null
fi

#videoHighQualityConvert
bash $PTHSCR/convertAll2Mp4.sh $MediaFolder $ORI$CourseID.$Extension $CourseID $PTHSCR/calculate_padding.sh false
#mp4Tag
/usr/bin/AtomicParsley $MediaFolder/$CourseID.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
#videoHighQualityConvert for ipod
bash $PTHSCR/convertAll2Mp4.sh $MediaFolder $ORI$CourseID.$Extension $CourseID $PTHSCR/calculate_padding.sh true
#mp4Tag for ipod
/usr/bin/AtomicParsley $MediaFolder/"$CourseID"_ipod.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
# webm for html5
bash $PTHSCR/convertAll2Webm.sh $MediaFolder $CourseID mp4

# Maj du mediatype sur le serveur avc
wget --spider "$SRVURL/avc/encodagestate?courseid=$CourseID&mediatype=$MediaType&jobtype=$JobType"
;;

ADDV)
FOLDER="additional_video"
ORI="ori_"
AV="addvideo_"
#convertAllToMp3
bash $PTHSCR/convertAll2Mp3.sh $MediaFolder/$FOLDER $ORI$AV$CourseID $Extension
mv $MediaFolder/$FOLDER/$ORI$AV$CourseID.mp3 $MediaFolder/$FOLDER/$AV$CourseID.mp3 
#videoHighQualityConvert
bash $PTHSCR/convertAll2Mp4.sh $MediaFolder/$FOLDER $ORI$AV$CourseID.$Extension $AV$CourseID $PTHSCR/calculate_padding.sh false
#mp4Tag
/usr/bin/AtomicParsley $MediaFolder/$FOLDER/$AV$CourseID.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
# remove original file
rm $MediaFolder/$FOLDER/$ORI$AV$CourseID.$Extension
rm $MediaFolder/$FOLDER/$AV$CourseID.mp3
# webm for html5
bash $PTHSCR/convertAll2Webm.sh $MediaFolder/$FOLDER $AV$CourseID mp4

# Maj du mediatype sur le serveur avc
wget --spider "$SRVURL/avc/encodagestate?courseid=$CourseID&mediatype=$MediaType&jobtype=$JobType"
;;


CVMP4)
#convert mp4 to flv for flash
bash $PTHSCR/convertAll2Flv.sh $MediaFolder $ORI$CourseID.$Extension $CourseID $PTHSCR/calculate_padding.sh
#injectMetadata
bash $PTHSCR/injectMetadata.sh $MediaFolder $CourseID "flv"
#convertAllToMp3
bash $PTHSCR/convertAll2Mp3.sh $MediaFolder $CourseID "mp4"
#mp3Tag
#/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3 &> /dev/null
#mp3 tag image
/usr/bin/eyeD3 -t "$TITLE" -a "$AUTHOR" -Y "$DATE" -A "$FORMATION" -c "::$COMMENT" --to-v2.3 --add-image=$PTHSCR/cover.jpg:FRONT_COVER $MediaFolder/$CourseID.mp3 &> /dev/null
#pdfCreation(c.getMediaFolder(), c.getMediasFileName()); // PDF
python $PTHSCR/CreatePDF.py $MediaFolder $CourseID
#mp3ToOgg
bash $PTHSCR/convertAll2Ogg.sh $MediaFolder $CourseID "mp3"
#oggTag
/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" $MediaFolder/$CourseID.ogg &> /dev/null
#videoslideCreation
bash $PTHSCR/videoslide.sh $MediaFolder $CourseID
#convert videoslide to webm
bash $PTHSCR/convertAll2Webm.sh $MediaFolder "$CourseID"_videoslide mp4
#mp4Tag
/usr/bin/AtomicParsley $MediaFolder/"$CourseID"_videoslide.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
/usr/bin/AtomicParsley $MediaFolder/"$CourseID"_videoslide_ipod.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
# link ipod
ln -s $MediaFolder/"$CourseID".mp4 $MediaFolder/"$CourseID"_ipod.mp4
# qt faststart for mp4
/usr/bin/qt-faststart $MediaFolder/"$CourseID".mp4 $MediaFolder/"$CourseID"_tmp.mp4 &> /dev/null
mv $MediaFolder/"$CourseID"_tmp.mp4 $MediaFolder/"$CourseID".mp4
#mp4Tag
/usr/bin/AtomicParsley $MediaFolder/"$CourseID".mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
# convert mp4 to webm for html5
bash $PTHSCR/convertAll2Webm.sh $MediaFolder $CourseID mp4

# Maj du mediatype sur le serveur avc
wget --spider "$SRVURL/avc/encodagestate?courseid=$CourseID&mediatype=$MediaType&jobtype=$JobType"
;;


CSC)
ORI="ori_"
#move atom
/usr/bin/qt-faststart $MediaFolder/$ORI$CourseID.$Extension $MediaFolder/$CourseID.$Extension &> /dev/null
#mp4Tag
/usr/bin/AtomicParsley $MediaFolder/$CourseID.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite &> /dev/null
# link ipod
ln -s $MediaFolder/"$CourseID".mp4 $MediaFolder/"$CourseID"_ipod.mp4
# remove ori
rm $MediaFolder/$ORI$CourseID.$Extension

#convertAllToMp3
bash $PTHSCR/convertAll2Mp3.sh $MediaFolder $CourseID "mp4"

# test if no sound
if [ `stat -c '%s' $MediaFolder/$CourseID.mp3` -eq 0 ]
then
	MediaType=$(echo "$MediaType-6"|bc)
else
	#mp3Tag
	#/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3 &> /dev/null
	#mp3 tag image
	/usr/bin/eyeD3 -t "$TITLE" -a "$AUTHOR" -Y "$DATE" -A "$FORMATION" -c "::$COMMENT" --to-v2.3 --add-image=$PTHSCR/cover.jpg:FRONT_COVER $MediaFolder/$CourseID.mp3 &> /dev/null
fi

# Maj du mediatype sur le serveur avc
wget --spider "$SRVURL/avc/encodagestate?courseid=$CourseID&mediatype=$MediaType&jobtype=$JobType"

# webm for html5
bash $PTHSCR/convertAll2Webm.sh $MediaFolder $CourseID mp4
;;

esac

fi
