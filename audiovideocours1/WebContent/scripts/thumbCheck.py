###############################################################################
##           Script for post production : thumb check and correction 
##                     (c) ULP Multimedia - 2007
##             Developer: francois.schnell@ullpmm.u-strasbg.fr
###############################################################################

"""
This script looks in the given screenshot folder in argument and if a thumb 
is missing it will create it (if the main sreenshot exist). 

USAGE:

python thumbCheck.py screenshotFolder
"""

import os,sys,fnmatch
from PIL import Image

for fileName in os.listdir ( sys.argv[1] ):
        if fnmatch.fnmatch ( fileName, '*.jpg' ) and fileName.find("thumb")<0:
            print "found",fileName,
            if os.path.isfile(sys.argv[1]+"/"+fileName.split(".")[0]+"-thumb.jpg")==True:
                print fileName,"has its thumb"
                #print "*",
            else:
                print "Creating a thumb for", fileName
                img=Image.open(sys.argv[1]+"/"+fileName)
                img.thumbnail((256,192))
                img.save(sys.argv[1]+"/"+fileName.split(".")[0]+"-thumb.jpg")
print "Finished"

    
                            
            


