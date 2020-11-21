package src.ui.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import src.sql.controller.*;

/**
 * UI for managing users, including
 * adding, removing and view users.
 */
public class ManageUsers extends Menu {

    private JLabel forenameLabel;
    private JLabel surnameLabel;
    private JLabel roleLabel;
    private JLabel passwordLabel;
    private JTextField forenameField;
    private JTextField surnameField;
    private JTextField passwordField;
    private JComboBox<String> roleSelector;
    private JButton addUserButton;
    private JButton removeUserButton;
    private AdminController controller;

    public ManageUsers(AdminUI adminUI) {

        super(adminUI);

        forenameLabel = new JLabel();
        surnameLabel = new JLabel();
        roleLabel = new JLabel();
        passwordLabel = new JLabel();
        forenameField = new JTextField();
        surnameField = new JTextField();
        passwordField = new JTextField();
        roleSelector = new JComboBox<>();
        addUserButton = new JButton();
        removeUserButton = new JButton();

        controller = new AdminController();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                        "Manage User Accounts", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 0, 12)));

        forenameLabel.setText("Forename: ");
        surnameLabel.setText("Surname: ");
        roleLabel.setText("Role: ");
        passwordLabel.setText("Password: ");

        addUserButton.setText("Add User");
        removeUserButton.setText("Remove User");
        roleSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Student", "Administrator", "Registrar", "Teacher" }));
        
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { addUser(); }
        });
        
        removeUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { removeUser(); }
        });

        placeComponents();
    }

    private void addUser() {
        String forename = forenameField.getText();
        String surname = surnameField.getText();
        String password = passwordField.getText();
        String role = (String) roleSelector.getSelectedItem();
        if (forename.isEmpty() || surname.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "One or more of the input fields is empty!");
        } else {
            controller.addUser(forename, surname, password, role);
            getAdminUI().getDatabaseView().showUsers();
        }
    }

    private void removeUser() {
        String username = getAdminUI().getDatabaseView().getSelectedRow(4);
        if (username == null) {
            JOptionPane.showMessageDialog(this, "No user selected!");
        } else {
            controller.removeUser(username);
            getAdminUI().getDatabaseView().showUsers();
        }
    }

    protected void placeComponents() {

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addUserButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeUserButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(passwordLabel)
                                    .addComponent(forenameLabel))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(forenameField, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(surnameLabel))
                    .addComponent(roleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(surnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roleSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {forenameField, roleSelector, surnameField});
        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addUserButton, backButton, removeUserButton});
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(forenameLabel)
                    .addComponent(forenameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(surnameLabel)
                    .addComponent(surnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roleLabel)
                    .addComponent(roleSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordLabel)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addUserButton)
                    .addComponent(removeUserButton)
                    .addComponent(backButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
}
