#!/bin/bash
#script which sends an email to confirm the reception of a new course
# First argument: the message
# Second argument: the subject
# Third argument: the e-mail adress

echo "$1"|mail -s "$2" $3 -- sendmail-options -f noreply@univrav