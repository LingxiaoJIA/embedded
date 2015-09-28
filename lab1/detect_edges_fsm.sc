#include <stdio.h>
#include <string.h>
#include <math.h>
import "c_queue";
import "detect_edges";
import "c_bit64_queue";	

behavior DetectEdgesFSM(i_bit64_receiver Portin, i_bit64_sender Portout)
{
	DetectEdges detect_edges(Portin, Portout);
	void main(void)
	{
		fsm {
			detect_edges: {
				goto detect_edges;
        //break;
      }
		}
	}
};


