//Razan Aljuhani
//CONVEX HULL BY QuickHull Algorithm 

package QuickHullCode;

import java.util.ArrayList; //for store points of convex hull
import java.util.InputMismatchException;
import java.util.Scanner;// for read tha input size (number of points) from user .

//QuickHull class 
public class QuickHullCode {

    public static int NumberOfPoints; // input size
    public static ArrayList<Point> ListOfPoints = new ArrayList<Point>(); //all points that generate randomly for specific input size
    public static ArrayList<Point> PointOfConvexHull = new ArrayList<Point>();  //solution points

//Main
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        System.out.println(">>>> Enter the number of points between 3 - 10000 :");
        try {
            NumberOfPoints = input.nextInt();
            for (int i = 0; i < NumberOfPoints; i++) {
                ListOfPoints.add(new Point());
            }
            System.out.println("The points generated  randomly : " + ListOfPoints );
        } catch (InputMismatchException e) {
            System.err.println("Invalid input.");
            System.exit(-1);
        }
        //to calculate the runtime
        long startTime;
        long endTime;
        startTime = System.currentTimeMillis(); //Start time (ms)
        
        System.out.println(">>>> The Solution Point of convex hull By Quick Hull Algorithm : ");
        
        QuickHullCode qh = new QuickHullCode();
        PointOfConvexHull = qh.QuickHull(ListOfPoints, PointOfConvexHull); 
        System.out.println(PointOfConvexHull); //print solution points for convex Hull
        
        endTime = System.currentTimeMillis() - startTime;  //End time (ms)
        System.out.println("\n\n >>>> The Runtime of Algorithm :" + endTime + " ms");
    }

/*QuickHull algorithm :
Let a[0…n-1] be the input array of points. Following are the steps for finding the convex hull of these points 
1.) Find the point with minimum x-coordinate lets say, min_x and similarly the point with maximum x-coordinate, max_x
2.) Make a line joining these two points ,this line will divide the whole set into two parts. Take both the parts one by one and proceed further.
3.) For a part, find the point P with maximum distance from the line. P forms a triangle with the points min_x, max_x. 
    It is clear that the points residing inside this triangle can never be the part of convex hull.
4.) The above step divides the problem into two sub-problems (solved recursively).*/
    
//Method of QuickHull algorithm that return thw points of convex hull .
    public ArrayList<Point> QuickHull(ArrayList<Point> ListOfPoints, ArrayList<Point> PointOfConvexHull) {
        if (ListOfPoints.size() < 3) {
            return (ArrayList) ListOfPoints.clone();
        }
        int minPoint = -1, maxPoint = -1;
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        for (int i = 0; i < ListOfPoints.size(); i++) {
            if (ListOfPoints.get(i).getX() < minX) {
                minX = ListOfPoints.get(i).getX();
                minPoint = i;
            }
            if (ListOfPoints.get(i).getX() > maxX) {
                maxX = ListOfPoints.get(i).getX();
                maxPoint = i;
            }
        }
        Point A = ListOfPoints.get(minPoint); 
        Point B = ListOfPoints.get(maxPoint);
        PointOfConvexHull.add(A);
        PointOfConvexHull.add(B);
        ListOfPoints.remove(A);
        ListOfPoints.remove(B);
        ArrayList<Point> leftSet = new ArrayList<Point>();
        ArrayList<Point> rightSet = new ArrayList<Point>();

        for (int i = 0; i < ListOfPoints.size(); i++) {
            Point p = ListOfPoints.get(i);
            if (pointLocation(A, B, p) == -1) {
                leftSet.add(p);
            } else if (pointLocation(A, B, p) == 1) {
                rightSet.add(p);
            }
        }
        findHull(A, B, rightSet, PointOfConvexHull); // call FindHull algorithm to find convex for rigth subset   
        findHull(B, A, leftSet, PointOfConvexHull); // call FindHull algorithm to find convex for left subset  
        return PointOfConvexHull;
    }

//Method of to find distance 
    public int distance(Point A, Point B, Point C) {
        int ABx = B.getX() - A.getX();
        int ABy = B.getY() - A.getY();
        int num = ABx * (A.getY() - C.getY()) - ABy * (A.getX() - C.getX());
        if (num < 0) {
            num = -num;
        }
        return num;
    }

//Method to findHull of subsets
    public void findHull(Point A, Point B, ArrayList<Point> set, ArrayList<Point> hull) {
        int insertPosition = hull.indexOf(B);
        if (set.isEmpty()) {
            return;
        }
        if (set.size() == 1) {
            Point p = set.get(0);
            set.remove(p);
            hull.add(insertPosition, p);
            return;
        }
        int dist = Integer.MIN_VALUE;
        int furthestPoint = -1;
        for (int i = 0; i < set.size(); i++) {
            Point p = set.get(i);
            int distance = distance(A, B, p);
            if (distance > dist) {
                dist = distance;
                furthestPoint = i;
            }
        }
        Point P = set.get(furthestPoint);
        set.remove(furthestPoint);
        hull.add(insertPosition, P);
        // Determine who's to the left of AP
        ArrayList<Point> leftSetAP = new ArrayList<Point>();
        for (int i = 0; i < set.size(); i++) {
            Point M = set.get(i);
            if (pointLocation(A, P, M) == 1) {
                leftSetAP.add(M);
            }
        }

        // Determine who's to the left of PB
        ArrayList<Point> leftSetPB = new ArrayList<Point>();
        for (int i = 0; i < set.size(); i++) {
            Point M = set.get(i);
            if (pointLocation(P, B, M) == 1) {
                leftSetPB.add(M);
            }
        }
        findHull(A, P, leftSetAP, hull);
        findHull(P, B, leftSetPB, hull);
    }
    
//Method to determine the location of point
    public int pointLocation(Point A, Point B, Point P) {
        int cp1 = (B.getX() - A.getX()) * (P.getY() - A.getY()) - (B.getY() - A.getY()) * (P.getX() - A.getX());
        if (cp1 > 0) {
            return 1;
        } else if (cp1 == 0) {
            return 0;
        } else {
            return -1;
        }
    }
    
}
