#include "susan.sh"

//import "c_uchar7220ORG_queue";
import "HWBus";

interface i_master_sender					
{									
    void send(unsigned char *data);						
};

channel MasterDriverWrite(IMasterHardwareBus hardware_bus)implements i_master_sender
{

	bit[15:0] addr = 0000000000000001b;


    void send(unsigned char *data)
    {
	hardware_bus.MasterSyncReceive1();
	hardware_bus.MasterWrite(addr, data, 7220);
    }
};

