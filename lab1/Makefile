SCC = scc
SCFLAGs	= -vv -w -g -d -Tvcds
MAINSRCS = susan_edge_detector.sc
OTHSRCS = stimulus.sc monitor.sc design.sc write_image.sc read_image.sc \
					susan.sc detect_edges.sc susan_thin.sc edge_draw.sc \
					detect_edges_fsm.sc susan_thin_fsm.sc edge_draw_fsm.sc 
SRCS = $(MAINSRCS) $(OTHSRCS)
CPPSRCS = $(SRCS:.sc=.cc)
OBJS = $(SRCS:.sc=.o)
SIRS = $(SRCS:.sc=.sir)
MAINSIR = $(MAINSRCS:.sc=.sir)
TARGET = $(MAINSRCS:.sc=)

CMP = diff -s
RM  = rm -rf

GOLDFILE = golden.pgm
INFILE =   input_small.pgm    
OUTFILE =  output_edge.pgm

%.sir: %.sc
	$(SCC) $* -sc2sir $(SCFLAGS)

$(TARGET): $(SIRS)
	$(SCC) $(TARGET) -sir2out $(SCFLAGS)

all:	$(TARGET)

par: $(TARGET)
	$(SCC) $(TARGET) $(SCFLAGS) -par

list: $(TARGET)
	sir_list -t $(MAINSIR)

tree: $(TARGET)
	sir_tree -blt $(MAINSIR)

test: $(TARGET)
	./susan_edge_detector
	$(CMP) $(OUTFILE) $(GOLDFILE)

clean:
	$(RM) *.cc *.o *.h *.si *.sir $(TARGET) $(OUTFILE)

.PHONY: all par list tree test clean 
