/**********************************************************************
 *  File Name:   setup_brightness_lut.c
 *  Author:      tengchieh
 *  Mail:        tengchieh@utexas.edu
 *  Create Time: 2015 Sep 18 05:52:05 PM
 **********************************************************************/
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
typedef  unsigned char uchar;

void setup_brightness_lut(bp,thresh,form)
  uchar bp[];
  int   thresh, form;
{
int   k;
float temp;

//printf('addr of bpptrarray = %d\n',bp);  


  //*bp=(unsigned char *)malloc(516);
  //*bp=*bp+258;

  for(k=-256;k<257;k++)
  {
    temp=((float)k)/((float)thresh);
    temp=temp*temp;
    if (form==6)
      temp=temp*temp*temp;
    temp=100.0*exp(-temp);
    //*(*bp[258]+k)= (uchar)temp;
    bp[258+k] = (uchar)temp;
  }
}
