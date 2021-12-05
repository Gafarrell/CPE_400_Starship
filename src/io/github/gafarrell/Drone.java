package io.github.gafarrell;

import io.github.gafarrell.events.Event;
import io.github.gafarrell.events.Packet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Drone {

    public static ArrayList<Drone> allDrones = new ArrayList<>();

    public static final double maxData = 5;
    private ArrayList<Packet> storedData;

    private Vendor currentVendor;
    private Connection<Vendor> destination;
    private Queue<Event> eventQueue = new LinkedList<>();

    private double distanceToDestinaion;
    private double batteryLife = 2.0;

    private boolean isDriving = false;

    public Drone(){
        currentVendor = Main.Hub;
        currentVendor.addRobot(this);
        allDrones.add(this);
    }

    public Drone(Vendor startingLocation){
        this.currentVendor = startingLocation;
        this.currentVendor.addRobot(this);
        allDrones.add(this);
    }

    public static boolean allDronesInHub(){
        for (Drone d : allDrones){
            if (!d.getCurrentVendor().isHub()) return false;
        }
        return true;
    }

    public void driveTo(Connection<Vendor> destination){
        if (this.batteryLife < (destination.getDistance()*0.01) + (destination.getDest().getRouteToHub().getDistance()*0.01)){
            this.destination = this.currentVendor.getRouteToHub();
        }
        this.destination = destination;
        this.distanceToDestinaion = destination.getDistance();
    }

    // Subtract battery life and move bot closer to destination.
    public void driveDistance(double distanceTravelled){
        this.batteryLife -= distanceTravelled*0.01;
        this.distanceToDestinaion -= distanceTravelled;
    }

    public void addEvent(Event e){
        this.eventQueue.add(e);
    }

    // Ends the drive, making all distance/destination null or 0.
    public void endDrive(){
        if (destination == null) return;

        Event e = eventQueue.peek();
        while (e.getType() != Event.Action.DRIVE_TO){
            storedData.add(new Packet());
        }
        Packet endOfDriveData = new Packet(destination.getDistance()*0.01, Packet.Priority.LOW);
        driveDistance(this.distanceToDestinaion);
        this.distanceToDestinaion = 0;
        this.destination = null;
        this.isDriving = false;
    }


    public Vendor getCurrentVendor(){return currentVendor;}
    public Connection<Vendor> getDestination(){return destination;}
    public boolean isDriving(){return isDriving;}
    public double getDistanceToDestinaion(){return distanceToDestinaion;}
}
