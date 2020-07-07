package edu.ahs.robotics.java;

public class Main {

    //private Point[] rawPoints;

    public static void main(String[] args) {
        /*System.out.println("Hello World! My name is Francis");
        System.out.println(2+7);
        System.out.println(2>7);
        double num =21/10;
        //System.out.println(num);
        double num1 =21.0/10.0;
        //System.out.println(num1);

//Problem Set 2.
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
        }*/
//Problem Set 3.
        //creates the rawPoints array
        Point[] rawPoints = {
                new Point (0,0),
                new Point (3,4),
                new Point (3,4),
                new Point (5,5),
                new Point (0,0)};
        //calls the constructor of the Path array, passing the rawPoints array.
        Path myPath= new Path (rawPoints);
        //prints the rawPoints array
        System.out.println("raw points array)");
        for (int i=0; i<rawPoints.length; i++){
            System.out.println(rawPoints[i]);
        }

        //Prints the Point objects in the ArrayList of the Path object.
        //this call goes from a Path orbject (myPath) using a method to call the ArrayList (getWayPoints)
        //then calling the i'th element of that ArrayList which is itself a WayPoint, the .point, gets
        //the point attribute within that WayPoint. Points are printable because we created an override of
        //the toString in Point.
        System.out.println("Point object of each element in ArrayList of WayPoints");
        for (int i=0; i<myPath.getWayPoints().size(); i++){
            System.out.println(myPath.getWayPoints().get(i).point);
        }

        System.out.println("the total distance of my path = " + myPath.totalDistance());

        Point pointA = new Point (3,2);
        Point pointB = new Point (15,8);
        LineSegment l1 = new LineSegment(pointA,pointB);
        System.out.println (l1.interpolate(7));


    }
}