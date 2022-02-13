//Razan Aljuhani
//CONVEX HULL BY Brute Force Algorithm 

package bruteforcecode;


import java.util.ArrayList; //for store points of convex hull
import java.util.Collections; //for sort points
import java.util.InputMismatchException;
import java.util.Scanner;// for read tha input size (number of points) from user .
import javax.swing.JFrame; // for plot the convex hull
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

//BruteForce class 
public class BruteForceCode {

    public static int NumberOfPoints; // input size
    public static ArrayList<Point> ListOfPoints = new ArrayList<Point>(); //all points that generate randomly for specific input size
    public static ArrayList<Point> PointOfConvexHull = new ArrayList<Point>(); //solution points
    public static ArrayList<Line> solutionLines = new ArrayList<Line>(); //for plotting 
    public static ArrayList<Line> triLines = new ArrayList<Line>();  

//Main
    public static void main(String[] args) {

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
        System.out.println(">>>> The Solution Point of convex hull By BruteForce Algorithm : ");
        BruteForce(ListOfPoints, PointOfConvexHull); //call of Brute Force function

        endTime = System.currentTimeMillis() - startTime;  //End time (ms)
        System.out.println("\n\n>>>> The Runtime of Algorithm :" + endTime + " ms");
       
        //Show solution visually 
        showVisually();
    }

    /**
     * Brute Force algorithm: 
     * 1.) Create all possible convex-hull lines between all the combinations between 2 points in the point list 
     * 2.) using the equations for a line between 2 points: A*x + B*y = C 2.) 
     * Compare all the other points in the list to check if they lie in the same side of the line created. 
     * 3.) If step 2 successfully completes add those two points to solution.
     */
    
//Method of Brute Force algorithm
    public static void BruteForce(ArrayList<Point> ListOfPoints, ArrayList<Point> PointOfConvexHull) {
        for (int i = 0; i < ListOfPoints.size() - 1; i++) {
            for (int j = i + 1; j < ListOfPoints.size(); j++) {
                boolean isExtremeLine = true;
                int initialSign = 0;
                Point p1 = ListOfPoints.get(i);
                Point p2 = ListOfPoints.get(j);
                int coefA = p2.getY() - p1.getY();
                int coefB = p1.getX() - p2.getX();
                int coefC = p1.getX() * p2.getY() - p1.getY() * p2.getX();
                for (int k = 0; k < ListOfPoints.size(); k++) {
                    Point p3 = ListOfPoints.get(k);
                    int PointRegion = coefA * p3.getX() + coefB * p3.getY() - coefC;
                    if (initialSign != 0) {
                        if (PointRegion > 0 && initialSign < 0) {
                            isExtremeLine = false;
                            break;
                        } else if (PointRegion < 0 && initialSign > 0) {
                            isExtremeLine = false;
                            break;
                        } 
                    } else {
                        if (PointRegion > 0) {
                            initialSign = 1;
                        } else if (PointRegion < 0) {
                            initialSign = -1;
                        } 
                    }
                }
                if (isExtremeLine) {
                    addToSol(PointOfConvexHull, p1);
                    addToSol(PointOfConvexHull, p2);
                    //Sol line for plotting 
                    solutionLines.add(new Line(p1, p2));
                }
            }
        }
        //Sort solution list 
        Collections.sort(PointOfConvexHull);
        //Print solution list of points 
        for (int i = PointOfConvexHull.size() - 1; i >= 0; i--) {
            System.out.print(PointOfConvexHull.get(i).toString() + " ");
        }
    }

//Method to add point to solution list for Convex Hull
    private static void addToSol(ArrayList<Point> PointsOfConvexHull, Point pToAdd) {
        for (Point p : PointsOfConvexHull) {
            if (p.equals(pToAdd)) {
                return;
            }
        }
        PointsOfConvexHull.add(pToAdd);
    }

//Method to show solution visually
    private static void showVisually() {

        JFrame JFrame = new JFrame(); //object from class JFrame
        JFrame.add(new JPanel() {
            @Override
            public void paintComponent(Graphics graph) {
                int x_max = 10000;
                int y_max = 10000;
                int displ = 500;

                //Draw BLACK Background 
                graph.setColor(Color.BLACK);
                for (int i = 0; i <= y_max; i++) {
                    graph.drawLine(0, i, x_max, i);
                }
                //Draw GREEN Axis 
                graph.setColor(Color.GREEN);
                graph.drawLine(0, displ, x_max, displ);
                graph.drawLine(displ, 0, displ, y_max);

                //Draw PINK Dots 
                graph.setColor(Color.PINK);
                for (Point p : ListOfPoints) {
                    graph.drawLine(p.getX() + displ, displ - p.getY(), p.getX() + displ, displ - p.getY());
                }

                //Draw Convex-Hull Lines 
                for (int i = 0; i < solutionLines.size(); i++) {
                    Point p1 = solutionLines.get(i).getP1();
                    Point p2 = solutionLines.get(i).getP2();
                    graph.drawLine(p1.getX() + displ, displ - p1.getY(), p2.getX() + displ, displ - p2.getY());
                }
            }
        });
        JFrame.setSize(1000, 1000);
        JFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFrame.setVisible(true);
    }
}
