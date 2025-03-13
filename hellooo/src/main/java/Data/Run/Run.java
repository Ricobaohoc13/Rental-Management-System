package Data.Run;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Run extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file from the resources folder using an absolute path.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/WelcomeScreen.fxml"));
        Parent root = loader.load();

        // Set up the scene with the loaded root node.
        Scene scene = new Scene(root);

        // Configure the primary stage.
        primaryStage.setTitle("Rental Management App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
