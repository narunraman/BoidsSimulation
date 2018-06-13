package ramann;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * @Authors: Nathaniel Sauerberg and Narun Raman
 * Starts the application, setting up the Stage, Scene, root Node, and Controller
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = new BorderPane();
        SimulationView simulationView  = new SimulationView();
        StatisticsView statisticsView = new StatisticsView();

        FXMLLoader optionsLoader = new FXMLLoader(getClass().getResource("optionsPanel.fxml"));
        BorderPane optionsPane = new BorderPane(optionsLoader.load()); // change to flow pane
        Controller controller = optionsLoader.getController();

        controller.setSimulationView(simulationView);

        ((BorderPane) root).setCenter(simulationView);
        ((BorderPane) root).setRight(optionsPane);
        ((BorderPane) root).setBottom(statisticsView);

        primaryStage.setTitle("Boids Simulation");
        primaryStage.setScene(new Scene(root, 1000, 675));
        primaryStage.setResizable(false);
        primaryStage.show();
        controller.startSimulation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
