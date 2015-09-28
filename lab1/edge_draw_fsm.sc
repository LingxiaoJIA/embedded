#include <sim.sh>
#include <stdio.h>
#include <string.h>
import "c_queue";
import "edge_draw";
import "c_bit64_queue";	

behavior EdgeDrawFSM(i_bit64_receiver Portin, i_bit64_sender Portout)
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

