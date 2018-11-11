package Entities;

public class Relation {
    private int amountOfInputRelations;
    private int amountOfOutputRelations;

    public Relation() {
    }

    public Relation(int amountOfInputRelations, int amountOfOutputRelations) {
        this.amountOfInputRelations = amountOfInputRelations;
        this.amountOfOutputRelations = amountOfOutputRelations;
    }

    public int getAmountOfInputRelations() {
        return amountOfInputRelations;
    }

    public void setAmountOfInputRelations(int amountOfInputRelations) {
        this.amountOfInputRelations = amountOfInputRelations;
    }

    public int getAmountOfOutputRelations() {
        return amountOfOutputRelations;
    }

    public void setAmountOfOutputRelations(int amountOfOutputRelations) {
        this.amountOfOutputRelations = amountOfOutputRelations;
    }
}
