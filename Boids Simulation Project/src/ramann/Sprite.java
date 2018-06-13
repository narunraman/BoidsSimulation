package ramann;

import javafx.scene.shape.Shape;

/**
 * @Authors: Nathaniel Sauerberg and Narun Raman
 * An interface that Boid and Obstacle implement
 * to allow the View to treat them similarly
 */

public interface Sprite {

    public Shape getShape();

}
