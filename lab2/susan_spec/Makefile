# -----------------------------------
# Makefile for Susan Edge Detection example
# ----------------------------------- 

# --- Macros

SCC = scc
CMP = diff -s
RM  = rm -f

SCFILES = 	c_uchar7220_queue.sc c_int7220_queue.sc\
        setup_brightness_lut.sc susan_edges.sc detect_edges.sc susan_thin.sc edge_draw.sc read_image.sc input.sc\
		write_image.sc output.sc init.sc osapi.sc os.sc pe1.sc pe2.sc stimulus.sc monitor.sc design.sc susan_edge_detector.sc

SIRFILES = $(SCFILES:.sc=.sir)

HFILES =	

SCCOPTS = 	-vv -ww -g $(DEFINES)

EXEFILE = 	susan_edge_detector
EXEFILE_PAR = susan_edge_detector_par
GOLDFILE =	golden.pgm
TESTFILE=	out.pgm


#--- Rules

.SUFFIXES: .sc .sir

.sc.sir:
	$(SCC) $* -sc2sir $(SCCOPTS)


#--- Build process

all: $(EXEFILE) 

$(EXEFILE): $(SIRFILES)
	$(SCC) $(EXEFILE) -sc2out $(SCCOPTS)

clean:
	$(RM) *~ *.si *.o *.h *.cc out*.pgm
	$(RM) $(SIRFILES) 
	$(RM) $(EXEFILE) $(EXEFILE_PAR)

test:	$(EXEFILE)
	./$(EXEFILE)
	$(CMP) $(TESTFILE) $(GOLDFILE)


