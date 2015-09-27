//read from queue and forwards into an outgoing double-handshake channel

#include <stdio.h>
#include <string.h>
import "c_queue";
import "c_double_handshake";

typedef  unsigned char uchar;

behavior WriteImageFSM(i_receiver queueSusanToWrite, i_sender dHSWriteToMonitor)
{
	uchar input[7220];


	void main(void)
	{
		queueSusanToWrite.receive(input,7220);
		dHSWriteToMonitor.send(input,7220);
	}
};
