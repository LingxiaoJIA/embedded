#include <stdio.h>
#include <string.h>
import "c_queue";
import "susan_thin";
import "c_bit64_queue";	

behavior SusanThinFSM(i_bit64_receiver Portin, i_bit64_sender Portout)
{
  SusanThin susan_thin(Portin, Portout);
	void main(void)
	{
		fsm {
			susan_thin: {
        goto susan_thin;
				// break;
      }
		}
	}
};




