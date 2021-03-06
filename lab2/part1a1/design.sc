#include "susan.sh"

import "susan";
import "read_image";
import "write_image";
import "c_uchar7220_queue";

import "input";
import "output";
import "pe1";

behavior Design(i_receive start, in uchar image_buffer[IMAGE_SIZE], i_sender out_image_susan)
{

    c_uchar7220_queue in_image(1ul);
    c_uchar7220_queue out_image(1ul);
    
    //ReadImage read_image(start, image_buffer, in_image);
    //Susan susan(in_image, out_image);
    //WriteImage write_image(out_image, out_image_susan);
	INPUT input(start, image_buffer, in_image);
	PE1 pe1(in_image, out_image);
	OUTPUT output(out_image, out_image_susan);

    void main(void) {
       par {
            input.main();
            pe1.main();
            output.main();
        }
    }
    
};
