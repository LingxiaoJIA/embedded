/**********************************************************************
 *  File Name:   susan_edge_detector.sc
 *  Author:      lingxiao.jia
 *  Mail:        lingxiao.jia@utexas.edu
 *  Create Time: 2015 Sep 20 11:05:17 AM
 **********************************************************************/
#include <sim.sh>
#include <stdio.h>
#include <string.h>
import "c_queue";
import "c_handshake";
import "stimulus";
import "susan_fsm";
import "monitor";
import "design_fsm";
import "c_double_handshake";


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


behavior Main(void)
{

//NEW
  event	SystemClock;
  Clock	ClockGen(SystemClock);
  //signal bool gotImage;

  c_queue input_buffer(7720ul);
  c_handshake start;
  //c_queue output_buffer(7720ul);
  c_double_handshake designToMonitor;
  long long time;

  Stimulus stimulus(start, input_buffer, time);
  DesignFSM design_fsm(start, input_buffer, designToMonitor, SystemClock);
  Monitor monitor(designToMonitor, time);

  int main(void) {
    printf("Starting....\n");
    printf("Starting....\n");
    printf("Starting....\n");
    par {
      stimulus.main();
      design_fsm.main();
      monitor.main();
      ClockGen.main();
    }
    return 0;
  }
};


