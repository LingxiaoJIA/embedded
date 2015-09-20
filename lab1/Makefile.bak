CC = gcc
CFLAGS = -static -O4
MAINSRCS = susan.c
OTHSRCS = get_image.c put_image.c susan_edges.c susan_thin.c setup_brightness_lut.c edge_draw.c
SRCS = $(MAINSRCS) $(OTHSRCS)
OBJS = $(SRCS:.c=.o)
TARGETS = $(MAINSRCS:.c=.out)

CMP = diff -s
RM  = rm -rf

GOLDFILE = golden.pgm
INFILE =   input_small.pgm    
OUTFILE =  output_edge.pgm

all: $(TARGETS)

%.o: %.c
	$(CC) $(CFLAGS) -o $@ -c $< 

$(TARGETS): $(OBJS)
	$(CC) $(CFLAGS) -o $(TARGETS) $(OBJS) -lm

test:
	./susan.out $(INFILE) $(OUTFILE) -e
	$(CMP) $(OUTFILE) $(GOLDFILE)

clean:
	$(RM) $(OBJS) $(TARGETS) $(OUTFILE)

.PHONY: all test clean
