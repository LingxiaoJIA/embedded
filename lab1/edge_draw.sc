#include <stdio.h>
#include <string.h>
import "c_queue";
import "c_bit64_queue";	

typedef  unsigned char uchar;

//receive in, mid
behavior EdgeDraw(i_bit64_receiver Portin, i_bit64_sender Portout)
{

void main(void)
{
int   i;
uchar *inp, *midp;
int x_size = 76, y_size = 95;
int drawing_mode = 0;
uchar input[76 * 95];
uchar mid[76*95];
int k;
bit[64] temp;


for(k=0;k<7220;k++){
	Portin.receive(&temp);
	input[k] = temp;
}
for(k=0;k<7220;k++){
	Portin.receive(&temp);
	mid[k] = temp;
}



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
for(k=0;k<7220;k++){
	temp = input[k];
	Portout.send(temp);
}

}//main
};


