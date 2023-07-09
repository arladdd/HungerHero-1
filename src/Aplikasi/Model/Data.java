package Aplikasi.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Data {
    private SimpleIntegerProperty ammount;
    private SimpleStringProperty foodItem;

    public Data(String foodItem, int ammount){
        this.foodItem = new SimpleStringProperty(foodItem);
        this.ammount = new SimpleIntegerProperty(ammount);
    }

    public SimpleIntegerProperty getAmmount() {
        return ammount;
    }

    public SimpleStringProperty getFoodItem() {
        return foodItem;
    }
    
}
