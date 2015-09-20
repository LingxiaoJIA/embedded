#include <stdlib.h>
#include <stdio.h>
#define  exit_error(IFB,IFC) { fprintf(stderr,IFB,IFC); exit(0); }
import "c_queue";

//const unsigned long SIZE = 1;

behavior Monitor(
  in char OUTFILE[100],
  i_receiver Port)
{
char filename [100];
FILE  *fd;
unsigned char input[7220];
int x_size = 76, y_size = 95;

  void main(void)
  {
    filename=OUTFILE;
    #ifdef FOPENB
      if ((fd=fopen(filename,"wb")) == NULL) 
    #else
      if ((fd=fopen(filename,"w")) == NULL) 
    #endif
      exit_error("Can't output image%s.\n",filename);

    fprintf(fd,"P5\n");
    fprintf(fd,"%d %d\n", x_size, y_size);
    fprintf(fd,"255\n");
  
    Port.receive(input,x_size*y_size);

    if (fwrite(input, x_size*y_size, 1, fd) != 1)
      exit_error("Can't write image %s.\n",filename);

   fclose(fd);
  }
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
