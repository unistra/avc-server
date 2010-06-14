#!/bin/bash
#$1 course directory
#$2 course id

cd $1
mkdir workingVS
cut -f1 -d . timecode.csv > VStimecode

# Diapos duplication
i=1; while read aLine ;do myarray[$i]=$aLine; i=$(($i+1)); done  < VStimecode

i=$(($i-1))
j=1
while (( j < i )) 
do
 k=$(($j+1))
 a=${myarray[$k]}
 b=${myarray[$j]}
 l=$(($a - $b))

# Copy diapos
 t=`seq -f %04.0f $k $k`
  for ((m=0; m<$l; m++))
  do
    cp ./screenshots/D$k.jpg ./workingVS/$t.$m.jpg;
  done

 j=$(($j+1))
done

# Videoslide creation
cd workingVS

# Convert original video into avi
/usr/bin/mencoder -really-quiet mf://*.jpg -mf fps=1 -ovc xvid -xvidencopts bitrate=3000:threads=2:autoaspect -o ./../$2_tmp.avi -oac copy -audiofile ./../$2.mp3

# Extract audio into aac
/usr/bin/mplayer -really-quiet -vo null -vc null -ao pcm:fast:file=./../$2.wav ./../$2.mp3
/usr/bin/faac -b 64k -w -o ./../$2.m4a ./../$2.wav &> /dev/null

# Convert avi+aac into mp4
/usr/bin/ffmpeg -v -1 -i ./../$2.m4a -acodec copy -i ./../$2_tmp.avi -f mp4 -vcodec libx264 -vpre normal -vpre ipod640 -crf 27 -s 640x480 -y ./../$2_videoslide_tmp.mp4 -threads 0 &> /dev/null

# Adds mov atom
/usr/bin/qt-faststart ./../$2_videoslide_tmp.mp4 ./../$2_videoslide.mp4 &> /dev/null

# Remove tmp files
rm ./../$2_videoslide_tmp.mp4
rm ./../$2_tmp.avi
rm ./../$2.wav
rm ./../$2.m4a

cd ..
rm -Rf workingVS
rm VStimecode
