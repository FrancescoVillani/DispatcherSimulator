package com.company;

public class Main {

    public static void main(String[] args) {
        double totalResponseTime = 0;
        int packetProcessed = 0;
        int packetNumber = 20;
	    Server[] serverList = new Server[4];
        serverList[0] = new Server(x -> (1.0/2.0), "server 1");
        serverList[1] = new Server(x -> (1.0/5.0), "server 2");
        serverList[2] = new Server(x -> (1.0/8.0), "server 3");
        serverList[3] = new Server(x -> (1.0/17.0), "server 4");
        /*serverList[3] = new Server(x -> -(1.0/5.0) * Math.log(x));*/

        Dispatcher[] dispatcher = new Dispatcher[5];
        dispatcher[0] = new RrDispatcher(serverList);
        dispatcher[1] = new RandomDispatcher(serverList);
        dispatcher[2] = new JsqDispatcher(serverList);
        dispatcher[3] = new JsqRandomDispatcher(serverList);
        dispatcher[4] = new WeighedRandomDispatcher(serverList);

        PacketGenerator gen = new PacketGenerator(x -> -(1.0/20.0) * Math.log(x));



        double time = 0.0;
        double nextArrival;

        for(int i = 0; i < packetNumber; ++i){
            /*a packet arrives at time nextArrival*/
            nextArrival = time + gen.getServiceRate();
            System.out.println("new packet at " + nextArrival );

            /*every server works until nextArrival*/
            for (Server a : serverList){ a.goTo(nextArrival); }

            /*the packet is sent to a server depending on the policy*/
            dispatcher[0].nextDispatch(nextArrival);
            time = nextArrival;

/*
            System.out.println("Content of the queues: ");
            System.out.println("server 1: " + serverList[0].queueToString());
            System.out.println("server 2: " + serverList[1].queueToString());
            System.out.println("server 3: " + serverList[2].queueToString());
            System.out.println("server 4: " + serverList[3].queueToString());

 */






        }
        /*finish emptying all server queues*/
        for (Server a : serverList){ a.emptyQueue(); }


        System.out.println("STATISTICS:");
        System.out.println("mean response time of server 1: " + serverList[0].getTotalResponseTime()/serverList[0].getPacketNumber() + " | processed : " + serverList[0].getPacketNumber());
        System.out.println("mean response time of server 2: " + serverList[1].getTotalResponseTime()/serverList[1].getPacketNumber() + " | processed : " + serverList[1].getPacketNumber());
        System.out.println("mean response time of server 3: " + serverList[2].getTotalResponseTime()/serverList[2].getPacketNumber() + " | processed : " + serverList[2].getPacketNumber());
        System.out.println("mean response time of server 4: " + serverList[3].getTotalResponseTime()/serverList[3].getPacketNumber() + " | processed : " + serverList[3].getPacketNumber());


        for (Server a : serverList){ totalResponseTime += a.getTotalResponseTime(); packetProcessed += a.getPacketNumber(); }
        System.out.println("Packets sent: " + packetNumber + " | Packet processed : " + packetProcessed);
        System.out.println("Total mean response time: " + (totalResponseTime/packetNumber));


/*
        System.out.println("Content of the queues (should be empty): ");
        System.out.println("server 1: " + serverList[0].queueToString());
        System.out.println("server 2: " + serverList[1].queueToString());
        System.out.println("server 3: " + serverList[2].queueToString());
        System.out.println("server 4: " + serverList[3].queueToString());

 */
    }
}
