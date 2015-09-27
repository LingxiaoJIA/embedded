#include <stdio.h>
#include <string.h>
import "c_queue";
import "read_image_fsm";
import "susan_fsm";
import "write_image";
import "c_double_handshake";

typedef  unsigned char uchar;

behavior DesignFSM(i_receive start, i_receiver stimulusQueue, i_sender dHSWriteToMonitor, event clk)
{

	const unsigned long readSusanSize = 7220;
	const unsigned long susanWriteSize = 7220;


//	c_double_handshake doubleHandToMonitor;
	c_queue queueReadToSusan(readSusanSize);
	c_queue queueSusanToWrite(susanWriteSize);

	ReadImageFSM read_image_fsm(start, stimulusQueue, queueReadToSusan);
	SusanFSM susan_fsm(queueReadToSusan, queueSusanToWrite, clk);
	WriteImage write_image(queueSusanToWrite, dHSWriteToMonitor);


	void main(void)
	{
		par{
			read_image_fsm.main();
			susan_fsm.main();
			write_image.main();
		}
	}
};



/*
behavior State4(i_receive start, i_receiver stimulusQueue, i_sender queueReadToSusan, event clk, signal bool gotImage)
{
//behavior ReadImageFSM(i_receive start, i_receiver stimulusQueue, i_sender queueReadToSusan)
	ReadImageFSM read_image_fsm(start, stimulusQueue, queueReadToSusan, gotImage);

	void main(void)
	{
		printf("in read image\n");
		read_image_fsm.main();
		wait(clk);
	}
};

behavior State5(i_receiver queueReadToSusan, i_sender queueSusanToWrite, event clk, signal bool susanFinish)
{
//behavior SusanFSM(i_receiver Port_readImage, i_sender Port_writeImage, event clk)
	SusanFSM susan_fsm(queueReadToSusan, queueSusanToWrite, clk, susanFinish);
	
	void main(void)
	{
		printf("in susan\n");
		susan_fsm.main();	
		//wait(clk);
	}
};


behavior State6(i_receiver queueSusanToWrite, i_sender dHSWriteToMonitor, event clk)
{
//behavior WriteImageFSM(i_receiver queueSusanToWrite, i_sender dHSWriteToMonitor)
	WriteImageFSM write_image_fsm(queueSusanToWrite, dHSWriteToMonitor);
	
	void main(void)
	{
		printf("in write image\n");
		write_image_fsm.main();	
		wait(clk);
	}
};


behavior DesignFSM(i_receive start, i_receiver stimulusQueue, i_sender dHSWriteToMonitor, event clk)
{
	signal bool susanFinish = false;
	signal bool gotImage = false;

	const unsigned long readSusanSize = 7220;
	const unsigned long susanWriteSize = 7220;


//	c_double_handshake doubleHandToMonitor;
	c_queue queueReadToSusan(readSusanSize);
	c_queue queueSusanToWrite(susanWriteSize);

	//ReadImage read_image(start, stimulusQueue, queueReadToSusan);
	//SusanFSM susan_fsm(queueReadToSusan, queueSusanToWrite, clk);
	//WriteImage write_image(queueSusanToWrite, dHSWriteToMonitor);
	State4 s1(start, stimulusQueue, queueReadToSusan, clk, gotImage);
	State5 s2(queueReadToSusan, queueSusanToWrite, clk, susanFinish);
	State6 s3(queueSusanToWrite, dHSWriteToMonitor, clk);

	void main(void)
	{
		fsm{
			s1:{
				//printf("in read image\n");
				//printf("Starting....\n");
				if(gotImage)
					goto s2;
			}
			s2:{
				//printf("in susan\n");
				if(susanFinish)
					goto s3;
			}
			s3:{
				//printf("in write image\n");
				//goto s1;
				break;
			}			
		}
	}
};
*/

//self test
/*
behavior Main(void)
{
	int void main(void)
	{
		
	}
};
*/
