package src.ui.admin;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import src.sql.controller.*;

/**
 * UI for the management of degrees, including add, removing and viewing
 * degrees.
 */
public class ManageDegrees extends Menu {

    private JLabel degreeNameLabel;
    private JLabel leadDepartmentLabel;
    private JLabel levelOfStudyLabel;
    private JLabel yearPlacementLabel;
    private JTextField degreeNameField;
    private JComboBox<String> leadDepartmentDropDown;
    private JComboBox<String> levelOfStudyDropDown;
    private JButton addDegreeButton;
    private JButton removeDegreeButton;
    private JButton manageDegreeLinksButton;
    private AdminController controller;

    public ManageDegrees(AdminUI adminUI) {

        super(adminUI);

        degreeNameLabel = new JLabel();
        leadDepartmentLabel = new JLabel();
        levelOfStudyLabel = new JLabel();
        yearPlacementLabel = new JLabel();
        degreeNameField = new JTextField();
        leadDepartmentDropDown = new JComboBox<>();
        levelOfStudyDropDown = new JComboBox<>();
        addDegreeButton = new JButton();
        removeDegreeButton = new JButton();
        manageDegreeLinksButton = new JButton();

        controller = new AdminController();

        degreeNameLabel.setText("Degree Name: ");
        leadDepartmentLabel.setText("Lead Department: ");
        levelOfStudyLabel.setText("Level of Study: ");
        yearPlacementLabel.setText("Year Placement: ");
        leadDepartmentDropDown.setModel(new javax.swing.DefaultComboBoxModel<>(controller.getDepartmentNames()));
        levelOfStudyDropDown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "123", "1234", "4", "12P3", "123P4"}));
        addDegreeButton.setText("Add Degree");
        removeDegreeButton.setText("Remove Degree");
        manageDegreeLinksButton.setText("Manage Degree Links");
        manageDegreeLinksButton.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDegreeLinkMenu();
            }
        });

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                        "Manage Degrees", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 0, 12)));
        placeComponents();
        
        addDegreeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDegree();
            }
        });

        removeDegreeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeDegree();
            }
        });

    }
    private void addDegree() {
        String degreeName = degreeNameField.getText();
        String leadDepartment = (String) leadDepartmentDropDown.getSelectedItem();
        String levelOfStudy = (String) levelOfStudyDropDown.getSelectedItem();
        if (degreeName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Degree name is empty!");
        } else {
            Boolean successfullyAdded = controller.addDegree(degreeName, leadDepartment, levelOfStudy);
            if (successfullyAdded) {
                getAdminUI().getDatabaseView().showDegrees();
            } else {
                JOptionPane.showMessageDialog(this, "Error with Degree name: Either name is too short or " +
                                                    "name already exists.");
            }
        }
    }
    private void removeDegree() {
        String degreeCode = getAdminUI().getDatabaseView().getSelectedRow(0);
        if (degreeCode == null) {
            JOptionPane.showMessageDialog(this, "No degree selected!");
        } else {
            Boolean successfullyRemoved = controller.removeDegree(degreeCode);
            if (successfullyRemoved)
                getAdminUI().getDatabaseView().showDegrees();
            else
                JOptionPane.showMessageDialog(this, "Degree has too many dependencies!");
        }
    }
    private void showDegreeLinkMenu() {
        getAdminUI().getDegreeLinkMenu().setVisible(true);
        getAdminUI().getDegreeLinkMenu().refreshDatabaseView();
        getAdminUI().getDatabaseView().showDegreeLinks();
        getAdminUI().getDegreeMenu().setVisible(false);
    }
    protected void placeComponents(){
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup().addGroup(layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(levelOfStudyLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(degreeNameLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(degreeNameField)
                                                .addComponent(levelOfStudyDropDown, 0, 237, Short.MAX_VALUE))
                                        .addGap(18, 18, 18).addComponent(leadDepartmentLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(leadDepartmentDropDown, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                217, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup().addComponent(addDegreeButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(removeDegreeButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(manageDegreeLinksButton)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(backButton)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
                new java.awt.Component[] { addDegreeButton, backButton, manageDegreeLinksButton, removeDegreeButton });

        layout.setVerticalGroup(layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(degreeNameLabel)
                                .addComponent(degreeNameField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(leadDepartmentLabel).addComponent(leadDepartmentDropDown,
                                        javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(levelOfStudyLabel).addComponent(levelOfStudyDropDown,
                                        javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(addDegreeButton).addComponent(removeDegreeButton).addComponent(backButton)
                                .addComponent(manageDegreeLinksButton))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    }
}
