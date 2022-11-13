package frames;

//navigation and group methods

import screens.Language;
import screens.LoginPanel;
import screens.TablesPanel;

public class BarRouter {
    public BarFrame frame;
    public Language language;

    public BarRouter(BarFrame frame) {
        this.frame = frame;
    }

    public void showLogin() {
        LoginPanel panel = new LoginPanel(frame, language);
        this.frame.setContentPane(panel); //the main panel of the frame
        this.frame.validate();

    }

    public void showTables() {
        TablesPanel panel = new TablesPanel(frame, language);
        this.frame.setContentPane(panel);
        this.frame.validate();
    }
}
