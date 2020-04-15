package com.company;

public class RandomDispatcher implements Dispatcher{
    /*the dispatcher has the random policy of dispatching, every packet is sent to a server choosen at random*/
    private Server[] serverList;

    public RandomDispatcher(Server[] serverList) {
        this.serverList = serverList;
    }

    public void nextDispatch(Double packet){
        int random = (int)Math.floor(Math.random() * serverList.length);
        //System.out.println("Packet -> server " + random);
        serverList[random].insertQueue(packet);
    }
}
