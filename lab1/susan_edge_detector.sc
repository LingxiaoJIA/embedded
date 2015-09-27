/**********************************************************************
 *  File Name:   susan_edge_detector.sc
 *  Author:      lingxiao.jia
 *  Mail:        lingxiao.jia@utexas.edu
 *  Create Time: 2015 Sep 20 11:05:17 AM
 **********************************************************************/

#include <stdio.h>
import "c_queue";
import "c_handshake";
import "c_double_handshake";
import "stimulus";
import "design";
import "monitor";

behavior Main(void)
{
  c_queue input_buffer(7220ul);
  c_handshake start;
  c_double_handshake designToMonitor;
  long long time;

  Stimulus stimulus(start, input_buffer, time);
  Design design(start, input_buffer, designToMonitor);
  Monitor monitor(designToMonitor, time);

  int main(void) {
    par {
      stimulus;
      design;
      monitor;
    }
    return 0;
  }
};


