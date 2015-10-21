/**********************************************************************
 *  File Name:   input.sc
 *  Author:      lingxiao.jia
 *  Mail:        lingxiao.jia@utexas.edu
 *  Create Time: 2015 Oct 16 02:04:35 PM
 **********************************************************************/

#include "susan.sh"

import "read_image";
import "c_uchar7220_queue";

behavior INPUT(i_receive start, in uchar image_buffer[IMAGE_SIZE], i_uchar7220_sender in_image)
{

    ReadImage read_image(start, image_buffer, in_image);

    void main(void) {
        read_image.main();
    }
    
};
