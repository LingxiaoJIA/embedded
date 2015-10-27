//////////////////////////////////////////////////////////////////////
// File:   	HWBus.sc
//////////////////////////////////////////////////////////////////////

import "i_send";
import "i_receive";

import "c_handshake";
#include "stdio.h"
// Simple hardware bus

#define DATA_WIDTH	32u
#define ADDR_WIDTH      16u

#if DATA_WIDTH == 32u
# define DATA_BYTES 4u
#elif DATA_WIDTH == 16u
# define DATA_BYTES 2u
#elif DATA_WIDTH == 8u
# define DATA_BYTES 1u
#else
# error "Invalid data width"
#endif

/* -----  Physical layer, interrupt handling ----- */

channel MasterHardwareSyncDetect(i_receive intr)
  implements i_receive
{
  void receive(void)
  {	
   intr.receive();
  }
};

channel SlaveHardwareSyncGenerate(i_send intr)
  implements i_send
{
  void send(void)
  {
    intr.send();
  }
};

/* -----  Media access layer ----- */

interface IMasterHardwareBusLinkAccess
{
  void MasterRead(int addr, void *data, unsigned long len);
  void MasterWrite(int addr, const void* data, unsigned long len);
};
  
interface ISlaveHardwareBusLinkAccess
{
  void SlaveRead(int addr, void *data, unsigned long len);
  void SlaveWrite(int addr, const void* data, unsigned long len);
};

channel BusMACTLM()
  implements  IMasterHardwareBusLinkAccess,  ISlaveHardwareBusLinkAccess
{
  signal unsigned bit[ADDR_WIDTH-1:0] A;
  unsigned char D[7220];
  unsigned char *p;
  event    ready;
  event    ack;

  void MasterWrite (int addr, const void* data, unsigned long len)
  {
	unsigned int i;
 	A=addr;
	for(p= (unsigned char*)data, i=0; i < len; i++, p++)
	{
		D[i]=*p;
	}
	notify(ready);
	wait(ack);
	waitfor(34000*(len/4));
  }
 
  void MasterRead (int addr, void *data, unsigned long len)
  {
	unsigned int i;
	A=addr;
	notify(ready);
	wait(ack);
	for(p= (unsigned char*)data, i=0; i < len; i++, p++)
	{
		*p=D[i];
	}

	waitfor(39000*(len/4));
  }

  void SlaveWrite (int addr, const void* data, unsigned long len)
  {
	unsigned int i;
	do{
		wait(ready);
	}while(addr!=A);

	for(p= (unsigned char*)data, i=0; i < len; i++, p++)
	{
		D[i]=*p;
	}
	notify(ack);
  }

  void SlaveRead (int addr, void *data, unsigned long len) 
  {
	unsigned int i;
	do{
		wait(ready);
	}while(addr!=A);
	for(p= (unsigned char*)data, i=0; i < len; i++, p++)
	{
		*p=D[i];
	}
	notify(ack);
  }
};

/* -----  Bus instantiation example ----- */

// Bus protocol interfaces
interface IMasterHardwareBus
{
  void MasterRead(unsigned bit[ADDR_WIDTH-1:0] addr, void *data, unsigned long len);
  void MasterWrite(unsigned bit[ADDR_WIDTH-1:0] addr, const void* data, unsigned long len);
  
  void MasterSyncReceive0();
  void MasterSyncReceive1();
//  void print();
};
  
interface ISlaveHardwareBus
{
  void SlaveRead(unsigned bit[ADDR_WIDTH-1:0] addr, void *data, unsigned long len);
  void SlaveWrite(unsigned bit[ADDR_WIDTH-1:0] addr, const void* data, unsigned long len);
  
  void SlaveSyncSend0();
  void SlaveSyncSend1();
 // void print();
};


// Bus protocol channel
channel HardwareBus()
  implements IMasterHardwareBus, ISlaveHardwareBus
{

  // interrupts
  c_handshake    intr0,intr1;


  MasterHardwareSyncDetect  MasterSync0(intr0);
  SlaveHardwareSyncGenerate SlaveSync0(intr0);

  MasterHardwareSyncDetect  MasterSync1(intr1);
  SlaveHardwareSyncGenerate SlaveSync1(intr1);

//  BusTLM bus_tlm();
//  MasterHardwareBus Master(A, D, ready, ack);
//  SlaveHardwareBus  Slave(A, D, ready, ack);
BusMACTLM bus_mactlm;
//  MasterHardwareBusLinkAccess MasterLink(bus_tlm);
//  SlaveHardwareBusLinkAccess SlaveLink(bus_tlm);

//    void print() {
//	if(intr0)
//        printf("1\n");
//	else printf("0\n");
//    }
  
  void MasterRead(unsigned bit[ADDR_WIDTH-1:0] addr, void *data, unsigned long len) {
    bus_mactlm.MasterRead(addr, data, len);
  }
  
  void MasterWrite(unsigned bit[ADDR_WIDTH-1:0] addr, const void *data, unsigned long len) {
    bus_mactlm.MasterWrite(addr, data, len);
  }
  
  void SlaveRead(unsigned bit[ADDR_WIDTH-1:0] addr, void *data, unsigned long len) {
    bus_mactlm.SlaveRead(addr, data, len);
  }
  
  void SlaveWrite(unsigned bit[ADDR_WIDTH-1:0] addr, const void *data, unsigned long len) {
    bus_mactlm.SlaveWrite(addr, data, len);
  }


  
  void SlaveSyncSend0() {
//print();
    SlaveSync0.send();
//intr0[0]=0;
//print();
  }

  void MasterSyncReceive0() {
//print();
    MasterSync0.receive();
//print();
  }

  void MasterSyncReceive1() {
    MasterSync1.receive();
  }
  
  void SlaveSyncSend1() {
    SlaveSync1.send();
  }


};
