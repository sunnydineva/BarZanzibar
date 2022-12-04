package models;

import java.util.ArrayList;

public class Order {
    private String uid;
    private int tableNumber;
    private ArrayList<Product> orderProducts;
    private User waitress;
    private int percentDiscount;
    private int maxDiscount = 50;
    private int minDiscount = 1;

    public Order(String uid, int tableNumber, User waitress) {
        this.uid = uid;
        this.tableNumber = tableNumber;
        this.orderProducts = new ArrayList<>();
        this.waitress = waitress;
    }

    public String getTotalPrice(boolean withDiscount){
        double sum = 0;
        for (Product product: orderProducts) {
            sum += product.getPrice() * product.getQuantity();
        }
        if(withDiscount) {
            double discount = sum * percentDiscount * 0.01;
            sum = sum - discount;
        }
        return String.format("%.2f лв.", sum);

    }

    public double getTotalPriceDouble(boolean withDiscount){
        double sum = 0;
        for(Product product : orderProducts){
            sum += product.getPrice() * product.getQuantity();
        }
        if(withDiscount) {
            double discount = sum * percentDiscount * 0.01;
            sum = sum - discount;
        }
        return sum;
    }

    public String getProductsCount(){
        int count = 0;
        for(Product product : orderProducts){
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

    public ArrayList<Product> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(ArrayList<Product> orderProducts) {
        this.orderProducts = orderProducts;
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

    public int getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(int maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public int getMinDiscount() {
        return minDiscount;
    }

    public void setMinDiscount(int minDiscount) {
        this.minDiscount = minDiscount;
    }

    public void setPercentDiscount(int percentDiscount) {
        this.percentDiscount = percentDiscount;
    }
}
