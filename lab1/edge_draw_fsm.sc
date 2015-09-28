#include <sim.sh>
#include <stdio.h>
#include <string.h>
import "c_queue";
import "edge_draw";

behavior EdgeDrawFSM(i_receiver Portin, i_sender Portout)
{
	EdgeDraw edge_draw(Portin, Portout);
	void main(void)
	{
		fsm {
			edge_draw: { 
        goto edge_draw;
				// break;
      }
		}
	}
};

