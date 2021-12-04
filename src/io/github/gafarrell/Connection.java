package io.github.gafarrell;

public class Connection<T> {
    private T src, dest;
    private double distance;

    public Connection(T src, T dest, double distance){
        this.src = src;
        this.dest = dest;
        this.distance = distance;
    }

    public T getSrc(){return src;}
    public T getDest(){return dest;}
    public double getDistance(){return distance;}
}
