#include <stdio.h>
#include <string.h>
#include <sim.sh>
import "c_queue";
import "detect_edges_fsm";
import "susan_thin_fsm";
import "edge_draw_fsm";

typedef unsigned char uchar;

behavior Susan(i_receiver Port_readImage, i_sender Port_writeImage)
{
	const unsigned long sizeEdgesout = 43320;
	const unsigned long sizeDrawin = 14440;

	c_queue		c_Edgesout(sizeEdgesout);
	c_queue		c_Drawin(sizeDrawin);

	sim_time_string	buf;	

	DetectEdgesFSM detect_edges_fsm(Port_readImage, c_Edgesout);
	SusanThinFSM susan_thin_fsm(c_Edgesout, c_Drawin);
	EdgeDrawFSM edge_draw_fsm(c_Drawin, Port_writeImage);
	
	void main(void)
	{
		par {
			detect_edges_fsm.main();
			susan_thin_fsm.main();
			edge_draw_fsm.main();
		}
	}
};

