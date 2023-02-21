package com.example.wgufinalproject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** This is where the initial data is generated. */
public class App extends Application {
    /** This method is called after the program launches.
     Parts (of both types) are created and Products.
     They are added to inventory then the MainForm FXML is loaded.
     @param stage the stage for building/displaying initial window
     */
    @Override
    public void start(Stage stage) throws IOException {
        InHouse in1 = new InHouse(0, "Bearing", 12, 50, 1, 10, 1 );
        InHouse in2 = new InHouse(1, "Washer", 14, 10, 2, 3, 2 );
        InHouse in3 = new InHouse(2, "Bolt", 11, 8, 3, 8, 3 );
        Outsourced out1 = new Outsourced(3, "Nut", 15, 25, 5, 11, "ZZZ Industries" );
        Outsourced out2 = new Outsourced(4, "Gear", 10, 19, 0, 7, "GearHeads Inc." );

        Product product1 = new Product(10, "widget A", 293.23, 300, 1, 5);
        Product product2 = new Product(15, "widget B", 223.56, 126, 10, 35);
        Product product3 = new Product(33, "widget C", 3.36, 74, 7, 9);

        Inventory.addPart((in1));
        Inventory.addPart((in2));
        Inventory.addPart((in3));
        Inventory.addPart((out1));
        Inventory.addPart((out2));
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
    /** This is the main method App.java. This is the first method called when
     you run your Java program. The Javadoc folder is located in
     the parent WguFinalProject folder */
    public static void main(String[] args) {
        launch();
    }

}

