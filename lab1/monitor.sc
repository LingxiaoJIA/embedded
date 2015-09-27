#include <stdlib.h>
#include <stdio.h>
#include "sim.sh"
#define  exit_error(IFB,IFC) { fprintf(stderr,IFB,IFC); exit(0); }
import "c_queue";
import "c_double_handshake";

//const unsigned long SIZE = 1;

behavior Monitor(
  i_receiver Port,in long long time_start)
{
char filename [100] = "output_edge.pgm";
FILE  *fd;
unsigned char input[7220];
int x_size = 76, y_size = 95;
void main(void)
{
    //while(true)
    //{
    sim_time time;
    sim_time_string buf,buf_total;
    const char *time_end_string;
    long long time_end,time_total;

    Port.receive(input,x_size*y_size);

    #ifdef FOPENB
      if ((fd=fopen(filename,"wb")) == NULL) 
    #else
      if ((fd=fopen(filename,"w")) == NULL) 
    #endif
      exit_error("Can't output image%s.\n",filename);

    fprintf(fd,"P5\n");
    fprintf(fd,"%d %d\n", x_size, y_size);
    fprintf(fd,"255\n");
  

    time=now();
    time_end_string=time2str(buf, time);
    time_end=str2ll(10,time_end_string);
    time_total= time_end - time_start;    
    printf("\nThe time is now: %s\n", ll2str(10,buf_total,time_total));
    
    if (fwrite(input, x_size*y_size, 1, fd) != 1)
      exit_error("Can't write image %s.\n",filename);

   fclose(fd);
   //}//while true
}//main
};

//For individual testing only.
/*behavior Main(void)
{
  char filename[100];
  c_queue C1((SIZE));
  Monitor R(filename,C1);
  int main(void)
  {
    R.main();
    return 0;
  }
};  */
