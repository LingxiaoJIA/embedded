// c_bit64_queue.sc:	instantiation of template c_typed_queue.sh
//			with type 'bit[64]'
//
// author:      Rainer Doemer
// last update: 02/26/02

// note:
// this file "instantiates" the "templates" for type 'bit[64]';
// the "templates" are actually implemented as preprocessor macros;
// they can be "instantiated" by calling the macro with parameters;
//
// if this file is fed through the SpecC compiler such that
// SpecC code is re-generated, the "magic" behind the macros
// becomes visible; this is very useful for debugging;
// please see the file "c_bit64_queue_dbg.sc";


#include <c_typed_queue.sh>	/* make the template available */

// define the tranceiver interface for data type 'bit[64]'
DEFINE_I_TYPED_TRANCEIVER(bit64, bit[64])

// define the sender interface for data type 'bit[64]'
DEFINE_I_TYPED_SENDER(bit64, bit[64])

// define the receiver interface for data type 'bit[64]'
DEFINE_I_TYPED_RECEIVER(bit64, bit[64])


// define the queue channel for data type 'bit[64]'
DEFINE_C_TYPED_QUEUE(bit64, bit[64])


// EOF c_bit64_queue.sc
