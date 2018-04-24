package falling;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Controller {

    @FXML
    private Button startStopButton;

    @FXML
    private Pane canvas;

    @FXML
    private Circle ball;

    private Timeline timeline;
    private boolean isRunning = false;
    private double dx;
    private double dy;
    private double a = 1; // ускорение при падении
    private double k = 0.8; // коэффициент потерь при подъеме

    @FXML
    public void initialize() {
        timeline = new Timeline(new KeyFrame(Duration.millis(20),
                event -> {
                    if (!isRunning)
                        return;
                    ball.setLayoutX(ball.getLayoutX() + dx);
                    ball.setLayoutY(ball.getLayoutY() + dy);

                    if (ball.getLayoutX() >= canvas.getWidth())
                        startStopButton.fire();
                    if (ball.getLayoutY() >= (canvas.getHeight() - ball.getRadius())) {
                        ball.setLayoutY(canvas.getHeight() - ball.getRadius());
                        dy = - dy * k;
                        if (Math.abs(dy) < 1)
                            startStopButton.fire();
                    }

                    dy += a;
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        startStopButton.setOnAction(event -> {
            if (isRunning) {
                timeline.stop();
                startStopButton.setText("Start");
                isRunning = false;
            } else {
                startStopButton.setText("Stop");
                isRunning = true;
                initBall();
                timeline.play();
            }
        });
    }

    private void initBall() {
        ball.relocate(10, 35);
        dx = 5;
        dy = 0;
    }
}
