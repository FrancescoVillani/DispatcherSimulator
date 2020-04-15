package com.company;
public class RrDispatcher implements Dispatcher {
    /*the dispatcher has the round robin policy of dispatching*/
    private Server[] serverList;

    /* index used for the round robin logic */
    private int index = 0;

    public RrDispatcher(Server[] serverList) {
        this.serverList = serverList;
    }

    public void nextDispatch(Double packet){
        //System.out.println("Packet -> server" + index);
        serverList[this.index].insertQueue(packet);
        this.index = (this.index + 1) % this.serverList.length;
    }
}
