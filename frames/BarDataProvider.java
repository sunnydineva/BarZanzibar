package frames;

import models.*;
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
    public ArrayList<Integer> tables;
    public List<Product> products;
    public ArrayList<Category> categories; //for JButtons
    public ArrayList<String> subCategories; //for JButtons
    public ArrayList<String> productBrands; //for JButtons
    public User loggedUser;
    public boolean isSearchingUsers;

    public BarDataProvider() {

        orders = new ArrayList<>();

        users = new ArrayList<>();
        User user1 = new User("Sunny", "9999", "0899044806", UserType.MANAGER);
        User user2 = new User("Bunny", "8888", "0899044807", UserType.WAITRESS);
        User user3 = new User("Funny", "7777", "0899044808", UserType.WAITRESS);
        users.add(user1);
        users.add(user2);
        users.add(user3);

        tables = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {  // 10 маси
            tables.add(i + 11);
        }

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
                break;
            }
        }
        return isUniquePIN;
    }

    public boolean isCorrectPinPattern(User newUser){
        Pattern pattern = Pattern.compile("\\d{4}");
        Matcher matcher = pattern.matcher(newUser.getPinCode());
        return matcher.matches();
    }

    public void fetchUsers(DefaultTableModel model) { //рефреш на таблицата; прави редове
        model.setRowCount(0); //занулява таблицата, иначе се наслояват една под друга
        ArrayList<User> activeUserList;
        if (isSearchingUsers) {
            activeUserList = new ArrayList<>(searchedUsers);
        } else {
            activeUserList = new ArrayList<>(users);
        }
        for (User user : activeUserList) {
            String[] row = new String[4]; // на реда имаме 4 стойности - име, пин, телефон, тип
            row[0] = user.getName();
            row[1] = user.getPinCode();
            row[2] = user.getPhoneNumber();
            row[3] = user.getUserRole(); //userType в String
            model.addRow(row);

        }
    }

    public void searchUsers(String searchedText) {
        searchedUsers = new ArrayList<>();
        for (User user : users) {
            if (user.getName().toLowerCase().contains(searchedText.toLowerCase())) {
                searchedUsers.add(user); //добавям в дублирания арей
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
        if (orders.size() > 0) {
            for (Order order : orders) {
                if (order.getTableNumber() == selectedTableNumber
                        && (order.getTotalPriceDouble(false)) == 0) {
                    System.out.println("Dovurshete predhodnata poruchka"); // ДА ПРОМЕНЯ !!!!!!!!!!!!!!!!!!!!!!
                    //basePanel.showError(createOrderFinishErrorMessage);  ДА ГО МЕСТЯ ЛИ ТОЗИ МЕТОД ИЛИ ДА СЕ ПРОБВАМ ДА ГО ДОСТЪПВАМ
                    return;
                }
            }
        }
        Order order = new Order("1", selectedTableNumber, loggedUser); //autoNumber not available at the moment
        orders.add(order);
        fetchOrders(ordersTableModel, selectedTableNumber);
    }

    public void adduserAction(UsersPanel adminPanel, DefaultTableModel usersTableModel, String uniquePinErrorMessage) {
        UserType userType = adminPanel.typeComboBox.getSelectedIndex() == 0 ? UserType.MANAGER :
                UserType.WAITRESS; //0-MANAGER, 1-WAITRESS

        User newUser = new User(adminPanel.getNameField().getText(), adminPanel.getPinField().getText(),
                adminPanel.getPhoneField().getText(), userType);

        if (isUniquePIN(newUser.getPinCode()) && isCorrectPinPattern(newUser)) {
            users.add(newUser);
            fetchUsers(usersTableModel);
        } else JOptionPane.showMessageDialog(null, uniquePinErrorMessage,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void deleteUserAction(BasePanel basePanel, UsersPanel adminPanel) {
        if (adminPanel.usersTable.getSelectedRow() < 0) {  //ако нямаме селектиран ред
            basePanel.showError("Нямате избран потебител");
            return;
        }
        boolean isYes = basePanel.showQuestion("Сигуни ли сте, че искате да изтриете този потебител?");
        if (isYes) {
            ArrayList<User> activeUserList;
            if (isSearchingUsers) {
                activeUserList = searchedUsers;
            } else {
                activeUserList = users;
            }

            User selectedUser = activeUserList.get(adminPanel.usersTable.getSelectedRow());
            if (selectedUser.getPhoneNumber().equals((loggedUser.getPhoneNumber()))) { // защото нямаме id
                basePanel.showError("Не може да изтриете текущия потебител");
                return;
            }
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                if (selectedUser.getPhoneNumber().equals(user.getPhoneNumber())) {
                    users.remove(user);
                }
            }
            isSearchingUsers = false;
            fetchUsers(adminPanel.usersTableModel);
        }
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
        Product p17 = new Product("4", ProductType.FOOD, "сандвичи", "картонен", 1, 1);//        products.add(p1);
        products = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17);
        return products;

    }

    public ArrayList<Category> getCategories() {
        categories = new ArrayList<>();
        Category c1 = new Category("Алкохоли", ProductType.ALCOHOLIC);
        Category c2 = new Category("Безалкохолни", ProductType.NONALCOHOLIC);
        Category c3 = new Category("Храни", ProductType.FOOD);
        categories.add(c1);
        categories.add(c2);
        categories.add(c3);
        return categories;
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


