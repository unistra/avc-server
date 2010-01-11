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

nice -n 9 /usr/bin/mencoder mf://*.jpg -mf fps=1 -of lavf -lavfopts format=mp4 -vf-add harddup -vf-add scale=640:480 -oac lavc -audiofile ./../$2.mp3 -ovc lavc -lavcopts aglobal=1:vglobal=1:acodec=libfaac:abitrate=64:vcodec=libx264:vbitrate=100:keyint=25:autoaspect -o ./../$2_videoslide.mp4 > /dev/null

cd ..
rm -Rf workingVS
rm VStimecode