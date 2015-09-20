#include <stdio.h>
#include <string.h>
#include <math.h>
import "c_queue";

typedef  unsigned char uchar;

//
//
//dependency between setup_brightness and susan_edges
behavior SetupBrightnessLut(i_tranceiver Port)
{
	//NEED to use port to receive bp here
	int   k;
	float temp;
	//hardcode here
	int form = 6;
	int thresh = 20;
	uchar bp[516];//make an array here and use Port to send it out and store in the array
		      //in the array at edgedetect behavior


	void main(void)
	{
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
		Port.send(bp,516);//send the bp[] out
	}
};

//  memset (mid,100,x_size * y_size);---set mid between two behaviors
//
//setup_brightness_lut(bp,bt,6);
//susan_edges(in,r,mid,bp,max_no_edges);

//uchar bp[516];
//int bt
//uchar in[x_size * y_size];
//int r[x_size * y_size * sizeof(int)];
//uchar mid[x_size*y_size];
//int max_no_edges=2650;


behavior SusanEdges(i_receiver FromBrightness, i_sender ToThin)
{
  float z;
  int   do_symmetry, i, j, m, n, a, b, x, y, w;
  uchar c,*p,*cp;
  int x_size = 76;
  int y_size = 95;
//receive in, bp
  uchar input[76 * 95];//use port to receive and store in the in[]
  uchar bp[516];

  int r[76 * 95];
  uchar mid[76*95];
  int	max_no=2650;


  
  void main(void)
  {
  //receive in and bp here
  FromBrightness.receive(input,7220);
  FromBrightness.receive(bp,516);
  memset (mid,100,x_size * y_size);//this is from susan.c main()
//original
  memset (r,0,x_size * y_size * sizeof(int));

  for (i=3;i<y_size-3;i++)
    for (j=3;j<x_size-3;j++)
    {
      n=100;
      p=input + (i-3)*x_size + j - 1;
      cp=bp+258 + input[i*x_size+j];

      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p);
      p+=x_size-3; 

      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p);
      p+=x_size-5;

      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p);
      p+=x_size-6;

      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p);
      p+=2;
      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p);
      p+=x_size-6;

      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p);
      p+=x_size-5;

      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p);
      p+=x_size-3;

      n+=*(cp-*p++);
      n+=*(cp-*p++);
      n+=*(cp-*p);

      if (n<=max_no)
        r[i*x_size+j] = max_no - n;
    }

  for (i=4;i<y_size-4;i++)
    for (j=4;j<x_size-4;j++)
    {
      if (r[i*x_size+j]>0)
      {
        m=r[i*x_size+j];
        n=max_no - m;
        cp=bp+258 + input[i*x_size+j];

        if (n>600)
        {
          p=input + (i-3)*x_size + j - 1;
          x=0;y=0;

          c=*(cp-*p++);x-=c;y-=3*c;
          c=*(cp-*p++);y-=3*c;
          c=*(cp-*p);x+=c;y-=3*c;
          p+=x_size-3; 
    
          c=*(cp-*p++);x-=2*c;y-=2*c;
          c=*(cp-*p++);x-=c;y-=2*c;
          c=*(cp-*p++);y-=2*c;
          c=*(cp-*p++);x+=c;y-=2*c;
          c=*(cp-*p);x+=2*c;y-=2*c;
          p+=x_size-5;
    
          c=*(cp-*p++);x-=3*c;y-=c;
          c=*(cp-*p++);x-=2*c;y-=c;
          c=*(cp-*p++);x-=c;y-=c;
          c=*(cp-*p++);y-=c;
          c=*(cp-*p++);x+=c;y-=c;
          c=*(cp-*p++);x+=2*c;y-=c;
          c=*(cp-*p);x+=3*c;y-=c;
          p+=x_size-6;

          c=*(cp-*p++);x-=3*c;
          c=*(cp-*p++);x-=2*c;
          c=*(cp-*p);x-=c;
          p+=2;
          c=*(cp-*p++);x+=c;
          c=*(cp-*p++);x+=2*c;
          c=*(cp-*p);x+=3*c;
          p+=x_size-6;
    
          c=*(cp-*p++);x-=3*c;y+=c;
          c=*(cp-*p++);x-=2*c;y+=c;
          c=*(cp-*p++);x-=c;y+=c;
          c=*(cp-*p++);y+=c;
          c=*(cp-*p++);x+=c;y+=c;
          c=*(cp-*p++);x+=2*c;y+=c;
          c=*(cp-*p);x+=3*c;y+=c;
          p+=x_size-5;

          c=*(cp-*p++);x-=2*c;y+=2*c;
          c=*(cp-*p++);x-=c;y+=2*c;
          c=*(cp-*p++);y+=2*c;
          c=*(cp-*p++);x+=c;y+=2*c;
          c=*(cp-*p);x+=2*c;y+=2*c;
          p+=x_size-3;

          c=*(cp-*p++);x-=c;y+=3*c;
          c=*(cp-*p++);y+=3*c;
          c=*(cp-*p);x+=c;y+=3*c;

          z = sqrt((float)((x*x) + (y*y)));
          if (z > (0.9*(float)n)) /* 0.5 */
	  {
            do_symmetry=0;
            if (x==0)
              z=1000000.0;
            else
              z=((float)y) / ((float)x);
            if (z < 0) { z=-z; w=-1; }
            else w=1;
            if (z < 0.5) { /* vert_edge */ a=0; b=1; }
            else { if (z > 2.0) { /* hor_edge */ a=1; b=0; }
            else { /* diag_edge */ if (w>0) { a=1; b=1; }
                                   else { a=-1; b=1; }}}
            if ( (m > r[(i+a)*x_size+j+b]) && (m >= r[(i-a)*x_size+j-b]) &&
                 (m > r[(i+(2*a))*x_size+j+(2*b)]) && (m >= r[(i-(2*a))*x_size+j-(2*b)]) )
              mid[i*x_size+j] = 1;
          }
          else
            do_symmetry=1;
        }
        else 
          do_symmetry=1;

        if (do_symmetry==1)
	{ 
          p=input + (i-3)*x_size + j - 1;
          x=0; y=0; w=0;

          /*   |      \
               y  -x-  w
               |        \   */

          c=*(cp-*p++);x+=c;y+=9*c;w+=3*c;
          c=*(cp-*p++);y+=9*c;
          c=*(cp-*p);x+=c;y+=9*c;w-=3*c;
          p+=x_size-3; 
  
          c=*(cp-*p++);x+=4*c;y+=4*c;w+=4*c;
          c=*(cp-*p++);x+=c;y+=4*c;w+=2*c;
          c=*(cp-*p++);y+=4*c;
          c=*(cp-*p++);x+=c;y+=4*c;w-=2*c;
          c=*(cp-*p);x+=4*c;y+=4*c;w-=4*c;
          p+=x_size-5;
    
          c=*(cp-*p++);x+=9*c;y+=c;w+=3*c;
          c=*(cp-*p++);x+=4*c;y+=c;w+=2*c;
          c=*(cp-*p++);x+=c;y+=c;w+=c;
          c=*(cp-*p++);y+=c;
          c=*(cp-*p++);x+=c;y+=c;w-=c;
          c=*(cp-*p++);x+=4*c;y+=c;w-=2*c;
          c=*(cp-*p);x+=9*c;y+=c;w-=3*c;
          p+=x_size-6;

          c=*(cp-*p++);x+=9*c;
          c=*(cp-*p++);x+=4*c;
          c=*(cp-*p);x+=c;
          p+=2;
          c=*(cp-*p++);x+=c;
          c=*(cp-*p++);x+=4*c;
          c=*(cp-*p);x+=9*c;
          p+=x_size-6;
    
          c=*(cp-*p++);x+=9*c;y+=c;w-=3*c;
          c=*(cp-*p++);x+=4*c;y+=c;w-=2*c;
          c=*(cp-*p++);x+=c;y+=c;w-=c;
          c=*(cp-*p++);y+=c;
          c=*(cp-*p++);x+=c;y+=c;w+=c;
          c=*(cp-*p++);x+=4*c;y+=c;w+=2*c;
          c=*(cp-*p);x+=9*c;y+=c;w+=3*c;
          p+=x_size-5;
 
          c=*(cp-*p++);x+=4*c;y+=4*c;w-=4*c;
          c=*(cp-*p++);x+=c;y+=4*c;w-=2*c;
          c=*(cp-*p++);y+=4*c;
          c=*(cp-*p++);x+=c;y+=4*c;w+=2*c;
          c=*(cp-*p);x+=4*c;y+=4*c;w+=4*c;
          p+=x_size-3;

          c=*(cp-*p++);x+=c;y+=9*c;w-=3*c;
          c=*(cp-*p++);y+=9*c;
          c=*(cp-*p);x+=c;y+=9*c;w+=3*c;

          if (y==0)
            z = 1000000.0;
          else
            z = ((float)x) / ((float)y);
          if (z < 0.5) { /* vertical */ a=0; b=1; }
          else { if (z > 2.0) { /* horizontal */ a=1; b=0; }
          else { /* diagonal */ if (w>0) { a=-1; b=1; }
                                else { a=1; b=1; }}}
          if ( (m > r[(i+a)*x_size+j+b]) && (m >= r[(i-a)*x_size+j-b]) &&
               (m > r[(i+(2*a))*x_size+j+(2*b)]) && (m >= r[(i-(2*a))*x_size+j-(2*b)]) )
            mid[i*x_size+j] = 2;	
        }
      }
    }	
  //do not need to send bp anymore
  ToThin.send(input,7220);
  ToThin.send(mid,7220);
  ToThin.send(r,7220*sizeof(int));
  }//void main of behavior
};


//encapsulate in detectedges
//the start is the signal from file input, the c_queue Port is the image buffer
//is there any build-buffer that better than c_queue
behavior DetectEdges(i_receiver Portin, i_sender Portout)
{
	//make port for buffer and communication
	const unsigned long sizebrightness = 516;//is it ok..?
	const unsigned long sizeedges = 43320;//is it ok..?
	c_queue		c_brightness(sizebrightness);
	c_queue		c_edges(sizeedges);
	SetupBrightnessLut	setup_brightness_lut(c_brightness);
	SusanEdges	susan_edges(c_edges, Portout);


	//bp is the array that brightness and edges will use	
	uchar bp[516];
	//r and mid buffer for edges use, and send to susanThin by Portout
	uchar input[76 * 95];//use port to receive and store in the in[]
	int r[76*95]; //mid and r and in will get updated by susan_edges
	uchar mid[76*95];//in and bp have to be sent to susan_edges

//the r is the only problem

	void main(void)
	{
		//receive from SUSAN
		Portin.receive(input,7220);//receive the whole uchar in[] array here?????
		//the process of detectEdges
		//setup brightness
		setup_brightness_lut.main();
		c_brightness.receive(bp,516);//receive all of the bp in one time???
		//send in and bp for edges use
		c_edges.send(input,7220);
		c_edges.send(bp,516);		
		//edges process
		susan_edges.main();
		//update in, r, mid
	}
};



