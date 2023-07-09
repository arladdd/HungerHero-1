package Aplikasi.Model;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MyData {
    private StringProperty pickUp;
    private StringProperty foodItem;
    private StringProperty date;
    private StringProperty unit;
    private StringProperty amount;
    private int id;

    public MyData(String foodItem, String date, String unit, String amount) {
    this.foodItem = new SimpleStringProperty(foodItem);
    this.date = new SimpleStringProperty(date);
    this.unit = new SimpleStringProperty(unit);
    this.amount = new SimpleStringProperty(amount);
}

    public String getFoodItem() {
        return foodItem.get();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem.set(foodItem);
    }

    public StringProperty foodItemProperty() {
        return foodItem;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }

    public StringProperty amountProperty() {
    return amount;
}

public String getAmount() {
    return amount.get();
}

public StringProperty unitProperty() {
    return unit;
}

public String getUnit() {
    return unit.get();
}

public String getPickUp() {
    return pickUp.get();
}

public void setPickUp(String pickUp) {
    this.pickUp.set(pickUp);
}

public StringProperty pickUpProperty() {
    return pickUp;
}

}