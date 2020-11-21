package src.ui.admin;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Abstract class for all menu items, handles calls to the
 * parent UI element as well as logging off.
 */
public abstract class Menu extends JPanel {

    private AdminUI adminUI;
    protected JButton backButton;
    private ActionListener backButtonActionListener;

    public Menu(AdminUI adminUI) {

        this.adminUI = adminUI;

        setBackButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                hideMenu();
            }
        });

        setVisible(false);
    }
    private void hideMenu() {
        setVisible(false);
        getAdminUI().getDatabaseView().showUsers();
        adminUI.getMainMenu().setVisible(true);
    }
    public void setBackButtonActionListener(ActionListener actionListener) {
        backButton = new JButton();
        backButton.setText("Back");
        backButton.addActionListener(actionListener);
    }
    public AdminUI getAdminUI() {
        return adminUI;
    }
    protected abstract void placeComponents();
}