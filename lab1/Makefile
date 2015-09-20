SCC = scc
SCFLAGs	= -vv -w -g -d -Tvcds
MAINSRCS = susan_edge_detector.sc
OTHSRCS = stimulus.sc monitor.sc susan.sc detect_edges.sc susan_thin.sc edge_draw.sc
SRCS = $(MAINSRCS) $(OTHSRCS)
CPPSRCS = $(SRCS:.sc=.cc)
OBJS = $(SRCS:.sc=.o)
SIRS = $(SRCS:.sc=.sir)
TARGETS = $(MAINSRCS:.sc=)

CMP = diff -s
RM  = rm -rf

GOLDFILE = golden.pgm
INFILE =   input_small.pgm    
OUTFILE =  output_edge.pgm

%.sir: %.sc
	$(SCC) $* -sc2sir $(SCFLAGS)

$(TARGETS): $(SIRS)
	$(SCC) $(TARGETS) -sir2out $(SCFLAGS)

all:	$(TARGETS)

test:
	./susan_edge_detector
	$(CMP) $(OUTFILE) $(GOLDFILE)

clean:
	$(RM) *.cc *.o *.h *.si *.sir $(TARGETS) $(OUTFILE)

.PHONY: all test clean
