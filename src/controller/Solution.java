package controller;

class Circle {

    Complex centerPoint;
    Double radius;

    public Circle(Complex cen, Double rad) {
        setCenterPoint(cen);
        setRadius(rad);
    }

    public Complex getCenterPoint() {
        return this.centerPoint;
    }

    public void setCenterPoint(Complex point) {
        this.centerPoint = point;
    }

    public Double getRadius() {
        return this.radius;
    }

    public void setRadius(Double r) {
        this.radius = r;
    }

}

public class Solution {

    Complex root;
    Integer m;
    Circle[] circles;

    public Solution(Complex r, Integer m1, Complex[] points, Double[] rad) {
        setRoot(r);
        setM(m1);
        setCircles(points, rad);
    }

    public Complex getRoot() {
        return root;
    }

    public void setRoot(Complex r) {
        root = r;
    }

    public Integer getM() {
        return m;
    }

    public void setM(Integer m1) {
        m = m1;
    }

    public void getCircles(Complex[] roots, Double[] rad) {
        for(int i = 0; i < roots.length; i++) {
            roots[i] = circles[i].getCenterPoint();
            rad[i] = circles[i].getRadius();
        }
    }

    public void setCircles(Complex[] roots, Double[] rad) {
        circles = new Circle[roots.length];
        for(int i = 0; i < roots.length; i++) {
            circles[i] = new Circle(roots[i], rad[i]);
        }
    }

}
