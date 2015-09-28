//start signal, and send the input over the queue
#include <stdio.h>
#include <string.h>
import "c_queue";
import "c_handshake";
import "c_bit64_queue";	

typedef  unsigned char uchar;

behavior ReadImage(i_receive start, i_bit64_receiver stimulusQueue, i_bit64_sender queueReadToSusan)
{

	uchar input[7220];	

	void main(void)
	{
	int k;
	bit[64] temp;
    while(true) {
      start.receive();
	for(k=0;k<7220;k++){
		stimulusQueue.receive(&temp);
		input[k] = temp;
	}
	for(k=0;k<7220;k++){
		temp = input[k];
	      queueReadToSusan.send(temp);
	}
    }
	}
};

