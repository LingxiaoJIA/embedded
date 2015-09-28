/**********************************************************************
 *  File Name:   get_image.sc
 *  Author:      lingxiao.jia
 *  Mail:        lingxiao.jia@utexas.edu
 *  Create Time: 2015 Sep 20 09:54:12 AM
 **********************************************************************/

#include <stdlib.h>
#include <stdio.h>
#include "sim.sh"
import "c_queue";
import "c_handshake";
import "c_bit64_queue";

#define  exit_error(IFB, IFC) { \
  fprintf(stderr, IFB, IFC); \
  exit(0); \
}

behavior Stimulus(i_send Start, i_bit64_sender InputBuffer,out long long time_start)
{

  /* {{{ get_image(filename,input,x_size,y_size) */

  /* {{{ int getint(fp) derived from XV */

  int getint(FILE *fd)
  {
    int c, i;
    char dummy[10000];

    c = getc(fd);
    while (1) /* find next integer */
    {
      if (c=='#')    /* if we're at a comment, read to end of line */
        fgets(dummy,9000,fd);
      if (c==EOF)
        exit_error("Image %s not binary PGM.\n","is");
      if (c>='0' && c<='9')
        break;   /* found what we were looking for */
      c = getc(fd);
    }

    /* we're at the start of a number, continue until we hit a non-number */
    i = 0;
    while (1) {
      i = (i*10) + (c - '0');
      c = getc(fd);
      if (c==EOF) return (i);
      if (c<'0' || c>'9') break;
    }

    return (i);
  }

  /* }}} */

  void main(void) {
  char  filename[200] = "input_small.pgm";
  unsigned char input[76*95];
  int x_size, y_size;
  FILE  *fd;
  char  header[100];
  int tmp;
  sim_time time;
  sim_time_string buf;
  const char *time_start_string;
    int k = 0;
    int j = 0;
    bit[64] temp;
  //sim_time_ll time_start;

    if ((fd=fopen(filename,"r")) == NULL)
      exit_error("Can't input image %s.\n",filename);

    /* {{{ read header */

    header[0]=fgetc(fd);
    header[1]=fgetc(fd);
    if(!(header[0]=='P' && header[1]=='5'))
      exit_error("Image %s does not have binary PGM header.\n",filename);

    x_size = getint(fd);
    y_size = getint(fd);
    tmp = getint(fd);

  /* }}} */

    if (fread(input, 1, x_size * y_size, fd) == 0)
      exit_error("Image %s is wrong size.\n", filename);

    fclose(fd);

    while(k<100) {
      waitfor(1000);  //wait for 1000 units before sending start signal.
      Start.send();
      for(j=0;j<7220;j++)
      {
	temp = input[j];
	InputBuffer.send(temp); // 76 * 95
      }
      time=now();
      time_start_string=time2str(buf,time);
      time_start=str2ll(10,time_start_string);
      k++;
    }
  }

  /* }}} */
};
