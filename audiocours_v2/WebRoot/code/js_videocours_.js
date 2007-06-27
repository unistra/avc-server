//preload img + fonction roll over

if (document.images)
        {
                var one=new Image();
        one.src="_mm/btn1_roll.jpg";
                var one2=new Image();
        one2.src="_mm/btn2_roll.jpg";
        }


 function SetImg(img_name,file)
        {
                if(document.images)
                        {
                        document.images[img_name].src = file;
                                }
                         }