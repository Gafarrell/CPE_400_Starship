package io.github.gafarrell.controller;

import io.github.gafarrell.Drone;
import io.github.gafarrell.Main;

import java.util.ArrayList;

public class DayClock implements Runnable {
    private ArrayList<Drone> drones;
    private double speed = 3.0;
    private long lastTime = System.currentTimeMillis();

    public DayClock(ArrayList<Drone> drones){
        this.drones = drones;
    }
    @Override
    public void run() {
        while (!Main.allDronesInHub()) {

        }
    }
}
