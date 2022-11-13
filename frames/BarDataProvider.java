package frames;

//ArrayLists, Data methods

import models.Product;
import models.User;
import models.UserType;

import java.util.ArrayList;

public class BarDataProvider {
    public ArrayList<User> users;

    public ArrayList<Integer> tables;
    public ArrayList<Product> products;

    public BarDataProvider() {
        users = new ArrayList<>();
        User user1 = new User("111", "Sunny", "9999", "0899044806", UserType.MANAGER);
        User user2 = new User("222", "Bunny", "8888", "0899044807", UserType.WAITRESS);
        User user3 = new User("333", "Funny", "7777", "0899044808", UserType.WAITRESS);
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
                return true;
            }
        }
        return false;
    }


}
