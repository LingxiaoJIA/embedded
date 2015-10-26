/**********************************************************************
 *  File Name:   pe2.sc
 *  Author:      lingxiao.jia
 *  Mail:        lingxiao.jia@utexas.edu
 *  Create Time: 2015 Oct 16 02:29:20 PM
 **********************************************************************/

#include "susan.sh"

import "c_uchar7220_queue";
import "c_int7220_queue";

import "susan_thin";
import "edge_draw";

     
behavior PE2(i_int7220_receiver in_r, i_uchar7220_receiver in_mid, i_uchar7220_receiver in_image, i_uchar7220_sender out_image)
{
    c_uchar7220_queue mid_edge_draw(1ul);

    Thin thin(in_r, in_mid, mid_edge_draw);
    Draw draw(in_image, mid_edge_draw, out_image);
        
    void main(void)
    {
        par {
            thin;
            draw;
        }      
    }
   
};   


