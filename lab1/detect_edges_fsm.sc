#include <stdio.h>
#include <string.h>
#include <math.h>
import "c_queue";
import "detect_edges";

behavior DetectEdgesFSM(i_receiver Portin, i_sender Portout)
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


