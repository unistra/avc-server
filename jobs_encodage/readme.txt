Ce dossier contient les scripts d'encodage nécessaires pour l'encodage séparé.

Il faut définir le paramètre suivant dans le fichier univrav.properties : 
sepEnc=true

Puis, il suffit de mettre la tache cron suivante sur le serveur d'encodage. N'oubliez-pas de modifier les scripts d'accès BDD :
1-59/2 *        * * *   root    nice -n 19 bash /audiovideocours/cours/jobs_encodage/JobEnc.sh /audiovideocours/cours/jobs_encodage 1> /dev/null 2>&1

