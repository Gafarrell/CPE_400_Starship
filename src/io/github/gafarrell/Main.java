package io.github.gafarrell;

import io.github.gafarrell.controller.DayClock;
import io.github.gafarrell.events.Packet;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// Also known as Command and Control Center (or in Starship terms, the HUB)
public class Main {
    public static ArrayList<Drone> allDrones = new ArrayList<>();
    public static ArrayList<Vendor> allVendors = new ArrayList<>();
    public static ArrayList<Packet> eventQueue = new ArrayList<>();
    public static final Vendor Hub = new Vendor("Hub", true, false);

    public static final double speed = 3.0;
    public static double lastTime = System.currentTimeMillis();

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

        fleetSize = fleetSize.toUpperCase();
        pop = pop.toUpperCase();

        switch (fleetSize) {
            case "SMALL" -> {
                for (int i = 0; i < 20; i++) allDrones.add(new Drone());
            }
            case "MEDIUM" -> {
                for (int i = 0; i < 50; i++) allDrones.add(new Drone());
            }
            case "LARGE" -> {
                for (int i = 0; i < 80; i++) allDrones.add(new Drone());
            }
        }

        switch (pop) {
            case "LOW" -> {
                for (int i = 0; i < 100; i++) eventQueue.add(new Packet());
            }
            case "MEDIUM" -> {
                for (int i = 0; i < 250; i++) eventQueue.add(new Packet());
            }
            case "HIGH" -> {
                for (int i = 0; i < 500; i++) eventQueue.add(new Packet());
            }
        }

        makeVendors();
        distributeEvents();
        startDay();
    }

    public static void startDay(){
        while (!allDronesInHub()){
            for (Drone d : allDrones) {
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

    public static boolean allDronesInHub(){
        for (Drone d : allDrones){
            if (!d.getCurrentVendor().isHub()) return false;
        }
        return true;
    }

    public static void makeVendors(){
        allVendors.add(Hub);

        // Restaurants for restaurant specific effects
        Vendor habit = new Vendor("Habit Burger", false, true);
        Vendor bowlLife = new Vendor("Bowl Life", false, true);
        Vendor create = new Vendor("Create", false, true);

        // Dorms for dorm specific effects
        Vendor canyon = new Vendor("Canyon Flats", false, false);
        Vendor canada = new Vendor("Vendor", false, false);

        Random rando = new Random();

        for (Vendor v : allVendors){
            for (Vendor d : allVendors){
                if (v != d) v.addRoute(new Connection<Vendor>(v, d, rando.nextDouble()));
            }
        }
    }

    public static void distributeEvents(){
        
    }
}
