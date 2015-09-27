#include <stdio.h>
#include <string.h>
#include <sim.sh>
import "c_queue";
import "detect_edges_fsm";
import "susan_thin_fsm";
import "edge_draw_fsm";

//import "detect_edges";
//import "susan_thin";
//import "edge_draw";
//import "c_handshake";

//bool	Condition1 = false,
//	Condition2 = true;

typedef  unsigned char uchar;

//contains of the FSM behavior of detect_edges
//contains of the FSM behavior of susan_thin
//contains of the FSM behavior of edge_draw

behavior State1(i_receiver Port_readImage, i_sender c_Edgesout, event clk)
{
	DetectEdgesFSM detect_edges_fsm(Port_readImage, c_Edgesout);
	
	void main(void)
	{
		printf("in detect_edges\n");
		detect_edges_fsm.main();
		wait(clk);
	}	
};


behavior State2(i_receiver c_Edgesout, i_sender c_Drawin, event clk)
{
	SusanThinFSM susan_thin_fsm(c_Edgesout, c_Drawin);

	void main(void)
	{
		printf("in susan_thin\n");
		susan_thin_fsm.main();
		wait(clk);
	}
};


behavior State3(i_receiver c_Drawin, i_sender Port_writeImage, event clk, signal bool susanFinish)
{
	EdgeDrawFSM edge_draw_fsm(c_Drawin, Port_writeImage);
	
	void main(void)
	{
		printf("in edge_draw\n");
		edge_draw_fsm.main();
		wait(clk);
		notify(susanFinish);
	}
};


behavior SusanFSM(i_receiver Port_readImage, i_sender Port_writeImage, event clk, signal bool susanFinish)
{
	const unsigned long sizeEdgesout = 43320 ;//is it ok..?
	const unsigned long sizeDrawin = 14440;

	c_queue		c_Edgesout(sizeEdgesout);
	c_queue		c_Drawin(sizeDrawin);

	//DetectEdgesFSM detect_edges_fsm(Port_readImage, c_Edgesout, clk);
	//SusanThinFSM susan_thin_fsm(c_Edgesout, c_Drawin, clk);
	//EdgeDrawFSM edge_draw_fsm(c_Drawin, Port_writeImage, clk);

	sim_time_string	buf;	

	State1 s1(Port_readImage, c_Edgesout, clk);
	State2 s2(c_Edgesout, c_Drawin, clk);
	State3 s3(c_Drawin, Port_writeImage,clk, susanFinish);


	void main(void)
	{
		//detect_edges.main();
		//susan_thin.main();
		//edge_draw.main();

		fsm{
			s1:
			   {//the initial state, get the input from the read_image
			    //and do the detect_edges behavior
			    //then go to the state 2
				goto s2;

				
			    }
			s2:
			   {//the susan thin process
			    //then go to the state 3
				goto s3;

				
			    }
			s3: 
			   {//the edge_draw process, 
			    //go to the S1 or S2 depends on whether there is an input
			    //from read_image
				//goto s1;
			    //SEE if there is image comes from read_image to decide whether go to s2 or not
				
				break;
				
			    }
		}
	}
};

/*
// definition of the clock generator
behavior Clock (event clk)
{
	void main(void)
	{
	int		i;
	sim_time_string	buf;
	for(i=1; i<10; i++)	// the demo shouldn't run forever
		{
		waitfor(100);
		printf("Time =%5s : Clock-tick!\n", time2str(buf, now()));
		notify(clk);
		}
	}
};
*/




/*
behavior Main()
{
	uchar input[7220];
	const unsigned long stimulusSize = 7220;
	const unsigned long monitorSize = 7220;
	uchar output[7220];

	c_queue		c1(stimulusSize);
	c_handshake	c2;
	c_queue		c3(monitorSize);
	Susan susan(c2,c1,c3);

	int main(void)
	{
		c1.send(input,7220);
		c2.send();
		
		susan.main();
		
		c3.receive(output,7220);

		return(0);
	}
};
*/


