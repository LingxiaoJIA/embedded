
/* }}} */
/* {{{ defines, includes and typedefs */

/* ********** Optional settings */

#ifndef PPC
typedef int        TOTAL_TYPE; /* this is faster for "int" but should be "float" for large d masks */
#else
typedef float      TOTAL_TYPE; /* for my PowerPC accelerator only */
#endif

/*#define FOPENB*/           /* uncomment if using djgpp gnu C for DOS or certain Win95 compilers */
#define SEVEN_SUPP           /* size for non-max corner suppression; SEVEN_SUPP or FIVE_SUPP */
#define MAX_CORNERS   15000  /* max corners per frame */

/* ********** Leave the rest - but you may need to remove one or both of sys/file.h and malloc.h lines */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
#include <sys/file.h>    /* may want to remove this line */
#include <malloc.h>      /* may want to remove this line */

#include "get_image.h"
#include "put_image.h"
#include "susan_edges.h"
#include "susan_thin.h"
#include "edge_draw.h"
#include "setup_brightness_lut.h"


#define  exit_error(IFB,IFC) { fprintf(stderr,IFB,IFC); exit(0); }
#define  FTOI(a) ( (a) < 0 ? ((int)(a-0.5)) : ((int)(a+0.5)) )
typedef  unsigned char uchar;
typedef  struct {int x,y,info, dx, dy, I;} CORNER_LIST[MAX_CORNERS];

#include "setup_brightness_lut.h"
#include "susan_edges.h"
#include "susan_thin.h"
#include "edge_draw.h"

/* }}} */

/* {{{ main(argc, argv) */

/*CW main function here*/

main(argc, argv)
  int   argc;
  char  *argv [];
{
/* {{{ vars */

FILE   *ofp;
char   filename [80],
       *tcp;
//uchar  *in;
//uchar  *bp;
uchar bp[516];
//uchar bp[1];
//uchar *bpPtrArray[516];
//bpPtrArray[0] = bp;

//uchar in[1];

float  dt=4.0;
int    argindex=3,
       bt=20,
       principle=0,
       thin_post_proc=1,
       three_by_three=0,
       drawing_mode=0,
       susan_quick=0,
       max_no_edges=2650,
       mode = 0, i,
       x_size = 76, y_size = 95;

uchar in[x_size * y_size];
int r[x_size * y_size * sizeof(int)];
uchar mid[x_size*y_size];

//printf("addr of bp = %d\n",bp);
//printf("addr of bpPtrArray[0] = %d\n",bpPtrArray[0]);
//printf("addr of bpPtrArray[1] = %d\n",bpPtrArray[1]);

/* }}} */


  /*CW get the image*/
  get_image(argv[1], in, x_size, y_size);


  while (argindex < argc)
  {
    tcp = argv[argindex];
    if (*tcp == '-')
      switch (*++tcp)
      {
        case 's': /* smoothing */
          mode=0;
	  break;
        case 'e': /* edges */
          mode=1;
	  break;
        case 'c': /* corners */
          mode=2;
	  break;
        case 'p': /* principle */
          principle=1;
	  break;
        case 'n': /* thinning post processing */
          thin_post_proc=0;
	  break;
        case 'b': /* simple drawing mode */
          drawing_mode=1;
	  break;
        case '3': /* 3x3 flat mask */
          three_by_three=1;
	  break;
        case 'q': /* quick susan mask */
          susan_quick=1;
	  break;
	case 'd': /* distance threshold */
          if (++argindex >= argc){
	    printf ("No argument following -d\n");
	    exit(0);}
	  dt=atof(argv[argindex]);
          if (dt<0) three_by_three=1;
	  break;
	case 't': /* brightness threshold */
          if (++argindex >= argc){
	    printf ("No argument following -t\n");
	    exit(0);}
	  bt=atoi(argv[argindex]);
	  break;
      }
      else
        usage();
    argindex++;
  }



  if ( (principle==1) && (mode==0) )
    mode=1;
/*
  printf("principle%d\n",principle);
  printf("%d\n",principle);
  printf("%d\n",principle);
  printf("%d\n",principle);
  printf("mode%d\n",mode);
  printf("%d\n",mode);
  printf("%d\n",mode);
  printf("%d\n",mode);
*/

/* }}} */
  /* {{{ main processing */

/*CW processing part according to the mode setting above*/
/*CW 3 modes, smoothing, edges, and corners*/


      //r   = (int *) malloc(x_size * y_size * sizeof(int));
      //*bp=(unsigned char *)malloc(516);---from lut function
      setup_brightness_lut(bp,bt,6);

      memset (mid,100,x_size * y_size); /* note not set to zero */

      susan_edges(in,r,mid,bp,max_no_edges);
      susan_thin(r,mid);
      edge_draw(in,mid,drawing_mode);

//issue about in, and bp

/* }}} */
/*CW put output image*/

  put_image(argv[2],in,x_size,y_size);
}

/* }}} */

