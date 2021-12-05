package io.github.gafarrell.events;

import java.util.Random;

public class Packet {
    public enum Priority{
        HIGH,
        MEDIUM,
        LOW
    }

    private final Priority priority;
    private final Random rando = new Random();
    private final double dataSize;

    private Priority getRandomPriority(){
        return switch (rando.nextInt(3)) {
            case 1 -> Priority.MEDIUM;
            case 2 -> Priority.HIGH;
            default -> Priority.LOW;
        };
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
