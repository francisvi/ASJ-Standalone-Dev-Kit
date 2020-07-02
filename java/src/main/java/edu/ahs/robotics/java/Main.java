package edu.ahs.robotics.java;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World! My name is Francis");
        System.out.println(2+7);
        System.out.println(2>7);
        double num =21/10;
        //System.out.println(num);
        double num1 =21.0/10.0;
        //System.out.println(num1);

        Point a=new Point(-20,-7);
        System.out.println(a.getX());
        System.out.println(a.getY());

        //System.out.println(a.getX()+a.getY());
        //System.out.println(a);
        //System.out.println(a.distanceFromOrigin());
        Point b=new Point (10,5);
        //System.out.println(b.distanceFromOrigin());
        //System.out.println(a.getQuadrant());

        //create a new lineSegment by passing the endpoints to it, a and b, created above.
        LineSegment l1=new LineSegment(a,b);

        //create an array to hold the output array created when calling the subDivide method for
        //contained in object of class LineSegment.  Then print each element of the array.  This
        //calls the overridden toString in Point.
        Point results []= l1.subDivide(9);
        for (int i=0; i<results.length; i++){
            System.out.println(results[i]);
        }


    }
}