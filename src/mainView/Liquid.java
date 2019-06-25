package mainView;

import com.google.gson.JsonObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class Liquid implements Cloneable {
    private StringProperty name;
    private String color;
    private IntegerProperty amount;
    private IntegerProperty percent;


    public Liquid() {
        this("", 0, 0, "");
    }

    public Liquid(String name, int amount, int percent, String color) {
        this.name = new SimpleStringProperty(name);
        this.amount = new SimpleIntegerProperty(amount);
        this.percent = new SimpleIntegerProperty(percent);
        this.color = color;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    @Override
    protected Liquid clone() {
        return new Liquid(name.getValue(), 100, percent.getValue(), color);
    }
}
