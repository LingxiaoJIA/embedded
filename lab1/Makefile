SCC = scc
SCFLAGs	= -vv -w -g -d -Tvcds
MAINSRCS = susan.sc
OTHSRCS = get_image.sc put_image.sc susan_edges.sc susan_thin.sc setup_brightness_lut.sc edge_draw.sc
SRCS = $(MAINSRCS) $(OTHSRCS)
CPPSRCS = $(SRCS:.sc=.cc)
OBJS = $(SRCS:.sc=.o)
TARGETS = $(MAINSRCS:.sc=)

CMP = diff -s
RM  = rm -rf

GOLDFILE = golden.pgm
INFILE =   input_small.pgm    
OUTFILE =  output_edge.pgm

.sc.cc:
	$(SCC) $* -sc2cch $(SCFLAGS)

.cc.o:
	$(SCC) $* -cc2o $(SCFLAGS)

.o:
	$(SCC) $* -o2out $(SCFLAGS)

.cc:
	$(SCC) $* -cc2out $(SCFLAGS)

.sc:
	$(SCC) $* -sc2out $(SCFLAGS)

all: $(TARGETS)

test:
	./$$TARGETS	
	$(CMP) $(OUTFILE) $(GOLDFILE)

clean:
	$(RM) $(CPPSRCS) $(OBJS) $(TARGETS) $(OUTFILE)

.PHONY: all test clean
