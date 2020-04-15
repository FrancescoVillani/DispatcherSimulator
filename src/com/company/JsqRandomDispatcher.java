package com.company;


import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class JsqRandomDispatcher implements Dispatcher {
    /* the dispatcher will choose K <= queues uniformly and use a JSQ policy on the K queues*/
    private Server[] serverList;
    private boolean debug= false;

    /* to recreate this policy i'll shuffle the servers, but i don't want to do it on serverList as the statistics would be all wrong
     */
    private int choosenIndex[];
    private int size;

    public JsqRandomDispatcher(Server[] serverList) {
        this.serverList = serverList;
    }

    @Override
    public void nextDispatch(Double packet) {
        size = (int)Math.floor(Math.random()*serverList.length) + 1;
        if(debug)
            System.out.print("size : " + size + ", ");
        choosenIndex = new int[serverList.length];

        int i;
        for(i = 0; i < choosenIndex.length; ++i) choosenIndex[i] = i;

        shuffle(choosenIndex);

        Server min = serverList[choosenIndex[0]];
        if(debug)
            System.out.print("Randomly choosen queues: ");

        for(i = 0; i < size; ++i) {
            if(debug)
                System.out.print((choosenIndex[i] + 1) + " | ");
            if (serverList[choosenIndex[i]].getQueueSize() < min.getQueueSize())
                min = serverList[choosenIndex[i]];
        }
        if(debug) {
            System.out.print("\n");
            System.out.println("packet -> " + min.getTag());
        }
        min.insertQueue(packet);
    }

    private void shuffle(int[] array){
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; --i)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }
}
