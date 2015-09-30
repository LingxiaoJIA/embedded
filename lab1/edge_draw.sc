#include <stdio.h>
#include <string.h>
import "c_queue";
import "c_bit64_queue";	

typedef unsigned char uchar;

behavior EdgeDrawWhite(inout uchar input[76*95], in uchar mid[76*95], 
    in int threadID, in int numThreads)
{
  void main(void)
  {
  int i;
  int x_size = 76, y_size = 95;
  int blockSize;
  uchar *inp, *midp;

    blockSize = x_size * y_size / numThreads;
    midp = mid + threadID * blockSize;
    /* mark 3x3 white block around each edge point */
    for (i=0; i<blockSize; i++)
    {
      if (*midp<8) 
      {
        inp = input + (midp - mid) - x_size - 1;
        *inp++=255; *inp++=255; *inp=255; inp+=x_size-2;
        *inp++=255; *inp++;   *inp=255; inp+=x_size-2;
        *inp++=255; *inp++=255; *inp=255;
      }
      midp++;
    }
  }
};

behavior EdgeDrawBlack(inout uchar input[76*95], in uchar mid[76*95], 
    in int threadID, in int numThreads)
{
  void main(void)
  {
  int i;
  int x_size = 76, y_size = 95;
  int blockSize;
  uchar *inp, *midp;

    blockSize = x_size * y_size / numThreads;
    midp = mid + threadID * blockSize;
    /* mark 1 black pixel at each edge point */
    for (i=0; i<blockSize; i++)
    {
      if (*midp<8) 
      {
        *(input + (midp - mid)) = 0;
      }
      midp++;
    }
  }
};

behavior EdgeDraw(i_bit64_receiver Portin, i_bit64_sender Portout)
{
  uchar input[76*95];
  uchar mid[76*95];

  EdgeDrawWhite w0(input, mid, 0, 4);
  EdgeDrawWhite w1(input, mid, 1, 4);
  EdgeDrawWhite w2(input, mid, 2, 4);
  EdgeDrawWhite w3(input, mid, 3, 4);

  EdgeDrawBlack b0(input, mid, 0, 4);
  EdgeDrawBlack b1(input, mid, 1, 4);
  EdgeDrawBlack b2(input, mid, 2, 4);
  EdgeDrawBlack b3(input, mid, 3, 4);

  void main(void)
  {
  int k;
  bit[64] temp;
  int i;
  FILE *f;

    for(k=0;k<7220;k++){
      Portin.receive(&temp);
      input[k] = temp;
    }
    for(k=0;k<7220;k++){
      Portin.receive(&temp);
      mid[k] = temp;
    }
    
    par {
      w0;
      w1;
      w2;
      w3;
    }
    par {
      b0;
      b1;
      b2;
      b3;
    }

    for(k=0;k<7220;k++){
      temp = input[k];
      Portout.send(temp);
    }
  }//main
};


