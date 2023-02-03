package screens;

import frames.BarFrame;
import models.UserType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DisplayUserPanel extends BasePanel implements MouseListener {
    private JLabel nameLabel;
    private JLabel phoneLabel;

    private JLabel pinLabel;
    private JLabel typeLabel;
    private JLabel displayUserLabel;
    private JTextField nameField;
    private JTextField pinField;
    private JTextField phoneField;
    public JComboBox<String> typeComboBox;
    private UserType selectedType;
    DefaultTableModel usersTableModel;
    //String uniquePinErrorMessage;
    String nameText;
    String phoneText;
    String pinText;
    String displayUserLabelText;
    String addButtonText;
    String clearButtonText;


    private JButton addButton;
    private JButton clearButton;
    public DisplayUserPanel(BarFrame frame, DefaultTableModel usersTableModel) {
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
        displayUserLabel = new JLabel(displayUserLabelText);
        displayUserLabel.setBounds(0, 10, elementWidth-14, 40);
        displayUserLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        displayUserLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(displayUserLabel);

        nameField = new JTextField(nameText);
        nameField.setBounds(elementWidth/3-10, displayUserLabel.getY()+50, elementWidth - elementWidth/3 -14 , 40);
        nameField.addMouseListener(this);
        add(nameField);

        pinField = new JTextField(pinText);
        pinField.setBounds(nameField.getX(), nameField.getY() + 50, nameField.getWidth(), 40);
        pinField.addMouseListener(this);
        add(pinField);

        phoneField = new JTextField(phoneText);
        phoneField.setBounds(nameField.getX(), pinField.getY() + 50, nameField.getWidth(), 40);
        phoneField.addMouseListener(this);
        add(phoneField);

        String[] options = {"M-Мениджър", "W-Сервитьор","-"};
        typeComboBox = new JComboBox<>(options);
        typeComboBox.setBounds(nameField.getX(), phoneField.getY() + 50, nameField.getWidth(), 40);
        add(typeComboBox);

        nameLabel = new JLabel(nameText +": ");
        nameLabel.setBounds(0, nameField.getY(), elementWidth/3-10, 40);
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(nameLabel);

        pinLabel = new JLabel(pinText +": ");
        pinLabel.setBounds(0, pinField.getY(), nameLabel.getWidth(), 40);
        pinLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(pinLabel);

        phoneLabel = new JLabel(phoneText +": ");
        phoneLabel.setBounds(0, phoneField.getY(), nameLabel.getWidth(), 40);
        phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(phoneLabel);

        typeLabel = new JLabel(typeLabel +": ");
        typeLabel.setBounds(0, typeComboBox.getY(), nameLabel.getWidth(), 40);
        typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(typeLabel);

        clearButton = new JButton(clearButtonText);
        selectedType = null;
        clearButton.setBounds(5, typeComboBox.getY()+50, elementWidth -30, 40);
        clearButton.addActionListener(e ->{
                    clearAction();
                    System.out.println("clearUserFields activated");
                });

        add(clearButton);

        addButton = new JButton(addButtonText);
        selectedType = null;
        addButton.setBounds(5, clearButton.getY()+50, elementWidth -30, 40);
        addButton.addActionListener(e ->
                frame.dataProvider.adduserAction(this, usersTableModel, frame.dataProvider.uniquePinErrorMessage));
        add(addButton);
    }

    public void clearAction(){
        nameField.setText("");
        pinField.setText("");
        phoneField.setText("");
        typeComboBox.setSelectedIndex(2);
    }

    public void resetAction(){
        nameField.setText("");
        pinField.setText("");
        phoneField.setText("");
        typeComboBox.setSelectedIndex(2);
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
                ((JTextField) e.getSource()).getText().equals("Searched nameText"))

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

    public JLabel getDisplayUserLabel() {
        return displayUserLabel;
    }

    public void setDisplayUserLabel(JLabel displayUserLabel) {
        this.displayUserLabel = displayUserLabel;
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

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public void bulgarianLanguage() {
        nameText = "Име";
        phoneText = "Телефон";
        pinText = "ПИН";
        displayUserLabelText = "Добавяне на нов потребител:";
        addButtonText = "Добави";
        clearButtonText = "Изчисти";

        nameField.setText(nameText);
        phoneField.setText(phoneText);
        pinField.setText(pinText);
        nameLabel.setText(nameText +": ");
        phoneLabel.setText(phoneText+": ");
        pinLabel.setText(pinText +": ");

        typeLabel.setText("Роля: ");
        displayUserLabel.setText(displayUserLabelText);
        String[] columns = {nameText, pinText, phoneText, "Тип"};
        usersTableModel.setColumnIdentifiers(columns);
        addButton.setText(addButtonText);
        clearButton.setText(clearButtonText);
        frame.dataProvider.uniquePinErrorMessage = "Невалиден ПИН номер! Изберете нов";
        repaint();
    }

    public void englishLanguage() {
        nameText = "Name";
        phoneText = "Phone";
        pinText = "PIN";
        displayUserLabelText = "Add new user:";
        addButtonText = "Add";
        clearButtonText = "Clear";

        nameField.setText(nameText);
        phoneField.setText(phoneText);
        pinField.setText(pinText);
        nameLabel.setText(nameText +": ");
        phoneLabel.setText(phoneText+": ");
        pinLabel.setText(pinText +": ");

        typeLabel.setText("Role: ");
        displayUserLabel.setText(displayUserLabelText);
        addButton.setText(addButtonText);
        clearButton.setText(clearButtonText);
        String[] columns = {nameText, pinText, phoneText, "Level"};
        usersTableModel.setColumnIdentifiers(columns);
        frame.dataProvider.uniquePinErrorMessage = "Invalid PIN number. Please choose another combination";
        repaint();
    }
}
