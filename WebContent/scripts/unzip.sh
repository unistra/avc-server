#!/bin/bash
# Unzip and move course folder
# $1 zip file with path
# $2 original folder
# $3 destination folder

unzip -u $1 &> /dev/null
mv $2 $3
