package models;

public class Product {
    private String uid;
    private ProductType type;
    private String subType;
    private String brand;
    private double price;
    private int quantity;

    public Product(String uid, ProductType type, String subType, String brand, double price, int quantity) {
        this.uid = uid;
        this.type = type;
        this.subType = subType;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTotalPrice(){
        return String.format("%.2f лв.", this.quantity * this.price);
    }

    public String getUid() {
        return uid;
    }

    public ProductType getType() {
        return type;
    }

    public String getSubType() {
        return subType;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String toString(){
        return brand + "-" +quantity;
    }
}
