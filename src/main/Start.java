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

import java.util.Map;
import java.util.Set;

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

        btn.setOnAction(e -> {
            rankOfTheMatrix = Integer.valueOf(textField.getText());
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

            createLabelsForOutputMatrix(grid);

            createLabelsForOutputGroup(grid);

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

    private void createLabelsForOutputMatrix(GridPane grid) {
        Label label;
        Map<Integer, Integer[]> matrix = ((Lab1) lab1).getMatrix();
        Integer[] values;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < matrix.size(); ++i) {
            values = matrix.get(i);
            for (Integer s: values){
                sb.append(s);
                sb.append(" ");
            }

            label = new Label(String.valueOf("String of matrix " + (i + 1) + ": " + sb.toString()));
            sb = new StringBuilder();
            grid.add(label, 3, 1 + i);
        }
    }

    @SuppressWarnings("unchecked")
    private void createLabelsForOutputGroup(GridPane grid) {
        Label label;
        Map<Integer, Set> group = ((Lab1) lab1).getGroups();
        Set<Integer> values;
        StringBuilder sb = new StringBuilder();
        int i = 0;

        for (Integer value: group.keySet()) {

            values = (Set<Integer>) group.get(value);
            for (Integer s : values) {
                sb.append(s + 1);
                sb.append(" ");
            }

            label = new Label(String.valueOf("Group " + (i + 1) + ": " + sb.toString()));
            sb = new StringBuilder();
            grid.add(label,  4, 1 + i);
            ++i;
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

