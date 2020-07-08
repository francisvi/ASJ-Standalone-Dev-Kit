package edu.ahs.robotics.java;

public class LineSegment {
    private Point point1;
    private Point point2;
    private double deltaX;
    private double deltaY;



    public LineSegment(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
        deltaX=point2.getX()-point1.getX();
        deltaY=point2.getY()-point1.getY();
    }



    public Point[] subDivide(int subSegments){

        //get the amount x needs to change to take you from point 1 to the first
        //x-coordinante a point point on the subsegement of the line.
        double moveX=deltaX/subSegments;
        double moveY=deltaY/subSegments;

        //create and array of Point objects that will contain points of the ends of the
        //subsegments.
        Point subSegCoordinates[]=new Point[subSegments-1];
        //run a for loop that populates our array with points.  Note that each element
        //in the array is itself a Point that needs to be created.  I use the
        //getX method of Class Point in order to get the x coordinate of the end
        //points of the line and then the moveX value to add to the amount x needs
        //to change by to take us to the endpoint of a segment.  I need to multiply the
        //move value of i+1 so that if I am on the second, or third, time through the loop,
        //the amount I am moving from the x value of the endpoint increases.
        for (int i=0; i<subSegments-1; i++)
            subSegCoordinates[i]=new Point(point1.getX()+moveX*(i+1),point1.getY()+moveY*(i+1));
        //my array of endpoints of the line segment pieces is returned.
        return subSegCoordinates;


    }


    //this method calculates the x and y coordinates of a targetPoint the line segment object.
    //if the slope is infinite, AKA deltaX=0, then it simply moves up the vertical line by the
    //total distance.
    public Point interpolate (double targetDistance){

        if (deltaX != 0){
            double targetPointX=targetDistance*Math.cos(Math.atan(deltaY/deltaX))+point1.getX();
            double targetPointY=targetDistance*Math.sin(Math.atan(deltaY/deltaX))+point1.getY();
            Point interpolatedPoint = new Point (targetPointX,targetPointY);
            return interpolatedPoint;
        }
        else {
            double targetPointX=point1.getX();
            double targetPointY=targetDistance+point1.getY();
            Point interpolatedPoint = new Point (targetPointX,targetPointY);
            return interpolatedPoint;
        }
    }
}
