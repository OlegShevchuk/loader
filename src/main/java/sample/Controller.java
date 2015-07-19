package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Controller {
    @FXML
    private Button button;
    @FXML
    private Button button1;
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;

    private Stage stageApp;

    public Stage getStageApp() {
        return stageApp;
    }

    public void setStageApp(Stage stageApp) {
        this.stageApp = stageApp;
    }

    public void initialize() {

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader fxmlLoader=new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("/scroll.fxml"));
                try {

                    Parent root = fxmlLoader.load();
                    VBox vBox=(VBox)((ScrollPane)root).getContent();
                    Stage stage=new Stage();
                    stage.setScene(new Scene(root));
                    stage.setX(stageApp.getX() + stageApp.getWidth());
                    stage.setY(stageApp.getY());
                    stage.show();

                    Loader loader=new Loader(textField1.getText(), textField2.getText(),vBox);
                    loader.readPage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        button1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                DirectoryChooser fileChooser = new DirectoryChooser();
                fileChooser.setTitle("Выбор папки");
                if (!textField2.getText().equals(""))fileChooser.setInitialDirectory(new File(textField2.getText()));

                File file=fileChooser.showDialog(null);
                if (file!=null)textField2.setText(file.getAbsolutePath());
            }
        });
    }



}
