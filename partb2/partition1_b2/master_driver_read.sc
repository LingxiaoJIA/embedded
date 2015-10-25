#include "susan.sh"
//#include "c_uchar7220_queue"

//import "c_uchar7220_queue";
//import "c_int7220_queue";

//import "c_uchar7220read_queue";
//import "c_uchar7220write_queue";
//import "c_uchar7220ORG_queue";
import "HWBus";

interface i_master_receiver					
{									
    void receive(unsigned char *data);						
};

channel MasterDriverRead(IMasterHardwareBus hardware_bus)implements i_master_receiver
{

	bit[15:0] addr = 0000000000000000b;

    void receive(unsigned char *data)
    {
	hardware_bus.MasterSyncReceive0();
	hardware_bus.MasterRead(addr, data, 7220);
    }
};

