package com.company;

import java.util.function.Function;

public class PacketGenerator {

    private Function<Double, Double> rate;

    public PacketGenerator(Function<Double, Double> rate) {
        this.rate = rate;
    }

    /*giving in input a value from an Uniform(0,1) to get a value from a known distribution*/
    public double getServiceRate() {
        return rate.apply(Math.random());
    }
}
