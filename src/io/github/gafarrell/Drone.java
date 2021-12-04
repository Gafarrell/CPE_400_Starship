package io.github.gafarrell;

import io.github.gafarrell.events.Packet;

import java.util.ArrayList;

public class Drone {

    private ArrayList<Packet> storedData;

    private Vendor currentVendor;
    private Connection<Vendor> destination;

    private double distanceToDestinaion;
    private double batteryLife = 2.0;

    private boolean isDriving = false;

    public Drone(){
        currentVendor = Main.Hub;
        currentVendor.addRobot(this);
        Main.allDrones.add(this);
    }

    public Drone(Vendor startingLocation){
        this.currentVendor = startingLocation;
        Main.allDrones.add(this);
    }

    public void driveTo(Connection<Vendor> destination){
        this.destination = destination;
        this.distanceToDestinaion = destination.getDistance();
    }

    public Vendor getCurrentVendor(){return currentVendor;}
    public Connection<Vendor> getDestination(){return destination;}
    public boolean isDriving(){return isDriving;}
    public double getDistanceToDestinaion(){return distanceToDestinaion;}

    // Subtract battery life and move bot closer to destination.
    public void driveDistance(double distanceTravelled){
        this.batteryLife -= distanceTravelled*0.01;
        this.distanceToDestinaion -= distanceTravelled;
    }

    // Ends the drive, making all distance/destination null or 0.
    public void endDrive(){
        if (destination == null) return;
        Packet endOfDriveData = new Packet(destination.getDistance()*0.01, Packet.Priority.LOW);
        driveDistance(this.distanceToDestinaion);
        this.distanceToDestinaion = 0;
        this.destination = null;
        this.isDriving = false;

    }
}
