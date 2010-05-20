#!/bin/bash
# $1 le chemin du répertoire contenant les scripts d'encodage
PTHSCR=$1
SRVURL="http://audiovideocours.u-strasbg.fr"

# script pour lancer les tâches d'encodage
# Lire le fichier job et récupérer la première ligne en mode "waiting"
ligne=`python $PTHSCR/accessbase.py`

if [ ! $ligne = "" ]; then
#Couper sur les 2 points
CourseID=`echo $ligne | cut -d: -f1`
JobState=`echo $ligne | cut -d: -f2`
MediaType=`echo $ligne | cut -d: -f3`
JobType=`echo $ligne | cut -d: -f4`
MediaFolder=`echo $ligne | cut -d: -f5`
Extension=`echo $ligne | cut -d: -f6`

#Extraction des Metadonnées
AUTHOR=`cat "$MediaFolder/description.txt" | grep Author: | cut -d":" -f2`
FORMATION=`cat "$MediaFolder/description.txt" | grep Formation: | cut -d":" -f2`
TITLE=`cat "$MediaFolder/description.txt" | grep Title: | cut -d":" -f2`
SUBJECT=`cat "$MediaFolder/description.txt" | grep Subject: | cut -d":" -f2`
DATE=`cat "$MediaFolder/description.txt" | grep Date: | cut -d":" -f2`
TYPE=`cat "$MediaFolder/description.txt" | grep Type: | cut -d":" -f2`
DURATION=`cat "$MediaFolder/description.txt" | grep Duration: | cut -d":" -f2`
COMMENT=`cat "$MediaFolder/description.txt" | grep Comment: | cut -d":" -f2`

# et on lance les scripts d'encodage en fonction du type et du mediatype
case $JobType in
CA)
#mp3Tag
/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3
#pdfCreation $MediaFolder $CourseID
python $PTHSCR/CreatePDF.py $MediaFolder $CourseID
#mp3ToOgg
bash $PTHSCR/convertAll2Ogg.sh $MediaFolder $CourseID "mp3"
#oggTag
/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" $MediaFolder/$CourseID.ogg $MediaFolder/new_$CourseID.ogg
#renameFile
mv $MediaFolder/new_$CourseID.ogg $MediaFolder/$CourseID.ogg
#videoslideCreation $MediaFolder $CourseID
bash $PTHSCR/videoslide.sh $MediaFolder $CourseID
#mp4Tag
/usr/bin/AtomicParsley $MediaFolder/"$CourseID"_videoslide.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite
;;

CV)
#convertAllToMp3 $MediaFolder $CourseID "flv"
bash $PTHSCR/convertAll2Mp3.sh $MediaFolder $CourseID "flv"
#mp3Tag(c, c.getMediaFolder(), c.getMediasFileName()); //MP3
/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3
#pdfCreation(c.getMediaFolder(), c.getMediasFileName()); // PDF
python $PTHSCR/CreatePDF.py $MediaFolder $CourseID
#mp3ToOgg
bash $PTHSCR/convertAll2Ogg.sh $MediaFolder $CourseID "mp3"
#oggTag
/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" $MediaFolder/$CourseID.ogg $MediaFolder/new_$CourseID.ogg
#renameFile
mv $MediaFolder/new_$CourseID.ogg $MediaFolder/$CourseID.ogg
#videoslideCreation(c.getMediaFolder(), c.getMediasFileName()); // VS
bash $PTHSCR/videoslide.sh $MediaFolder $CourseID
#mp4Tag
/usr/bin/AtomicParsley $MediaFolder/"$CourseID"_videoslide.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite
;;

MUA)
#mp3
if [ $Extension = "mp3" ]; then
#mp3Tag
/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3
#mp3ToOgg
bash $PTHSCR/convertAll2Ogg.sh $MediaFolder $CourseID "mp3"
#oggTag
/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" $MediaFolder/$CourseID.ogg $MediaFolder/new_$CourseID.ogg
#renameFile
mv $MediaFolder/new_$CourseID.ogg $MediaFolder/$CourseID.ogg
fi

if [ $Extension = "ogg" ]; then
#ogg
#oggTag
/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" $MediaFolder/$CourseID.ogg $MediaFolder/new_$CourseID.ogg
#renameFile
mv $MediaFolder/new_$CourseID.ogg $MediaFolder/$CourseID.ogg
#convertAllToMp3
bash $PTHSCR/convertAll2Mp3.sh $MediaFolder $CourseID "ogg"
#mp3Tag
/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3
fi

if [ $Extension = "wma" ]||[ $Extension = "wav" ]; then
#wma wav
#convertAllToMp3
bash $PTHSCR/convertAll2Mp3.sh $MediaFolder $CourseID $Extension
#mp3Tag
/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3
#mp3ToOgg
bash $PTHSCR/convertAll2Ogg.sh $MediaFolder $CourseID "mp3"
#oggTag
/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" $MediaFolder/$CourseID.ogg $MediaFolder/new_$CourseID.ogg
#renameFile
mv $MediaFolder/new_$CourseID.ogg $MediaFolder/$CourseID.ogg
fi
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
#mp3Tag
/usr/bin/mp3info -t "$TITLE" -a "$AUTHOR" -y "$DATE" -l "$FORMATION" -c "$COMMENT" $MediaFolder/$CourseID.mp3
#mp3ToOgg
bash $PTHSCR/convertAll2Ogg.sh $MediaFolder $CourseID "mp3"
#oggTag
/usr/bin/vorbiscomment -w -t "title=$TITLE" -t "artist=$AUTHOR" -t "date=$DATE" -t "album=$FORMATION" -t "COMMENT=$COMMENT" $MediaFolder/$CourseID.ogg $MediaFolder/new_$CourseID.ogg
#renameFile
mv $MediaFolder/new_$CourseID.ogg $MediaFolder/$CourseID.ogg
#courseZip
#/usr/bin/zip $MediaFolder/$CourseID.zip -j $MediaFolder/description.txt $MediaFolder/$CourseID.flv
#videoHighQualityConvert
bash $PTHSCR/convertAll2Mp4.sh $MediaFolder $ORI$CourseID.$Extension $CourseID $PTHSCR/calculate_padding.sh
#mp4Tag
/usr/bin/AtomicParsley $MediaFolder/$CourseID.mp4 --title "$TITLE" --artist "$AUTHOR" --year "$DATE" --album "$FORMATION" --comment "$COMMENT" --overWrite
;;

esac

# Maj du mediatype sur le serveur avc
wget --spider "$SRVURL/avc/encodagestate?courseid=$CourseID&mediatype=$MediaType"

fi
