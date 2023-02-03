package screens;

import frames.BarFrame;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class SearchUserPanel extends BasePanel implements MouseListener {
    private JTextField searchField;
    private JLabel searchLabel;
    private DefaultTableModel usersTableModel;


    public SearchUserPanel(BarFrame frame, DefaultTableModel usersTableModel) {
        super(frame);
        this.language = frame.language;
        cancelButton.setVisible(false);
        this.usersTableModel = usersTableModel;
        initializeElements();
        setBackground(Color.darkGray);
        this.setBorder(BorderFactory.createMatteBorder(tableBorder, tableBorder, tableBorder, tableBorder, Color.darkGray));
        super.languageSwitch(language);
    }

    public void initializeElements(){
        searchLabel = new JLabel("Търсене по име: ");
        //nameLabel.setBounds(0, 230, elementWidth * 0.2, 40);
        //searchLabel.setBounds(elementWidth / 2, 50, elementWidth / 2, 40);
        searchLabel.setBounds(5, 20, elementWidth-tableBorder, 40);
        searchLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        searchLabel.setHorizontalAlignment(SwingConstants.RIGHT);
       // this.setBorder(BorderFactory.createMatteBorder(tableBorder, tableBorder, tableBorder, tableBorder, Color.darkGray));
        add(searchLabel);

        searchField = new JTextField("Търсено име");
        frame.dataProvider.isSearchingUsers = false;
        searchField.addMouseListener(this);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                frame.dataProvider.isSearchingUsers = (!searchField.getText().equalsIgnoreCase("Търсено име"))
                        && (!searchField.getText().equalsIgnoreCase("Searched name"));
                frame.dataProvider.searchUsers(searchField.getText());
                frame.dataProvider.fetchUsers(usersTableModel, false);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (searchField.getText().isEmpty()) {
                    frame.dataProvider.isSearchingUsers = false;
                }
                frame.dataProvider.searchUsers(searchField.getText());
                frame.dataProvider.fetchUsers(usersTableModel, false);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        //searchField.setBounds(frame.getWidth() / 2 - elementWidth / 2, 230, elementWidth, 40);
        searchField.setBounds(frame.getWidth() / 2 - elementWidth / 2-tableBorder, 20, elementWidth-15, 40);
        add(searchField);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (((JTextField) e.getSource()).getText().equals("Търсено име") ||
                ((JTextField) e.getSource()).getText().equals("Searched name")) {
            ((JTextField) e.getSource()).setText("");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void bulgarianLanguage() {
        searchField.setText("Търсено име");
        searchLabel.setText("Търсене по име:  ");
        repaint();
    }

    public void englishLanguage() {
        searchField.setText("Searched name");
        searchLabel.setText("Search by name:  ");
        repaint();
    }
}
