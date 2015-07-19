package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage stageApp;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stageApp=primaryStage;
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/sample.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("LoaderEX");
        primaryStage.setScene(new Scene(root));
        Controller controller=fxmlLoader.getController();
        controller.setStageApp(stageApp);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
