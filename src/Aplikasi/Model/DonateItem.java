package Aplikasi.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DonateItem{
private final SimpleStringProperty foodItem;
    private final SimpleStringProperty date;
    private final SimpleIntegerProperty amount;
    private final SimpleStringProperty pickupLocation;

    public DonateItem(String foodItem, String date, int amount, String pickupLocation) {
        this.foodItem = new SimpleStringProperty(foodItem);
        this.date = new SimpleStringProperty(date);
        this.amount = new SimpleIntegerProperty(amount);
        this.pickupLocation = new SimpleStringProperty(pickupLocation);
    }

    public String getFoodItem() {
        return foodItem.get();
    }

    public void setFoodItem(String foodItem) {
        this.foodItem.set(foodItem);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public int getAmount() {
        return amount.get();
    }

    public void setAmount(int amount) {
        this.amount.set(amount);
    }

    public String getPickupLocation() {
        return pickupLocation.get();
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation.set(pickupLocation);
    }
}