package screens;

import frames.BarFrame;
import models.Category;
import models.Language;
import models.Product;
import models.ProductType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrdersPanel extends BasePanel {
    public int selectedTableNumber;
    public JLabel tableLabel;
    public JLabel waitressLabel;
    public JTable ordersTable;
    public JTable productsTable;
    public DefaultTableModel ordersTableModel;
    public DefaultTableModel productsTableModel;
    JButton createButton;
    JButton finishButton;
    JButton backButton;
    public String table;
    public String createOrderMessage;
    public ArrayList<JButton> categoryButtons;
    public ArrayList<JButton> subTypeButtons;
    public ArrayList<JButton> productButtons;

    public ProductType selectedCategory;
    public int buttonX;
    public int buttonY;


    public OrdersPanel(BarFrame frame, int selectedTableNumber) {
        super(frame);
        this.selectedTableNumber = selectedTableNumber;

        initializeHeader();
        initializeOrdersTable();
        initializeProductsTable();
        initializeFooter();

        if (language == Language.BULGARIAN) {
            bulgarianLanguage();
        } else englishLanguage();

        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);

        buttonX = frame.getWidth() / 2 - elementWidth / 2;
        buttonY = 100;

    }

    public void initializeHeader() {
        createButton = new JButton("Създай");
        createButton.setBounds(0, 15, elementWidth, 40);
        createButton.addActionListener(e -> createOrder());
        add(createButton);

        finishButton = new JButton("Приключи");
        finishButton.setBounds(frame.getWidth() - elementWidth, 15, elementWidth, 40);
        add(finishButton);

        waitressLabel = new JLabel(frame.dataProvider.loggedUser.getName());
        waitressLabel.setBounds(frame.getWidth() / 2 - 50, 10, 120, 30);
        waitressLabel.setFont(new Font("Helvetica", Font.BOLD, 26));
        waitressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(waitressLabel);

        String tableText = table + selectedTableNumber;
        tableLabel = new JLabel(tableText);
        tableLabel.setBounds(frame.getWidth() / 2 - 50, 50, 120, 30);
        tableLabel.setFont(new Font("Helvetica", Font.BOLD, 26));
        tableLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(tableLabel);
    }

    public void initializeOrdersTable() {
        String cols[] = {"Поръчка", "Продукти", "Цена"};
        ordersTableModel = new DefaultTableModel();
        ordersTableModel.setColumnIdentifiers(cols);

        ordersTable = new JTable(ordersTableModel);

        JScrollPane ordersPane = new JScrollPane(ordersTable);
        ordersPane.setBounds(0, 60, elementWidth, frame.getHeight() - 60 - 100 - 10);
        add(ordersPane);
        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);

    }

    public void initializeCategoryButtons() {  //product.productType
        int buttonX = frame.getWidth() / 2 - elementWidth / 2;
        int buttonY = 100;
        categoryButtons = new ArrayList<>();
        for (Category category : frame.dataProvider.getCategories()) {
            JButton button = new JButton(category.title);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        for (Product product : frame.dataProvider.products) {
                        if (product.getType() == category.type) {
                            initializeSubProductsButtons(product.getType());
                        }
                    }
                }
            });
            button.setBounds(buttonX, buttonY, elementWidth, 40);
            add(button);
            categoryButtons.add(button);
            buttonY += 40;
        }
        repaint();
    }

    public void initializeSubProductsButtons(ProductType type) { //product.subType

        clearButtons(categoryButtons);

        int buttonX = frame.getWidth() / 2 - elementWidth / 2;
        int buttonY = 100;
        subTypeButtons = new ArrayList<>();
        for (String subCategory : frame.dataProvider.getSubCategories(type)) {
            JButton button = new JButton(subCategory);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    initializeProductsButtons(subCategory);
                }
            });

                    button.setBounds(buttonX, buttonY, elementWidth, 40);
            add(button);
            subTypeButtons.add(button);
            buttonY += 40;
        }
        backButton = new JButton("Назад");
        backButton.setBounds(buttonX, buttonY, elementWidth, 40);
        backButton.addActionListener(e -> backButtonAction(subTypeButtons));
        add(backButton);
        subTypeButtons.add(backButton);

        repaint();

    }

    public void initializeProductsButtons(String selectedSubType) {
        System.out.println("Clear must happen here");
        clearButtons(subTypeButtons);

        int buttonX = frame.getWidth() / 2 - elementWidth / 2;
        int buttonY = 400;
        productButtons = new ArrayList<>();

        for (String productBrand : frame.dataProvider.getProductsBrands(selectedSubType)) {

            JButton button = new JButton(productBrand);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("add product to order");
                }
            });
            button.setBounds(buttonX, buttonY, elementWidth, 40);
            add(button);
            productButtons.add(button);
            buttonY += 40;
        }
        backButton = new JButton("Назад");
        backButton.setBounds(buttonX, buttonY, elementWidth, 40);
        backButton.addActionListener(e -> backButtonAction(productButtons));
        add(backButton);
        productButtons.add(backButton);

        repaint();

    }

    public void clearButtons(ArrayList<JButton> activeButtonsList) {
        if (activeButtonsList != null) {
            for (JButton button : activeButtonsList) {
                remove(button);
            }
            repaint();
        }
    }

    // НЕ МИ РАБОТИ CLEAR BUTTONS за BACK

    /* deletes the current array with buttons and goes to the superior */
    public void backButtonAction(ArrayList<JButton> activeButtList) {
        if (activeButtList == subTypeButtons) {
            clearButtons(activeButtList);
            System.out.println("opa sub");
            initializeCategoryButtons();
            repaint();
        } else if (activeButtList == productButtons) {
            clearButtons(activeButtList);
            System.out.println("opa pr");
            initializeSubProductsButtons(selectedCategory);
            repaint();
        }
    }


    public void initializeProductsTable() {
        String[] cols = {"Продукт", "Количество", "Цена"};
        productsTableModel = new DefaultTableModel();
        productsTableModel.setColumnIdentifiers(cols);

        productsTable = new JTable(productsTableModel);
        JScrollPane productsPane = new JScrollPane(productsTable);
        productsPane.setBounds(frame.getWidth() - elementWidth, 60, elementWidth,
                frame.getHeight() - 60 - 100 - 10);
        add(productsPane);

    }

    public void initializeFooter() {
        //BUTTON PLUS, MINUS, DISCOUNT, BACK TO TABLES
    }

    public void createOrder() {
        boolean isYes = showQuestion(createOrderMessage);
        if (isYes) {
            frame.dataProvider.createOrderAction(selectedTableNumber, ordersTableModel);
            // ДА СЕ СЕЛЕКТИРА
            initializeCategoryButtons();
            ;
        }
    }

    public void bulgarianLanguage() {
        table = "Маса: ";
        tableLabel.setText(table + selectedTableNumber);
        createButton.setText("Създай");
        finishButton.setText("Приключи");
        try {
            backButton.setText("Назад");
        } catch (Exception e) {
        }


        createOrderMessage = "Отваряне на нова поръчка?";

    }

    public void englishLanguage() {
        table = "Table: ";
        tableLabel.setText(table + selectedTableNumber);
        createButton.setText("Create");
        finishButton.setText("Settle");
        try {
            backButton.setText("Back");
        } catch (Exception e) {
        }

        createOrderMessage = "New order?";
    }

    @Override
    public String toString() {
        return "OrdersPanel";
    }
}
