package screens;

import frames.BarFrame;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends BasePanel {

    public LoginPanel(BarFrame frame) {
        super(frame);

        JLabel welcomeLabel = new JLabel("Бар Zanzibar вписване");
        welcomeLabel.setBounds(frame.getWidth() / 2 - 150, 100, 300, 50);
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 26));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        JLabel enterPassLabel = new JLabel("Въведи парола");
        enterPassLabel.setBounds(frame.getWidth() / 2 - 60, welcomeLabel.getY() + 60, 120, 40);
        enterPassLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterPassLabel);

        JTextField pinField = new JTextField("Въведи парола");
        pinField.setBounds(frame.getWidth() / 2 - 60, enterPassLabel.getY() + 50, 120, 40);
        pinField.setHorizontalAlignment(JTextField.CENTER);
        add(pinField);

        JButton loginButton = new JButton("ВХОД");
        loginButton.setBounds(frame.getWidth() / 2 - 50, pinField.getY() + 50, 100, 40); //buttons not more than 40 height
        loginButton.addActionListener(e -> {
            if (frame.dataProvider.isCorrectLogin(pinField.getText())) {
                frame.router.showTables();
            } else {
                showError("Грешна парола. Моля, въведете Вашата парола отново!");
            }
        });
        add(loginButton);
    }
}
