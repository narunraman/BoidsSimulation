package ramann;

import java.lang.Math;

/**
 * @Authors: Nathaniel Sauerberg and Narun Raman
 * Represent a Vector, with x and y components
 * Supports various Vector mathematics operations
 */
public class Vector {

    double x;
    double y;

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public static Vector add(Vector a, Vector b){
        return (new Vector (a.getX() + b.getX(), a.getY() + b.getY()));
    }

    public static Vector subtract(Vector a, Vector b){
        return (new Vector (a.getX() - b.getX(), a.getY() - b.getY()));
    }

    /**
     * Adds another Vector to this Vector and returns this Vector
     * @param toAdd Another Vector to add to this one
     * @return this vector
     */
    public Vector add (Vector toAdd){
        x += toAdd.getX();
        y += toAdd.getY();
        return this;
    }

    /**
     * normalizes this Vector (makes it a unit vector) and returns it
     * @return this Vector once its been normalized
     */
    public Vector normalize(){
        double tempX = x;
        x = x / (Math.sqrt(x*x + y*y));
        y = y / (Math.sqrt(tempX*tempX + y*y));
        return this;
    }

    /**
     * Gets a new Vector equal to this one but scaled to magnitude 1
     * @return a new Vector equal to this but normalized
     */
    public Vector getNormal(){
        return new Vector(this.x, this.y).normalize();
    }

    /**
     * @return the magnitude of this Vector
     */
    public double getMagnitude(){
        return (Math.sqrt(x*x + y*y));
    }

    /**
     * Multiplies the x and y coordinates of this Vector by a scalar and returns it
     * @return this Vector after it has been multiplied
     */
    public Vector scalarMultiply(double scalar){
        x = scalar * x;
        y = scalar * y;
        return this;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

}
