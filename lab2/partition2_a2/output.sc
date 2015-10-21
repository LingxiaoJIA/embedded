/**********************************************************************
 *  File Name:   output.sc
 *  Author:      lingxiao.jia
 *  Mail:        lingxiao.jia@utexas.edu
 *  Create Time: 2015 Oct 16 02:05:54 PM
 **********************************************************************/

#include "susan.sh"

import "write_image";
import "c_uchar7220_queue";

behavior OUTPUT(i_uchar7220_receiver in_image, i_sender out_image)
{
    WriteImage write_image(in_image, out_image);

    void main(void) {
        write_image.main();
    }
};
