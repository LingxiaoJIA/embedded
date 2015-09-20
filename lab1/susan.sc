#include <stdio.h>
#include <string.h>
import "c_queue";


behavior SUSAN(signal bool start, i_receiver Port_stimulus, i_sender Port_monitor, signal bool finish)
{
	const unsigned long sizeEdgesout =     ;//is it ok..?
	const unsigned long sizeThinin = 
	const unsigned long sizeThinout = 
	const unsigned long sizeDrawin = 

	uchar in[76*95];//use port to receive and store in the in[]
	int r[76*95]; //mid and r and in will get updated by susan_edges
	uchar mid[76*95];//in and bp have to be sent to susan_edges

	c_queue		c_Edgesout(sizeEdgesout);
	c_queue		c_Thinin(sizeThinin);
	c_queue		c_Thinout(sizeThinout);
	c_queue		c_Drawin(sizeDrawin);

	DETECT_EDGES detect_edges(Port_stimulus, c_Edgesout);
	SUSAN_THIN susan_thin(c_Thinin, c_Thinout);
	EDGE_DRAW edge_draw(c_Drawin, Port_monitor);

	
	void main(void)
	{
		wait start;
		//manage the data between detectedge, susanthin, edgedraw
		//start detectedges
		detect_edges.main();
		//receive in, r, mid and store in SUSAN
		c_Edgesout.receive(in, );
		c_Edgesout.receive(r, );
		c_Edgesout.receive(mid, );
		//send data to susanthin
		c_Thinin.send(r,);
		c_Thinin.send(mid,);
		//susan_thin process
		susan_thin.main();
		//receive from susan_thin
		c_Thinout.receive(mid,);
		//send data to edge draw
		c_Drawin.send(in,);
		c_Drawin.send(mid,);
		//edge_draw process
		edge_draw.main();
		//finish and file out
		notify finish;
	}
};



