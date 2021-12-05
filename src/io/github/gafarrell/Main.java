package io.github.gafarrell;

import io.github.gafarrell.events.Event;

import java.util.*;

// Also known as Command and Control Center (or in Starship terms, the HUB)
public class Main {
    public static Queue<Event> eventQueue = new LinkedList<>();
    public static final Vendor Hub = new Vendor("Hub", true, false);

    public static final double speed = 3.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask for campus popularity. this will gauge how many events will happen when the simulation runs.
        System.out.println("Please select a popularity level. (HIGH, MEDIUM, LOW)");
        String pop = scanner.nextLine();
        while (!pop.equalsIgnoreCase("hiGH") && !pop.equalsIgnoreCase("medium") && !pop.equalsIgnoreCase("low")){
            System.out.println("Please enter either HIGH, MEDIUM, or LOW for the popularity level.");
            pop = scanner.nextLine();
        }

        // Ask for fleet size to handle the events and popularity. Small fleet size with popular campus will strain the system the most.
        System.out.println("Please enter the size of the fleet. (SMALL, MEDIUM, LARGE)");
        String fleetSize = scanner.nextLine();
        while (!fleetSize.equalsIgnoreCase("SMALL") && !fleetSize.equalsIgnoreCase("MEDIUM") && !fleetSize.equalsIgnoreCase("HIGH")){
            System.out.println("Please enter either SMALL, MEDIUM, or LARGE for the fleet size.");
            fleetSize = scanner.nextLine();
        }

        // Set input to upper case for switch statements
        fleetSize = fleetSize.toUpperCase();
        pop = pop.toUpperCase();

        switch (fleetSize) {
            case "SMALL" -> {
                for (int i = 0; i < 20; i++) Drone.allDrones.add(new Drone(Hub));
            }
            case "MEDIUM" -> {
                for (int i = 0; i < 50; i++) Drone.allDrones.add(new Drone(Hub));
            }
            case "LARGE" -> {
                for (int i = 0; i < 80; i++) Drone.allDrones.add(new Drone(Hub));
            }
        }

        switch (pop) {
            case "LOW" -> {
                for (int i = 0; i < 100; i++) eventQueue.add(new Event());
            }
            case "MEDIUM" -> {
                for (int i = 0; i < 250; i++) eventQueue.add(new Event());
            }
            case "HIGH" -> {
                for (int i = 0; i < 500; i++) eventQueue.add(new Event());
            }
        }

        makeVendors();
        distributeEvents();
        startDay();
    }

    public static void startDay(){
        double lastTime = System.currentTimeMillis();
        while (!Drone.allDronesInHub()){
            for (Drone d : Drone.allDrones) {
                if (d.isDriving()) {
                    double distance = speed * (System.currentTimeMillis() - lastTime);
                    if (distance > d.getDistanceToDestinaion())
                        d.endDrive();
                    else
                        d.driveDistance(distance);
                }
            }
            lastTime = System.currentTimeMillis();
        }
    }

    public static void makeVendors(){
        Vendor.allVendors.add(Hub);

        // Restaurants for restaurant specific effects
        Vendor habit = new Vendor("Habit Burger", false, true);
        Vendor bowlLife = new Vendor("Bowl Life", false, true);
        Vendor create = new Vendor("Create", false, true);

        // Dorms for dorm specific effects
        Vendor canyon = new Vendor("Canyon Flats", false, false);
        Vendor canada = new Vendor("Vendor", false, false);

        Random rando = new Random();

        for (Vendor v : Vendor.allVendors){
            for (Vendor d : Vendor.allVendors){
                if (v != d) v.addRoute(new Connection<Vendor>(v, d, rando.nextDouble()*1.5));
            }
        }
    }

    public static void distributeEvents(){
        Drone.allDrones.forEach(d -> {
            d.addEvent(new Event(d, Vendor.getRandomVendorRouteFrom(Hub)));
        });

        for (int i = 0; !eventQueue.isEmpty(); i=(i+1)%Drone.allDrones.size()){
            Drone.allDrones.get(i).addEvent(eventQueue.remove());
        }
        Drone.allDrones.forEach(d -> {
            d.addEvent(new Event(d, d.getCurrentVendor().getRouteToHub()));
        });
    }
}
