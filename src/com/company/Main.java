package com.company;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application
{
    public static void main(String[] args)
    {

        Application.launch(args);
        //cypher.getCodeAndFileName();
        //cypher.determineArrayRows();

        //long startTime = System.currentTimeMillis();

        //cypher.readAndEncryptData();
        //cypher.writeEncryptedFile();
        //cypher.readEncryptedData();
        //cypher.writeDecryptedData();

        //System.out.println("Encrypt/Decrypt Complete");
        //System.out.println("Running time (ms): " + (System.currentTimeMillis() - startTime));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ADFGVX cypher = new ADFGVX();

        primaryStage.setTitle("My First JavaFX App");

        Label insertKey = new Label("Insert key here:   ");
        Label encrypt = new Label("Hello World, JavaFX !");
        Label decrypt = new Label("Hello World, JavaFX !");
        Label KEY = new Label("Hello World, JavaFX !");
        TextField textField = new TextField();
        textField.setText("ADME");
        Button button = new Button("My Button");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                cypher.loadEncryptValues();
                encrypt.setText(cypher.returnEncryptHashMap());
                cypher.loadDecryptValues();
                decrypt.setText(cypher.returnDecryptHashMap());
                KEY.setText(textField.getText());
            }
        });

        HBox hbox = new HBox( textField, button);
        VBox vboxhbox = new VBox(insertKey, hbox);
        vboxhbox.setStyle("-fx-background-color: #abc; -fx-padding: 15px; -fg-margin: 10px; -fx-font-size: 20;");
        VBox vbox = new VBox(vboxhbox, KEY, encrypt, decrypt);
        Scene scene = new Scene(vbox, 800, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
