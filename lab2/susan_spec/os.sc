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
    c_handshake e1, e2, e3, e4, e5, e6, e7, e8, e9, e10;
    Task tasks[NUMTHREADS];
    Task *RunPt = 0;
    Task *NextPt = 0;

    // Task current = 0;
    // os_queue rdyq;

    /* helper function */
    int getNumCreated() {
        return NumCreated;
    }

    void print() {
        Task *temp;
        if (RunPt == 0) return;
        temp = RunPt->next;
        while (temp != RunPt) {
            printf("%d\n", temp->id);
            temp = temp->next;
        }
        printf("%d\n", temp->id);
    }

    void Remove(Task **listPt){
        if (*listPt == 0) return;
        if ((*listPt)->next == (*listPt)) {
            *listPt = 0;
            return;
        }
        (*listPt)->prev->next = (*listPt)->next;
        (*listPt)->next->prev = (*listPt)->prev;
        *listPt = (*listPt)->prev;
    }

    void Insert(Task *listPt, Task *currPt){
        currPt->prev = listPt->prev;
        currPt->prev->next = currPt;
        currPt->next = listPt;
        listPt->prev = currPt;
    }

    void os_wait(int id) {
        switch (id) {
            case 1: e1.receive(); break;
            case 2: e2.receive(); break;
            case 3: e3.receive(); break;
            case 4: e4.receive(); break;
            case 5: e5.receive(); break;
            case 6: e6.receive(); break;
            case 7: e7.receive(); break;
            case 8: e8.receive(); break;
            case 9: e9.receive(); break;
            case 10: e10.receive(); break;
            default: break;
        }
    }

    void os_notify(int id) {
        switch (id) {
            case 1: e1.send(); break;
            case 2: e2.send(); break;
            case 3: e3.send(); break;
            case 4: e4.send(); break;
            case 5: e5.send(); break;
            case 6: e6.send(); break;
            case 7: e7.send(); break;
            case 8: e8.send(); break;
            case 9: e9.send(); break;
            case 10: e10.send(); break;
            default: break;
        }
    }

    void dispatch(void) {
        RunPt = NextPt;
        NextPt = RunPt->next;
        os_notify(RunPt->id);
    }

    void yield() {
        Task *currPt;
        currPt = RunPt;
        dispatch();
        os_wait(currPt->id);
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
        if (RunPt == 0) {
            RunPt = currPt;
            RunPt->prev = RunPt->next = RunPt;
        } else {
            Insert(RunPt, currPt);
        }
        NextPt = RunPt->next;
        return currPt;
    }

    void task_terminate() {
        Task *currPt;
        currPt = RunPt;
        Remove(&RunPt);
        if (currPt) {
            currPt->id = 0;
            NumCreated--;
        }
        if (RunPt) dispatch();
    }

    void task_activate(Task *currPt) {
        os_wait(currPt->id);
    }

    Task* par_start() {
        Task *currPt;
        currPt = RunPt;
        Remove(&RunPt);
        if (currPt) {
            NumCreated--;
        }
        if (RunPt) dispatch();
        return currPt;
    }

    void par_end(Task *currPt) {
        if (RunPt == 0) {
            RunPt = currPt;
            RunPt->prev = RunPt->next = RunPt;
        } else {
            Insert(RunPt, currPt);
        }
        NumCreated++;
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

