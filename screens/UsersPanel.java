package screens;

import frames.BarFrame;
import models.UserType;

import javax.swing.*;
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
    private JButton editSaveButton;
    private JButton showPinButton;
    private JButton hidePinButton;
    public JComboBox<String> typeComboBox;
    public DefaultTableModel usersTableModel;
    public JTable usersTable;
    private DisplayUserPanel displayUser;

    public String noSelectedUserErrorMessage = "Моля селектирайте потребител";
    boolean isShownPin = false;

    public String uniquePinErrorMessage;
    String userPanelText;
    String deleteButtonText;
    String editSaveButtonText;
    String showPinButtonText;
    String hidePinButtonText;

    public UsersPanel(BarFrame frame) {
        super(frame);
        initializeHeader();
        initializeUsersTable();
        initializeUserArea(1);
        initializeButtons();
        initializeSearchArea();
        languageSwitch(language);
    }

    public void initializeHeader(){
        userPanelLabel = new JLabel(userPanelText);
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
        usersTable.getSelectionModel().addListSelectionListener(e-> {

            displayUser.getDisplayUserLabel().setText(displayUser.displayUserLabelText);

            //да поиграя със заглавието от дисплейЮзъра - при селекция - да става Редактиране,
            //при изчистване - да става добавяне



            frame.dataProvider.displayUserInUserArea(this.usersTable, displayUser);
        });
        usersTablePane.setBounds(elementWidth, frame.getHeight()/3-20, elementWidth * 2 - 14, 230);
        add(usersTablePane);
        frame.dataProvider.fetchUsers(usersTableModel, false);
    }

    public void initializeUserArea(int modes_1_edit_2_add) {
            displayUser = new DisplayUserPanel(frame, usersTableModel);
            displayUser.setBounds(5, frame.getHeight() / 3 - 105,
                    elementWidth - 14, frame.getHeight() / 3 + 130);
            add(displayUser);
            this.frame.validate();
    }

    public void initializeButtons() {
        showPinButton = new JButton(showPinButtonText);
        showPinButton.setBounds(elementWidth, frame.getHeight()/3 + 220, elementWidth, 40);
        showPinButton.addActionListener(e -> showPin());
        add(showPinButton);

        hidePinButton = new JButton(hidePinButtonText);
        hidePinButton.setBounds(elementWidth, frame.getHeight()/3 + 220, elementWidth, 40);
        hidePinButton.addActionListener(e -> hidePin());

        editSaveButton = new JButton(editSaveButtonText);
        editSaveButton.setBounds(elementWidth*2+10, showPinButton.getY(), elementWidth-25, 40);
        editSaveButton.addActionListener(e -> {
                        System.out.println("userEdit activated");
                        frame.dataProvider.editUserAction(displayUser, this, uniquePinErrorMessage);
        });
        add(editSaveButton);

        deleteButton = new JButton(deleteButtonText);
        deleteButton.setBounds(elementWidth*2+10, editSaveButton.getY() + 50,
                elementWidth-25, 40);
        deleteButton.addActionListener(e -> frame.dataProvider.deleteUserAction(this, this));
        add(deleteButton);
    }

    public void initializeSearchArea() {
        JPanel searchPanel = new SearchUserPanel(frame, usersTableModel);
        searchPanel.setBounds(elementWidth, frame.getHeight()/3-105,
                elementWidth * 2 -14, frame.getHeight()/3/3);
        //this.frame.setContentPane(searchPanel);
        add(searchPanel);
        this.frame.validate();
    }

    public void bulgarianLanguage() {

        userPanelText = "Управление на потребителите";
        deleteButtonText = "Изтрий";
        editSaveButtonText = "Запази промените";
        showPinButtonText = "Покажи ПИН";
        hidePinButtonText = "Скрий ПИН";

        String[] columns = {"Име", "ПИН", "Телефон", "Тип"};
        usersTableModel.setColumnIdentifiers(columns);
        userPanelLabel.setText("Управление на потребителите");
        deleteButton.setText("Изтрий");
        editSaveButton.setText("Запази промените");
        try {showPinButton.setText("Покажи ПИН");} catch (Exception ignored) {}
        try {hidePinButton.setText("Скрий ПИН");} catch (Exception ignored) {}
        noSelectedUserErrorMessage = "Моля селектирайте потребител";

        repaint();
    }

    public void englishLanguage() {
        userPanelText = "Users management";
        deleteButtonText = "Remove";
        editSaveButtonText = "Save changes";
        showPinButtonText = "Show PIN";
        hidePinButtonText = "Hide PIN";

        String[] columns = {"Name", "PIN", "Phone", "Level"};
        usersTableModel.setColumnIdentifiers(columns);
        userPanelLabel.setText(userPanelText);
        deleteButton.setText(deleteButtonText);
        editSaveButton.setText(editSaveButtonText);
        try {showPinButton.setText(showPinButtonText);} catch (Exception ignored) {}
        try {hidePinButton.setText(hidePinButtonText);} catch (Exception ignored) {}
        noSelectedUserErrorMessage = "Please select user";

        repaint();
    }
    public void showPin(){
        frame.dataProvider.fetchUsers(usersTableModel, true);
        remove(showPinButton);
        add(hidePinButton);
        isShownPin = true;
        repaint();
    }

    public void hidePin(){
        frame.dataProvider.fetchUsers(usersTableModel, false);
        remove(hidePinButton);
        add(showPinButton);
        isShownPin = false;
        repaint();
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


