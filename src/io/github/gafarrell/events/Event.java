package io.github.gafarrell.events;

import java.util.Random;

public class Event {

    public enum Priority{
        HIGH,
        MEDIUM,
        LOW;
    }

    private Priority priority;
    private final double size;
    private final Random r = new Random();

    // Creates event with random priority and random size
    public Event(){
        setRandomPriority();
        this.size = r.nextDouble()*0.5;
    }

    // Creates event with specific priority and random size.
    public Event(Priority p){
        this.size = r.nextDouble()*0.5;
        this.priority = p;
    }

    // Creates event with specific size and random priority.
    public Event(double size){
        setRandomPriority();
        this.size = size;
    }

    // Creates event with specific size and priority.
    public Event(Priority p, double size){
        this.priority = p;
        this.size = size;
    }

    private void setRandomPriority(){
        switch (r.nextInt(3)){
            case 1 -> this.priority = Priority.HIGH;
            case 2 -> this.priority = Priority.MEDIUM;
            default -> this.priority = Priority.LOW;
        }
    }

    public double getSize() {
        return size;
    }

    public Priority getPriority() {
        return priority;
    }
}
