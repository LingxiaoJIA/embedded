#include <stdio.h>
#include <string.h>
import "c_queue";
import "read_image";
import "susan";
import "write_image";
import "c_double_handshake";

typedef  unsigned char uchar;

behavior Design(i_receive start, i_receiver stimulusQueue, i_sender dHSWriteToMonitor)
{

	const unsigned long readSusanSize = 7220;
	const unsigned long susanWriteSize = 7220;


//	c_double_handshake doubleHandToMonitor;
	c_queue queueReadToSusan(readSusanSize);
	c_queue queueSusanToWrite(susanWriteSize);

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

//self test
/*
behavior Main(void)
{
	int void main(void)
	{
		
	}
};
*/
