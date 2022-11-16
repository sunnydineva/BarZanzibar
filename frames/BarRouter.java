package frames;

//navigation and group methods

import screens.UsersPanel;
import screens.Language;
import screens.LoginPanel;
import screens.TablePanel;

public class BarRouter {
    public BarFrame frame;
    public Language language;

    public BarRouter(BarFrame frame) {
        this.frame = frame;
    }

    public void showLogin() {
        LoginPanel panel = new LoginPanel(frame);
        this.frame.setContentPane(panel); //the main panel of the frame
        this.frame.validate();

    }

    public void showUsersPanel() {
        UsersPanel panel = new UsersPanel(frame);
        this.frame.setContentPane(panel); //the main panel of the frame
        this.frame.validate();

    }

    public void showTables() {
        TablePanel panel = new TablePanel(frame);
        this.frame.setContentPane(panel);
        this.frame.validate();
    }


    public void showManagerPanel(){

    }
}
