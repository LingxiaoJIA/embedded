# ----------------------------------------------------------------------------
# setup.sh: shell setup for the SpecC system
# ----------------------------------------------------------------------------
# USAGE: ". $SPECC/bin/setup.sh"
#
# author:	Rainer Doemer
# last update:	May 24, 2002


# define the SpecC system home directory
export SPECC; SPECC=/home/projects/gerstl/esld/scc

# extend the search path
export PATH; PATH=$PATH:$SPECC/bin

# extend the manual path (if any)
if [ -n "$MANPATH" ]; then
	export MANPATH; MANPATH=$MANPATH:$SPECC/man
fi

# extend the search path for dynamically-linked libraries
# (usually not necessary)
#if [ -z "$LD_LIBRARY_PATH" ]; then
#	export LD_LIBRARY_PATH; LD_LIBRARY_PATH=$SPECC/lib;
#else
#	export LD_LIBRARY_PATH; LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$SPECC/lib;
#fi


# EOF
