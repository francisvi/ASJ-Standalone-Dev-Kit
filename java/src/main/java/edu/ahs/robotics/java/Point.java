package edu.ahs.robotics.java;

public class Point {
    //our variables to store the x and y coordinants
    private double x;
    private double y;

    //Constructor
    public Point(double x, double y){
        this.x=x;
        this.y=y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    public double distanceFromOrigin(){
        double dx=0-x;
        double dy=0-y;
        double d=Math.pow(dx,2)+Math.pow(dy,2);
        double distance=Math.sqrt(d);
        return distance;

    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
