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
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UsersPanel extends BasePanel implements MouseListener {

    UserType selectedType;
//    private JTextField nameField;
//    private JTextField pinField;
//    private JTextField phoneField;
//    private JLabel nameLabel;
//    private JLabel phoneLabel;
//
//    private JLabel pinLabel;
//    private JLabel typeLabel;
//    private JLabel newUserLabel;
//    private JButton addButton;

    private JLabel userPanelLabel;
    private JButton deleteButton;
    private JButton editButton;
    private JButton showPinButton;
    public JComboBox<String> typeComboBox;
    public DefaultTableModel usersTableModel;
    public JTable usersTable;

    String uniquePinErrorMessage;

    public UsersPanel(BarFrame frame) {
        super(frame);
        initializeHeader();
        initializeUsersTable();
        initializeUserArea();
        initializeButtons();
        initializeSearchArea();
        languageSwitch(language);
    }

    public void initializeHeader(){
        userPanelLabel = new JLabel("Управление на потребителите");
        userPanelLabel.setBounds(0, 40, frame.getWidth(), 40);
        userPanelLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
        userPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(userPanelLabel);
    }

    public void initializeUsersTable() {
        String[] columns = {"Име", "ПИН", "Телефон", "Тип"};
        usersTableModel = new DefaultTableModel();
        usersTableModel.setColumnIdentifiers(columns);
        usersTable = new JTable(usersTableModel);
        JScrollPane usersTablePane = new JScrollPane(usersTable);
        //usersTablePane.setBounds(elementWidth, 90+50, elementWidth * 2 - 14, 200);
        usersTablePane.setBounds(elementWidth, frame.getHeight()/3-20, elementWidth * 2 - 14, 230);
        add(usersTablePane);
        frame.dataProvider.fetchUsers(usersTableModel);
    }

    public void initializeUserArea() {
        JPanel displayUser = new DisplayUser(frame, usersTableModel);
        displayUser.setBounds(5, frame.getHeight()/3-105,
                elementWidth -14, frame.getHeight()/3+80);
        add(displayUser);
        this.frame.validate();

//        newUserLabel = new JLabel("Добавяне на нов потребител:");
//        newUserLabel.setBounds(0, frame.getHeight()/3-90, elementWidth-10, 40);
//        newUserLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
//        newUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        add(newUserLabel);
//
//        nameField = new JTextField("Име");
//        //nameField.setBounds(phoneLabel.getX() + phoneLabel.getWidth(), 230, elementWidth * 0.8, 40);
//        nameField.setBounds(elementWidth/3-10, newUserLabel.getY()+50, elementWidth - elementWidth/3 , 40);
//        nameField.addMouseListener(this);
//        add(nameField);
//
//        pinField = new JTextField("ПИН");
//        pinField.setBounds(nameField.getX(), nameField.getY() + 50, nameField.getWidth(), 40);
//        pinField.addMouseListener(this);
//        add(pinField);
//
//        phoneField = new JTextField("Телефон");
//        phoneField.setBounds(nameField.getX(), pinField.getY() + 50, nameField.getWidth(), 40);
//        phoneField.addMouseListener(this);
//        add(phoneField);
//
//        String[] options = {"Мениджър", "Сервитьор"};
//        typeComboBox = new JComboBox<>(options);
//        typeComboBox.setBounds(nameField.getX(), phoneField.getY() + 50, nameField.getWidth(), 40);
//        add(typeComboBox);
//
//        nameLabel = new JLabel("Име:");
//        nameLabel.setBounds(0, nameField.getY(), elementWidth/3-10, 40);
//        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//        add(nameLabel);
//
//        pinLabel = new JLabel("ПИН:");
//        pinLabel.setBounds(0, pinField.getY(), nameLabel.getWidth(), 40);
//        pinLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//        add(pinLabel);
//
//        phoneLabel = new JLabel("Телефон:");
//        phoneLabel.setBounds(0, phoneField.getY(), nameLabel.getWidth(), 40);
//        phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//        add(phoneLabel);
//
//        typeLabel = new JLabel("Роля:");
//        typeLabel.setBounds(0, typeComboBox.getY(), nameLabel.getWidth(), 40);
//        typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
//        add(typeLabel);
//
//        addButton = new JButton("Добави");
//        selectedType = null;
//
//        addButton.setBounds(5, typeComboBox.getY()+50, elementWidth-15, 40);
//        addButton.addActionListener(e ->
//                frame.dataProvider.adduserAction(this, usersTableModel, uniquePinErrorMessage));
//        add(addButton);
    }

    public void initializeButtons() {
        showPinButton = new JButton("Покажи ПИН");
        //addButton.setBounds(0, 230 + 160 + 40, elementWidth, 40);
        showPinButton.setBounds(elementWidth, frame.getHeight()/3 + 220, elementWidth, 40);
        showPinButton.addActionListener(e -> System.out.println("userEdit activated")
        );
        add(showPinButton);

        editButton = new JButton("Редактирай");
        //addButton.setBounds(0, 230 + 160 + 40, elementWidth, 40);
        editButton.setBounds(elementWidth*2+10, showPinButton.getY(), elementWidth-25, 40);
        editButton.addActionListener(e -> System.out.println("userEdit activated")
                );
        add(editButton);

        deleteButton = new JButton("Изтрий");
        deleteButton.setBounds(elementWidth*2+10, editButton.getY() + 50,
                elementWidth-25, 40);
        deleteButton.addActionListener(e -> frame.dataProvider.deleteUserAction(this, this));
        add(deleteButton);
    }

    public void initializeSearchArea() {
        JPanel searchPanel = new SearchUser(frame, usersTableModel);
        searchPanel.setBounds(elementWidth, frame.getHeight()/3-105,
                elementWidth * 2 -14, frame.getHeight()/3/3);
        //this.frame.setContentPane(searchPanel);
        add(searchPanel);
        this.frame.validate();

//        searchField = new JTextField("Търсено име");
//        frame.dataProvider.isSearchingUsers = false;
//        searchField.addMouseListener(this);
//        searchField.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                frame.dataProvider.isSearchingUsers = (!searchField.getText().equalsIgnoreCase("Търсено име"))
//                        && (!searchField.getText().equalsIgnoreCase("Searched name"));
//                frame.dataProvider.searchUsers(searchField.getText());
//                frame.dataProvider.fetchUsers(usersTableModel);
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                if (searchField.getText().isEmpty()) {
//                    frame.dataProvider.isSearchingUsers = false;
//                }
//                frame.dataProvider.searchUsers(searchField.getText());
//                frame.dataProvider.fetchUsers(usersTableModel);
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//
//            }
//        });
//        //searchField.setBounds(frame.getWidth() / 2 - elementWidth / 2, 230, elementWidth, 40);
//        searchField.setBounds(frame.getWidth() / 2 - elementWidth / 2, 50, elementWidth, 40);
//        add(searchField);
    }

    public void bulgarianLanguage() {
//        nameField.setText("Име");
//        phoneField.setText("Телефон");
//        pinField.setText("ПИН");
//        nameLabel.setText("Име: ");
//        phoneLabel.setText("Телефон: ");
//        pinLabel.setText("ПИН: ");
//        typeLabel.setText("Роля: ");
//        newUserLabel.setText("Добавяне на нов потребител:");
//        uniquePinErrorMessage = "Невалиден ПИН номер! Изберете нов";
//        addButton.setText("Добави");

       // searchField.setText("Търсено име");
        String[] columns = {"Име", "ПИН", "Телефон", "Тип"};
        usersTableModel.setColumnIdentifiers(columns);

        deleteButton.setText("Изтрий");
        editButton.setText("Редактирай");
        showPinButton.setText("Покажи ПИН");
        userPanelLabel.setText("Управление на потребителите");

        repaint();
    }

    public void englishLanguage() {
//        nameField.setText("Name");
//        phoneField.setText("Phone");
//        pinField.setText("PIN");
//        nameLabel.setText("Name: ");
//        phoneLabel.setText("Phone: ");
//        pinLabel.setText("PIN: ");
//        typeLabel.setText("Role: ");
//        newUserLabel.setText("Add new user:");
//        uniquePinErrorMessage = "Invalid PIN number. Please choose another combination";
//        addButton.setText("Add");

        //searchField.setText("Searched name");

        deleteButton.setText("Remove");
        editButton.setText("Edit");
        showPinButton.setText("Show PIN");
        String[] columns = {"Name", "PIN", "Phone", "Level"};
        usersTableModel.setColumnIdentifiers(columns);
        userPanelLabel.setText("Users management");

        repaint();
    }
//
//    public UserType getSelectedType() {
//        return selectedType;
//    }
//
//    public void setSelectedType(UserType selectedType) {
//        this.selectedType = selectedType;
//    }
//
//    public JTextField getNameField() {
//        return nameField;
//    }
//
//    public void setNameField(JTextField nameField) {
//        this.nameField = nameField;
//    }
//
//    public JTextField getPinField() {
//        return pinField;
//    }

//    public void setPinField(JTextField pinField) {
//        this.pinField = pinField;
//    }
//
//    public JTextField getPhoneField() {
//        return phoneField;
//    }
//
//    public void setPhoneField(JTextField phoneField) {
//        this.phoneField = phoneField;
//    }

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


