
package QuickHullCode;

import java.util.Random; //to generate random points

//Class point to generate random points and order it .

public class Point implements Comparable<Point> {

    private static Random randomPoints = new Random(); //object from Random class
    private int x;
    private int y;

//Constructor of Point class with parameter x and y . 
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

//Constructor of Point class with no parameter 
//generate random points within the range of x and y
    public Point() {
        x = -400 + randomPoints.nextInt(801);
        y = -400 + randomPoints.nextInt(801);
    }
    
//getter methods
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "\n" + "(" + x + "," + y + ")";
    }

    public boolean equals(Point p) {
        return p.getX() == this.x && p.getY() == this.y;
    }

//The comparison is based on clockwise way to order points that generate randomly. 
    @Override 
    public int compareTo(Point p) {
        if (y > 0 && p.getY() < 0) {
            return 1;
        } else if (y < 0 && p.getY() > 0) {
            return -1;
        } else {
            if (x > 0 && p.getX() < 0) {
                if (y > 0) {
                    return -1;
                } else {
                    return 1;
                }
            } else if (x < 0 && p.getX() > 0) {
                if (y > 0) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                if (y >= 0 && p.getY() >= 0) {
                    if (x <= p.getX()) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {
                    if (x >= p.getX()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        }
    }
}
