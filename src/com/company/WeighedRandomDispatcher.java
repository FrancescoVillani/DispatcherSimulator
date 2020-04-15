package com.company;


public class WeighedRandomDispatcher implements Dispatcher{

    private Server[] serverList;
    private double[] weighs;
    private boolean debug= false;

    public WeighedRandomDispatcher(Server[] serverList) {

        this.serverList = serverList;
        weighs = new double[serverList.length];
    }


    @Override
    public void nextDispatch(Double packet) {
        double totalWeigh = 0;
        for(Server a : serverList)
            totalWeigh += 1.0/(a.getQueueSize()+1.0);

        if(debug)
            System.out.print("Weigh array: ");
        for(int i = 0; i < serverList.length; ++i){
            if(i == 0)
                weighs[i] = 1.0/(serverList[i].getQueueSize()+1.0)/totalWeigh;
            else
                weighs[i] = weighs[i-1] + 1.0/(serverList[i].getQueueSize()+1)/totalWeigh;

            if(debug)
                System.out.print(weighs[i] + " ");
        }
        if(debug)
            System.out.print("\n");

        double rnd = Math.random();
        int choosen = -1;
        int i = 0;
        while(i < weighs.length && choosen == -1){
            if (rnd < weighs[i]){
                choosen = i;
            }
            i++;
        }
        serverList[choosen].insertQueue(packet);
        if(debug)
            System.out.println("random value: " + rnd + " choosen server: " + (choosen + 1));
    }
}
