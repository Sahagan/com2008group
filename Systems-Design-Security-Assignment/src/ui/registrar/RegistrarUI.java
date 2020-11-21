package src.ui.registrar;

import javax.swing.JPanel;
import javax.swing.JButton;
import src.ui.MainWindow;
import java.awt.event.*;
import src.ui.registrar.ManageStudents;

/**
 * Class extends JPanel and houses all the registrar ui elements, and can display and
 * hide the menus in registrarUI.
 */
public class RegistrarUI extends JPanel {

    private MainWindow mainWindow;
    private JButton logOffButton;
    private String username;

    public RegistrarUI(MainWindow mainWindow, String username) {
        this.mainWindow = mainWindow;
        this.username = username;
        setVisible(true);
        logOffButton = new JButton();
        logOffButton.setText("Log Off");
        logOffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainWindow.showLogInWindow();
            }
        });
        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                "Registrar : Welcome user " + username, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 0, 24)));
        showRegistrarMenu();
        //showManageStudents();
        //showManageModules();
    }
    public void showRegistrarMenu() {
        removeAll();
        placeMenuComponent(new Registrar(this));
        repaint();
    }
    public void showManageStudents() {
        removeAll();
        placeMenuComponent(new ManageStudents(this));
        repaint();
    }
    public void showManageModules(int recordId) {
        removeAll();
        placeMenuComponent(new ManageModules(this,recordId));
        repaint();
    }
    private void placeMenuComponent(JPanel menu) {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()
                        .addComponent(logOffButton)
                        .addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap()
                        .addComponent(logOffButton)
                        .addContainerGap()));
    }
}
