#include <stdio.h>
#include <string.h>
import "c_queue";
import "susan_thin";

behavior SusanThinFSM(i_receiver Portin, i_sender Portout)
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




