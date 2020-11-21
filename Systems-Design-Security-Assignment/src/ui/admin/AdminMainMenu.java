package src.ui.admin;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.*;
import java.awt.event.*;

/**
 * GUI Class handles the admin main menu, allowing the admin to naviagte to different
 * parts of the UI.
 */
public class AdminMainMenu extends Menu {

    private JButton manageUserAccountsButton;
    private JButton manageDepartmentsButton;
    private JButton manageDegreesButton;
    private JButton manageModulesButton;
    private JLabel databaseSelectorLabel;
    private JComboBox<String> databaseSelector;

    public AdminMainMenu (AdminUI adminUI) {
        super(adminUI);
        setVisible(true);
        initComponents();
        placeComponents();
    }
    private void  initComponents() {
        manageUserAccountsButton = new JButton();
        manageDepartmentsButton = new JButton();
        manageDegreesButton = new JButton();
        manageModulesButton = new JButton();
        databaseSelectorLabel = new JLabel();
        databaseSelector = new JComboBox<>();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());

        databaseSelectorLabel.setText("Choose infomation to show:");
        databaseSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User Accounts (Staff)", 
            "User Accounts (Students)", "Departments", "Degrees", "Modules" }));
        databaseSelector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switchDatabase(e);
            }
        });

        //initialise JButtons for UI
        manageUserAccountsButton.setText("Manage User Accounts");
        manageUserAccountsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showManageUserMenu();
            }
        }); 
        manageDepartmentsButton.setText("Manage Departments");
        manageDepartmentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showManageDepartmentMenu();
            }
        });
        manageDegreesButton.setText("Manage Degrees");
        manageDegreesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showManageDegreeMenu();
            }
        });
        manageModulesButton.setText("Manage Modules");
        manageModulesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showManageModuleMenu();
            }
        });
    }
    private void switchDatabase(ActionEvent e) {
        String selectedString = (String) databaseSelector.getSelectedItem();
        getAdminUI().getDatabaseView().switchDatabase(selectedString);
    }
    private void showManageUserMenu() {
        setVisible(false);
        getAdminUI().getDatabaseView().showUsers();
        getAdminUI().getUserMenu().setVisible(true);
    }
    private void showManageDepartmentMenu() {
        setVisible(false);
        getAdminUI().getDatabaseView().showDepartments();
        getAdminUI().getDepartmentMenu().setVisible(true);
    }
    private void showManageDegreeMenu() {
        setVisible(false);
        getAdminUI().getDatabaseView().showDegrees();
        getAdminUI().getDegreeMenu().setVisible(true);
    }
    private void showManageModuleMenu() {
        setVisible(false);
        getAdminUI().getDatabaseView().showModules();
        getAdminUI().getModuleMenu().setVisible(true);
    }
    protected void placeComponents() {
        javax.swing.GroupLayout adminMainMenuLayout = new javax.swing.GroupLayout(this);
      setLayout(adminMainMenuLayout);
      adminMainMenuLayout.setHorizontalGroup(
          adminMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(adminMainMenuLayout.createSequentialGroup()
              .addContainerGap()
              .addGroup(adminMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(adminMainMenuLayout.createSequentialGroup()
                      .addComponent(manageUserAccountsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                      .addGap(1, 1, 1))
                  .addComponent(databaseSelectorLabel))
              .addGap(5, 5, 5)
              .addGroup(adminMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(databaseSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGroup(adminMainMenuLayout.createSequentialGroup()
                      .addComponent(manageDepartmentsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                      .addComponent(manageDegreesButton, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                      .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                      .addComponent(manageModulesButton, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)))
              .addGap(228, 228, 228))
      );
      adminMainMenuLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {manageDegreesButton, manageDepartmentsButton, manageModulesButton, manageUserAccountsButton});
      adminMainMenuLayout.setVerticalGroup(
          adminMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(adminMainMenuLayout.createSequentialGroup()
              .addContainerGap()
              .addGroup(adminMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(manageUserAccountsButton)
                  .addComponent(manageDepartmentsButton)
                  .addComponent(manageDegreesButton)
                  .addComponent(manageModulesButton))
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addGroup(adminMainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(databaseSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(databaseSelectorLabel))
              .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
    }
}