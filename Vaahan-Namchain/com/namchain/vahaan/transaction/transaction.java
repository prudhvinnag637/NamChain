package com.namchain.vahaan.transaction;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * @author Ramaguru Radhakrishnan <br/>
 * Created : 26.03.2019 <br/>
 * Updated : 27.03.2019
 */

public class transaction extends Application {
    @Override
    public void start(Stage stage) {

        Label UUIDLbl = new Label("UUID");
        Label VRegLbl = new Label("VReg");
        Label ENoLbl = new Label("ENo");
        Label CNoLbl = new Label("CNo");
        Label VmodelLbl = new Label("VModel");
        Label VTypeLbl = new Label("VType");
        Label VSSLbl = new Label("VSS");

        TextField UUID = new TextField();
        TextField VReg = new TextField();
        TextField ENo = new TextField();
        TextField CNo = new TextField();
        TextField Vmodel = new TextField();
        TextField VType = new TextField();
        TextField VSS = new TextField();

        Button register = new Button("Register");

        GridPane gridPane = new GridPane();

        gridPane.setMinSize(400, 200);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(15);
        gridPane.setHgap(5);

        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(UUIDLbl, 0, 1);
        gridPane.add(VRegLbl, 0, 2);
        gridPane.add(ENoLbl, 0, 3);
        gridPane.add(CNoLbl, 0, 4);
        gridPane.add(VmodelLbl, 0, 5);
        gridPane.add(VTypeLbl, 0, 6);
        gridPane.add(VSSLbl, 0, 7);


        gridPane.add(UUID, 1, 1);
        gridPane.add(VReg, 1, 2);
        gridPane.add(ENo, 1, 3);
        gridPane.add(CNo, 1, 4);
        gridPane.add(Vmodel, 1, 5);
        gridPane.add(VType, 1, 6);
        gridPane.add(VSS, 1, 7);

        gridPane.add(register, 1, 11);

        register.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {


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