package main;

import impl.Lab1;
import interf.Processable;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Start extends Application {
    private Stage primaryS;

    private Group root;

    private TextField textField;

    private int rankOfTheMatrix;

    private TextField[] inputFields;
    private String[] inputStrings;

    private Processable lab1;

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
        Button lab1Button = new Button("Process lab 1");
        lab1Button.setDisable(true);

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 1);

//        final Text actiontarget = new Text();
//        grid.add(actiontarget, 0, 2);
//        GridPane.setColumnSpan(actiontarget, 2);
//        GridPane.setHalignment(actiontarget, RIGHT);
//        actiontarget.setId("actiontarget");

        btn.setOnAction(e -> {
//            actiontarget.setFill(Color.FIREBRICK);
            rankOfTheMatrix = Integer.valueOf(textField.getText());
//            actiontarget.setText("Rank of the matrix set to " + rankOfTheMatrix);
            createLabelsForInputMatrix(grid);
            createFieldsForInputMatrix(grid);
            lab1Button.setDisable(false);
        });


        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(lab1Button);
        grid.add(hbBtn1, 2, 1);

        lab1Button.setOnAction(e -> {
            lab1 = new Lab1(this);

            inputStrings = new String[rankOfTheMatrix];

            for (int i = 0; i < rankOfTheMatrix; ++i) {
                inputStrings[i] = inputFields[i].getText();
            }

            lab1.process();
        });

    }

    private void createFieldsForInputMatrix(GridPane grid) {
        inputFields = new TextField[rankOfTheMatrix];

        for (int i = 0; i < rankOfTheMatrix; ++i) {
            inputFields[i] = new TextField();
            grid.add(inputFields[i], 1, 2 + i);
        }
    }

    private void createLabelsForInputMatrix(GridPane grid) {
        Label label;

        for (int i = 1; i <= rankOfTheMatrix; ++i) {
            label = new Label(String.valueOf(i));
            grid.add(label, 0, 1 + i);
        }
    }

    public String[] getInputStrings() {
        return inputStrings;
    }

    public int getRankOfTheMatrix() {
        return  rankOfTheMatrix;
    }


    public static void main(String[] args) {

        launch(args);
    }
}

