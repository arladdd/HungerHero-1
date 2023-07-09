package Aplikasi.Model;

public class HistoryModel {
    private String foodItem;
    private int amountElement;
    private String donateDate;
    private String location;

    public HistoryModel(String foodItem, int amountElement, String donateDate, String location) {
        this.foodItem = foodItem;
        this.amountElement = amountElement;
        this.donateDate = donateDate;
        this.location = location;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public int getAmountElement() {
        return amountElement;
    }

    public String getDonateDate() {
        return donateDate;
    }

    public String getLocation() {
        return location;
    }
}
