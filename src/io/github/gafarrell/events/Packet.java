package io.github.gafarrell.events;

import java.util.Random;

public class Packet {
    public enum Priority{
        HIGH,
        MEDIUM,
        LOW
    }

    private Priority priority;
    private Random rando = new Random();
    private double dataSize;

    private Priority getRandomPriority(){
        int prio = rando.nextInt(2)+1;
        switch (prio){
            case 1: return Priority.LOW;
            case 2: return Priority.MEDIUM;
            case 3: return Priority.HIGH;
        }
        return Priority.LOW;
    }

    public Packet(){
        dataSize = rando.nextDouble()*2;
        priority = getRandomPriority();
    }

    public Packet(double dataSize, Priority priority){
        this.dataSize = dataSize;
        this.priority = priority;
    }

    public double getSize(){return dataSize;}
    public Priority getPriority(){return priority;}
}
