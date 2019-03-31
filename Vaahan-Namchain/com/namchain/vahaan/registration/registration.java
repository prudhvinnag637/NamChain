package com.namchain.vahaan.registration;

import com.namchain.lib.net.HTTP;
import com.sun.codemodel.internal.JOp;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;


/**
 *
 */
public class registration extends Application {
    final String rto= "TN 40 ";
    String letter = "R";
    int runningno =4577;
    @Override
    public void start(Stage stage) {

        Label rtolbl = new Label("RTO");
        rtolbl.setStyle("-fx-background-color: #ADD8E6; -fx-font-weight: bold;");
        rtolbl.setFont(Font.font("Verdana", FontWeight.BOLD,15));
        Label rtonamelbl = new Label ("Code");
        rtonamelbl.setStyle("-fx-background-color: #ADD8E6; -fx-font-weight: bold;");
        rtonamelbl.setFont(Font.font("Verdana", FontWeight.BOLD,15));
        Label RTOlbl = new Label("T40");
        RTOlbl.setStyle("-fx-background-color: #ADD8E6;");
        RTOlbl.setFont(Font.font("Verdana", 15));
        Label RTOnamelbl = new Label ("MTP");
        RTOnamelbl.setStyle("-fx-background-color: #ADD8E6;");
        RTOnamelbl.setFont(Font.font("Verdana",15));

        Label UUIDLbl = new Label("Aadhar Number");
        Label VRegLbl = new Label("Vehicle Registration Number");
        Label ENoLbl = new Label("Engine Number");
        Label CNoLbl = new Label("Chasis Number");
        Label VmodelLbl = new Label("Vehicle Model");
        Label VTypeLbl = new Label("Vehicle Type");
        Label VSSLbl = new Label("Vehicle Service Center");

        TextField UUID = new TextField();
        TextField VReg = new TextField();
        TextField ENo = new TextField();
        TextField CNo = new TextField();
        TextField Vmodel = new TextField();
        TextField VType = new TextField();
        TextField VSS = new TextField();

        Button Authenticate = new Button ("Authenticate");
        Button assignno = new Button("Get next number");
        Button register = new Button("Register");

        GridPane gridPane = new GridPane();

        gridPane.setMinSize(400, 200);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(15);
        gridPane.setHgap(5);

        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(rtolbl,0,0);
        gridPane.add(RTOlbl,1,0);
        gridPane.add(rtonamelbl,2,0);
        gridPane.add(RTOnamelbl,3,0);


        gridPane.add(UUIDLbl, 0, 2);
        gridPane.add(Authenticate,2,2);
        gridPane.add(ENoLbl, 0, 3);
        gridPane.add(CNoLbl, 0, 4);
        gridPane.add(VmodelLbl, 0, 5);
        gridPane.add(VTypeLbl, 0, 6);
        gridPane.add(VSSLbl, 0, 7);
        gridPane.add(VRegLbl, 0, 8);
        gridPane.add(assignno,2,8);


        gridPane.add(UUID, 1, 2);
        gridPane.add(ENo, 1, 3);
        gridPane.add(CNo, 1, 4);
        gridPane.add(Vmodel, 1, 5);
        gridPane.add(VType, 1, 6);
        gridPane.add(VSS, 1, 7);
        gridPane.add(VReg, 1, 8);

        gridPane.add(register, 1, 12);
        Alert msg = new Alert(Alert.AlertType.INFORMATION);
        Alert error = new Alert(Alert.AlertType.WARNING);

        Authenticate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Authenticate pressed ");
                if(!UUID.getText().isEmpty() && UUID.getText().length()==14) {
                    msg.setContentText("Authentiction Successful");
                    msg.show();
                }
                else{
                    error.setContentText("Invalid Aadhar");
                    error.show();
                }

            }
        });

        assignno.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                runningno = runningno + 1;
                VReg.setText(rto+letter+" "+runningno);
            }
        });

        register.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {

                String response = HTTP.addblock(UUID.getText(),VReg.getText(),ENo.getText(),CNo.getText(),Vmodel.getText(),VType.getText(),VSS.getText());
                System.out.println("Response : "+ response);
            }

        });



        Scene scene = new Scene(gridPane, 650, 500);

        stage.setTitle("Vahaan-Namchain v1.0");

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String args[]){
        launch(args);
    }
} 