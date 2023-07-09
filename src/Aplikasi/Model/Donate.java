package Aplikasi.Model;

import java.lang.reflect.Constructor;

public class Donate {
    private String foodItem;
    private String date;
    private String amount;
    private String pickUp;
    private String portion;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Donate(String foodItem, String date, String amount, String pickUp, String portion){
        this.foodItem = foodItem;
        this.date = date;
        this.amount = amount;
        this.pickUp = pickUp;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
    
    public String getPickUp() {
        return pickUp;
    }
    public String getPortion() {
        return portion;
    }
}
