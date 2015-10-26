/**********************************************************************
 *  File Name:   pe1.sc
 *  Author:      lingxiao.jia
 *  Mail:        lingxiao.jia@utexas.edu
 *  Create Time: 2015 Oct 16 02:24:14 PM
 **********************************************************************/

#include "susan.sh"

import "c_uchar7220_queue";
import "c_int7220_queue";

import "detect_edges";
     
behavior PE1(i_uchar7220_receiver in_image,  i_int7220_sender out_r, i_uchar7220_sender out_mid, i_uchar7220_sender out_image)
{

    Edges edges(in_image, out_r, out_mid, out_image);
        
    void main(void)
    {
        edges;
    }
   
};   



