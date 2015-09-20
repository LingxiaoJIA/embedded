#include <stdio.h>
#include <string.h>
import "c_queue";


behavior Susan(i_receiver start, i_receiver Port_stimulus, i_sender Port_monitor)
{
	//the size of port_stimulus should be 7220
	const unsigned long sizeEdgesout = 43320 ;//is it ok..?
	const unsigned long sizeThinin = 36100;
	const unsigned long sizeThinout = 7220;
	const unsigned long sizeDrawin = 14440;
	//the size of port_monitor should be 7220;

	uchar input[76*95];//use port to receive and store in the in[]
	uchar mid[76*95];//in and bp have to be sent to susan_edges
	int r[76*95]; //mid and r and in will get updated by susan_edges

	c_queue		c_Edgesout(sizeEdgesout);
	c_queue		c_Thinin(sizeThinin);
	c_queue		c_Thinout(sizeThinout);
	c_queue		c_Drawin(sizeDrawin);

	DetectEdges detect_edges(Port_stimulus, c_Edgesout);
	SuanThin susan_thin(c_Thinin, c_Thinout);
	EdgeDraw edge_draw(c_Drawin, Port_monitor);

	
	void main(void)
	{
		start.receive();
		//manage the data between detectedge, susanthin, edgedraw
		//start detectedges
		detect_edges.main();
		//receive in, r, mid and store in SUSAN
		c_Edgesout.receive(input, 7220);
		c_Edgesout.receive(mid, 7220);
		c_Edgesout.receive(r, 7220*sizeof(int));
		//send data to susanthin
		c_Thinin.send(mid,7220);
		c_Thinin.send(r,7220*sizeof(int));
		//susan_thin process
		susan_thin.main();
		//receive from susan_thin
		c_Thinout.receive(mid,7220);
		//send data to edge draw
		c_Drawin.send(input,7220);
		c_Drawin.send(mid,7220);
		//edge_draw process
		edge_draw.main();
		//finish and file out
	}
};



