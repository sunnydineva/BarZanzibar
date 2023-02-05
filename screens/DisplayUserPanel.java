package screens;

import frames.BarFrame;
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
    DefaultTableModel usersTableModel;
    String nameText;
    String phoneText;
    String pinText;
    String displayUserLabelText;
    public String editUserLabelText;
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

        String[] options = {"-", "W-Сервитьор","M-Мениджър"};
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
        clearButton.setBounds(5, typeComboBox.getY()+50, elementWidth -30, 40);
        clearButton.addActionListener(e ->clearAction());
        add(clearButton);

        addButton = new JButton(addButtonText);
        addButton.setBounds(5, clearButton.getY()+50, elementWidth -30, 40);
        addButton.addActionListener(e ->
                frame.dataProvider.adduserAction(this, usersTableModel));
        add(addButton);
    }

    public void clearAction(){
        nameField.setText("");
        pinField.setText("");
        phoneField.setText("");
        typeComboBox.setSelectedIndex(0);
        displayUserLabel.setText(displayUserLabelText);
    }

    public void resetAction(){
        nameField.setText("");
        pinField.setText("");
        phoneField.setText("");
        typeComboBox.setSelectedIndex(0);
        displayUserLabel.setText(displayUserLabelText);
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

    public JLabel getDisplayUserLabel() {
        return displayUserLabel;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getPinField() {
        return pinField;
    }

    public JTextField getPhoneField() {
        return phoneField;
    }

    public JComboBox<String> getTypeComboBox() {
        return typeComboBox;
    }

    public void bulgarianLanguage() {
        nameText = "Име";
        phoneText = "Телефон";
        pinText = "ПИН";
        displayUserLabelText = "Добавяне на нов потребител:";
        editUserLabelText = "Избран потребител:";
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
        frame.dataProvider.pinPatternErrorMessage = "Невалиден ПИН номер! Изберете ПИН от 4 цифри";
        frame.dataProvider.selectUserTypeErrorMessage = "Моля изберете Тип потребител";
        repaint();
    }

    public void englishLanguage() {
        nameText = "Name";
        phoneText = "Phone";
        pinText = "PIN";
        displayUserLabelText = "Add new user:";
        editUserLabelText = "Избран потребител:";
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
        frame.dataProvider.pinPatternErrorMessage = "Invalid PIN number. Choose 4 digit combination";
        frame.dataProvider.selectUserTypeErrorMessage = "Please select User type";
        repaint();
    }
}
