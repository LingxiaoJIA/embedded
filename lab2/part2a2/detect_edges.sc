#include "susan.sh"

import "c_uchar7220_queue";
import "c_int7220_queue";
import "setup_brightness_lut";
import "susan_edges";
import "osapi";

behavior DetectEdges(i_uchar7220_receiver in_image,  i_int7220_sender out_r, i_uchar7220_sender out_mid, i_uchar7220_sender out_image, OSAPI os)
{

    uchar bp[516];

    SetupBrightnessLut setup_brightness_lut(bp, os);
    SusanEdges susan_edges(in_image, out_r, out_mid, bp, out_image, os);

    void main(void) {
        setup_brightness_lut.main();
        susan_edges.main();
    }

};

behavior Edges(i_uchar7220_receiver in_image,  i_int7220_sender out_r, i_uchar7220_sender out_mid, i_uchar7220_sender out_image, OSAPI os)
{

    DetectEdges detect_edges(in_image,  out_r, out_mid, out_image, os);

    void main(void) {
        fsm {
            detect_edges: {goto detect_edges;}
        }
    }
};

