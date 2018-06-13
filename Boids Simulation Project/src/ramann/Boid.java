package ramann;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Authors: Nathaniel Sauerberg and Narun Raman
 * Represents a Boid in the BoidModel
 * Each Boid has x and y locations and a velocity Vector
 * And knows how to move itself based on the boid/obstacle/wall lists in the model
 */
public class Boid implements Sprite{

    private Model model;
    private double x;
    private final double SCALAR_MULTIPLE = 0.0001;
    private double y;
    private Vector velocity;
    private Polygon triangle;
    Rotate rx = new Rotate();
    //{ rx.setAxis(Rotate.X_AXIS); }

    /**
     * create a new Boid at given location with 0 initial velocity
     * @param x initial x location of the Boid
     * @param y initial y location of the Boid
     */
    public Boid (double x, double y, Model model){
        rx = new Rotate();
        this.x = x;
        this.y = y;
        this.velocity = new Vector(1, 0);
        this.model = model;
        triangle = new Polygon();
        triangle.setFill(Color.WHITE);
        triangle.setStroke(Color.BLUE);
    }

    public Shape getShape(){
        triangle.getPoints().clear();
        if((velocity.getX() > 0 && velocity.getY() > 0) || (velocity.getX() > 0 && velocity.getY() < 0)){
            rx.setAngle(Math.atan(velocity.getY()/velocity.getX()));
        }
        else if(velocity.getY() < 0 && velocity.getX() < 0){
            rx.setAngle(90 + (-1) * Math.atan(velocity.getY()/velocity.getX()));
        }
        else{
            rx.setAngle(90 + (-1) * Math.atan(velocity.getY()/velocity.getX()));
        }
        rx.setPivotX(this.x);
        rx.setPivotY(this.y);
        triangle.getTransforms().addAll(rx);
        triangle.getPoints().addAll(trianglePoints());
        return triangle;
    }

    private Double[] trianglePoints(){
        return new Double[]{
                x, y - 3,
                x - 1.5, y + 2,
                x + 1.5, y + 2
        };
    }

    /**
     * @return a Reference to this Vector's velocity Vector
     */
    public Vector getVelocity() {
        return velocity;
    }

    /**
     * @return a new Vector equal to this Boid's velocity Vector
     */
    public Vector getVelocityValue(){
        return new Vector(velocity.getX(), velocity.getY());
    }

    /**
     * Returns
     * @return a new Vector equal to this Boid's velocity vector normalized
     */
    public Vector getDirectionValue() {
        return new Vector(velocity.getX(), velocity.getY()).normalize();
    }

//    public ImageView getBoidImage(){
//        if(velocity.getX() * velocity.getY() < 0){
//            Boid.boidHitBox.setRotate((int) Math.tan(velocity.getY()/velocity.getX()));
//        }
//        return Boid.boidHitBox;
//    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * changes x and y velocities based on presence of nearby flockmates and obstacles
     * then changes the location based on the new velocities
     */
    public void move(){
        x = (x + velocity.getX()); //% model.getWidthOfSimulation();
        y = (y + velocity.getY()); //% model.getHeightOfSimulation();
    }

    /**
     * updates the Boid's velocity based on the positions of the other Boids, Obstacles, and Walls
     * The new velocity Vector will never have magnitude greater than 1
     */
    public void updateVelocity(){

        ArrayList<Boid> nearbyBoids = new ArrayList<>();
        Vector avgDirection = new Vector(0,0);
        ArrayList<Boid> boidsToAvoid = new ArrayList<>();
        ArrayList<Vector> locationsToAvoid = new ArrayList<>(); // obstacles and closest points on walls

        for (Boid otherBoid : model.getBoidList()){
            if (otherBoid == this) continue; // so we don't consider this specific boid
            // if within perception radius
            if (Math.hypot(this.x - otherBoid.getX(), this.y - otherBoid.getY()) <= model.getBoidPerceptionRadius()){
                // add to avg locations (deals with cohesion)
                nearbyBoids.add(otherBoid);
                // add to avg direction (deals with alignment)
                avgDirection.add(otherBoid.getDirectionValue());
                // if too close, add to boids to avoid (deals with separation)
                if (Math.hypot(this.x - otherBoid.getX(), this.y - otherBoid.getY()) <= model.getBoidSeparationForceRadius()){
                    boidsToAvoid.add(otherBoid);
                }
            }
        }

        for (Obstacle obstacle : model.getObstacleList()) {
            if (Math.hypot(this.x - obstacle.getX(), this.y - obstacle.getY()) <= model.getBoidSeparationForceRadius()) {
                locationsToAvoid.add(new Vector (obstacle.getX(), obstacle.getY()));
            }
        }

        // set new velocity vector by:
        // V1 = V0 + .1cohesionFactor + .1alignmentFactor + (.1separationFactor/distance For ALl Separations/Obstacles/Walls)
        // where all vectors are unit vectors

        if (model.getCohesion()){
            double avgX = 0;
            double avgY = 0;
            for (Boid otherBoid : nearbyBoids){
                avgX += otherBoid.getX();
                avgY += otherBoid.getY();
            }
            avgX = avgX / nearbyBoids.size();
            avgY = avgY / nearbyBoids.size();
            velocity.add(new Vector(avgX - this.x, avgY - this.y).normalize().scalarMultiply(SCALAR_MULTIPLE));
        }
        if (model.getAlignment()){
            velocity.add(avgDirection.normalize().scalarMultiply(SCALAR_MULTIPLE));
        }
        if (model.getSeparation()){
            for (Boid otherBoid : boidsToAvoid) {
                Vector away = new Vector(this.x - otherBoid.getX(), this.y - otherBoid.getY());
                if (!(away.getMagnitude() == 0))
                    velocity.add(away.getNormal().scalarMultiply(SCALAR_MULTIPLE).scalarMultiply(10 / away.getMagnitude()));
                else
                    velocity.add(away.getNormal().scalarMultiply(SCALAR_MULTIPLE).scalarMultiply(1 / 1000));
            }
        }
        // deals with avoiding obstacles and walls
        // could add an if statement if we want to allow turning on/off obstacle/wall avoidance
        for (Vector v : locationsToAvoid){
            Vector away = new Vector (this.x - v.getX(), this.y - v.getY());
            velocity.add(away.getNormal().scalarMultiply(1 / away.getMagnitude()));
        }

        // since max velocity is 1
        if (velocity.getMagnitude() > 1) velocity.normalize();
        if (x < 0 || x > model.getWidthOfSimulation()) velocity.setX(-1*velocity.getX());
        if (y < 0 || y > model.getHeightOfSimulation()) velocity.setY(-1*velocity.getY());
    }
}

