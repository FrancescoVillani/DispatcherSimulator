package com.company;

public class Main {

    public static void main(String[] args) {
	    Server[] serverList = new Server[4];
        serverList[0] = new Server(x -> 2.0);
        serverList[1] = new Server(x -> 2.0);
        serverList[2] = new Server(x -> 2.0);
        serverList[3] = new Server(x -> 2.0);

        RRdispatcher roundRobin = new RRdispatcher(serverList);
        for(int i = 0; i < 10; i++){
            //roundRobin.nextDispatch();
        }
    }
}
