package src.ui.admin;

import src.ui.database.DatabaseView;
import src.ui.MainWindow;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * JPanel class the houses all the Admin UI , so its ready to be
 * displayed by the main JFrame.
 */
public class AdminUI extends JPanel {

    private JPanel adminMainMenu;
    private ManageUsers manageUsers;
    private ManageDepartments manageDepartments;
    private ManageDegrees manageDegrees;
    private ManageModules manageModules;
    private ManageModuleLinks manageModuleLinks;
    private ManageDegreeLinks manageDegreeLinks;
    private DatabaseView databaseView;
    private JButton logOffButton;
    private MainWindow mainWindow;
    private String username;

    public AdminUI(MainWindow mainWindow, String username) {
        this.mainWindow = mainWindow;
        this.username = username;
        setVisible(true);
        initComponents();
    }
    /**
    *Initializes components in the JPanel
    */
    private void initComponents() {

        //initialise JPanels
        databaseView = new DatabaseView(this);
        adminMainMenu = new AdminMainMenu(this);
        manageUsers = new ManageUsers(this);
        manageDepartments = new ManageDepartments(this);
        manageDegrees = new ManageDegrees(this);
        manageModules = new ManageModules(this);
        manageModuleLinks = new ManageModuleLinks(this);
        manageDegreeLinks = new ManageDegreeLinks(this);

        databaseView.showUsers();

        logOffButton = new JButton();
        logOffButton.setText("Log Off");
        logOffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainWindow.showLogInWindow();
            }
        });

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                                      "Administrator Page : Welcome user " + username, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                        new java.awt.Font("Trebuchet MS", 0, 24)));
        setFont(new java.awt.Font("Trebuchet MS", 0, 11));

        placeComponents();
    }
    public JPanel getMainMenu() {
        return adminMainMenu;
    }
    public JPanel getUserMenu() {
        return manageUsers;
    }
    public JPanel getDepartmentMenu() {
        return manageDepartments;
    }
    public JPanel getDegreeMenu() {
        return manageDegrees;
    }
    public JPanel getModuleMenu() {
        return manageModules;
    }
    public ManageDegreeLinks getDegreeLinkMenu() {
        return manageDegreeLinks;
    }
    public ManageModuleLinks getModuleLinkMenu() {
        return manageModuleLinks;
    }
    public DatabaseView getDatabaseView() {
        return databaseView;
    }

    private void placeComponents() {

        javax.swing.GroupLayout adminUILayout = new javax.swing.GroupLayout(this);
            setLayout(adminUILayout);
            adminUILayout.setHorizontalGroup(
                adminUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(adminUILayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(adminUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(adminUILayout.createSequentialGroup()
                            .addComponent(logOffButton)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(adminUILayout.createSequentialGroup()
                            .addGroup(adminUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(manageDegreeLinks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(databaseView)
                                .addComponent(manageModules, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(manageDepartments, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(manageUsers, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(adminMainMenu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(manageDegrees, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(manageModuleLinks, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addContainerGap())))
        );
        adminUILayout.setVerticalGroup(
            adminUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(adminUILayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adminMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manageUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manageDepartments, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manageDegrees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(manageDegreeLinks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manageModules, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manageModuleLinks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(databaseView, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logOffButton)
                .addContainerGap())
        );
    }
}
