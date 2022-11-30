package screens;

import frames.BarFrame;
import models.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
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
    JButton plusButton;
    JButton minusButton;
    JButton tablesButton;
    JButton discountButton;
    public String table;
    public String createOrderMessage;
    public String createOrderSelectErrorMessage;
    public String createOrderFinishErrorMessage;
    public String plusMinusOrderErrorMessage;
    public String plusMinusProductErrorMessage;
    public String discountMessage;
    public String discountErrorMessage;
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
        selectFirstRowOrderTable();


        buttonX = frame.getWidth() / 2 - elementWidth / 2;
        buttonY = 100;

    }

    /* Create, Settle, Table, Waitress */
    public void initializeHeader() {
        createButton = new JButton("Създай");
        createButton.setBounds(0, 15, elementWidth, 40);
        createButton.addActionListener(e -> {
            createOrderAction();
            int currentlyCreatedRow = frame.dataProvider.orders.size() - 1;  // да сменя!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            ordersTable.setRowSelectionInterval(currentlyCreatedRow, currentlyCreatedRow);  // да сменя!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        });

        add(createButton);

        finishButton = new JButton("Приключи");
        finishButton.setBounds(frame.getWidth() - elementWidth, 15, elementWidth, 40);
        finishButton.addActionListener(e -> finishAction());
        add(finishButton);

        waitressLabel = new JLabel(frame.dataProvider.loggedUser.getName());
        waitressLabel.setBounds(frame.getWidth() / 2 - 100, 20, 200, 30);
        waitressLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        waitressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(waitressLabel);

        String tableText = table + selectedTableNumber;
        tableLabel = new JLabel(tableText);
        tableLabel.setBounds(frame.getWidth() / 2 - 60, 50, 120, 30);
        tableLabel.setFont(new Font("Helvetica", Font.BOLD, 24));
        tableLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(tableLabel);
    }

    public void initializeOrdersTable() {
        String[] cols = {"Поръчка", "Продукти", "Цена"};
        ordersTableModel = new DefaultTableModel();
        ordersTableModel.setColumnIdentifiers(cols);
        ordersTable = new JTable(ordersTableModel);
        ordersTable.getSelectionModel().addListSelectionListener(e -> showProductsForOrder());
        tableCellRenderer(ordersTable);
        JScrollPane ordersPane = new JScrollPane(ordersTable);
        ordersPane.setBounds(0, 65, elementWidth, frame.getHeight() - 65 - 100 - 10);
        add(ordersPane);
        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
    }

    public void initializeProductsTable() {
        String[] cols = {"Продукт", "Количество", "Цена"};
        productsTableModel = new DefaultTableModel();
        productsTableModel.setColumnIdentifiers(cols);

        productsTable = new JTable(productsTableModel);
        tableCellRenderer(productsTable);
        JScrollPane productsPane = new JScrollPane(productsTable);
        productsPane.setBounds(frame.getWidth() - elementWidth, 65, elementWidth - 15,
                frame.getHeight() - 65 - 100 - 10);
        add(productsPane);

    }

    public void tableCellRenderer(JTable table) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    }

    /* initialize buttons for every unique category product */
    public void initializeCategoryButtons() {
        int buttonX = frame.getWidth() / 2 - elementWidth / 2;
        int buttonY = 100;
        categoryButtons = new ArrayList<>();
        for (Category category : frame.dataProvider.getCategories()) {
            JButton button = new JButton(category.title);
            button.addActionListener(e -> {
                selectedCategory = category.type;
                initializeSubProductsButtons(category.type);
            });
            button.setBounds(buttonX, buttonY, elementWidth, 40);
            add(button);
            categoryButtons.add(button);
            buttonY += 40;
        }
        repaint();
    }

    /* initialize buttons for every unique product subtype */
    public void initializeSubProductsButtons(ProductType type) {
        clearButtons(categoryButtons);
        int buttonX = frame.getWidth() / 2 - elementWidth / 2;
        int buttonY = 100;
        subTypeButtons = new ArrayList<>();
        for (String subCategory : frame.dataProvider.getSubCategories(type)) {
            JButton button = new JButton(subCategory);
            button.addActionListener(e -> {
                clearButtons(subTypeButtons);
                initializeProductsButtons(subCategory);
            });
            button.setBounds(buttonX, buttonY, elementWidth, 40);
            add(button);
            subTypeButtons.add(button);
            buttonY += 40;
        }
        backButton = new JButton("Назад");
        backButton.setBounds(buttonX, buttonY, elementWidth, 40);
        backButton.addActionListener(e -> backAction(subTypeButtons));
        add(backButton);
        subTypeButtons.add(backButton);
        repaint();
    }

    /* initialize buttons for every unique product brand */
    public void initializeProductsButtons(String selectedSubType) {
        int buttonX = frame.getWidth() / 2 - elementWidth / 2;
        int buttonY = 100;
        productButtons = new ArrayList<>();
        for (String productBrand : frame.dataProvider.getProductsBrands(selectedSubType)) {
            JButton button = new JButton(productBrand);
            button.addActionListener(e -> addProductAction(productBrand));
            button.setBounds(buttonX, buttonY, elementWidth, 40);
            add(button);
            productButtons.add(button);
            buttonY += 40;
        }
        backButton = new JButton("Назад");
        backButton.setBounds(buttonX, buttonY, elementWidth, 40);
        backButton.addActionListener(e -> backAction(productButtons));
        add(backButton);
        productButtons.add(backButton);

        repaint();

    }

    /* removes buttons for category/subtype/brand */
    public void clearButtons(ArrayList<JButton> activeButtonsList) {
        if (activeButtonsList != null) {
            for (JButton button : activeButtonsList) {
                remove(button);
            }
            repaint();
        }
    }


    /* deletes the current array with buttons and goes to the superior */
    public void backAction(ArrayList<JButton> activeButtList) {
        if (activeButtList == subTypeButtons) {
            clearButtons(activeButtList);

            initializeCategoryButtons();
            repaint();
        } else if (activeButtList == productButtons) {
            clearButtons(activeButtList);
            initializeSubProductsButtons(selectedCategory);
            repaint();
        }
    }

    /* buttons Plus, Minus, Discount, Back to tables */
    public void initializeFooter() {

        tablesButton = new JButton("Маси");
        tablesButton.setBounds(0, frame.getHeight() - 100,
                elementWidth, 40);
        tablesButton.addActionListener(e -> frame.router.showTables());
        add(tablesButton);

        plusButton = new JButton("+");
        plusButton.setBounds(frame.getWidth() - elementWidth, frame.getHeight() - 100,
                elementWidth / 3, 40);
        plusButton.addActionListener(e -> plusAction());
        add(plusButton);

        minusButton = new JButton("-");
        minusButton.setBounds(plusButton.getX() + elementWidth / 3, frame.getHeight() - 100,
                elementWidth / 3, 40);
        minusButton.addActionListener(e -> minusAction());
        add(minusButton);

        discountButton = new JButton("Отстъпка");
        discountButton.setBounds(plusButton.getX() + 2 * elementWidth / 3, frame.getHeight() - 100,
                elementWidth / 3 - 15, 40);
        discountButton.addActionListener(e -> discountAction());
        add(discountButton);
    }

    public void createOrderAction() {
        boolean isYes = showQuestion(createOrderMessage);
        if (isYes) {

            if (frame.dataProvider.orders.size() > 0) {
                for (Order order : frame.dataProvider.orders) {
                    if (order.getTableNumber() == selectedTableNumber
                            && (order.getTotalPriceDouble(false)) == 0) {
                        showError(createOrderFinishErrorMessage);
                        return;
//                    } else {
//                        frame.dataProvider.createOrderAction(selectedTableNumber, ordersTableModel);
//                        initializeCategoryButtons();
//                    }

                    }
                }
            }

            frame.dataProvider.createOrderAction(selectedTableNumber, ordersTableModel);
            initializeCategoryButtons();

        }

    }


    public void finishAction() {

    }

    public void plusAction() {
        if (ordersTable.getSelectedRow() < 0) {
            showError(plusMinusOrderErrorMessage);
            return;
        } else if (productsTable.getSelectedRow() < 0) {
            showError(plusMinusProductErrorMessage);
            return;
        }

        Order order = frame.dataProvider.orders.get(ordersTable.getSelectedRow());
        Product prd = order.getProducts().get(productsTable.getSelectedRow());
        int currentlySelectedOrderRow = ordersTable.getSelectedRow();
        int currentlySelectedProductRow = productsTable.getSelectedRow();

        prd.setQuantity(prd.getQuantity() + 1);

//        frame.dataProvider.fetchProducts(productsTableModel, order);
//        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
//        ordersTable.setRowSelectionInterval(currentlySelectedOrderRow, currentlySelectedOrderRow);

        fetchTablesAndSelectOrder(order, currentlySelectedOrderRow);
        productsTable.setRowSelectionInterval(currentlySelectedProductRow, currentlySelectedProductRow);
    }

    public void minusAction() {
        if (ordersTable.getSelectedRow() < 0) {
            showError(plusMinusOrderErrorMessage);
            return;
        } else if (productsTable.getSelectedRow() < 0) {
            showError(plusMinusProductErrorMessage);
            return;
        }
        int currentlySelectedOrderRow = ordersTable.getSelectedRow();
        int currentlySelectedProductRow = productsTable.getSelectedRow();
        Order order = frame.dataProvider.orders.get(ordersTable.getSelectedRow());
        Product prd = order.getProducts().get(productsTable.getSelectedRow());

        if (prd.getQuantity() == 1) {
            order.getProducts().remove(productsTable.getSelectedRow());

            if (!(productsTable.getColumnCount() == 0)) {
                currentlySelectedProductRow -= 1;
            }

        } else {
            prd.setQuantity(prd.getQuantity() - 1);
        }

//        frame.dataProvider.fetchProducts(productsTableModel, order);
//        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
//        ordersTable.setRowSelectionInterval(currentlySelectedOrderRow, currentlySelectedOrderRow);

        fetchTablesAndSelectOrder(order, currentlySelectedOrderRow);

        if (!(productsTable.getRowCount() == 0)) {
                productsTable.setRowSelectionInterval(currentlySelectedProductRow, currentlySelectedProductRow);
        }
    }

/* makes discount for an order within min and max */
    public void discountAction() {
        Order order = frame.dataProvider.orders.get(ordersTable.getSelectedRow());
        int currentlySelectedRow = ordersTable.getSelectedRow();

        if (order.getPercentDiscount() > 0) { //if there is a discount
            showError(discountErrorMessage);
            return;
        }
        int discount = Integer.parseInt(JOptionPane.showInputDialog(discountMessage));

        if (discount > order.getMaxDiscount() || discount < order.getMinDiscount()) {
            showError(discountMessage);
            order.setPercentDiscount(discount); // test

        } else {
            order.setPercentDiscount(discount);
        }

        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
        ordersTable.setRowSelectionInterval(currentlySelectedRow, currentlySelectedRow);
    }

    public void addProductAction(String productBrand) {
        if (ordersTable.getSelectedRow() < 0) {
            showError(createOrderSelectErrorMessage);
            return;
        }
        int currentlySelectedOrderRow = ordersTable.getSelectedRow();
        Order order = frame.dataProvider.orders.get(ordersTable.getSelectedRow());
        boolean isFound = false;
        for (Product prd : order.getProducts()) {
            if (prd.getBrand().equals(productBrand)) {
                prd.setQuantity(prd.getQuantity() + 1);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            for (Product product : frame.dataProvider.products) {
                if (product.getBrand().equals(productBrand)) {
                    order.getProducts().add(product);
                }
            }
        }
//        frame.dataProvider.fetchProducts(productsTableModel, order);
//        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
//        ordersTable.setRowSelectionInterval(currentlySelectedOrderRow, currentlySelectedOrderRow);
          fetchTablesAndSelectOrder(order, currentlySelectedOrderRow);

    }

    public void selectFirstRowOrderTable(){
        if(frame.dataProvider.orders.size() > 0) {
            for (Order order : frame.dataProvider.orders) {
                if (order.getTableNumber() == selectedTableNumber) {
                    ordersTable.setRowSelectionInterval(0, 0);
                }
            }
        }
    }

    public void fetchTablesAndSelectOrder(Order order, int currentlySelectedOrderRow){
        frame.dataProvider.fetchProducts(productsTableModel, order);
        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
        ordersTable.setRowSelectionInterval(currentlySelectedOrderRow, currentlySelectedOrderRow);
    }

    public void showProductsForOrder() {
        if (ordersTable.getSelectedRow() > -1) {

            frame.dataProvider.fetchProducts(productsTableModel,
                        frame.dataProvider.orders.get(ordersTable.getSelectedRow()));
            }

        }
// да го оправя

    public void bulgarianLanguage() {
        table = "Маса: ";
        tableLabel.setText(table + selectedTableNumber);
        createButton.setText("Създай");
        finishButton.setText("Приключи");
        discountButton.setText("Отстъпка");
        tablesButton.setText("Маси");
        try {
            backButton.setText("Назад");
        } catch (Exception ignored) {
        }
        createOrderMessage = "Отваряне на нова поръчка?";
        createOrderSelectErrorMessage = "Моля селектирайте поръчка";
        createOrderFinishErrorMessage = "Моля довършете предходната поръчка";
        plusMinusProductErrorMessage = "Моля селектирайте продукт";
        plusMinusOrderErrorMessage = "Моля селектирайте поръчка";
        discountMessage = "Моля въведете процент на отстъпка между 1 и 50";
        discountErrorMessage = "Вече има приложена отстъпка.";

        String[] orderCols = {"Поръчка", "Продукти", "Цена"};
        ordersTableModel.setColumnIdentifiers(orderCols);
        String[] productCols = {"Продукт", "Количество", "Цена"};
        productsTableModel.setColumnIdentifiers(productCols);

    }

    public void englishLanguage() {
        table = "Table: ";
        tableLabel.setText(table + selectedTableNumber);
        createButton.setText("Create");
        finishButton.setText("Settle");
        discountButton.setText("Discount");
        tablesButton.setText("Tables");
        try {
            backButton.setText("Back");
        } catch (Exception ignored) {
        }
        createOrderMessage = "New order?";
        createOrderSelectErrorMessage = "Please select order";
        createOrderFinishErrorMessage = "Please finalize the previous order";
        plusMinusProductErrorMessage = "Please select product";
        plusMinusOrderErrorMessage = "Please select order";
        discountMessage = "Please enter discount percent between 1 and 50 %";
        discountErrorMessage = "Discount has been applied already.";

        String[] orderCols = {"Order", "Products", "Price"};
        ordersTableModel.setColumnIdentifiers(orderCols);
        String[] productCols = {"Product", "Quantity", "Price"};
        productsTableModel.setColumnIdentifiers(productCols);

        repaint();
    }

    @Override
    public String toString() {
        return "OrdersPanel";
    }
}
