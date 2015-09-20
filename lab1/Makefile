SCC = scc
SCFLAGs	= -vv -w -g -d -Tvcds
MAINSRCS = susan_edge_detector.sc
OTHSRCS = stimulus.sc monitor.sc susan.sc setup_brightness_lut.sc susan_edges.sc susan_thin.sc edge_draw.sc
SRCS = $(MAINSRCS) $(OTHSRCS)
CPPSRCS = $(SRCS:.sc=.cc)
OBJS = $(SRCS:.sc=.o)
TARGETS = $(MAINSRCS:.sc=)

CMP = diff -s
RM  = rm -rf

GOLDFILE = golden.pgm
INFILE =   input_small.pgm    
OUTFILE =  output_edge.pgm

.SUFFIXES:	.sc .cc .o

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

all:	$(TARGETS)

test:
	./susan_edge_detector
	$(CMP) $(OUTFILE) $(GOLDFILE)

clean:
	$(RM) *.cc *.o *.h *.si *.sir $(TARGETS) $(OUTFILE)

.PHONY: all test clean
