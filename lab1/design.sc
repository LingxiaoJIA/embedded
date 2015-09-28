#include <stdio.h>
#include <string.h>
import "c_queue";
import "c_double_handshake";
import "read_image";
import "susan";
import "write_image";
import "c_bit64_queue";	

typedef  unsigned char uchar;

behavior Design(i_receive start, i_bit64_receiver stimulusQueue, i_sender dHSWriteToMonitor)
{

	c_bit64_queue queueReadToSusan(7220ul);
	c_bit64_queue queueSusanToWrite(7220ul);

	ReadImage read_image(start, stimulusQueue, queueReadToSusan);
	Susan susan(queueReadToSusan, queueSusanToWrite);
	WriteImage write_image(queueSusanToWrite, dHSWriteToMonitor);


	void main(void)
	{
		par{
			read_image.main();
			susan.main();
			write_image.main();
		}
	}
};

