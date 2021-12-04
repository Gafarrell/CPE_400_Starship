package io.github.gafarrell;

import java.util.ArrayList;
import java.util.List;

public class Vendor {
    String name;
    private ArrayList<Connection<Vendor>> routes = new ArrayList<>();
    private ArrayList<Drone> localDrones = new ArrayList<>();

    private final boolean isHub;
    private final boolean isSeller;

    public Vendor(String name){
        routes = new ArrayList<>();
        localDrones = new ArrayList<>();
        this.isHub = false;
        this.isSeller = false;
    }

    public Vendor(String name, boolean isHub, boolean isSeller){
        Main.allVendors.add(this);
        this.name = name;
        this.isHub = isHub;
        this.isSeller = isSeller;
    }

    public void addRobot(Drone drone){
        this.localDrones.add(drone);
    }

    public void addRoute(Connection<Vendor> route){
        this.routes.add(route);
    }

    public boolean isSeller(){return isSeller;}
    public boolean isHub(){return isHub;}
    public void addVendorRoute(Connection<Vendor> route){

    }
}
