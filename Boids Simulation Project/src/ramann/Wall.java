package ramann;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

/**
 * @Authors: Nathaniel Sauerberg and Narun Raman
 * Represents a Wall, which can be placed into the Simulation
 * The wall has x and y start and end points, but should always be really thin in one direction
 * The wall is intended to be an impassable barrier for the Boids and they should repel it
 * xStart will always be <= xEnd; Similarly yStart will be <= yEnd
 */
public class Wall implements Sprite {

    private double xStart;
    private double yStart;
    private double xEnd;
    private double yEnd;
    private Line line;

    /**
     * create a new obstacle at given location
     * @param xStart x start location of the Wall
     * @param yStart y start location of the Wall
     * @param xEnd x end location of the Wall
     * @param yEnd y end location of the Wall
     */
    public Wall(double xStart, double yStart, double xEnd, double yEnd){
        if (xStart < xEnd) {
            this.xStart = xStart;
            this.xEnd = xEnd;
        } else {
            this.xStart = xEnd;
            this.xEnd = xStart;
        }
        if (yStart < yEnd){
            this.yStart = yStart;
            this.yEnd = yEnd;
        } else {
            this.yStart = yEnd;
            this.yEnd = yStart;
        }
        line = new Line(xStart, yStart, xEnd, yEnd);
    }

    public Shape getShape(){
        return line;
    }

    public double getXStart() {
        return xStart;
    }

    public double getYStart() {
        return yStart;
    }

    public double getXEnd() {
        return xEnd;
    }

    public double getYEnd() {
        return yEnd;
    }

}
