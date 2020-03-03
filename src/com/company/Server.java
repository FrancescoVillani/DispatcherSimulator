package com.company;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;


public class Server {

    /*packet queue, a packet will be defined by its arrival time in the system and it's arrival time relative only to
    * a queue, this is to divide the overall logic*/
    private Queue<Pair<Double,Double>> queue = new LinkedList<>();
    /*defined as packets per second*/
    private Function<Double, Double> serviceRate;

    /*the constructor takes a lambda with an Uniform(0,1) as input and returns a value from a known distribution, if the
    * service rate needs to be constant the function should always return the same value.*/
    public Server(Function<Double, Double> serviceRate) {
        this.serviceRate = serviceRate;
    }

    /*giving in input a value from an Uniform(0,1) to get a value from a known distribution*/
    public double getServiceRate() {
        return serviceRate.apply(Math.random());
    }

    /* stabs to use the queue from an outer scope */
    public void addPacket (Pair<Double,Double> packet){
        queue.add(packet);
    }
    public boolean isEmpty(){
        return queue.isEmpty();
    }
    public Pair<Double,Double> peekHead(){
        return queue.peek();
    }
    public void removeHead(){
        queue.remove();
    }
}
