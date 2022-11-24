package screens;

import frames.BarFrame;
import models.Language;
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
    private JTextField searchField;
    private JButton addButton;
    private JButton deleteButton;
    public JComboBox<String> typeComboBox;
    public DefaultTableModel usersTableModel;
    public JTable usersTable;
    public UsersPanel(BarFrame frame) {
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

        if (language == Language.BULGARIAN) {
            bulgarianLanguage();
        } else englishLanguage();


    }


    public void initializeFields() {
        nameField = new JTextField("Име");
        nameField.setBounds(0, 230, elementWidth, 40);
        add(nameField);

        pinField = new JTextField("ПИН");
        pinField.setBounds(0, nameField.getY() + 50, elementWidth, 40);
        add(pinField);

        phoneField = new JTextField("Телефон");
        phoneField.setBounds(0, pinField.getY() + 50, elementWidth, 40);
        add(phoneField);

        String[] options = {"Мениджър", "Сервитьор"};
        typeComboBox = new JComboBox<>(options);
        typeComboBox.setBounds(0, phoneField.getY() + 50, elementWidth, 40);
        add(typeComboBox);

    }

    public void initializeButtons() {
        deleteButton = new JButton("Изтрий"); //уволняваме
        deleteButton.setBounds(frame.getWidth() / 2 - elementWidth / 2, 230 + 50,
                elementWidth, 40);
        deleteButton.addActionListener(e -> frame.dataProvider.deleteAction(this, this, usersTableModel));
        add(deleteButton);

       addButton = new JButton("Добави");
        selectedType = null;
        addButton.setBounds(0, 230 + 160 + 40, elementWidth, 40);
        addButton.addActionListener(e -> {
            frame.dataProvider.adduserAction(this, usersTableModel);
        });
        add(addButton);

    }

    public void initializeSearchField() {
        searchField = new JTextField("Име");
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
        searchField.setBounds(frame.getWidth() / 2 - elementWidth / 2, 230, elementWidth, 40);
        add(searchField);
    }

    public void bulgarianLanguage(){

/* така или иначе при отваряне ми излиза на BULGARIAN
        nameField.setText("Име");
        phoneField.setText("Телефон");
        pinField.setText("ПИН");
        searchField.setText("Търсено име");

        addButton.setText("Добави");
        deleteButton.setText("Изтрий");
*/
    }
    public void englishLanguage(){
/* незнайно защо ми разваля fetchUsers - не ми пълни при стартиране таблицата ??????????????????????????
        nameField.setText("Name");
        phoneField.setText("Phone");
        pinField.setText("PIN");
        searchField.setText("Searched name");

 */

        addButton.setText("Add");
        deleteButton.setText("Remove");
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

    @Override
    public String toString() {
        return "UsersPanel";
    }
}


