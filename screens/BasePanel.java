package screens;

import frames.BarFrame;
import models.Language;
import javax.swing.*;
import java.awt.*;


public class BasePanel extends JPanel {
    public BarFrame frame;
    public JButton cancelButton;
    public JButton bulgarianButton;
    public JButton englishButton;
    public Language language;
    public int tableBorder = 4;
    public static int elementWidth;
    public static int elementHeight;
    public static int selectedTableNumber; //the table labels start from 11, table[i] in table[selectedTableNumber-11]

    public BasePanel(BarFrame frame){
        this.frame = frame;
        this.language = frame.language;

        setLayout(null);
        setBackground(Color.gray);

        elementWidth = (frame.getWidth()) / 3;
        elementHeight = 40; //buttons not more than 40 height

        initializeBaseButtons();

        if (language == Language.BULGARIAN) {
            baseBulgarianLanguage();
        } else baseEnglishLanguage();
    }

    public void languageSwitch(Language language){
        if (language == Language.BULGARIAN) {
            bulgarianLanguage();
        } else englishLanguage();
    }

    public void initializeBaseButtons() {
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(frame.getWidth() / 2 - elementWidth / 2, frame.getHeight() - 150,
                elementWidth, elementHeight);
        cancelButton.addActionListener(e -> frame.router.showLogin());
        add(cancelButton);

        englishButton = new JButton("EN");
        englishButton.setBounds(cancelButton.getX(), frame.getHeight() - 100,
                elementWidth / 2, elementHeight);
        englishButton.addActionListener(e -> {
            frame.currentLanguage.englishLanguageAction();
            reopenPanel();

        });
        add(englishButton);

        bulgarianButton = new JButton("BG");
        bulgarianButton.setBounds(englishButton.getX() + englishButton.getWidth(), englishButton.getY(),
                elementWidth / 2, elementHeight);
        bulgarianButton.addActionListener(e -> {
            frame.currentLanguage.bulgarianLanguageActon();
            reopenPanel();
        });
        add(bulgarianButton);
    }

    public void reopenPanel() {
        if (frame.getContentPane().toString().equals("LoginPanel")) {
            frame.router.showLogin();
        } else if (frame.getContentPane().toString().equals("ManagerPanel")) {
            frame.router.showManagerPanel();
        } else if (frame.getContentPane().toString().equals("TablePanel")) {
            frame.router.showTables();
        } else if (frame.getContentPane().toString().equals("UsersPanel")) {
            frame.router.showUsersPanel();
        } else if (frame.getContentPane().toString().equals("OrdersPanel")) {
            frame.router.showOrdersPanel(selectedTableNumber);
        }
    }

    public void baseEnglishLanguage() {
        cancelButton.setText("Cancel");
    }

    public void baseBulgarianLanguage() {
        cancelButton.setText("Отказ");
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public boolean showQuestion(String message) {
        int result = JOptionPane.showConfirmDialog(null, message, "Attention",
                JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }


    public void englishLanguage() {

    }


    public void bulgarianLanguage() {

    }
}