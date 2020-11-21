package src.ui.admin;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import src.sql.controller.*;

/**
 * UI for managing modules, including adding,
 * removing and viewing modules.
 */
public class ManageModules extends Menu {

    private JLabel moduleNameLabel;
    private JLabel leadDepartmentLabel;
    private JTextField moduleNameField;
    private JComboBox<String> departmentComboBox;
    private JButton addModuleButton;
    private JButton removeModuleButton;
    private JButton manageModuleLinks;
    private AdminController controller;

    public ManageModules(AdminUI adminUI) {

        super(adminUI);

        moduleNameLabel = new JLabel();
        leadDepartmentLabel = new JLabel(); 
        moduleNameField = new JTextField();
        departmentComboBox = new JComboBox<>();
        addModuleButton = new JButton();
        removeModuleButton = new JButton();
        manageModuleLinks = new JButton();

        controller = new AdminController();

        moduleNameLabel.setText("Module Name: ");
        leadDepartmentLabel.setText("Teaching Department: ");
        departmentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(controller.getDepartmentNames()));
        addModuleButton.setText("Add Module");
        removeModuleButton.setText("Remove Module");

        manageModuleLinks.setText("Manage Module Links");
        manageModuleLinks.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showModuleLinkMenu();
            }
        });

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                        "Manage Modules", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 0, 12)));
        placeComponents();
        
        addModuleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addModule();
            }
        });
        removeModuleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeModule();
            }
        });
    }
    
    private void addModule() {
        String moduleName = moduleNameField.getText();
        String teachingDepartment = (String) departmentComboBox.getSelectedItem();
        if (moduleName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Module name field empty!");
        } else {
            Boolean successfullyAdded = controller.addModule(moduleName, teachingDepartment);
            if (successfullyAdded) {
                getAdminUI().getDatabaseView().showModules();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Module could not be added to database! Could already be in database or Module name was invalid.");
            }

        }
    }

    private void removeModule() {
        String moduleID = getAdminUI().getDatabaseView().getSelectedRow(0);
        if (moduleID == null) {
            JOptionPane.showMessageDialog(this, "No module selected!");
        } else {
            Boolean successfullyRemoved = controller.removeModule(moduleID);
            if (successfullyRemoved)
                getAdminUI().getDatabaseView().showModules();
            else
                JOptionPane.showMessageDialog(this, "Module has too many dependencies!");
        }
    }
    private void showModuleLinkMenu() {
        getAdminUI().getModuleLinkMenu().setVisible(true);
        getAdminUI().getModuleLinkMenu().refreshDatabaseView();
        getAdminUI().getDatabaseView().showModuleLinks();
        getAdminUI().getModuleMenu().setVisible(false);
    }
    protected void placeComponents() {
        javax.swing.GroupLayout manageModuleMenuLayout = new javax.swing.GroupLayout(this);
        setLayout(manageModuleMenuLayout);
        manageModuleMenuLayout.setHorizontalGroup(
            manageModuleMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageModuleMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageModuleMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageModuleMenuLayout.createSequentialGroup()
                        .addComponent(moduleNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(moduleNameField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(leadDepartmentLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(departmentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(227, 227, 227))
                    .addGroup(manageModuleMenuLayout.createSequentialGroup()
                        .addComponent(addModuleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeModuleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(manageModuleLinks)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        manageModuleMenuLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addModuleButton, backButton, manageModuleLinks, removeModuleButton});
        manageModuleMenuLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {departmentComboBox, moduleNameField});

        manageModuleMenuLayout.setVerticalGroup(
            manageModuleMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, manageModuleMenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(manageModuleMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(moduleNameLabel)
                    .addComponent(moduleNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(leadDepartmentLabel)
                    .addComponent(departmentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(manageModuleMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(addModuleButton)
                    .addComponent(removeModuleButton)
                    .addComponent(manageModuleLinks))
                .addContainerGap())
        );

    }
}
