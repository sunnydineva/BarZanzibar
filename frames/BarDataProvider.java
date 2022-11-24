package frames;

//ArrayLists, Data methods

import models.Order;
import models.Product;
import models.User;
import models.UserType;
import screens.LoginPanel;
import screens.UsersPanel;
import screens.BasePanel;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class BarDataProvider {

    public ArrayList<User> users;
    public ArrayList<User> searchedUsers;
    public ArrayList<Order> orders;

    public ArrayList<Integer> tables;
    public ArrayList<Product> products;

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
        tables.add(11);
        tables.add(12);
        tables.add(13);
        tables.add(14);
        tables.add(15);
        tables.add(16);
        tables.add(17);
        tables.add(18);
        tables.add(19);
        tables.add(20);

        // да създам арей-я за продукти
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
    public void fetchOrders(DefaultTableModel model, int tableNumber){
        model.setColumnCount(0);
        for (int i = 0; i <orders.size() ; i++) {
            Order order = orders.get(i);
            if (order.getTableNumber() == tableNumber){
                System.out.println("равни са");
                String row[] = new String[3];
                row[0] = Integer.toString(i+1);
                row[1] = order.getProductsCount();
                row[2] = order.getTotalPrice(false);
                model.addRow(row);
            }
        }

    }

    public void adduserAction(UsersPanel adminPanel, DefaultTableModel usersTableModel) {

        //validations
        UserType userType = adminPanel.typeComboBox.getSelectedIndex() == 0 ? UserType.MANAGER :
                UserType.WAITRESS; //0-MANAGER, 1-WAITRESS
        User newUser = new User(adminPanel.getNameField().getText(), adminPanel.getPinField().getText(),
                adminPanel.getPhoneField().getText(), userType);
        users.add(newUser);
        fetchUsers(usersTableModel);
    }


    public void deleteAction(BasePanel basePanel, UsersPanel adminPanel, DefaultTableModel usersTableModelBase) {

        // Търсенияте е нулевия индекс и така ще изтриема първия от арея,
        // а не селектирания!!!!!!
        // ако търсим ще трябва да вземем от арей листа SearchedUsers Петко -
        // влизаме в оригиналния арей и изтриваме Петко, isSearchingUser = false, fetch

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
}
