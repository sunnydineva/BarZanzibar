package screens;

import frames.BarFrame;
import models.Language;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrdersPanel extends BasePanel {
    public int selectedTableNumber;
    public JLabel tableLabel;
    public JTable ordersTable;
    public DefaultTableModel ordersTableModel;
    public JTable productsTable;
    public DefaultTableModel productsTableModel;
    JButton createButton;
    JButton finishButton;

    public String table;


    public OrdersPanel(BarFrame frame, int selectedTableNumber) {
        super(frame);
        this.selectedTableNumber = selectedTableNumber;

        initializeHeader();
        initalizeOrdersTable();
        initalizeProductsTable();

        if (language == Language.BULGARIAN) {
            bulgarianLanguage();
        } else englishLanguage();

    }

    public void initializeHeader() {
        createButton = new JButton();
        createButton.setBounds(0, 15, elementWidth, 40);
        add(createButton);

        finishButton = new JButton();
        finishButton.setBounds(frame.getWidth() -elementWidth, 15, elementWidth, 40);
        add(finishButton);

        String tableText = table + selectedTableNumber;
        tableLabel = new JLabel(tableText);
        tableLabel.setBounds(frame.getWidth() / 2 - 50, 30, 100, 30);
        tableLabel.setFont(new Font("Helvetica", Font.BOLD, 26));
        tableLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(tableLabel);
    }

    public void initalizeOrdersTable(){
        String cols[] = {"Поръчка", "Продукти", "Цена"};
        ordersTableModel = new DefaultTableModel();
        ordersTableModel.setColumnIdentifiers(cols);

        ordersTable = new JTable(ordersTableModel);
        JScrollPane ordersPane  = new JScrollPane(ordersTable);
        ordersPane.setBounds(0, 60, frame.getWidth()/3,  frame.getHeight()-150);
        add(ordersPane);
    }

    public void initalizeProductsTable(){
        String[] cols = {"Продукт", "Количество", "Цена"};
        productsTableModel = new DefaultTableModel();
        productsTableModel.setColumnIdentifiers(cols);

        productsTable = new JTable(productsTableModel);
        JScrollPane productsPane  = new JScrollPane(productsTable);
        productsPane.setBounds(frame.getWidth() - elementWidth, 60, elementWidth,  frame.getHeight()-150);
        add(productsPane);

    }


    public void bulgarianLanguage() {
        table = "Маса ";
        createButton.setText("Създай");
        finishButton.setText("Приключи");


    }

    public void englishLanguage() {
        table = "Table ";
        createButton.setText("Create");
        finishButton.setText("Settle");
    }

    @Override
    public String toString() {
        return "OrdersPanel";
    }
}
