/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuickHullCode;


public class Line {
    private Point p1; 
      private Point p2; 
 
//Constructor of Line class with parameter p1 and p2 that object from point calss . 
      public Line(Point p1, Point p2){ 
            this.p1 = p1; 
            this.p2 = p2; 
      } 
      
//getter methods
      public Point getP1() { 
            return p1; 
      } 
      public Point getP2() { 
            return p2; 
      } 
 
      @Override 
      public String toString() { 
            return "<"+p1.toString()+"|"+p2.toString()+">"; 
      } 
}