package com.company;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;


public class Server {

    /*packet queue, a packet will be defined by its arrival time in the system and it's arrival time relative only to
    * a queue, this is to divide the overall logic*/
    private Queue<Double> queue = new LinkedList<>();
    /*defined as packets per second*/
    private Function<Double, Double> serviceRate;
    /*time relative to server operations*/
    private double currentTime = 0.0;
    /*time next packet finished being processed, starts at -1.0 to prevent errors on the first packet*/
    private double nextDeparture = -1.0;
    /*number of packets processed by the server*/
    private int packetNumber = 0;
    /*total response time of the server*/
    private double totalResponseTime = 0.0;
    /*flag used for debugging*/
    private boolean debug = false;
    private String tag;

    /*the constructor takes a lambda with an Uniform(0,1) as input and returns a value from a known distribution, if the
    * service rate needs to be constant the function should always return the same value.*/
    public Server(Function<Double, Double> serviceRate, String tag) {
        this.serviceRate = serviceRate;
        this.tag = "[" + tag + "] ";
    }

    /*giving in input a value from an Uniform(0,1) to get a value from a known distribution*/
    public double getServiceRate() {
        return serviceRate.apply(Math.random());
    }

    /*stab to insert packets in the queue */
    public void insertQueue(Double packet){
        queue.add(packet);
    }

    public String queueToString(){
        return queue.toString();
    }

    public void goTo(double time){
        while (currentTime < time){
            if(queue.isEmpty() && nextDeparture <= currentTime) {
                if (debug)
                    System.out.println(tag + "queue is empty, advancing to " + time);
                currentTime = time;
            }
            else{
                /*if the departure has already happened, i'll start working on a new packet*/
                if (nextDeparture <= currentTime){
                    double packet = queue.peek();
                    nextDeparture = currentTime + (getServiceRate());
                    if (debug)
                        System.out.println(tag + "server is processing nothing so i'll work on the next packet until " + nextDeparture);
                    totalResponseTime += nextDeparture - packet;
                }
                else{
                    /* finish processing the current packet, if the departure is before the time i'm going to */
                    if(nextDeparture <= time) {
                        currentTime = nextDeparture;
                        queue.remove();
                        packetNumber++;
                        if (debug)
                            System.out.println(tag + "finished packet, packet processed = " + packetNumber);
                    }
                    else{
                        /*i'm working on a packet but it will finish after the time i'm jumping to, so i'll jump straight to the time requested*/
                        currentTime = time;
                    }
                }
            }
        }
    }
    public void emptyQueue(){
        packetNumber += queue.size();
        while(!queue.isEmpty()){
            /*if the departure has already happened, i'll start working on a new packet*/
            if (nextDeparture <= currentTime){
                double packet = queue.remove();
                nextDeparture = currentTime + (getServiceRate());
                if (debug)
                    System.out.println(tag + "server is processing nothing so i'll work on the next packet until " + nextDeparture);
                totalResponseTime += nextDeparture - packet;
            }
            else{
                /* finish processing the current packet */
                currentTime = nextDeparture;
            }
        }
    }

    public double getTotalResponseTime(){
        return totalResponseTime;
    }
    public int getPacketNumber(){
        return packetNumber;
    }

    public String getTag() {
        return tag;
    }

    public int getQueueSize() {
        return queue.size();
    }
}