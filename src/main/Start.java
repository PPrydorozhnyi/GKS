package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static javafx.geometry.HPos.RIGHT;

public class Start extends Application {
    private Stage primaryS;

    private Group root;

    private TextField textField;

    private int rankOfTheMatrix;

    @Override
    public void start(Stage primaryStage) {

        primaryS = primaryStage;

        primaryStage.setTitle("lab");
        primaryStage.setHeight(850);
        primaryStage.setWidth(1200);
        primaryStage.setResizable(true);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_RIGHT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        makeLabels(grid);
        makeTextFields(grid);
        makeButtons(grid);

        drawScene(grid);

    }

    private void drawScene(GridPane grid) {

        root = new Group();

        // example how to add children to group
        //root.getChildren().add(background);


        HBox hBox = new HBox(10, root, grid);

        primaryS.setScene(new Scene(hBox, 800, 600));
        primaryS.show();

    }

    private void makeLabels(GridPane grid) {
        Label label = new Label("Rank of the matrix:");
        grid.add(label, 0, 1);

    }

    private void makeTextFields(GridPane grid) {

        textField = new TextField();
        grid.add(textField, 1, 1);

    }

    private void makeButtons(GridPane grid) {

        Button btn = new Button("Accept");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 6);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 0, 7);
        GridPane.setColumnSpan(actiontarget, 2);
        GridPane.setHalignment(actiontarget, RIGHT);
        actiontarget.setId("actiontarget");

        btn.setOnAction(e -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Processing");
            rankOfTheMatrix = Integer.valueOf(textField.getText());
            System.out.println(rankOfTheMatrix);
        });

    }


    public static void main(String[] args) {

        launch(args);
    }
}

