package Aplikasi.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Donation {
    private final StringProperty foodItem;
    private final IntegerProperty amount;

    public Donation(String foodItem, int amount2) {
        this.foodItem = new SimpleStringProperty(foodItem);
        this.amount = new SimpleIntegerProperty(amount2);
    }

    public String getFoodItem() {
        return foodItem.get();
    }

    public void setFoodItem(String foodItem) {
        this.foodItem.set(foodItem);
    }

    public StringProperty foodItemProperty() {
        return foodItem;
    }

    public int getAmount() {
        return amount.get();
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public IntegerProperty amountProperty() {
        return amount;
    }
}