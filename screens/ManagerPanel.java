package screens;

import frames.BarFrame;
import models.Language;
import javax.swing.*;

public class ManagerPanel extends BasePanel {

    public JButton tableButton;
    public JButton userButton;

    public ManagerPanel(BarFrame frame){
        super(frame);

        initializeButtons();

        if (language == Language.BULGARIAN) {
            bulgarianLanguage();
        } else englishLanguage();
    }

    public void initializeButtons() {
        tableButton = new JButton("Маси");
        tableButton.setBounds(frame.getWidth() /2 - elementWidth / 2, frame.getHeight() / 2 - 20,
                elementWidth, elementHeight);
        tableButton.addActionListener(e -> frame.router.showTables());
        add(tableButton);

        userButton = new JButton("Персонал");
        userButton.setBounds(tableButton.getX(), tableButton.getY() + 50,
                elementWidth, elementHeight);
        userButton.addActionListener(e -> frame.router.showUsersPanel());
        add(userButton);
    }

    public void bulgarianLanguage(){
        tableButton.setText("Маси");
        userButton.setText("Персонал");
    }

    public void englishLanguage(){
        tableButton.setText("Tables");
        userButton.setText("Staff");
    }

    @Override
    public String toString() {
        return "ManagerPanel";
    }

}
