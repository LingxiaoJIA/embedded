#include "susan.sh"

import "input";
import "pe1";
import "pe2";
import "output";
import "c_uchar7220_queue";

behavior Design(i_receive start, in uchar image_buffer[IMAGE_SIZE], i_sender out_image_susan)
{

    c_uchar7220_queue in_image(1ul);
    c_uchar7220_queue out_image(1ul);

    c_int7220_queue r(1ul);
    c_uchar7220_queue mid(1ul);
    c_uchar7220_queue image_edge_draw(1ul);

    INPUT input(start, image_buffer, in_image);
    PE1 pe1(in_image, r, mid, image_edge_draw);
    PE2 pe2(r, mid, image_edge_draw, out_image);
    OUTPUT output(out_image, out_image_susan);

    void main(void) {
        par {
            input.main();
            pe1.main();
            pe2.main();
            output.main();
        }
    }
};
