package ramann;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * @Authors: Nathaniel Sauerberg and Narun Raman
 * The Model component of the MVC structure of the Boids Simulation
 * Contains the rules of the simulation, information about the simulation area
 * and lists of all boid and obstacle objects
 */
public class Model {

    private ArrayList<Boid> boidList;
    private ArrayList<Obstacle> obstacleList;
    private ArrayDeque<Sprite> stack;

    private boolean separation;
    private boolean alignment;
    private boolean cohesion;
    private double boidPerceptionRadius;
    private double boidSeparationForceRadius;

    private boolean leftRightEdgesCircular;
    private boolean topBottomEdgesCircular;

    private double heightOfSimulation;
    private double widthOfSimulation;

    /**
     * Basic Constructor
     * Initializes the 3 basic rules for the boids to true (turned on)
     * Initializes rules governing simulation space edges to false (circular)
     * Initializes the boid perception/separation radii to defaults
     */
    public Model() {
        this.boidList = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            boidList.add(new Boid(Math.random()*600, Math.random()*690, this));
        }
        this.obstacleList = new ArrayList<>();
        this.stack = new ArrayDeque<>();
        this.separation = true;
        this.alignment = true;
        this.cohesion = true;
        this.leftRightEdgesCircular = false;
        this.topBottomEdgesCircular = false;
        this.boidPerceptionRadius = 1000;
        this.boidSeparationForceRadius = 100;
    }

    /**
     * Updates the model for one move (tick) in the Simulation space
     */
    public void executeFrameTick(){
        for(Boid boid : boidList){
            boid.updateVelocity();
        }
        for(Boid boid : boidList){
            boid.move();
        }
    }

    /**
     * Adds a new boid to the model at given location with 0 initial velocity
     * @param x initial x location of boid
     * @param y initial y location of boid
     */
    public void addBoid(double x, double y){
        Boid b = new Boid(x, y,this);
        boidList.add(b);
        stack.push(b);
    }

    /**
     * Adds a new obstacle to the model at given location
     * @param x initial x location of obstacle
     * @param y initial y location of obstacle
     */
    public void addObstacle(double x, double y){
        Obstacle o = new Obstacle(x,y);
        obstacleList.add(o);
        stack.push(o);
    }

    /**
     * Removes the Boid, Obstacle, or Wall that was most recently added
     */
    public void undo(){
        if (!stack.isEmpty()) {
            Sprite sprite = stack.pop();
            if (!boidList.remove(sprite))
                obstacleList.remove(sprite);
        }
    }

    public boolean getSeparation() {
        return separation;
    }

    public boolean getAlignment() {
        return alignment;
    }

    public boolean getCohesion() {
        return cohesion;
    }

    public double getBoidPerceptionRadius() {
        return boidPerceptionRadius;
    }

    public double getBoidSeparationForceRadius() {
        return boidSeparationForceRadius;
    }

    public ArrayList<Boid> getBoidList() {
        return boidList;
    }

    public ArrayList<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public void setSeparation(boolean separation) {
        this.separation = separation;
    }

    public void setAlignment(boolean alignment) {
        this.alignment = alignment;
    }

    public void setCohesion(boolean cohesion) {
        this.cohesion = cohesion;
    }

    public double getHeightOfSimulation() {
        return heightOfSimulation;
    }

    public double getWidthOfSimulation() {
        return widthOfSimulation;
    }

    public void setBoidPerceptionRadius(double boidPerceptionRadius) {
        this.boidPerceptionRadius = boidPerceptionRadius;
    }

    public boolean isLeftRightEdgesCircular() {
        return leftRightEdgesCircular;
    }

    public boolean isTopBottomEdgesCircular() {
        return topBottomEdgesCircular;
    }

    public void setHeightOfSimulation(double heightOfSimulation) {
        this.heightOfSimulation = heightOfSimulation;
    }

    public void setWidthOfSimulation(double widthOfSimulation) {
        this.widthOfSimulation = widthOfSimulation;
    }
}
