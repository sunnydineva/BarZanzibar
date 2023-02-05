package frames;

import screens.*;

//navigation and group methods
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
        ManagerPanel panel = new ManagerPanel(frame);
        this.frame.setContentPane(panel);
        this.frame.validate();
    }

    public void showOrdersPanel(int selectedTable) {
        OrdersPanel panel = new OrdersPanel(frame);
        this.frame.setContentPane(panel);
        this.frame.validate();
    }
}
