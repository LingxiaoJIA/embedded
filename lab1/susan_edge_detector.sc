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

behavior Main(void)
{
  c_queue input_buffer(7720ul);
  c_handshake start;
  c_queue output_buffer(7720ul);

  Stimulus stimulus(start, input_buffer);
  Susan susan(start, input_buffer, output_buffer);
  Monitor monitor(output_buffer);

  int main(void) {
    par {
      stimulus;
      susan;
      monitor;
    }
    return 0;
  }
};


