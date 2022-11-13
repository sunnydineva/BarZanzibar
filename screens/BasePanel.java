package screens;

import frames.BarFrame;
import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel {
    public BarFrame frame;
    public Language language;

    public BasePanel(BarFrame frame, Language language){
        this.frame = frame;
        this.language = language;

        setLayout(null);
        setBackground(Color.gray);
    }

    public void showError(String message){
        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

}
