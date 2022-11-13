package frames;

//navigation and group methods

import screens.LoginPanel;
import screens.TablesPanel;

public class BarRouter {
    public BarFrame frame;

    public BarRouter(BarFrame frame) {
        this.frame = frame;
    }

    public void showLogin() {
        LoginPanel panel = new LoginPanel(frame);
        this.frame.setContentPane(panel); //the main panel of the frame
        this.frame.validate();

    }

    public void showTables() {
        TablesPanel panel = new TablesPanel(frame);
        this.frame.setContentPane(panel);
        this.frame.validate();
    }
}
