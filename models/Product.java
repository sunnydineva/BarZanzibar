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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
