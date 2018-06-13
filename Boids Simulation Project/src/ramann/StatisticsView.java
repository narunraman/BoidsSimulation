package ramann;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


public class StatisticsView extends AnchorPane {
    private AnchorPane statisticsPane;
    private Label boidsCount;
    private Label obstacleCount;
    private String numBoids;
    private String numObstacles;
    private Model model;
    private HBox statBox;

    public StatisticsView(){
        this.statisticsPane = new AnchorPane();
        this.boidsCount = new Label();
        this.numBoids = "Number of Boids: ";
        this.obstacleCount = new Label();
        this.numObstacles = "Number of Obstacles: ";
        model = new Model();
        this.statBox = new HBox();
        statisticsPane.setLeftAnchor(statBox, 10.0);
    }

    public void updateCount(){
        boidsCount.setText(numBoids += model.getBoidList().size());
        obstacleCount.setText(numObstacles += model.getObstacleList().size());
    }
}
