package edu.ahs.robotics.java;
//this needs to be imported if you want to use an ArrayList.
import java.util.ArrayList;

//A new class, called path
public class Path {

    //this is an attribute of each instantiation of Path.  It is private, can't be accesses outside the
    //object.  It is of type ArrayList, with Object Type WayPoint as elements.  The name of the
    //Arraylist is wayPoints.  Then, it is created.  I am not sure why the Object Type, Waypoint is not
    //inside the <> in the second case.  In exampels on line, it is included.
    private ArrayList<WayPoint> wayPoints=new ArrayList<>();

    //constructor
    /**
     * @param rawPoints Array of X,Y points.  Duplicate points are discarded
     *                  A path must have at least 2 non-identical points
     */

    public Path (Point[] rawPoints){
        //the x displacement between a waypoint and the previous waypoint.  Similar for Y.
        double deltaXFromPrevious=0;
        double deltaYFromPrevious=0;
        double distanceFromPrevious=0;

        //loops through the array Points passed to the Path constructor.  Each Point in the array,
        //is passed to Waypoint constructor.  The zeroth element gets special treatment because
        //there is no previous element from which to take a difference.
        for (int i=0; i<rawPoints.length; i++){
            //create a variable, local in scope to this for loop, of type WayPoint called myWayPoint.
            WayPoint myWayPoint;

            if (i==0){
                myWayPoint =new WayPoint(rawPoints[i], 0, 0, 0);
                wayPoints.add(myWayPoint);
            }
            else {
                //the if conditional checks to see that two rawPoints are not identical.  If they are identical then
                //no object of type WayPoint is created from them.
                if(rawPoints[i].getX()!=rawPoints[i - 1].getX() || rawPoints[i].getY()!=rawPoints[i - 1].getY()) {
                    deltaXFromPrevious = rawPoints[i].getX() - rawPoints[i - 1].getX();
                    deltaYFromPrevious = rawPoints[i].getY() - rawPoints[i - 1].getY();
                    distanceFromPrevious = Math.sqrt(deltaXFromPrevious * deltaXFromPrevious + deltaYFromPrevious * deltaYFromPrevious);
                    myWayPoint = new WayPoint(rawPoints[i], deltaXFromPrevious, deltaYFromPrevious, distanceFromPrevious);
                    wayPoints.add(myWayPoint);
                }
            }
        }
    }
    //this is the getter for wayPoints.
    //public, it's an arraylist of type Waypoint, and its a method called getWayPoints.  It returns the
    //the ArrayList "wayPoints".
    public ArrayList<WayPoint> getWayPoints(){
        return wayPoints;
    }
    //a method
    /**
     * @return total distance of the path
     */
    //unfinished
    public double totalDistance(){
        double d=0;
        for(int i=0; i<wayPoints.size(); i++){
            //I am adding old d to the i'th distance from previous of the i'th index of the wayPoints ArrayList.
            // or, I am augmenting d with the i'th WayPoint of the ArrayList's distanceFromPrevious.
            //wayPoints is an ArrayList, .get(i) tells it to retrieve that WayPoint, .distanceFromPrevious is
            //an attribute of each WayPoint.
            d=d + wayPoints.get(i).getDistanceFromPrevious();
            //I want to add the distanceFromPrevious value for each successive element in the the wayPoints ArrayList.
        }
        return d;
    }
    //You’ll see that this method returns something called a Path.WayPoint.
    // That’s an inner class of Path, which means a class inside a class.
    /**
     * @return a point at the supplied distance along the path from the supplied current position
     * Note that the point will usually be interpolated between the points that originally defined the Path
     */
    //looks unfinished.
    //public Path.WayPoint targetPoint(Point current, double distance) {
        //this is a call to the constructor of WayPoint.  What is odd is that this call does not
        //seem to be passing the correct parameters to the constructor.  The call starts well by
        //calling the constructor of Point, creating a Point, and then passing it to the WayPoint constructor.
        //But, then the three doubles that should be passed are missing.
        //return new WayPoint(new Point(0,0) // missing deltaXFromPrevious, double deltaYFromPrevious, double distanceFromPrevious);
    //}
    //An inner class, WayPoint.  This part of the code is explained in BlackForest PS3.
    public static class WayPoint {
        public Point point;
        private double deltaXFromPrevious;
        private double deltaYFromPrevious;
        private double distanceFromPrevious;

        //this must be the constructor for the inner class, WayPoint.  The constructor has
        //four parameters, each one corresponding to the fields above.

        private WayPoint(Point point, double deltaXFromPrevious, double deltaYFromPrevious, double distanceFromPrevious) {
            this.point = point;
            this.deltaXFromPrevious = deltaXFromPrevious;
            this.deltaYFromPrevious = deltaYFromPrevious;
            this.distanceFromPrevious = distanceFromPrevious;
        }

        /**
         * Calculates the projection of the vector Vcurrent leading from the supplied current
         * point to this WayPoint onto the vector Vpath leading from the previous point on the path
         * to this WayPoint.  If the return value is positive, it means that the WayPoint is
         * farther along the path from the current point.  If the return value is negative, it means
         * that the WayPoint is before the current point.  The magnitude of the value tells the
         * distance along the path.  The value is computed as the dot product between Vcurrent and
         * Vpath, normalized by the length of vPath.
         * SECOND Explanation:
         * This method lets you supply a point representing the current position of the robot and
         * compare it to a WayPoint.  It returns the distance along the path to the WayPoint from an
         * imaginary closest point on the path to the robot.  If that distance is positive, it means
         * the WayPoint is ahead of the robot.  If it’s negative, it means the WayPoint is behind
         * the robot.  You’ll use this to find the next point just ahead of the robot’s current
         * position, and then you’ll be able to calculate where the target point should be relative
         * to that point.  You’ll also eventually be able to use it to detect if the robot has
         * reached the end of the path (quiz: how?)  If you’re curious, this method works by
         * finding the projection of the vector between the robot and the WayPoint on the vector
         * between the preceding WayPoint and the passed in WayPoint.  Diagram it out if you’d like
         * to really understand the geometry.
         * @param current The source point to compare to the WayPoint
         */
        private double componentAlongPath(Point current) {
            double deltaXFromCurrent = point.getX() - current.getX();
            double deltaYFromCurrent = point.getY() - current.getY();

            double dp = deltaXFromCurrent * deltaXFromPrevious + deltaYFromCurrent * deltaYFromPrevious;
            return dp / distanceFromPrevious;

        }

        public double getDistanceFromPrevious() {
            return distanceFromPrevious;
        }
    }



}
