package frames;

import models.*;
import screens.DisplayUserPanel;
import screens.UsersPanel;
import screens.BasePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BarDataProvider {
    public ArrayList<User> users;
    public ArrayList<User> searchedUsers;
    public ArrayList<Order> orders;
    public List<Table> tables;
    public static int tableNumberCorrective;
    public List<Product> products;
    public List<Category> categories; //for JButtons
    public ArrayList<String> subCategories; //for JButtons
    public ArrayList<String> productBrands; //for JButtons
    public User loggedUser;
    public boolean isSearchingUsers;
    public String uniquePinErrorMessage;
    public String pinPatternErrorMessage;
    public String selectUserTypeErrorMessage;
    public String currentUserErrorMessage;
    public String userNotSelectedErrorMessage;
    public String deleteUserConfirmationMessage;


    public BarDataProvider() {

        orders = new ArrayList<>();

        users = new ArrayList<>();
        User user1 = new User("Sunny", "9999", "0899044806", UserType.MANAGER);
        User user2 = new User("Bunny", "8888", "0899044807", UserType.WAITRESS);
        User user3 = new User("Funny", "7777", "0899044808", UserType.WAITRESS);
        users.add(user1);
        users.add(user2);
        users.add(user3);

        Table table1 = new Table(11, false);
        Table table2 = new Table(12, false);
        Table table3 = new Table(13, false);
        Table table4 = new Table(14, false);
        Table table5 = new Table(15, false);
        Table table6 = new Table(16, false);
        Table table7 = new Table(17, false);
        Table table8 = new Table(18, false);
        Table table9 = new Table(19, false);
        Table table10 = new Table(20, false);
        tables = Arrays.asList(table1, table2, table3, table4, table5, table6, table7, table8, table9, table10);

        tableNumberCorrective = 11; //for manipulating List<Table> tables / table numbers starts from 11

        /* old version only with tableNumber w/o table status
        tables = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {  // 10 маси
            tables.add(i + 11);
        }
         */

        getProducts();
    }

    public boolean isCorrectLogin(String pin) {
        for (User user : users) {
            if ((user.getPinCode().equals(pin))) {
                loggedUser = user;
                return true;
            }
        }
        return false;
    }

    public boolean isUniquePIN(String newPin) {
        boolean isUniquePIN = true;
        for (User user : users) {
            if (user.getPinCode().equals(newPin)) {
                isUniquePIN = false;
                showError(uniquePinErrorMessage);
                break;
            }
        }
        return isUniquePIN;
    }

    public boolean isCorrectPinPattern(String newPin) {
        Pattern pattern = Pattern.compile("\\d{4}");
        Matcher matcher = pattern.matcher(newPin);
        if (!matcher.matches()) {
            showError(pinPatternErrorMessage);
            return false;
        }
        return true;
    }

    public void fetchUsers(DefaultTableModel model, Boolean isPinShown) {
        model.setRowCount(0);
        ArrayList<User> activeUserList;
        if (isSearchingUsers) {
            activeUserList = new ArrayList<>(searchedUsers);
        } else {
            activeUserList = new ArrayList<>(users);
        }
        for (User user : activeUserList) {
            String[] row = new String[4];
            row[0] = user.getName();
            if (isPinShown) {
                row[1] = user.getPinCode();
            } else {
                row[1] = "****";
            }
            row[2] = user.getPhoneNumber();
            row[3] = user.getType().label;
            model.addRow(row);
        }
    }

    public void searchUsers(String searchedText) {
        searchedUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getName().toLowerCase().contains(searchedText.toLowerCase())) {
                searchedUsers.add(user);
            }
        }
    }

    public void fetchOrders(DefaultTableModel model, int tableNumber) {
        model.setRowCount(0);
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getTableNumber() == tableNumber) {
                String[] row = new String[3];
                row[0] = Integer.toString(i + 1);
                row[1] = order.getProductsCount();

                if (order.getPercentDiscount() > 0) {
                    row[2] = order.getTotalPrice(true) + " " + order.getPercentDiscount() + "%";
                } else {
                    row[2] = order.getTotalPrice(false);
                }
                model.addRow(row);
            }
        }
    }

    public void fetchProducts(DefaultTableModel model, Order order) {
        model.setRowCount(0);
        for (Product product : order.getOrderProducts()) {
            String[] row = new String[3];
            row[0] = product.getBrand();
            row[1] = Integer.toString(product.getQuantity());
            row[2] = product.getTotalPrice();
            model.addRow(row);
        }
    }

    public void createOrderAction(int selectedTableNumber, DefaultTableModel ordersTableModel) {
        Order order = new Order("1", selectedTableNumber, loggedUser); //autoNumber not available at the moment
        orders.add(order);
        tables.get(selectedTableNumber - tableNumberCorrective).setOccupied(true);
        fetchOrders(ordersTableModel, selectedTableNumber);
    }

    public void finishOrder(Order order) { //under construction - waits for autoNumber from server
        int selectedTableNumber = 0;

        if (orders.size() > 0) {
            for (Order ord : orders) {
                if (ord.getUid().equals(order.getUid())) {
                    selectedTableNumber = order.getTableNumber();
                    orders.remove(ord);
                    return;
                }
            }
        }
        tables.get(selectedTableNumber).setOccupied(false);
    }

    public boolean isLastOrderForTable(int tableNumber) { // по-добре да проверявам наличните редове в таблицата с ордъри
        int counterOrdersForTable = 0;
        for (Order order : orders) {
            if (order.getTableNumber() == (tableNumber)) {
                counterOrdersForTable++;
                if (counterOrdersForTable == 2) {
                    break;
                }
            }
        }
        return (counterOrdersForTable == 1);
    }

    public void vacatingTable(int selectedTableNumber) {
        if (isLastOrderForTable(selectedTableNumber)) {
            tables.get(selectedTableNumber - tableNumberCorrective).setOccupied(false);//to be removed //това работи..
        }
    }

    public boolean isFinishedPreviousOrder(int selectedTableNumber) {
        if (orders.size() > 0) {
            for (Order order : orders) {
                if (order.getTableNumber() == selectedTableNumber
                        && (order.getTotalPriceDouble(false)) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public Product newProduct(String productBrand) {
        Product product1 = null;
        for (Product product : products) {
            if (product.getBrand().equals(productBrand)) {
                product1 = new Product(product.getUid(), product.getType(),
                        product.getSubType(), product.getBrand(), product.getPrice(), product.getQuantity());
                break;
            }
        }
        return product1;
    }

    public void adduserAction(DisplayUserPanel panel, DefaultTableModel usersTableModel) {
        String newPin = panel.getPinField().getText();
        if (isUniquePIN(newPin) && isCorrectPinPattern(newPin)){
            UserType userType = userTypeFromComboBox(panel);
            User newUser = new User(panel.getNameField().getText(), panel.getPinField().getText(),
                    panel.getPhoneField().getText(), userType);
            if (userType != null) {
                users.add(newUser);
                panel.resetAction();
                fetchUsers(usersTableModel, isShownPin(usersTableModel));
            }
        }
    }

    public void editUserAction(DisplayUserPanel displayUserPanel, UsersPanel usersPanel, String uniquePinErrorMessage) {
        if (!isAnySelectedUser(usersPanel)) return;
        User userToEdit = selectedUser(usersPanel.usersTable);
        if (!displayUserPanel.getPinField().getText().equals(userToEdit.getPinCode())) {
            if (isUniquePIN(displayUserPanel.getPinField().getText())
                    && isCorrectPinPattern(displayUserPanel.getPinField().getText())
                    && userTypeFromComboBox(displayUserPanel) != null) {
                userToEdit.setName(displayUserPanel.getNameField().getText());
                userToEdit.setPinCode(displayUserPanel.getPinField().getText());
                userToEdit.setPhoneNumber(displayUserPanel.getPhoneField().getText());
                userToEdit.setType(userTypeFromComboBox(displayUserPanel));
                //MORE VALIDATIONS TO BE ADDED
                displayUserPanel.resetAction();
                fetchUsers(usersPanel.usersTableModel, isShownPin(usersPanel.usersTableModel));
            }
        }
    }

    public void deleteUserAction(BasePanel basePanel, UsersPanel usersPanel) {
        if (usersPanel.usersTable.getSelectedRow() < 0) {
            basePanel.showError(userNotSelectedErrorMessage);
            return;
        }
        boolean isYes = basePanel.showQuestion(deleteUserConfirmationMessage);
        if (isYes) {
            ArrayList<User> activeUserList;
            if (isSearchingUsers) activeUserList = searchedUsers;
            else activeUserList = users;

            User selectedUser = activeUserList.get(usersPanel.usersTable.getSelectedRow());
            if (selectedUser.getPhoneNumber().equals((loggedUser.getPhoneNumber()))) { // защото нямаме id
                basePanel.showError(currentUserErrorMessage);
                return;
            }
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (selectedUser.getPhoneNumber().equals(user.getPhoneNumber())) {
                    users.remove(user);
                }
            }
            isSearchingUsers = false;
            fetchUsers(usersPanel.usersTableModel, isShownPin(usersPanel.usersTableModel));
        }
    }

    public boolean isAnySelectedUser(UsersPanel usersPanel) {
        if (usersPanel.usersTable.getSelectedRow() > -1) {
            return true;
        } else {
            usersPanel.showError(usersPanel.noSelectedUserErrorMessage);
            return false;
        }
    }

    public User selectedUser(JTable usersTable) {
        return users.get(usersTable.getSelectedRow());
    }

    public UserType userTypeFromComboBox(DisplayUserPanel panel) {
        if (panel.typeComboBox.getSelectedIndex() == 0) {
            panel.showError(selectUserTypeErrorMessage);
        }
        return switch (panel.typeComboBox.getSelectedIndex()) {
            case 0 -> null;
            case 1 -> UserType.WAITRESS;
            case 2 -> UserType.MANAGER;
            default -> null;
        };
    }

    public int userTypeFromTable(JTable table) {
        int currentlySelectedUserRow = table.getSelectedRow();
        String currentlySelectedUserType =
                ((String) table.getModel().getValueAt(currentlySelectedUserRow, 3));
        UserType userType = UserType.MANAGER;
        return currentlySelectedUserType.equals(userType.label) ? 2 : 1; //2-MANAGER, 1-WAITRESS
    }

    public void displayUserInUserArea(JTable table, DisplayUserPanel displayUserPanel) {
        if (!(table.getSelectedRow() == -1)) {
            displayUserPanel.getDisplayUserLabel().setText(displayUserPanel.editUserLabelText);
            displayUserPanel.getNameField().setText((selectedUser(table).getName()));
            displayUserPanel.getPinField().setText((selectedUser(table).getPinCode()));
            displayUserPanel.getPhoneField().setText((selectedUser(table).getPhoneNumber()));
            displayUserPanel.getTypeComboBox().setSelectedIndex(userTypeFromTable(table));
        }
    }

    public boolean isShownPin(DefaultTableModel usersTableModel) {
        //try {return !(usersTable.getModel().getValueAt(0,1).equals("****"));
        try {
            return !(usersTableModel.getValueAt(0, 1).equals("****"));
        } catch (Exception ignored) {
        }
        return false;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public List<Product> getProducts() {
        Product p1 = new Product("1", ProductType.ALCOHOLIC, "уиски", "Johnie Walker", 6.47, 1);
        Product p2 = new Product("2", ProductType.ALCOHOLIC, "уиски", "Jack Daniels", 5.47, 1);
        Product p3 = new Product("3", ProductType.ALCOHOLIC, "уиски", "Tullamore Dew", 7.20, 1);
        Product p4 = new Product("4", ProductType.ALCOHOLIC, "уиски", "Dumple", 9.47, 1);
        Product p5 = new Product("4", ProductType.ALCOHOLIC, "водка", "Smirnoff", 6.50, 1);
        Product p6 = new Product("4", ProductType.ALCOHOLIC, "водка", "Akademika", 6.40, 1);
        Product p7 = new Product("4", ProductType.NONALCOHOLIC, "горещи напитки", "Coffe", 2, 1);
        Product p8 = new Product("4", ProductType.NONALCOHOLIC, "горещи напитки", "Tea", 2.5, 1);
        Product p9 = new Product("4", ProductType.NONALCOHOLIC, "сок", "Orange", 3, 1);
        Product p10 = new Product("4", ProductType.NONALCOHOLIC, "сок", "Peach", 3, 1);
        Product p11 = new Product("4", ProductType.NONALCOHOLIC, "газирани напитки", "Coca Cola", 6.60, 1);
        Product p12 = new Product("4", ProductType.NONALCOHOLIC, "газирани напитки", "Fanta", 6.60, 1);
        Product p13 = new Product("4", ProductType.FOOD, "ядки", "бадеми", 8, 1);
        Product p14 = new Product("4", ProductType.FOOD, "ядки", "фъстъци", 7, 1);
        Product p15 = new Product("4", ProductType.FOOD, "сандвичи", "вегетариански", 8, 1);
        Product p16 = new Product("4", ProductType.FOOD, "сандвичи", "занзибарски", 10, 1);
        Product p17 = new Product("4", ProductType.FOOD, "сандвичи", "картонен", 100, 1);//        products.add(p1);
        Product p18 = new Product("4", ProductType.CIGARETTES, "Davidoff", "white", 10, 1);//        products.add(p1);
        Product p19 = new Product("4", ProductType.CIGARETTES, "Marlboro", "red", 20, 1);//        products.add(p1);
        products = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19);
        return products;

    }

    public List<Category> getCategories(Language language) {
        if (language == Language.BULGARIAN) {
            Category c11 = new Category("Алкохоли", ProductType.ALCOHOLIC);
            Category c21 = new Category("Безалкохолни", ProductType.NONALCOHOLIC);
            Category c31 = new Category("Храни", ProductType.FOOD);
            Category c41 = new Category("Цигари", ProductType.CIGARETTES);
            return categories = Arrays.asList(c11, c21, c31, c41);
        } else if (language == Language.ENGLISH) {
            Category c12 = new Category("Alcohol", ProductType.ALCOHOLIC);
            Category c22 = new Category("Non alcoholic", ProductType.NONALCOHOLIC);
            Category c32 = new Category("Food", ProductType.FOOD);
            Category c42 = new Category("Cigarettes", ProductType.CIGARETTES);
            return categories = Arrays.asList(c12, c22, c32, c42);
        } else return null;
    }

    public ArrayList<String> getSubCategories(ProductType productCategory) {
        subCategories = new ArrayList<>();  //for all the subTypes: уиски, водка и т.н. колкото ще има
        ArrayList<String> subCategoriesTemp = new ArrayList<>();
        for (Product product : getProducts()) {
            if (product.getType() == productCategory) {
                subCategoriesTemp.add(product.getSubType());
            }
        }
        for (String subCategory : subCategoriesTemp) {
            if (!subCategories.contains(subCategory)) {
                subCategories.add(subCategory);
            }
        }
        return subCategories;
    }

    public ArrayList<String> getProductsBrands(String subType) {
        productBrands = new ArrayList<>();  //for all the product brands, no duplicates
        for (Product product : products) {
            ArrayList<String> subProductsTemp = new ArrayList<>();
            if (product.getSubType().equals(subType)) {
                subProductsTemp.add(product.getBrand());
            }
            for (String tempProducts : subProductsTemp) {
                if (!productBrands.contains(tempProducts)) {
                    productBrands.add(tempProducts);
                }
            }
        }
        return productBrands;
    }

}


