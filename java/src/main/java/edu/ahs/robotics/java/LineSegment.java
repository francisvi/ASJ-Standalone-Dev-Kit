package edu.ahs.robotics.java;

public class LineSegment {
    private Point point1;
    private Point point2;

    public LineSegment(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Point[] subDivide(int subSegments){
        //get the change in x and y between the two points.
        double deltaX=point2.getX()-point1.getX();
        double deltaY=point2.getY()-point1.getY();
        //get the amount x needs to change to take you from point 1 to the first
        //x-coordinante a point point on the subsegement of the line.
        double moveX=deltaX/subSegments;
        double moveY=deltaY/subSegments;

        //create and array of Point objects that will contain points of the ends of the
        //subsegments.
        Point subSegCoordinates[]=new Point[subSegments-1];
        //run a for loop that populates our array with points.  Note that each element
        //in the array is itself a Point that needs to be created.  I use the
        //getX method of Class Point in order to get the x coordinatne of the end
        //points of the line and then the moveX value to add to the amount x needs
        //to change by to take us to the endpoint of a segment.  I need to multiply the
        //move value of i+1 so that if I am on the second, or third, time through the loop,
        //the amount I am moving from the x value of the endpoint increases.
        for (int i=0; i<subSegments-1; i++)
            subSegCoordinates[i]=new Point(point1.getX()+moveX*(i+1),point1.getY()+moveY*(i+1));
        //my array of endpoints of the line segment pieces is returned.
        return subSegCoordinates;

    }
}
