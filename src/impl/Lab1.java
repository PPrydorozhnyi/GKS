package impl;

import interf.Processable;
import main.Start;

import java.util.Arrays;

public class Lab1 implements Processable {

    private Start main;

    public Lab1(Start main) {
        this.main = main;
    }

    @Override
    public void process() {
        System.out.println("Processing lab1");
        System.out.println(Arrays.toString(main.getInputStrings()));
    }
}
