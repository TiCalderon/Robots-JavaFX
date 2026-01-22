package vista;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX App
 */
public class Robots extends Application {

    @Override
    public void start(Stage stageActual) throws IOException {
        VistaMenu vistaMenu = new VistaMenu(stageActual);
    }

    public static void main(String[] args) {
        launch();
    }
}