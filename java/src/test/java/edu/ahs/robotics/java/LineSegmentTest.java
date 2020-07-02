package edu.ahs.robotics.java;

import org.junit.Test;

import static org.junit.Assert.*;

public class LineSegmentTest {

    @Test
    public void sudivide() {
        //creating an array of expected test results
        Point[] pointsExpected = new Point[2];
        pointsExpected[0]=new Point (1,1);
        pointsExpected[1]=new Point (2,2);

        //creating the end points of a line to be subdivided.
        Point point1=new Point(0,0);
        Point point2 = new Point (3,3);
        //creating a new object of class LineSegment, with end points created above.
        LineSegment lineSegment = new LineSegment(point1, point2);

        //creating an array of Points called pointsActual and assinging to it the
        //values that result from running the subDivide method on the object called
        //linesegment, created above.
        Point[] pointsActual=lineSegment.subDivide(3);

        //looping the the arrays, point by point, and seeing if, at each index, the point in
        //pointsActual is identical to the pointsExpected.
        for(int i = 0; i<pointsActual.length; i++){
            assertEquals(pointsExpected[i].getX(),pointsActual[i].getX(),0.000001);
            assertEquals(pointsExpected[i].getY(),pointsActual[i].getY(),0.000001);


        }

    }
}