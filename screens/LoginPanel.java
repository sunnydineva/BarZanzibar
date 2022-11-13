package screens;

import frames.BarFrame;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends BasePanel {
    public JLabel welcomeLabel;
    public JLabel enterPassLabel;
    public JTextField pinField;
    public JButton loginButton;
    public JButton englishButton;
    public JButton bulgarianButton;
    public LoginPanel(BarFrame frame, Language language) {
        super(frame, language);

        welcomeLabel = new JLabel();
        welcomeLabel.setBounds(frame.getWidth() / 2 - 150, 100, 300, 50);
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 26));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        enterPassLabel = new JLabel();
        enterPassLabel.setBounds(frame.getWidth() / 2 - 60, welcomeLabel.getY() + 60, 120, 40);
        enterPassLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterPassLabel);

        pinField = new JTextField();
        pinField.setBounds(frame.getWidth() / 2 - 60, enterPassLabel.getY() + 50, 120, 40);
        pinField.setHorizontalAlignment(JTextField.CENTER);
        add(pinField);

        loginButton = new JButton();
        loginButton.setBounds(frame.getWidth() / 2 - 50, pinField.getY() + 50, 100, 40); //buttons not more than 40 height
        loginButton.addActionListener(e -> {
            if (frame.dataProvider.isCorrectLogin(pinField.getText())) {
                frame.router.showTables();
            } else {
                showError("Грешна парола. Моля, въведете Вашата парола отново!");   // иф за езиците
            }
        });
        add(loginButton);

        englishButton = new JButton("EN");
        englishButton.setBounds(frame.getWidth() / 2 - 100, loginButton.getY() + 50, 100, 40);
        englishButton.addActionListener(e -> {
            englishLanguageAction();

        });
        add(englishButton);

        bulgarianButton = new JButton("BG");
        bulgarianButton.setBounds(englishButton.getX() + 100, loginButton.getY() + 50, 100, 40);
        bulgarianButton.addActionListener(e -> {
            bulgarianLanguageActon();
        });
        add(bulgarianButton);

        bulgarianLanguageActon();

    }

    public void englishLanguageAction(){
        welcomeLabel.setText("Bar Zanzibar login");
        enterPassLabel.setText("Enter password");
        pinField.setText("Enter PIN");
        loginButton.setText("Enter");
    }

    public void bulgarianLanguageActon(){
        welcomeLabel.setText("Бар Zanzibar вписване");
        enterPassLabel.setText("Въведи парола");
        pinField.setText("Въведи парола");
        loginButton.setText("ВХОД");
    }
}
