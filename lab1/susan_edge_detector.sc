/**********************************************************************
 *  File Name:   susan_edge_detector.sc
 *  Author:      lingxiao.jia
 *  Mail:        lingxiao.jia@utexas.edu
 *  Create Time: 2015 Sep 20 11:05:17 AM
 **********************************************************************/

#include <stdio.h>
import "c_queue";
import "c_handshake";
import "stimulus";
import "susan";
import "monitor";
import "design";
import "c_double_handshake";

behavior Main(void)
{
  c_queue input_buffer(7720ul);
  c_handshake start;
  //c_queue output_buffer(7720ul);
  c_double_handshake designToMonitor;

  Stimulus stimulus(start, input_buffer);
  //Susan susan(start, input_buffer, output_buffer);
  Design design(start, input_buffer, designToMonitor);
  Monitor monitor(designToMonitor);

  int main(void) {
    par {
      stimulus;
      //susan;
      design;
      monitor;
    }
    return 0;
  }
};


