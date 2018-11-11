package Entities;

public class Relation<T> {
    private T amountOfInputRelations;
    private T amountOfOutputRelations;

    public Relation() {
    }

    public Relation(T amountOfInputRelations, T amountOfOutputRelations) {
        this.amountOfInputRelations = amountOfInputRelations;
        this.amountOfOutputRelations = amountOfOutputRelations;
    }

    public T getAmountOfInputRelations() {
        return amountOfInputRelations;
    }

    public void setAmountOfInputRelations(T amountOfInputRelations) {
        this.amountOfInputRelations = amountOfInputRelations;
    }

    public T getAmountOfOutputRelations() {
        return amountOfOutputRelations;
    }

    public void setAmountOfOutputRelations(T amountOfOutputRelations) {
        this.amountOfOutputRelations = amountOfOutputRelations;
    }
}
