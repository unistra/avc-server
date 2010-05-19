#!/bin/bash
cd $1
ffdur=$(/usr/bin/ffmpeg -i $2.$3 2>&1 | grep "Duration" | cut -d ' ' -f 4 | sed s/,//)

hours=$(echo $ffdur | cut -d: -f1)
hours=$(printf %.0f "$hours")
hours=$((hours*3600))
mins=$(echo $ffdur | cut -d: -f2)
mins=$(printf %.0f "$mins")
mins=$((mins*60))
secs=$(echo $ffdur | cut -d: -f3 | cut -d. -f1)
secs=$(printf %.0f "$secs")

echo $(($hours+$mins+$secs))
