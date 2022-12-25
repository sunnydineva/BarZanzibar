package screens;

import frames.BarFrame;
import models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class OrdersPanel extends BasePanel {
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
    public String finishOrderMessage;
    public String createOrderSelectErrorMessage;
    public String createOrderFinishErrorMessage;
    public String plusMinusOrderErrorMessage;
    public String plusMinusProductErrorMessage;
    public String discountMessage;
    public String discountErrorMessage;
    public ArrayList<JButton> categoryButtons;
    public ArrayList<JButton> subTypeButtons;
    public ArrayList<JButton> productButtons;
    public List categories;
    public ProductType selectedCategory;
    public int buttonX;
    public int buttonY;


    public OrdersPanel(BarFrame frame) {
        super(frame);

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
        createButton.addActionListener(e -> createOrderAction());
        add(createButton);

        finishButton = new JButton("Приключи");
        finishButton.setBounds(frame.getWidth() - elementWidth, 15, elementWidth - 15, 40);
        finishButton.addActionListener(e -> finishOrderAction());
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
        JScrollPane productsPane = new JScrollPane(productsTable);
        productsPane.setBounds(frame.getWidth() - elementWidth, 65, elementWidth - 15,
                frame.getHeight() - 65 - 100 - 10);
        add(productsPane);

    }

    /* initialize buttons for every unique category product */
    public void initializeCategoryButtons() {
        int buttonX = frame.getWidth() / 2 - elementWidth / 2;
        int buttonY = 100;
        categoryButtons = new ArrayList<>();
        for (Category category : frame.dataProvider.getCategories(frame.language)) {
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
        if (activeButtList == subTypeButtons) initializeCategoryButtons();
        else if (activeButtList == productButtons) initializeSubProductsButtons(selectedCategory);
        clearButtons(activeButtList);
        repaint();
    }

    /* buttons Plus, Minus, Discount, Back to tables */
    public void initializeFooter() {
        int footerY = frame.getHeight() - 100;

        tablesButton = new JButton("Маси");
        tablesButton.setBounds(0, footerY,
                elementWidth, 40);
        tablesButton.addActionListener(e -> frame.router.showTables());
        add(tablesButton);

        plusButton = new JButton("+");
        plusButton.setBounds(frame.getWidth() - elementWidth, footerY,
                elementWidth / 3, 40);
        plusButton.addActionListener(e -> plusAction());
        add(plusButton);

        minusButton = new JButton("-");
        minusButton.setBounds(plusButton.getX() + elementWidth / 3, footerY,
                elementWidth / 3, 40);
        minusButton.addActionListener(e -> minusAction());
        add(minusButton);

        discountButton = new JButton("Отстъпка");
        discountButton.setBounds(plusButton.getX() + 2 * elementWidth / 3, footerY,
                elementWidth / 3 - 15, 40);
        discountButton.addActionListener(e -> discountAction());
        add(discountButton);
    }

    public void createOrderAction() {
        boolean isYes = showQuestion(createOrderMessage); //asks for confirmation
        if (isYes) {
            if(!(frame.dataProvider.isFinishedPreviousOrder(selectedTableNumber))) { //checks is the previous order has products
                showError(createOrderFinishErrorMessage);
                return;
            }

            frame.dataProvider.createOrderAction(selectedTableNumber, ordersTableModel);

            selectLastCreatedRow(ordersTable);
            //Table currentTable = frame.dataProvider.tables.get(selectedTableNumber - 11); //the tables start from 10
            if (ordersTable.getRowCount() == 1) initializeCategoryButtons();  //if it is the first order for this table
        }
    }

    public void finishOrderAction() { //moves the order to table 0, because the lack of autonumber for order no.
        boolean isYes = showQuestion(finishOrderMessage);
        if (isYes) {
            int currentlySelectedRow = ordersTable.getSelectedRow();

            int selectedTblNumber = currentlySelectedOrder().getTableNumber() - 11; //to be removed
            currentlySelectedOrder().setTableNumber(0);//to be removed
            frame.dataProvider.setTableOccupied(selectedTblNumber, false); //to be removed
            frame.dataProvider.tables.get(selectedTblNumber).setOccupied(false);//to be removed

            //frame.dataProvider.finishOrder(currentlySelectedOrder()); //to be de-commented

            frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
            selectRow(ordersTable, upperRow(ordersTable, currentlySelectedRow));
        }
    }

    public void addProductAction(String productBrand) {
        if (isNoSelectedRow(ordersTable)) {
            showError(createOrderSelectErrorMessage);
            return;
        }
        boolean isFound = false;
        for (Product prd : currentlySelectedOrder().getOrderProducts()) {
            if (prd.getBrand().equals(productBrand)) {
                prd.setQuantity(prd.getQuantity() + 1);   //increase product quantity
                isFound = true;
                break;
            }
        }
        if (!isFound) currentlySelectedOrder().getOrderProducts().add(frame.dataProvider.newProduct(productBrand));

        fetchTablesAndSelectOrder(currentlySelectedOrder());
    }

    public void plusAction() {
        if (!isValidPlusMinus()) return;

        Product prd = currentlySelectedOrder().getOrderProducts().get(productsTable.getSelectedRow());

        int currentlySelectedProductRow = productsTable.getSelectedRow();

        prd.setQuantity(prd.getQuantity() + 1);
        if (prd.getQuantity() > 10 && prd.getQuantity() % 10 == 0) showError("Стига помпи плюса:P"); // my sweet joke

        fetchTablesAndSelectOrder(currentlySelectedOrder());
        selectRow(productsTable, currentlySelectedProductRow);
    }


    public void minusAction() {
        if (!isValidPlusMinus()) return;

        Product product = currentlySelectedProduct();
        int currentlySelectedRow = productsTable.getSelectedRow();

        if (isLastProduct(product)) {
            removeProduct(product);
            currentlySelectedRow = upperRow(productsTable, currentlySelectedRow);
        } else {
            product.setQuantity(product.getQuantity() - 1);
        }

        fetchTablesAndSelectOrder(currentlySelectedOrder());
        selectRow(productsTable, currentlySelectedRow);
    }


    public void removeProduct(Product product) {
        currentlySelectedOrder().getOrderProducts().remove(productsTable.getSelectedRow());
    }

    public boolean isLastProduct(Product product) {
        return (product.getQuantity() == 1);
    }

    public boolean isAnyRow(JTable table) {
        return table.getRowCount() != 0;
    }

    public boolean isNoSelectedRow(JTable table) {
        return (table.getSelectedRow() < 0);
    }

    public int upperRow(JTable table, int currentlySelectedRow) {
        if (isAnyRow(table)) {
            currentlySelectedRow -= 1;
        }
        return currentlySelectedRow;
    }

    public void selectRow(JTable table, int currentlySelectedRow) {
        if (isAnyRow(table)) {
            table.setRowSelectionInterval(currentlySelectedRow, currentlySelectedRow);
        }
    }

    /* makes discount for an order within min and max; Discount is not applied to productType CIGARETTES */
    public void discountAction() {
        if (isOrderDiscounted()) {
            showError(discountErrorMessage);
            return;
        }
        int discount = askDiscount();
        if (!isValidDiscount(discount)) {
            showError(discountMessage);
            discount = askDiscount();
        }
        currentlySelectedOrder().setPercentDiscount(discount);

        int currentlySelectedRow = ordersTable.getSelectedRow();
        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
        ordersTable.setRowSelectionInterval(currentlySelectedRow, currentlySelectedRow);
    }

    public boolean isOrderDiscounted() {
        return (currentlySelectedOrder().getPercentDiscount() > 0);  //if there is a discount
    }

    public boolean isValidDiscount(int discount) {
        return discount <= currentlySelectedOrder().getMaxDiscount() && discount >= currentlySelectedOrder().getMinDiscount();
    }

    public int askDiscount() {
        return Integer.parseInt(JOptionPane.showInputDialog(discountMessage));
    }

    public boolean isOneRow(JTable table) {
        return table.getRowCount() == 1;
    }

    public Product currentlySelectedProduct() {
        return currentlySelectedOrder().getOrderProducts().get(productsTable.getSelectedRow());
    }


    public Order currentlySelectedOrder() {
        int currentlySelectedOrderRow = ordersTable.getSelectedRow();
        int currentlySelectedOrder =
                Integer.parseInt((String) ordersTable.getModel().getValueAt(currentlySelectedOrderRow, 0)) - 1;
        return frame.dataProvider.orders.get(currentlySelectedOrder);
    }

    public int currentlySelectedOrderNumber() {
        int currentlySelectedOrderRow = ordersTable.getSelectedRow();
        return Integer.parseInt((String) ordersTable.getModel().getValueAt(currentlySelectedOrderRow, 0)) - 1;
    }

    public void selectFirstRowOrderTable() {
        if (frame.dataProvider.orders.size() > 0) {
            for (Order order : frame.dataProvider.orders) {
                if (order.getTableNumber() == selectedTableNumber) {
                    ordersTable.setRowSelectionInterval(0, 0);
                    initializeCategoryButtons();
                    break;
                }
            }
        }
    }

    public void selectLastCreatedRow(JTable table) {
        int currentlyCreatedTableRow = 0;
        if (table.getRowCount() != 0) currentlyCreatedTableRow = table.getRowCount() - 1;
        table.setRowSelectionInterval(currentlyCreatedTableRow, currentlyCreatedTableRow);
    }

    public void fetchTablesAndSelectOrder(Order order) {
        int currentlySelectedOrderRow = ordersTable.getSelectedRow();
        frame.dataProvider.fetchProducts(productsTableModel, order);
        frame.dataProvider.fetchOrders(ordersTableModel, selectedTableNumber);
        ordersTable.setRowSelectionInterval(currentlySelectedOrderRow, currentlySelectedOrderRow);
    }

    public void showProductsForOrder() {
        if (ordersTable.getSelectedRow() > -1) {
            frame.dataProvider.fetchProducts(productsTableModel,
                    currentlySelectedOrder());
        }
    }

    public boolean isValidPlusMinus() {
        //if (ordersTable.getSelectedRow() < 0) {
        if (isNoSelectedRow(ordersTable)) {
            showError(plusMinusOrderErrorMessage);
            return false;
            //} else if (productsTable.getSelectedRow() < 0) {
        } else if (isNoSelectedRow(productsTable)) {
            showError(plusMinusProductErrorMessage);
            return false;
        }
        return true;
    }

    public void tableCellRenderer(JTable table) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
    }

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
        finishOrderMessage = "Приключване на поръчка?";
        createOrderSelectErrorMessage = "Моля селектирайте поръчка";
        createOrderFinishErrorMessage = "Моля довършете предходната поръчка";
        plusMinusProductErrorMessage = "Моля селектирайте продукт";
        plusMinusOrderErrorMessage = "Моля селектирайте поръчка";
        discountMessage = "Моля въведете процент на отстъпка между 1 и 50. За цигари не се ползва отстъпка";
        discountErrorMessage = "Вече има приложена отстъпка.";

        String[] orderCols = {"Поръчка", "Продукти", "Цена"};
        ordersTableModel.setColumnIdentifiers(orderCols);
        String[] productCols = {"Продукт", "Количество", "Цена"};
        productsTableModel.setColumnIdentifiers(productCols);
        tableCellRenderer(ordersTable);
        tableCellRenderer(productsTable);

    }

    public void englishLanguage() {
        table = "Table: ";
        tableLabel.setText(table + selectedTableNumber);
        createButton.setText("Create");
        finishButton.setText("Settle");
        discountButton.setText("Discount");
        tablesButton.setText("Tables");
        try {backButton.setText("Back");} catch (Exception ignored) {}
        createOrderMessage = "New order?";
        finishOrderMessage = "Settle the order?";
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
        tableCellRenderer(ordersTable);
        tableCellRenderer(productsTable);

        repaint();
    }

    @Override
    public String toString() {
        return "OrdersPanel";
    }
}
