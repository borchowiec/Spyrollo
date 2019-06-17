package mainView;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

class Liquid {
    private String name;
    private String color;
    private IntegerProperty amount;
    private IntegerProperty percent;


    public Liquid() {
        this("", 0, 0, "");
    }

    public Liquid(String name, int amount, int percent, String color) {
        this.name = name;
        this.amount = new SimpleIntegerProperty(amount);
        this.percent = new SimpleIntegerProperty(percent);
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public int getPercent() {
        return percent.get();
    }

    public IntegerProperty percentProperty() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent.set(percent);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
