#!/bin/bash
#script which sends an email to confirm the reception of a new course
# First argument: the message
# Second argument: the subject
# Third argument: the e-mail adress

echo "$1"|mail -s "$2" -a "Content-type: text/plain; charset=utf-8;" -a "From: no-reply@unistra.fr" $3
