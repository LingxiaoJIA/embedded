/**********************************************************************
 *  File Name:   os.sc
 *  Author:      lingxiao.jia
 *  Mail:        lingxiao.jia@utexas.edu
 *  Create Time: 2015 Oct 17 11:13:03 AM
 **********************************************************************/

#include "susan.sh"

import "c_handshake";
import "osapi";

#define NUMTHREADS  15

channel OS implements OSAPI {
    int NumCreated = 0;
    c_handshake e0, e1, e2, e3, e4, e5, e6, e7, e8, e9;
    Task tasks[NUMTHREADS];
    Task *RunPt = 0;
    Task *NextPt = 0;

    // Task current = 0;
    // os_queue rdyq;

    /* helper function */
    void Remove(Task **listPt){
        if (*listPt == 0) return;
        if ((*listPt)->prev == 0 && (*listPt)->next == 0) return;
        (*listPt)->prev->next = (*listPt)->next;
        (*listPt)->next->prev = (*listPt)->prev;
    }

    void Insert(Task *listPt, Task *currPt){
        currPt->prev = listPt->prev;
        currPt->prev->next = currPt;
        currPt->next = listPt;
        listPt->prev = currPt;
    }

    void dispatch(void) {
    /*
        current = schedule(rdyq);
        if(current)
            notify(current.event);
    */
    }

    void yield() {
    /*
        task = current;
        rdyq.insert(task);
        dispatch();
        wait(task.event);
    */
    }

    /* OS management */
    void init() {

    }

    void start() {

    }

    /* Task management */
    Task* task_create(char *name) {
        int idx = 0;
        Task *currPt;
        if (NumCreated == NUMTHREADS) {     // Meet maximum number of threads
            return 0;
        }
        while (tasks[idx].id != 0) {
            idx++;
        }
        NumCreated++;
        currPt = &tasks[idx];
        currPt->id = idx+1;
        return currPt;
    }

    void task_terminate() {

    }

    void task_activate(Task *currPt) {
        if (RunPt == 0) {
            RunPt = currPt;
            RunPt->prev = RunPt->next = RunPt;
        } else {
            Insert(RunPt, currPt);
        }
        NextPt = RunPt->next;
    }

    Task* par_start() {
        Task *currPt;
        return currPt;
    }

    void par_end(Task *t) {

    }

    /* Event handling */
    Task pre_wait() {
        Task t;
        dispatch();
        return t;
    }

    void post_wait(Task t) {
    /*
        rdyq.insert(t);
        if (!current) dispatch();
        wait(t.event);
    */
    }

    /* Time modeling */
    void time_wait(sim_time t) {
        waitfor(t);
        yield();
    }
};

