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
cat *.jpg | /usr/bin/ffmpeg -v -1 -f image2pipe -r 1 -vcodec mjpeg -i - -y ../$2_tmp.avi &> /dev/null

# Convert avi+aac into mp4 ipod
/usr/bin/ffmpeg -v -1 -i ./../$2.mp3 -i ../$2_tmp.avi -acodec libvo_aacenc -f mp4 -vcodec libx264 -vpre medium -vpre ipod640 -crf 27 -s 640x480 -y ./../$2_videoslide_tmp_ipod.mp4 &> /dev/null

# Adds mov atom
/usr/bin/qt-faststart ./../$2_videoslide_tmp_ipod.mp4 ./../$2_videoslide_ipod.mp4 &> /dev/null

# Convert avi+aac into mp4
/usr/bin/ffmpeg -v -1 -i ./../$2.mp3 -i ../$2_tmp.avi -acodec libvo_aacenc -f mp4 -vcodec libx264 -vpre medium -crf 27 -y ./../$2_videoslide_tmp.mp4 &> /dev/null

# Adds mov atom
/usr/bin/qt-faststart ./../$2_videoslide_tmp.mp4 ./../$2_videoslide.mp4 &> /dev/null

# Remove tmp files
rm ./../$2_videoslide_tmp_ipod.mp4
rm ./../$2_videoslide_tmp.mp4
rm ./../$2_tmp.avi

cd ..
rm -Rf workingVS
rm VStimecode
