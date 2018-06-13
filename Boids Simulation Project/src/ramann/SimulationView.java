package ramann;

import javafx.scene.layout.Region;

/**
 * @Authors: Nathaniel Sauerberg and Narun Raman
 * The main view in the MVC structure
 * Displays the simulation screen holding all of the boids and obstacles
 */

public class SimulationView extends Region {

    public SimulationView() {
    }

    public void updateSimulation(Model model) {

        this.getChildren().clear();

        for (Boid aBoid : model.getBoidList()) {
            this.getChildren().addAll(aBoid.getShape());
        }
        for (Obstacle anObstacle : model.getObstacleList()) {
            this.getChildren().addAll(anObstacle.getShape());
        }
    }
}
