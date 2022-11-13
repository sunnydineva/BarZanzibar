package models;

import java.util.ArrayList;

public class Order {
    private String uid;
    private int tableNumber;
    private ArrayList<Product> products;
    private User waitress;
    private int percentDiscount;

    public Order(String uid, int tableNumber, ArrayList<Product> products, User waitress) {
        this.uid = uid;
        this.tableNumber = tableNumber;
        this.products = products;
        this.waitress = waitress;
    }

    public String getTotalPrice(boolean withDiscount){
        double sum = 0;
        for (Product product: products) {
            sum += product.getPrice();
        }
        if(withDiscount) {
            double discount = sum * percentDiscount * 0.01;
            sum = sum - discount;
        }
        return Double.toString(sum);

    }

    public String getProductsCount(){
        int count = 0;
        for(Product product : products){
            count += product.getQuantity();
        }
        return Integer.toString(count);
    }



    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public User getWaitress() {
        return waitress;
    }

    public void setWaitress(User waitress) {
        this.waitress = waitress;
    }

    public int getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(int percentDiscount) {
        this.percentDiscount = percentDiscount;
    }
}
