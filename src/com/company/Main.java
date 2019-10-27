package com.company;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application {
    public boolean progress = false;
    public String readData, writeData, keyData, readFileName, writeFileName;

    public static void main(String[] args) {
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

        primaryStage.setTitle("ADFGVX Cypher app");


        Label insertKey = new Label("Insert key here:");
        Label insertText = new Label("Insert text here:");
        Label insertedKey = new Label("Inserted key:");
        Label result = new Label("Result:");
        Label encrypt = new Label("Characters encription:");
        Label decrypt = new Label("Encription to characters:");
        CheckBox checkBox = new CheckBox("Read from file?");
        TextField read = new TextField();
        TextField write = new TextField();
        Label enc = new Label(" ");
        Label dec = new Label(" ");
        Label KEY = new Label("");
        TextField keyTextField = new TextField();
        keyTextField.setPrefWidth(100);
        TextField textField = new TextField();
        textField.setPrefWidth(250);
        TextField res = new TextField();
        res.setPrefWidth(550);
        keyTextField.setText("ADME");
        textField.setText("SupeRTestowanko");
        read.setText("read.txt");
        write.setText("write.txt");
        Button gen = new Button("Generate table");
        Button encr = new Button("Encryption");
        Button decr = new Button("Decryption");
        Label error = new Label("OK");
        error.setStyle("-fx-font-size: 20; -fx-color: #ff3333; -fx-font-decoration: strong;");
        //Button generating tables
        gen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                cypher.loadEncryptValues();
                enc.setText(cypher.returnEncryptHashMap());
                enc.setWrapText(true);
                cypher.loadDecryptValues();
                dec.setText(cypher.returnDecryptHashMap());
                dec.setWrapText(true);
                if (keyTextField.getText().length() % 2 != 0) {
                    error.setText("Blad! Wprowadz klucz o parzystej ilosci znakow!");
                    progress = false;
                } else {
                    error.setText("KEY OK!");
                    KEY.setText(keyTextField.getText());
                    cypher.getKey(keyTextField.getText());
                    progress = true;

                }
            }
        });

        //Button encrypting text
        encr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (progress) {
                    if (checkBox.isSelected()) {
                        readFileName = read.getText();
                        writeFileName = write.getText();
                        readData = file_to_string(readFileName);
                        //System.out.println(readData);
                        File writeFile = new File(writeFileName);
                        cypher.determineArrayRows(readData, true);
                        res.setText(cypher.encryptData());
                        try {
                            FileWriter fr = new FileWriter(writeFile, false);
                            fr.write(cypher.encryptData());
                            fr.close();
                        } catch (Exception g) {
                            g.printStackTrace();
                        }
                    } else {
                        cypher.determineArrayRows(textField.getText(), true);
                        writeData = cypher.encryptData();
                        res.setText(writeData);
                    }
                }

            }
        });

        //Button decrypting text
        decr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (progress) {
                    if (checkBox.isSelected()) {
                        readFileName = read.getText();
                        writeFileName = write.getText();
                        readData = file_to_string(readFileName);
                        File writeFile = new File(writeFileName);
                        cypher.determineArrayRows(readData, false);
                        res.setText(cypher.decryptData());
                        try {
                            FileWriter fr = new FileWriter(writeFile, false);
                            fr.write(cypher.decryptData());
                            fr.close();
                        } catch (Exception g) {
                            g.printStackTrace();
                        }
                    } else {
                        cypher.determineArrayRows(textField.getText(), false);
                        res.setText(cypher.decryptData());
                    }
                }

            }
        });
//        GridPane stepOne = new GridPane();
//        for (int x = 0; x < cypher.stepOne.length; x++){
//            for (int y = 0; y < cypher.stepOne[x].length; y++){
//                Label label = new Label(Character.toString(cypher.stepOne[x][y]));
//                stepOne.add(label, x, y);
//            }
//        }
//        GridPane stepTwo = new GridPane();
//        for (int x = 0; x < cypher.stepTwo.length; x++){
//            for (int y = 0; y < cypher.stepTwo[x].length; y++){
//                Label label = new Label(Character.toString(cypher.stepTwo[x][y]));
//                stepOne.add(label, x, y);
//            }
//        }
//        GridPane stepThree = new GridPane();
//        for (int x = 0; x < cypher.stepThree.length; x++){
//            for (int y = 0; y < cypher.stepThree[x].length; y++){
//                Label label = new Label(Character.toString(cypher.stepThree[x][y]));
//                stepOne.add(label, x, y);
//            }
//        }
        HBox buttons = new HBox(gen, encr, decr);
        HBox write_read = new HBox(checkBox, write, read);
        buttons.setSpacing(5);
        HBox key = new HBox(insertKey, keyTextField, error);
        //HBox steps = new HBox (stepOne, stepTwo, stepThree);
        key.setSpacing(5);
        HBox text = new HBox(insertText, textField);
        text.setSpacing(5);
        HBox results = new HBox(result, res);
        HBox ki = new HBox(insertedKey, KEY);
        VBox tableData = new VBox(encrypt, enc, decrypt, dec);
        VBox programData = new VBox(key, text, ki, results);
        programData.setSpacing(5);
        VBox container = new VBox(programData, tableData, write_read, buttons);
        container.setStyle("-fx-background-color: #abc; -fx-padding: 15px; -fx-font-size: 14;");
        enc.setStyle("-fx-font-size: 12;");
        dec.setStyle("-fx-font-size: 12;");
        buttons.setStyle("-fx-padding-top: 10; -fx-margin-top: 10;");
        Scene scene = new Scene(container, 800, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static String file_to_string(String fileName) {
        String data="";
        try {
            data = new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}