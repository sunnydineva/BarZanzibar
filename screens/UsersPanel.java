package screens;

import frames.BarFrame;
import jdk.swing.interop.SwingInterOpUtils;
import models.Language;
import models.UserType;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UsersPanel extends BasePanel implements MouseListener {

    UserType selectedType;
    private JTextField nameField;
    private JTextField pinField;
    private JTextField phoneField;
    private JLabel nameLabel;
    private JLabel pinLabel;
    private JLabel phoneLabel;
    private JTextField searchField;
    private JButton addButton;
    private JButton deleteButton;
    public JComboBox<String> typeComboBox;
    public DefaultTableModel usersTableModel;
    public JTable usersTable;
    String uniquePinErrorMessage;

    public UsersPanel(BarFrame frame) {
        super(frame);
        initializeUsersTable();
        initializeFieldsAndLabels();
        initializeButtons();
        initializeSearchField();
        languageSwitch(language);
    }

    public void initializeUsersTable() {
        String[] columns = {"Име", "ПИН", "Телефон", "Тип"};
        usersTableModel = new DefaultTableModel();
        usersTableModel.setColumnIdentifiers(columns);

        usersTable = new JTable(usersTableModel);

        JScrollPane usersTablePane = new JScrollPane(usersTable);
        usersTablePane.setBounds(0, 0, frame.getWidth(), 200);
        add(usersTablePane);

        frame.dataProvider.fetchUsers(usersTableModel);
    }


    public void initializeFieldsAndLabels() {
        nameLabel = new JLabel("Име");
        nameLabel.setBounds(0, 230, elementWidth / 2, 40);
        add(nameLabel);

        nameField = new JTextField("Име");
        nameField.setBounds(nameLabel.getX() + nameLabel.getWidth(), 230, elementWidth / 2, 40);
        nameField.addMouseListener(this);
        add(nameField);

        pinField = new JTextField("ПИН");
        pinField.setBounds(0, nameField.getY() + 50, elementWidth, 40);
        pinField.addMouseListener(this);
        add(pinField);

        phoneField = new JTextField("Телефон");
        phoneField.setBounds(0, pinField.getY() + 50, elementWidth, 40);
        phoneField.addMouseListener(this);
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
        deleteButton.addActionListener(e -> frame.dataProvider.deleteUserAction(this, this));
        add(deleteButton);

        addButton = new JButton("Добави");
        selectedType = null;
        addButton.setBounds(0, 230 + 160 + 40, elementWidth, 40);
        addButton.addActionListener(e ->
                frame.dataProvider.adduserAction(this, usersTableModel, uniquePinErrorMessage));
        add(addButton);
    }

    public void initializeSearchField() {
        searchField = new JTextField("Търсено име");
        frame.dataProvider.isSearchingUsers = false;
        searchField.addMouseListener(this);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                frame.dataProvider.isSearchingUsers = (!searchField.getText().equalsIgnoreCase("Търсено име"))
                        && (!searchField.getText().equalsIgnoreCase("Searched name"));
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
            public void changedUpdate(DocumentEvent e) {

            }
        });
        searchField.setBounds(frame.getWidth() / 2 - elementWidth / 2, 230, elementWidth, 40);
        add(searchField);
    }

    public void bulgarianLanguage() {
        nameField.setText("Име");
        phoneField.setText("Телефон");
        pinField.setText("ПИН");
        searchField.setText("Търсено име");
        String[] columns = {"Име", "ПИН", "Телефон", "Тип"};
        usersTableModel.setColumnIdentifiers(columns);
        addButton.setText("Добави");
        deleteButton.setText("Изтрий");
        uniquePinErrorMessage = "Невалиден ПИН номер! Изберете нов";
        repaint();
    }

    public void englishLanguage() {
        nameField.setText("Name");
        phoneField.setText("Phone");
        pinField.setText("PIN");
        searchField.setText("Searched name");
        addButton.setText("Add");
        deleteButton.setText("Remove");
        String[] columns = {"Name", "PIN", "Phone", "Level"};
        usersTableModel.setColumnIdentifiers(columns);
        uniquePinErrorMessage = "Invalid PIN number. Please choose another combination";
        repaint();
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

    @Override
    public void mouseClicked(MouseEvent e) {
        if (((JTextField) e.getSource()).getText().equals("Име") ||
                ((JTextField) e.getSource()).getText().equals("Name") ||
                ((JTextField) e.getSource()).getText().equals("ПИН") ||
                ((JTextField) e.getSource()).getText().equals("PIN") ||
                ((JTextField) e.getSource()).getText().equals("Телефон") ||
                ((JTextField) e.getSource()).getText().equals("Phone") ||
                ((JTextField) e.getSource()).getText().equals("Търсено име") ||
                ((JTextField) e.getSource()).getText().equals("Searched name"))

            ((JTextField) e.getSource()).setText("");
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}


