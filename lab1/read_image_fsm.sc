//start signal, and send the input over the queue
#include <stdio.h>
#include <string.h>
import "c_queue";
import "c_handshake";

typedef  unsigned char uchar;

behavior ReadImageFSM(i_receive start, i_receiver stimulusQueue, i_sender queueReadToSusan)
{

	uchar input[7220];	

	void main(void)
	{
		start.receive();
		stimulusQueue.receive(input,7220);
		queueReadToSusan.send(input,7220);
		//notify(gotImage);
	}
};

