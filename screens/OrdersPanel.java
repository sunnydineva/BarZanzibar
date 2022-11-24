package screens;

import frames.BarFrame;
import models.Language;
import models.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrdersPanel extends BasePanel {
    public int selectedTableNumber;
    public JLabel tableLabel;
    public JLabel waitressLabel;
    public JTable ordersTable;
    public DefaultTableModel ordersTableModel;
    public JTable productsTable;
    public DefaultTableModel productsTableModel;
    JButton createButton;
    JButton finishButton;

    public String table;
    public String createOrderMessage;


    public OrdersPanel(BarFrame frame, int selectedTableNumber) {
        super(frame);
        this.selectedTableNumber = selectedTableNumber;

        initializeHeader();
        initializeOrdersTable();
        initializeProductsTable();
        initializeFooter();
/*
        if (language == Language.BULGARIAN) {
            bulgarianLanguage();
        } else englishLanguage();
        
 */




    }

    public void initializeHeader() {
        createButton = new JButton("Създай");
        createButton.setBounds(0, 15, elementWidth, 40);
        createButton.addActionListener(e -> createOrder());
        add(createButton);

        finishButton = new JButton("Приключи");
        finishButton.setBounds(frame.getWidth() -elementWidth, 15, elementWidth, 40);
        add(finishButton);

        waitressLabel = new JLabel(frame.dataProvider.loggedUser.getName());
        waitressLabel.setBounds(frame.getWidth() / 2 - 50, 10, 120, 30);
        waitressLabel.setFont(new Font("Helvetica", Font.BOLD, 26));
        waitressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(waitressLabel);

        String tableText = table + selectedTableNumber;
        tableLabel= new JLabel(tableText);
        tableLabel.setBounds(frame.getWidth() / 2 - 50, 50, 120, 30);
        tableLabel.setFont(new Font("Helvetica", Font.BOLD, 26));
        tableLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(tableLabel);
    }

    public void initializeOrdersTable(){
        String cols[] = {"Поръчка", "Продукти", "Цена"};
        ordersTableModel = new DefaultTableModel();
        ordersTableModel.setColumnIdentifiers(cols);

        ordersTable = new JTable(ordersTableModel);
        JScrollPane ordersPane  = new JScrollPane(ordersTable);
        ordersPane.setBounds(0, 60, elementWidth, frame.getHeight()- 60 - 100 - 10);
        add(ordersPane);
    }

    public void initializeProductsTable(){
        String[] cols = {"Продукт", "Количество", "Цена"};
        productsTableModel = new DefaultTableModel();
        productsTableModel.setColumnIdentifiers(cols);

        productsTable = new JTable(productsTableModel);
        JScrollPane productsPane  = new JScrollPane(productsTable);
        productsPane.setBounds(frame.getWidth()- elementWidth, 60, elementWidth,
                frame.getHeight()-60-100-10);
        add(productsPane);

    }

    public void initializeFooter(){
        //BUTTON PLUS, MINUS, DISCOUNT, BACK TO TABLES
    }

    public void createOrder(){
        boolean isYes = showQuestion(createOrderMessage);
        if(isYes){
            Order order = new Order("1", selectedTableNumber, frame.dataProvider.loggedUser);
            frame.dataProvider.orders.add(order);
            System.out.println("@");
            frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
        }
    }


    public void bulgarianLanguage() {
        table = "Маса: ";
        tableLabel.setText(table + selectedTableNumber);
        createButton.setText("Създай");
        finishButton.setText("Приключи");

        createOrderMessage = "Отваряне на нова поръчка?";


    }

    public void englishLanguage() {
        table = "Table: ";
        tableLabel.setText(table + selectedTableNumber);
        createButton.setText("Create");
        finishButton.setText("Settle");

        createOrderMessage = "New order?";
    }

    @Override
    public String toString() {
        return "OrdersPanel";
    }
}
