package src.ui.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import src.sql.controller.*;

/**
 * UI for managing departments including
 * removing, adding and viewing departments.
 */
public class ManageDepartments extends Menu {

    private JLabel departmentNameLabel;
    private JTextField departmentNameField;
    private JButton addDepartmentButton;
    private JButton removeDepartmentButton;
    private AdminController controller;

    public ManageDepartments(AdminUI adminUI) {

        super(adminUI);

        departmentNameLabel = new JLabel();
        departmentNameField = new JTextField();
        addDepartmentButton = new JButton();
        removeDepartmentButton = new JButton();
        
        controller = new AdminController();

        departmentNameLabel.setText("Department Name: ");
        addDepartmentButton.setText("Add Department");
        removeDepartmentButton.setText("Remove Department");

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                        "Manage Departments", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 0, 12)));
        placeComponents();
        
        addDepartmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDepartment();
            }
        });
        
        removeDepartmentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeDepartment();
            }
        });
    }

    private void addDepartment() {
        String departmentName = departmentNameField.getText();
        if (departmentName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Department Name Field Empty!");
        } else {
            Boolean successfullyAdded = controller.addDepartment(departmentName);
            if (successfullyAdded) {
                getAdminUI().getDatabaseView().showDepartments();
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Error with Degree name: Either name is too short or " + "name already exists.");
            }
        }
    }

    private void removeDepartment() {
        String departmentID = getAdminUI().getDatabaseView().getSelectedRow(0);
        if (departmentID == null) {
            JOptionPane.showMessageDialog(this, "No department selected!");
        } else {
            Boolean successfullyRemoved = controller.removeDepartment(departmentID);
            if (successfullyRemoved)
                getAdminUI().getDatabaseView().showDepartments();
            else
                JOptionPane.showMessageDialog(this, "Department has too many dependencies!");
        }
    }

    protected void placeComponents() {
        javax.swing.GroupLayout manageDepartmentMenuLayout = new javax.swing.GroupLayout(this);
        setLayout(manageDepartmentMenuLayout);
        manageDepartmentMenuLayout.setHorizontalGroup(
            manageDepartmentMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageDepartmentMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageDepartmentMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(manageDepartmentMenuLayout.createSequentialGroup()
                        .addComponent(departmentNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(departmentNameField))
                    .addGroup(manageDepartmentMenuLayout.createSequentialGroup()
                        .addComponent(addDepartmentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeDepartmentButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)))
                .addGap(478, 478, 478))
        );
        manageDepartmentMenuLayout.setVerticalGroup(
            manageDepartmentMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(manageDepartmentMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(manageDepartmentMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(departmentNameLabel)
                    .addComponent(departmentNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(manageDepartmentMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(addDepartmentButton)
                    .addComponent(removeDepartmentButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
}
