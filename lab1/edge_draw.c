/**********************************************************************
 *  File Name:   edge_draw.c
 *  Author:      tengchieh
 *  Mail:        tengchieh@utexas.edu
 *  Create Time: 2015 Sep 18 05:59:15 PM
 **********************************************************************/
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
typedef  unsigned char uchar;

edge_draw(in,mid,drawing_mode)
  uchar in[], mid[];
  int  drawing_mode;
{
int   i;
uchar *inp, *midp;
int x_size = 76, y_size = 95;

  if (drawing_mode==0)
  {
    /* mark 3x3 white block around each edge point */
    midp=mid;
    for (i=0; i<x_size*y_size; i++)
    {
      if (*midp<8)
      {
        inp = in + (midp - mid) - x_size - 1;
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
      *(in + (midp - mid)) = 0;
    midp++;
  }
}
