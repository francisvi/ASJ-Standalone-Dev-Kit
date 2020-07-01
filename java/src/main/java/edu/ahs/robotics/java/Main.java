package edu.ahs.robotics.java;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World! My name is Francis");
        System.out.println(2+7);
        System.out.println(2>7);
        double num =21/10;
        System.out.println(num);
        double num1 =21.0/10.0;
        System.out.println(num1);

        Point a=new Point(7,2);
        System.out.println(a.getX());
        System.out.println(a.getY());
        System.out.println(a.getX()+a.getY());
        System.out.println(a);
        System.out.println(a.distanceFromOrigin());
        Point b=new Point (-12,13);
        System.out.println(b.distanceFromOrigin());

    }
}