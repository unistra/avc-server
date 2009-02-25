#!/bin/bash

cmd=`ls -r --sort=time /var/log/apache2/access-audiovideocours.log*`
for i in $cmd
do
webalizer -p $i
done

