/**********************************************************************
 *  File Name:   osapi.sc
 *  Author:      lingxiao.jia
 *  Mail:        lingxiao.jia@utexas.edu
 *  Create Time: 2015 Oct 17 03:46:04 PM
 **********************************************************************/

#include "susan.sh"

typedef struct Task {
    struct Task *next;  // linked-list pointer
    struct Task *prev;  // point to previous task
    int32_t id;
} Task;

interface OSAPI
{
    int getNumCreated();
    void print();
    /* OS management */
    void init();
    void start();

    /* Task management */
    Task* task_create(char *name);
    void task_terminate();
    void task_activate(Task *t);

    Task* par_start();
    void par_end(Task *t);

    /* Event handling */
    Task* pre_wait();
    void post_wait(Task *t);

    /* Time modeling */
    void time_wait(sim_time nsec);
};

