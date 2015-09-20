/**********************************************************************
 *  File Name:   get_image.sc
 *  Author:      lingxiao.jia
 *  Mail:        lingxiao.jia@utexas.edu
 *  Create Time: 2015 Sep 20 09:54:12 AM
 **********************************************************************/

#include <stdio.h>
import "c_queue";
import "c_handshake";

#define  exit_error(IFB, IFC) { \
  fprintf(stderr, IFB, IFC); \
  exit(0); \
}

behavior Stimulus(i_sender Start, out i_sender ImageBuffer)
{

  /* {{{ get_image(filename,in,x_size,y_size) */

  /* {{{ int getint(fp) derived from XV */

  int getint(fd)
    FILE *fd;
  {
    int c, i;
    char dummy[10000];

    c = getc(fd);
    while (1) /* find next integer */
    {
      if (c=='#')    /* if we're at a comment, read to end of line */
        fgets(dummy,9000,fd);
      if (c==EOF)
        exit_error("Image %s not binary PGM.\n","is");
      if (c>='0' && c<='9')
        break;   /* found what we were looking for */
      c = getc(fd);
    }

    /* we're at the start of a number, continue until we hit a non-number */
    i = 0;
    while (1) {
      i = (i*10) + (c - '0');
      c = getc(fd);
      if (c==EOF) return (i);
      if (c<'0' || c>'9') break;
    }

    return (i);
  }

  /* }}} */

  void main(void) {
  char  filename[200] = "input_small.pgm";
  unsigned char in[76*95]
  int x_size, y_size;
  FILE  *fd;
  char  header[100];
  int tmp;

    if ((fd=fopen(filename,"r")) == NULL)
      exit_error("Can't input image %s.\n",filename);

    /* {{{ read header */

    header[0]=fgetc(fd);
    header[1]=fgetc(fd);
    if(!(header[0]=='P' && header[1]=='5'))
      exit_error("Image %s does not have binary PGM header.\n",filename);

    x_size = getint(fd);
    y_size = getint(fd);
    tmp = getint(fd);

  /* }}} */

    if (fread(in, 1, x_size * y_size, fd) == 0)
      exit_error("Image %s is wrong size.\n", filename);

    fclose(fd);
    ImageBuffer.send(in, 7220ul); // 76 * 95
  }

  /* }}} */
};
