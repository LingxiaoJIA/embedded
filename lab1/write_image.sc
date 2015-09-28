//read from queue and forwards into an outgoing double-handshake channel

#include <stdio.h>
#include <string.h>
import "c_queue";
import "c_double_handshake";
import "c_bit64_queue";	

typedef  unsigned char uchar;

behavior WriteImage(i_bit64_receiver queueSusanToWrite, i_sender dHSWriteToMonitor)
{
	uchar input[7220];
	int k;
	bit[64] temp;
	void main(void)
	{
    while(true) {
	for(k=0;k<7220;k++){
	      queueSusanToWrite.receive(&temp);
		input[k] = temp;
	}
      dHSWriteToMonitor.send(input,7220);
    }
	}
};
