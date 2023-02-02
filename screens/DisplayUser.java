package screens;

import frames.BarFrame;
import models.UserType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DisplayUser extends BasePanel implements MouseListener {
    private JLabel nameLabel;
    private JLabel phoneLabel;

    private JLabel pinLabel;
    private JLabel typeLabel;
    private JLabel newUserLabel;
    private JTextField nameField;
    private JTextField pinField;
    private JTextField phoneField;
    public JComboBox<String> typeComboBox;
    private UserType selectedType;
    DefaultTableModel usersTableModel;
    String uniquePinErrorMessage;

    private JButton addButton;
    public DisplayUser(BarFrame frame, DefaultTableModel usersTableModel) {
        super(frame);
        this.language = frame.language;
        cancelButton.setVisible(false);
        this.usersTableModel = usersTableModel;
        initializeElements();
        setBackground(Color.darkGray);
        this.setBorder(BorderFactory.createMatteBorder(tableBorder, tableBorder, tableBorder, tableBorder, Color.darkGray));
        super.languageSwitch(language);
    }
    public void initializeElements(){
        newUserLabel = new JLabel("Добавяне на нов потребител:");
        newUserLabel.setBounds(0, 10, elementWidth-14, 40);
        newUserLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        newUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(newUserLabel);

        nameField = new JTextField("Име");
        nameField.setBounds(elementWidth/3-10, newUserLabel.getY()+50, elementWidth - elementWidth/3 -14 , 40);
        nameField.addMouseListener(this);
        add(nameField);

        pinField = new JTextField("ПИН");
        pinField.setBounds(nameField.getX(), nameField.getY() + 50, nameField.getWidth(), 40);
        pinField.addMouseListener(this);
        add(pinField);

        phoneField = new JTextField("Телефон");
        phoneField.setBounds(nameField.getX(), pinField.getY() + 50, nameField.getWidth(), 40);
        phoneField.addMouseListener(this);
        add(phoneField);

        String[] options = {"Мениджър", "Сервитьор"};
        typeComboBox = new JComboBox<>(options);
        typeComboBox.setBounds(nameField.getX(), phoneField.getY() + 50, nameField.getWidth(), 40);
        add(typeComboBox);

        nameLabel = new JLabel("Име: ");
        nameLabel.setBounds(0, nameField.getY(), elementWidth/3-10, 40);
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(nameLabel);

        pinLabel = new JLabel("ПИН: ");
        pinLabel.setBounds(0, pinField.getY(), nameLabel.getWidth(), 40);
        pinLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(pinLabel);

        phoneLabel = new JLabel("Телефон: ");
        phoneLabel.setBounds(0, phoneField.getY(), nameLabel.getWidth(), 40);
        phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(phoneLabel);

        typeLabel = new JLabel("Роля: ");
        typeLabel.setBounds(0, typeComboBox.getY(), nameLabel.getWidth(), 40);
        typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(typeLabel);

        addButton = new JButton("Добави");
        selectedType = null;
        addButton.setBounds(5, typeComboBox.getY()+50, elementWidth -30, 40);
        addButton.addActionListener(e ->
                frame.dataProvider.adduserAction(this, usersTableModel, uniquePinErrorMessage));
        add(addButton);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public JLabel getPhoneLabel() {
        return phoneLabel;
    }

    public void setPhoneLabel(JLabel phoneLabel) {
        this.phoneLabel = phoneLabel;
    }

    public JLabel getPinLabel() {
        return pinLabel;
    }

    public void setPinLabel(JLabel pinLabel) {
        this.pinLabel = pinLabel;
    }

    public JLabel getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(JLabel typeLabel) {
        this.typeLabel = typeLabel;
    }

    public JLabel getNewUserLabel() {
        return newUserLabel;
    }

    public void setNewUserLabel(JLabel newUserLabel) {
        this.newUserLabel = newUserLabel;
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

    public UserType getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(UserType selectedType) {
        this.selectedType = selectedType;
    }

    public DefaultTableModel getUsersTableModel() {
        return usersTableModel;
    }

    public void setUsersTableModel(DefaultTableModel usersTableModel) {
        this.usersTableModel = usersTableModel;
    }

    public String getUniquePinErrorMessage() {
        return uniquePinErrorMessage;
    }

    public void setUniquePinErrorMessage(String uniquePinErrorMessage) {
        this.uniquePinErrorMessage = uniquePinErrorMessage;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public void bulgarianLanguage() {
        nameField.setText("Име");
        phoneField.setText("Телефон");
        pinField.setText("ПИН");
        nameLabel.setText("Име: ");
        phoneLabel.setText("Телефон: ");
        pinLabel.setText("ПИН: ");
        typeLabel.setText("Роля: ");
        newUserLabel.setText("Добавяне на нов потребител:");
        String[] columns = {"Име", "ПИН", "Телефон", "Тип"};
        usersTableModel.setColumnIdentifiers(columns);
        addButton.setText("Добави");
        uniquePinErrorMessage = "Невалиден ПИН номер! Изберете нов";
        repaint();
    }

    public void englishLanguage() {
        nameField.setText("Name");
        phoneField.setText("Phone");
        pinField.setText("PIN");
        nameLabel.setText("Name: ");
        phoneLabel.setText("Phone: ");
        pinLabel.setText("PIN: ");
        typeLabel.setText("Role: ");
        newUserLabel.setText("Add new user:");
        addButton.setText("Add");
        String[] columns = {"Name", "PIN", "Phone", "Level"};
        usersTableModel.setColumnIdentifiers(columns);
        uniquePinErrorMessage = "Invalid PIN number. Please choose another combination";
        repaint();
    }
}
