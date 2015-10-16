#include "susan.sh"

import "c_uchar7220_queue";
import "c_int7220_queue";

import "susan";

//i_uchar7220_receiver in_image, i_uchar7220_sender out_image
//PE1 is the wrapper class of susan
behavior PE1(i_uchar7220_receiver in_image, i_uchar7220_sender out_image)
{
    
    Susan susan(in_image, out_image);

    void main(void) {
       par {
            susan.main();
        }
    }
    
};
