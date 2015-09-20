#include <stdio.h>
#include <string.h>
import "c_queue";

typedef  unsigned char uchar;

//receive in, mid
behavior EdgeDraw(i_receiver Portin, i_sender Portout)
{

void main(void)
{
int   i;
uchar *inp, *midp;
int x_size = 76, y_size = 95;
int drawing_mode = 0;
uchar input[76 * 95];
uchar mid[76*95];

  Portin.receive(input, 7220);
  Portin.receive(mid, 7220);

  if (drawing_mode==0)
  {
    /* mark 3x3 white block around each edge point */
    midp=mid;
    for (i=0; i<x_size*y_size; i++)
    {
      if (*midp<8) 
      {
        inp = input + (midp - mid) - x_size - 1;
        *inp++=255; *inp++=255; *inp=255; inp+=x_size-2;
        *inp++=255; *inp++;     *inp=255; inp+=x_size-2;
        *inp++=255; *inp++=255; *inp=255;
      }
      midp++;
    }
  }

  /* now mark 1 black pixel at each edge point */
  midp=mid;
  for (i=0; i<x_size*y_size; i++)
  {
    if (*midp<8) 
      *(input + (midp - mid)) = 0;
    midp++;
  }
  //send in out
  Portout.send(input, 7220);
}//main
};


