#include <stdio.h>
#include <string.h>
import "c_queue";

typedef unsigned char uchar;

behavior EdgeDrawWhite(inout uchar input[76*95], in uchar mid[76*95], in int threadID, in int numThreads)
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

behavior EdgeDrawBlack(inout uchar input[76*95], in uchar mid[76*95], in int threadID, in int numThreads)
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

//receive in, mid
behavior EdgeDraw(i_receiver FromThin, i_sender ToWrite)
{
  uchar input[76*95];
  uchar mid[76*95];

  EdgeDrawWhite w0(input, mid, 0, 2);
  EdgeDrawWhite w1(input, mid, 1, 2);
  EdgeDrawBlack b0(input, mid, 0, 2);
  EdgeDrawBlack b1(input, mid, 1, 2);

  void main(void)
  {
    int i;
    FILE *f;

    FromThin.receive(input, 7220);
    FromThin.receive(mid, 7220);
    
    par {
      w0;
      w1;
    }
    par {
      b0;
      b1;
    }
    f = fopen("output", "a+");
    for (i = 0; i < 7220; ++i) {
      fprintf(f, "input[%d] = %d\n", i, input[i]);
      fprintf(f, "mid[%d] = %d\n", i, mid[i]);
    }
    fclose(f);

    //send in out
    ToWrite.send(input, 7220);
  }//main
};


