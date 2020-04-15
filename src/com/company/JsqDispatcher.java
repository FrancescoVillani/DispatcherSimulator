package com.company;

public class JsqDispatcher implements Dispatcher{
    /* the dispatcher sends the packet to the queue with less packets waiting */
    private Server[] serverList;


    public JsqDispatcher(Server[] serverList) {
        this.serverList = serverList;
    }

    @Override
    public void nextDispatch(Double packet) {
        Server min = serverList[0];

        for(Server a : serverList)
            if(a.getQueueSize() < min.getQueueSize())
                min = a;

        min.insertQueue(packet);
    }
}