package ramann;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * @Authors: Nathaniel Sauerberg and Narun Raman
 * Represents an Obstacle that can be placed into the Simulation
 * Obstacles have a location
 */
public class Obstacle implements Sprite {

    private double x;
    private double y;
    private static final double RADIUS_OF_CIRCLE = 2;
    private Circle circle;

    /**
     * create a new obstacle at given location
     * @param x x location of the Obstacle
     * @param y y location of the Obstacle
     */
    public Obstacle(double x, double y){
        this.x = x;
        this.y = y;
        circle = new Circle(x, y, RADIUS_OF_CIRCLE);
    }

    public Shape getShape(){
        return circle;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
