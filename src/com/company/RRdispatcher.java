package com.company;

import javafx.util.Pair;

public class RRdispatcher {

    private Server[] serverList;

    /* index used for the round robin logic */
    private int index = 0;

    public RRdispatcher(Server[] serverList) {
        this.serverList = serverList;
    }

    public void nextDispatch(Pair<Double,Double> packet){
        System.out.println("Next packet going to server n" + index);
        serverList[this.index].esegui(packet);
        this.index = (this.index + 1) % this.serverList.length;
    }
}
