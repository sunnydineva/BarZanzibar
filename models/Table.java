package models;

public class Table {
    int tableNumber;
    boolean isOccupied;

    public Table(int tableNumber, boolean isOccupied) {
        this.tableNumber = tableNumber;
        this.isOccupied = isOccupied;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}
