package com.company;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

        primaryStage.setTitle("Cypher app");

        Label insertKey = new Label("Insert key here:");
        Label insertText = new Label("Insert text here:");
        Label insertedKey = new Label("Inserted key:");
        Label result = new Label("Result:");
        Label encrypt = new Label("Characters encription:");
        Label decrypt = new Label("Encription to characters:");
        Label enc = new Label(" ");
        Label dec = new Label(" ");
        Label KEY = new Label("");
        TextField keyTextField = new TextField();
        TextField textField = new TextField();
        TextField res = new TextField();
        keyTextField.setText("ADME");
        textField.setText("SupeRTestowanko");
        Button gen = new Button("Generate table");
        Button encr = new Button("Encryption");
        Button decr = new Button("Decryption");

        //Button generating tables
        gen.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                cypher.loadEncryptValues();
                enc.setText(cypher.returnEncryptHashMap());
                enc.setWrapText(true);
                cypher.loadDecryptValues();
                dec.setText(cypher.returnDecryptHashMap());
                dec.setWrapText(true);
                KEY.setText(keyTextField.getText());
                if(KEY.getText().length()%2 != 0){
                    res.setText("Blad! Wprowadz klucz o parzystej ilosci znakow!");
                } else cypher.getKey(KEY.getText());
            }
        });

        //Button encrypting text
        encr.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                cypher.determineArrayRows(textField.getText());
                cypher.encryptData();
            }
        });

        //Button decrypting text
        decr.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                cypher.determineArrayRows(textField.getText());
            }
        });
        HBox buttons = new HBox(gen, encr, decr );
        buttons.setSpacing(5);
        HBox key = new HBox(insertKey, keyTextField);
        key.setSpacing(5);
        HBox text = new HBox(insertText, textField);
        text.setSpacing(5);
        HBox results = new HBox(result, res);
        HBox ki = new HBox(insertedKey, KEY);
        VBox tableData = new VBox(encrypt, enc, decrypt, dec);
        VBox programData = new VBox(key,text, ki, results);
        programData.setSpacing(5);
        VBox container = new VBox(programData, tableData,buttons);
        container.setStyle("-fx-background-color: #abc; -fx-padding: 15px; -fx-font-size: 14;");
        enc.setStyle("-fx-font-size: 12;");
        dec.setStyle("-fx-font-size: 12;");
        buttons.setStyle("-fx-padding-top: 10; -fx-margin-top: 10;");
        Scene scene = new Scene(container, 800, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
