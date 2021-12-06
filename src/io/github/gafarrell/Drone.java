package io.github.gafarrell;

import io.github.gafarrell.events.Event;

import java.util.ArrayList;

public class Drone {

    public static ArrayList<Drone> allDrones = new ArrayList<>();

    public static final double MAX_DATA = 5;
    private final ArrayList<Event> storedEvents = new ArrayList<>();

    private Vendor currentVendor;
    private Connection<Vendor> route;

    private double storedData;
    private double batteryLife = 2.0;

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

    public void drive(){
        if ((this.route.getDistance()*0.01 + this.route.getDest().getRouteToHub().getDistance()) > this.batteryLife) {
            this.route = this.currentVendor.getRouteToHub();
            this.batteryLife -= this.route.getDistance()*0.01;
            this.storedData += this.route.getDistance()*0.02;
            setCurrentVendor(this.route.getDest());
            return;
        }

        this.batteryLife -= this.route.getDistance()*0.01;
        this.storedData += this.route.getDistance()*0.02;
        setCurrentVendor(this.route.getDest());
    }

    public void endDay(){
        this.route = this.currentVendor.getRouteToHub();
        drive();
        this.route = null;
        unloadData();
    }

    public void registerEvent(Event e){
        if (this.storedData > Drone.MAX_DATA){
            this.route = this.currentVendor.getRouteToHub();
            return;
        }

        this.storedEvents.add(e);
        this.storedData += e.getSize();

        if (e.getPriority() == Event.Priority.HIGH){
            this.route = this.currentVendor.getRouteToHub();
        }
    }

    public void giveRandomRoute(){
        this.route = Vendor.getRandomVendorRouteFrom(this.currentVendor);
        if (((this.route.getDistance()*0.01) + (this.route.getDest().getRouteToHub().getDistance()*0.01)) > this.batteryLife){
            this.route = null;
        }
    }

    public void unloadData(){
        Main.throughput += storedData;
        this.batteryLife -= this.storedData*0.02;
        this.storedData = 0;
    }

    public void transferEvents(Drone d){
        for (Event e : d.storedEvents) {
            if (e.getPriority() == Event.Priority.HIGH && e.getSize() + this.storedData < Drone.MAX_DATA) {
                d.storedEvents.remove(e);
                this.storedData += e.getSize();
                this.storedEvents.add(e);
                d.giveRandomRoute();
            }
        }
    }

    public Vendor getCurrentVendor(){return currentVendor;}
    public Connection<Vendor> getRoute(){return route;}
    public void setCurrentVendor(Vendor currentVendor){
        this.currentVendor.removeRobot(this);
        this.currentVendor = currentVendor;
        this.currentVendor.addRobot(this);
    }
    public void setRoute(Connection<Vendor> route){this.route = route;}
    public double getStoredData(){return storedData;}

    public static boolean allDronesInHub(){
        for (Drone d : allDrones){
            if (!d.getCurrentVendor().isHub()) return false;
        }
        return true;
    }
}
