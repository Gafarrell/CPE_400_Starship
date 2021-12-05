package io.github.gafarrell;

import java.util.ArrayList;
import java.util.Random;

public class Vendor {

    public static ArrayList<Vendor> allVendors = new ArrayList<>();

    private String name;
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
        allVendors.add(this);
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

    public Connection<Vendor> getRouteToHub(){
        for (Connection<Vendor> v : routes)
            if (v.getDest() == Main.Hub) return v;
        return null;
    }
    public boolean isSeller(){return isSeller;}
    public boolean isHub(){return isHub;}
    public void addVendorRoute(Connection<Vendor> route){
        this.routes.add(route);
    }



    public static Connection<Vendor> getRandomVendorRouteFrom(Vendor v){
        Random r = new Random();
        Connection<Vendor> randomRoute;
        do {
            randomRoute = v.routes.get(r.nextInt(v.routes.size()));
        } while (randomRoute.getDest() == v);
        return randomRoute;
    }
}
