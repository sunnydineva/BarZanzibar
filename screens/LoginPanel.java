package screens;

import frames.BarFrame;
import frames.BarLanguages;
import models.Language;
import models.UserType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginPanel extends BasePanel{
    public JLabel welcomeLabel;
    public JLabel enterPassLabel;
    public JTextField pinField;
    public JButton loginButton;
    public String loginErrorMsg;


    public LoginPanel(BarFrame frame) {
        super(frame);
        this.language = frame.language;
        cancelButton.setVisible(false);
        initializeElements();

        if (language == Language.BULGARIAN) {
            bulgarianLanguage();
        } else englishLanguage();


    }

    public void initializeElements() {

        welcomeLabel = new JLabel("");
        welcomeLabel.setBounds(frame.getWidth() / 2 - 150, 100, 300, 50);
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 26));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel);

        enterPassLabel = new JLabel("");
        enterPassLabel.setBounds(frame.getWidth() / 2 - 60, welcomeLabel.getY() + 60, 120, elementHeight);
        enterPassLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(enterPassLabel);

        pinField = new JTextField("");
        pinField.setBounds(frame.getWidth() / 2 - elementWidth / 2, enterPassLabel.getY() + 50,
                elementWidth, elementHeight);
        pinField.setHorizontalAlignment(JTextField.CENTER);
        add(pinField);

        loginButton = new JButton("");
        loginButton.setBounds(frame.getWidth() / 2 - elementWidth / 2, pinField.getY() + 50,
                elementWidth, elementHeight);
        loginButton.addActionListener(e -> loginAction());
        loginButton.setSelected(true);
        loginButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                loginAction();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


//        JButton submit = new JButton("Submit");
//        submit.addActionListener(new SubmitButton(textBoxToEnterName));
//        submit.addKeyListener(new SubmitButton(textBoxToEnterName));
//        JPanel panelBottom = new JPanel();
//        panelBottom.add(submit);
//
//        SubmitButton listener = new SubmitButton(textBoxToEnterName);
//        textBoxToEnterName.addActionListener(listener);
//        submit.addKeyListener(listener);


        InputMap im = loginButton.getInputMap();
        im.put(KeyStroke.getKeyStroke("ENTER"), "none");
        im.put(KeyStroke.getKeyStroke("released ENTER"), "released");

        add(loginButton);
    }

    public void loginAction() {
        if (frame.dataProvider.isCorrectLogin(pinField.getText())) {
            cancelButton.setVisible(true);
            if (frame.dataProvider.loggedUser.getType() == UserType.MANAGER)
                frame.router.showManagerPanel();
            else frame.router.showTables();
        } else {
            showError(loginErrorMsg);
        }
    }

    public void englishLanguage() {
        loginErrorMsg = "Wrong password! Please enter your password again!";
        welcomeLabel.setText("Bar Zanzibar login");
        enterPassLabel.setText("Enter password");
        pinField.setText("Enter PIN");
        loginButton.setText("Enter");
    }

    public void bulgarianLanguage() {
        loginErrorMsg = "Грешна парола. Моля, въведете Вашата парола отново!";
        welcomeLabel.setText("Бар Zanzibar вписване");
        enterPassLabel.setText("Въведи парола");
        pinField.setText("Парола");
        loginButton.setText("ВХОД");
    }




    @Override
    public String toString() {
        return "LoginPanel";
    }
}
