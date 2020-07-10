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

        //loops through the array rawPoints passed to the Path constructor.  Each Point in the array,
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
        //current is the current location of the robot.  distance is the look ahead distance.
        //counter is an incrementer.  The for loop below runs until the value of component
        //along path for the current point is a positive number.  Any points behind the
        //robot will have negative componentAlongPath values.
    public Path.WayPoint targetPoint(Point current, double targetDistance) {

        // counter needs to function in such a way that the robot can follow paths that loop back.  Make the scope glogal.
        int counter = 0;
        for (int i = 0; i < wayPoints.size(); i++) {
            if (wayPoints.get(i).componentAlongPath(current) > 0) {
                counter = i;
                break;
            }
        }
        //creating targetWayPoint for case where: the line is not vertical, the robot is passed WayPoint 0,
        // and where the distance along path is less than the targetdistance.
        // This case works for horizontal lines too.
        if ((counter > 0) && (wayPoints.get(counter).getDeltaXFromPrevious() != 0) &&
                (targetDistance < wayPoints.get(counter).componentAlongPath(current))) {
            System.out.println("td< component along path, not vertical");

            //The x and y coordinates of the two endpoints of the line.
            double wP0X=wayPoints.get(counter-1).getPoint().getX();
            double wP0Y=wayPoints.get(counter-1).getPoint().getY();
            double wP1X=wayPoints.get(counter).getPoint().getX();
            double wP1Y=wayPoints.get(counter).getPoint().getY();

            System.out.println("current Point =" + current);
            System.out.println("counter = " + counter);
            System.out.println("wP0X = "+ wP0X);
            System.out.println("wP0Y = "+ wP0Y);
            System.out.println("wP1X = "+ wP1X);
            System.out.println("wP1Y = "+ wP1Y);

            //delta x and y from previous point.
            double dxfp=wayPoints.get(counter).getDeltaXFromPrevious();
            double dyfp=wayPoints.get(counter).getDeltaYFromPrevious();

            System.out.println("dxfp, delta x from previous point = "+ dxfp);
            System.out.println("dyfp = "+ dyfp);


            //distance from previous point.
            double dsfp=wayPoints.get(counter).getDistanceFromPrevious();
            System.out.println("dsfp distance from previous point = "+ dsfp);

            //calculating the distance from starting point of line to where the projection lands on the path.
            double dtpop=dsfp - wayPoints.get(counter).componentAlongPath(current);
            System.out.println("wayPoints.get(counter).componentAlongPath(current) = " + wayPoints.get(counter).componentAlongPath(current));
            System.out.println("dtpop = " + dtpop);

            //calculating the x and y coordinates of the projection on path (pop).
            double popX=dtpop*Math.cos(Math.atan2(dyfp,dxfp))+ wP0X;
            double popY=dtpop*Math.sin(Math.atan2(dyfp,dxfp))+ wP0Y;
            System.out.println("popX = " + popX);
            System.out.println("popY = " + popY);

            //calculating the x and y coordinates of the targetPoint.
            double tPX=targetDistance*Math.cos(Math.atan2(dyfp,dxfp))+ popX;
            double tPY=targetDistance*Math.sin(Math.atan2(dyfp,dxfp))+ popY;
            System.out.println("tPX = " + tPX);
            System.out.println("tPY = " + tPY);


            //creating a the Point targetPoint from tPX and tPY.
            Point targetPoint = new Point(tPX,tPY);

            //creating deltaX and deltaY from current to target point.
            double dXTP=tPX-current.getX();
            double dYTP=tPY-current.getY();


            //creating distance from previous to targetPoint.
            double dTP=Math.sqrt(dXTP*dXTP+dYTP*dYTP);

            //creating targetWayPoint,
            WayPoint targetWayPoint = new WayPoint(targetPoint, dXTP, dYTP, dTP);

            return targetWayPoint;

        }
        //the case in which the line is vertical and the target distance is less
        //than the component along path.
        else if ((wayPoints.get(counter).getDeltaXFromPrevious() == 0) &&
                (targetDistance < wayPoints.get(counter).componentAlongPath(current))){
            System.out.println("vertical case, td<component along path");
            //The x and y coordinates of the two endpoints of the line.
            double wP0X=wayPoints.get(counter-1).getPoint().getX();
            double wP0Y=wayPoints.get(counter-1).getPoint().getY();
            double wP1X=wayPoints.get(counter).getPoint().getX();
            double wP1Y=wayPoints.get(counter).getPoint().getY();

            System.out.println("current Point =" + current);
            System.out.println("counter = " + counter);
            System.out.println("wP0X = "+ wP0X);
            System.out.println("wP0Y = "+ wP0Y);
            System.out.println("wP1X = "+ wP1X);
            System.out.println("wP1Y = "+ wP1Y);

            //delta x and y from previous point.
            double dxfp=wayPoints.get(counter).getDeltaXFromPrevious();
            double dyfp=wayPoints.get(counter).getDeltaYFromPrevious();

            System.out.println("dxfp, delta x from previous point = "+ dxfp);
            System.out.println("dyfp = "+ dyfp);


            //distance from previous point.
            double dsfp=wayPoints.get(counter).getDistanceFromPrevious();
            System.out.println("dsfp distance from previous point = "+ dsfp);

            //calculating the distance from starting point of line to where the projection lands on the path.
            double dtpop=dsfp - wayPoints.get(counter).componentAlongPath(current);
            System.out.println("wayPoints.get(counter).componentAlongPath(current) = " + wayPoints.get(counter).componentAlongPath(current));
            System.out.println("dtpop = " + dtpop);

            //calculating the x and y coordinates of the projection on path (pop).
            //in this special case it assigns the value of WayPoint 0.x to the popX;
            // it's on the same vertical line.
            //Assinges popY the value of the y value of current.  It must be at the same
            //height as current.
            double popX=wP0X;
            double popY= current.getY();
            System.out.println("popX = " + popX);
            System.out.println("popY = " + popY);

            //calculating the x and y coordinates of the targetPoint.
            // target x is is the same as path.x.
            //target y is just popy plus distance to target.
            double tPX=popX;
            double tPY=targetDistance + popY;
            System.out.println("tPX = " + tPX);
            System.out.println("tPY = " + tPY);


            //creating a the Point targetPoint from tPX and tPY.
            Point targetPoint = new Point(tPX,tPY);

            //creating deltaX and deltaY from current to target point.
            double dXTP=tPX-current.getX();
            double dYTP=tPY-current.getY();

            //creating distance from previous to targetPoint.
            double dTP=Math.sqrt(dXTP*dXTP+dYTP*dYTP);

            //creating targetWayPoint,
            WayPoint targetWayPoint = new WayPoint(targetPoint, dXTP, dYTP, dTP);

            return targetWayPoint;
        }

        //The case where the target distance is larger than the component along path,
        //and not a vertical line.  First we need to see which WayPoint we need to look at.
        //This is done by creating a partialPathDist.  We keep adding to this distance successive
        // distances from previous values until the
        //the partialPathDist is larger than the target distance.  At the same time we are
        //incrementing i so that we can keep track of which WayPoint is actually the one just beyond
        // the targetDistance.
        // The actual index of the WayPoint we end up on is counter + i.
        //After identifying which Waypoint we need to use, we can do very similar math as the earlier cases,
        //but we need to adjust which WayPoints to WayPoints.get(counter+i) and WayPoints.get((counter+i)-1).


        else if ((wayPoints.get(counter).getDeltaXFromPrevious() != 0) &&
                (targetDistance > wayPoints.get(counter).componentAlongPath(current))){
            System.out.println("td > component along path");
            double pathPartialDist=wayPoints.get(counter).componentAlongPath(current);
            int i=0;
            //adds up the path distance from pop to where target distance lands on the path.
            while (targetDistance > pathPartialDist && counter<(wayPoints.size())){
                i++;
                //if the target point travels past the final waypoint, we are in danger of calling an element in the
                //arrayList that is out of range.  This takes care of that by making the final Waypoint the TargetWayPoint
                //if this occurs.
                if (counter > wayPoints.size()-1 || (counter+i) > wayPoints.size()-1){

                    double dXTP=wayPoints.get(wayPoints.size()-1).getPoint().getX()-current.getX();
                    double dYTP=wayPoints.get(wayPoints.size()-1).getPoint().getY()-current.getY();
                    double dTP= Math.sqrt(dXTP*dXTP+dYTP*dYTP);
                    WayPoint targetWayPoint = new WayPoint(wayPoints.get(wayPoints.size()-1).point, dXTP , dYTP, dTP);
                    return targetWayPoint;

                }
                pathPartialDist= pathPartialDist + wayPoints.get((counter+i)).distanceFromPrevious;
                System.out.println("pathPartialDist = " + pathPartialDist);
                System.out.println(counter+i);
            }


            //The x and y coordinates of the two endpoints of the final line.
            double wP0X=wayPoints.get(counter+i-1).getPoint().getX();
            double wP0Y=wayPoints.get(counter+i-1).getPoint().getY();
            double wP1X=wayPoints.get(counter+i).getPoint().getX();
            double wP1Y=wayPoints.get(counter+i).getPoint().getY();

            System.out.println("current Point =" + current);
            System.out.println("counter = " + counter);
            System.out.println("i = " +i);
            System.out.println("wP0X = "+ wP0X);
            System.out.println("wP0Y = "+ wP0Y);
            System.out.println("wP1X = "+ wP1X);
            System.out.println("wP1Y = "+ wP1Y);
            System.out.println("wayPoints.get(counter).componentAlongPath(current) = " + wayPoints.get(counter).componentAlongPath(current));

            //delta x and y from previous point for final segment.
            double dxfp=wayPoints.get(counter +i).getDeltaXFromPrevious();
            double dyfp=wayPoints.get(counter +i).getDeltaYFromPrevious();

            System.out.println("dxfp, delta x from previous point = "+ dxfp);
            System.out.println("dyfp = "+ dyfp);

            //distance from previous point.
            double dsfp=wayPoints.get(counter +i).getDistanceFromPrevious();
            System.out.println("dsfp distance from previous point = "+ dsfp);

            //calculating the distance from WayPoints.get(counter+i-1) to where targetDistance lands
            //on the path.

            double extraDistance = pathPartialDist-targetDistance;
            double remainingDistance=wayPoints.get(counter+i).distanceFromPrevious-extraDistance;
            System.out.println("pathPartialDist = " + pathPartialDist);
            System.out.println("targetDistance = " + targetDistance);
            System.out.println("remainingDistance = " + remainingDistance);

            //final line is vertical
            if (dxfp==0){
                System.out.println("targetDistance > component Along path and final line is vertical,");

                //calculating the x and y coordinates of the targetPoint.
                //
                // projection on path (pop).
                //in this special case it assigns the value of WayPoint 0.x to the tPX;
                // it's on the same vertical line.
                //Assinges remaining distance plus wP0Y.

                double tPX=wP0X;
                double tPY=remainingDistance + wP0Y;
                System.out.println("tPX = " + tPX);
                System.out.println("tPY = " + tPY);


                //creating a the Point targetPoint from tPX and tPY.
                Point targetPoint = new Point(tPX,tPY);

                //creating deltaX and deltaY from current to target point.
                double dXTP=tPX-current.getX();
                double dYTP=tPY-current.getY();

                //creating distance from current to targetPoint.
                double dTP=Math.sqrt(dXTP*dXTP+dYTP*dYTP);

                //creating targetWayPoint,
                WayPoint targetWayPoint = new WayPoint(targetPoint, dXTP, dYTP, dTP);

                return targetWayPoint;
                /*
                Point errorPoint = new Point (0,0);
                WayPoint targetWayPoint = new WayPoint(errorPoint, 0, 0, 0);
                System.out.println("counter = " + counter);
                System.out.println("componentAlongPath(current) = "+ wayPoints.get(counter).componentAlongPath(current));

                return targetWayPoint;

                 */
            }
            else {
                //if the final line is not vertical
                System.out.println("final line is not vertical and target distance is more than componentAlongPath");


                //non coordinates of the projection onto the path are needed in this case.
                //calculating the x and y coordinates of the targetPoint.
                double tPX=remainingDistance*Math.cos(Math.atan2(dyfp, dxfp))+ wP0X;
                double tPY=remainingDistance*Math.sin(Math.atan2(dyfp, dxfp))+ wP0Y;
                System.out.println("tPX = " + tPX);
                System.out.println("tPY = " + tPY);

                //creating a the Point targetPoint from tPX and tPY.
                Point targetPoint = new Point(tPX,tPY);

                //creating deltaX and deltaY from current to target point.
                double dXTP=tPX-current.getX();
                double dYTP=tPY-current.getY();

                //creating distance from previous to targetPoint.
                double dTP=Math.sqrt(dXTP*dXTP+dYTP*dYTP);

                //creating targetWayPoint,
                WayPoint targetWayPoint = new WayPoint(targetPoint, dXTP, dYTP, dTP);

                return targetWayPoint;


            }


        }



        else{
            Point errorPoint = new Point (0,0);
            WayPoint targetWayPoint = new WayPoint(errorPoint, 0, 0, 0);
            System.out.println("counter = " + counter);
            System.out.println("componentAlongPath(current) = "+ wayPoints.get(counter).componentAlongPath(current));

            return targetWayPoint;
        }


    }



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

            public double getDeltaXFromPrevious() {
                return deltaXFromPrevious;
            }

            public double getDeltaYFromPrevious() {
                return deltaYFromPrevious;
            }

            public Point getPoint() {
                return point;
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
            //System.out.println("componentAlongPath INFO");
            //System.out.println("deltaXFromCurrent = " + deltaXFromCurrent);

            double deltaYFromCurrent = point.getY() - current.getY();
            //System.out.println("deltaYFromCurrent = " + deltaYFromCurrent);


            double dp = deltaXFromCurrent * deltaXFromPrevious + deltaYFromCurrent * deltaYFromPrevious;

            //System.out.println("dp = " + dp);
            //System.out.println("distanceFromPrevious" + distanceFromPrevious);
            //System.out.println("dp/distanceFromPrevious = " + dp/distanceFromPrevious);
            return dp / distanceFromPrevious;


        }

        public double getDistanceFromPrevious() {
            return distanceFromPrevious;
        }
    }



}
