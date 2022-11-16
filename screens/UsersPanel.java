package screens;

import frames.BarFrame;
import models.UserType;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class UsersPanel extends BasePanel {

    UserType selectedType;
    private JTextField nameField;
    private JTextField pinField;
    private JTextField phoneField;
    public JComboBox<String> typeComboBox;
    public DefaultTableModel usersTableModel;
    public JTable usersTable;

    //public AdminPanel(BarFrame frame, Language language) {
    public UsersPanel(BarFrame frame) {
        //super(frame, language);
        super(frame);

        String[] columns = {"Име", "ПИН", "Телефон", "Тип"};
        usersTableModel = new DefaultTableModel();
        usersTableModel.setColumnIdentifiers(columns);

        usersTable = new JTable(usersTableModel);

        JScrollPane usersTablePane = new JScrollPane(usersTable);
        usersTablePane.setBounds(0, 0, frame.getWidth(), 200);
        add(usersTablePane);

        frame.dataProvider.fetchUsers(usersTableModel); //при отварянето да запълни таблицата

        initializeFields();
        initializeButtons();
        initializeSearchField();

    }
/*
    public void adduserAction() { // да отиде в Datarpvders
        //validations

        User newUser = new User(nameField.getText(), pinField.getText(), phoneField.getText(),selectedType);
        frame.dataProvider.users.add(newUser);
        frame.dataProvider.fetchUsers(usersTableModel);
    }

    public void deleteAction() { // да отиде в DataProviders
        if (usersTable.getSelectedRow() < 0) {
            showError("Нямате избран потебител");
            return; //нямаме селектиран ред
        }
        boolean isYes = showQuestion("Сигуни ли сте, че искате да изтриете този потебител?");

        User selectedUser = frame.dataProvider.users.get(usersTable.getSelectedRow());
        if (selectedUser.getPhoneNumber().equals((frame.dataProvider.loggedUser.getPhoneNumber()))) {
            showError("Не може да изтриете текущия потебител");
            return;
        }
        frame.dataProvider.users.remove(usersTable.getSelectedRow());
        frame.dataProvider.fetchUsers(usersTableModel);
    }

 */

    public void initializeButtons() {
        JButton deleteButton = new JButton("Изтрий"); //уволняваме
        deleteButton.setBounds(frame.getWidth() / 2 + 160, 230, 150, 40);
        deleteButton.addActionListener(e -> frame.dataProvider.deleteAction(this, this, usersTableModel));
        add(deleteButton);

        JButton exitButton = new JButton("Изход");
        exitButton.setBounds(frame.getWidth() / 2 + 160, deleteButton.getY() + 50, 150, 40);
      //  exitButton.addActionListener(e -> frame.router.showLogin();  ?????????????????
        add(exitButton);

        JButton addButton = new JButton("Добави");
        selectedType = null;
        addButton.setBounds(150 + 20, phoneField.getY() + 50, 150, 40);
        addButton.addActionListener(e -> {
            //      selectedType = typeComboBox.getSelectedIndex() == 0 ? UserType.MANAGER : UserType.WAITRESS; //0-MANAGER, 1-WAITRESS
            frame.dataProvider.adduserAction(this, usersTableModel);
        });
        add(addButton);
    }

    public void initializeFields() {
        nameField = new JTextField("Име");
        nameField.setBounds(8, 230, 150, 40);
        add(nameField);

        pinField = new JTextField("ПИН");
        pinField.setBounds(8, nameField.getY() + 50, 150, 40);
        add(pinField);

        phoneField = new JTextField("Телефон");
        phoneField.setBounds(8, pinField.getY() + 50, 150, 40);
        add(phoneField);

        String[] options = {"Мениджър", "Сервитьор"};
        typeComboBox = new JComboBox<>(options);
        typeComboBox.setBounds(8, phoneField.getY() + 50, 150, 40);
        add(typeComboBox);

    }

    public void initializeSearchField() {
        JTextField searchField = new JTextField("");
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                //викам метода за търсене:
                frame.dataProvider.isSearchingUsers = true;
                frame.dataProvider.searchUsers(searchField.getText());
                frame.dataProvider.fetchUsers(usersTableModel);

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (searchField.getText().isEmpty()) {
                    frame.dataProvider.isSearchingUsers = false;
                }
                frame.dataProvider.searchUsers(searchField.getText());
                frame.dataProvider.fetchUsers(usersTableModel);
            }


            @Override
            public void changedUpdate(DocumentEvent e) { //когато нещо се промени визуално по текстовото поле

            }
        });
        searchField.setBounds(frame.getWidth() / 2, 230, 150, 40);
        add(searchField);
    }

    public UserType getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(UserType selectedType) {
        this.selectedType = selectedType;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public void setNameField(JTextField nameField) {
        this.nameField = nameField;
    }

    public JTextField getPinField() {
        return pinField;
    }

    public void setPinField(JTextField pinField) {
        this.pinField = pinField;
    }

    public JTextField getPhoneField() {
        return phoneField;
    }

    public void setPhoneField(JTextField phoneField) {
        this.phoneField = phoneField;
    }

    public JComboBox<String> getTypeComboBox() {
        return typeComboBox;
    }

    public void setTypeComboBox(JComboBox<String> typeComboBox) {
        this.typeComboBox = typeComboBox;
    }

    public DefaultTableModel getUsersTableModel() {
        return usersTableModel;
    }

    public void setUsersTableModel(DefaultTableModel usersTableModel) {
        this.usersTableModel = usersTableModel;
    }

    public JTable getUsersTable() {
        return usersTable;
    }

    public void setUsersTable(JTable usersTable) {
        this.usersTable = usersTable;
    }
}


