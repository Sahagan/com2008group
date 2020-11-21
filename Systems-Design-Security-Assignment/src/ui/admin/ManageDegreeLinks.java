package src.ui.admin;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import src.sql.controller.AdminController;

/**
 * UI for linking degrees to departments
 */
public class ManageDegreeLinks extends LinkingMenu {

    private AdminController controller;

    public ManageDegreeLinks(AdminUI adminUI) {
        
        super(adminUI);

        controller = new AdminController();

        setAddLinkButtonText("Add Degree Link");
        setRemoveLinkButtonText("Remove Degree Link");
        setSelectParentLabelText("Select Degree: ");
        setChildTableLabelText("Select Department to add from table: ");
        setMenuTitle("Manage Degrees Links");
        
        refreshDatabaseView();

        getAddLinkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addDegreeLink();
            }
        });
        getRemoveLinkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeDegreeLink();
            }
        });

        setBackButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
                getAdminUI().getDegreeMenu().setVisible(true);
                getAdminUI().getDatabaseView().showDegrees();
            }
        });

        placeComponents();
    }
    private void addDegreeLink() {
        String degreeName = (String) getParentSelector().getSelectedItem();
        String departmentCode = getChildSelectorTable().getSelectedRow(0);
        if (departmentCode == null) {
            JOptionPane.showMessageDialog(this, "No Department Selected!");
        } else {
            Boolean successfullyAdded = controller.addDegreeLink(degreeName, departmentCode, "no degree code");
            if (successfullyAdded) {
                getAdminUI().getDatabaseView().showDegreeLinks();
            } else {
                JOptionPane.showMessageDialog(this, "Degree Link already exists");
            }
        }
    }
    private void removeDegreeLink() {
        String degreeCode = getAdminUI().getDatabaseView().getSelectedRow(0);
        String departmentCode = getAdminUI().getDatabaseView().getSelectedRow(2);
        if (degreeCode == null || departmentCode == null) {
            JOptionPane.showMessageDialog(this, "No Degree Link selected!");
        } else {
            if(controller.removeDegreeLink(degreeCode, departmentCode)) {
                getAdminUI().getDatabaseView().showDegreeLinks();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid choice! Can't delete Lead Department Link");
            }
            
        }
    }
    public void refreshDatabaseView() {
        getChildSelectorTable().showDepartments(); 
        setParentSelectorText(controller.getDegreeNames());
    }
    protected void placeComponents() {
        javax.swing.GroupLayout linkingMenuLayout = new javax.swing.GroupLayout(this);
        setLayout(linkingMenuLayout);
        linkingMenuLayout.setHorizontalGroup(linkingMenuLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(linkingMenuLayout.createSequentialGroup().addContainerGap().addGroup(linkingMenuLayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(linkingMenuLayout.createSequentialGroup().addComponent(getSelectParentLabel())
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(getParentSelector(), 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(getChildTableLabel())
                        .addComponent(getChildSelectorTable(), javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(linkingMenuLayout.createSequentialGroup().addComponent(getAddLinkButton())
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(getRemoveLinkButton())
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(backButton)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        linkingMenuLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
                new java.awt.Component[] { getAddLinkButton(), backButton, getRemoveLinkButton() });
        linkingMenuLayout.setVerticalGroup(linkingMenuLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(linkingMenuLayout.createSequentialGroup().addContainerGap()
                        .addGroup(linkingMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(getSelectParentLabel()).addComponent(getParentSelector(),
                                        javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(getChildTableLabel())
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(getChildSelectorTable(), javax.swing.GroupLayout.PREFERRED_SIZE, 100,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(linkingMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(backButton).addComponent(getAddLinkButton()).addComponent(getRemoveLinkButton()))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    }
}