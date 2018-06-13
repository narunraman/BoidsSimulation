package ramann;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Authors: Nathaniel Sauerberg and Narun Raman
 * The Controller part of the MCV Paradigm for the Boids Project
 */

public class Controller {

    private final int FRAMES_PER_SECOND = 30;

    @FXML private SimulationView simulationView;
    private Model model;
    private Timer timer;
    boolean isPaused;

    @FXML private RadioButton addBoid;
    @FXML private RadioButton addObstacle;

    @FXML private ToggleButton separationToggle;
    @FXML private ToggleButton alignmentToggle;
    @FXML private ToggleButton cohesionToggle;

    public Controller (){
    }

    public void initialize(){
        model = new Model();
        isPaused = false;
    }

    public void startSimulation(){
        startTimer();
        model.setWidthOfSimulation(600);
        model.setHeightOfSimulation(690);
    }

    private void startTimer() {
        this.timer = new java.util.Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        model.executeFrameTick();
                        simulationView.updateSimulation(model);
                    }
                });
            }
        };
        long frameTimeInMilliseconds = (long)(1000.0 / FRAMES_PER_SECOND);
        this.timer.schedule(timerTask, 0, frameTimeInMilliseconds);
    }

    // respond to each thing on options panel
        // enable or disable the 3 boid control rules
        // toggle the 2 circular edge options
        // options to add new boid/obstacle/wall

    public void onSeparationButton(ActionEvent actionEvent){
        if (isPaused) {
            model.setSeparation(!separationToggle.isSelected());
        } else {
            pause();
            model.setSeparation(!separationToggle.isSelected());
            resume();
        }
    }

    public void onAlignmentButton(ActionEvent actionEvent){
        if (isPaused) {
            model.setAlignment(!alignmentToggle.isSelected());
        } else {
            pause();
            model.setAlignment(!alignmentToggle.isSelected());
            resume();
        }
    }

    public void onCohesionButton(ActionEvent actionEvent) {
        if (isPaused) {
            model.setCohesion(!cohesionToggle.isSelected());
        } else {
            pause();
            model.setCohesion(!cohesionToggle.isSelected());
            resume();
        }
    }

    public void onUndoAddButton(ActionEvent actionEvent){
        if (isPaused) {
            model.undo();
        } else {
            pause();
            model.undo();
            resume();
        }
    }
    /**
     * Handles all clicks on the simulation space:
     * Checks to see if an addObject radio button is enabled
     * If so, adds the object
     *    (pausing the sim, showing a preview while waiting for the second click, and resuming if adding a wall)
     * Else, pauses or resumes the simulation
     * @param actionEvent
     */
    public void onSimClicked(ActionEvent actionEvent){
        // To avoid messy additions to the sim while the sim is running, on all object adds
        // pauses simulation is wasn't already paused
        // adds object to simulation
        // then resumes if it hadn't started paused
        simulationView.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (addBoid.isSelected()) {
                if (isPaused) {
                    model.addBoid(e.getSceneX(), e.getSceneY());
                } else {
                    pause();
                    model.addBoid(e.getSceneX(), e.getSceneY());
                    resume();
                }
            } else if (addObstacle.isSelected()) {
                if (isPaused) {
                    model.addObstacle(e.getSceneX(), e.getSceneY());
                } else {
                    pause();
                    model.addObstacle(e.getSceneX(), e.getSceneY());
                    resume();
                }
            } else {
                togglePause();
            }
        });
    }

    public void setSimulationView(SimulationView s){
        simulationView = s;

    }

    private void togglePause(){
        if (isPaused) resume();
        else pause();
    }

    private void pause(){
        isPaused = true;
        timer.cancel();
    }

    private void resume(){
        isPaused = false;
        startTimer();
    }

}
