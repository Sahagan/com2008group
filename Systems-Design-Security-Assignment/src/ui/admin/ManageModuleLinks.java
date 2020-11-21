package src.ui.admin;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import src.sql.controller.AdminController;

/**
 * UI for linking modules to degrees, including
 * removing, adding and viewing module links.
 */
public class ManageModuleLinks extends LinkingMenu {

    private JLabel degreeLevelLabel;
    private JLabel seasonLabel;
    private JLabel dissertationLabel;
    private JLabel coreLabel;
    private JComboBox<String> degreeLevelDropDown;
    private JComboBox<String> seasonDropDown;
    private JComboBox<String> coreDropDown;
    private JComboBox<String> typeOfModule;
    private AdminController controller;

    public ManageModuleLinks(AdminUI adminUI) {

        super(adminUI);

        controller = new AdminController();

        setAddLinkButtonText("Add Module Link");
        setRemoveLinkButtonText("Remove Module Link");
        setSelectParentLabelText("Select Module: ");
        setChildTableLabelText("Select Degree to add from table (Listed degrees depend on selected Degree Level): ");
        setMenuTitle("Manage Module Links");

        degreeLevelLabel = new JLabel();
        seasonLabel = new JLabel();
        dissertationLabel = new JLabel();
        coreLabel = new JLabel();
        degreeLevelDropDown = new JComboBox<>();
        seasonDropDown = new JComboBox<>();
        coreDropDown = new JComboBox<>();
        typeOfModule = new JComboBox<>();

        degreeLevelLabel.setText("Degree Level: ");
        seasonLabel.setText("Season: ");
        dissertationLabel.setText("Regular Module / Dissertation / Year in Industry: ");
        degreeLevelDropDown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Level 1", "Level 2", "Level 3", "Level 4", "Level P"}));
        seasonDropDown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Autumn", "Spring", "Summer", "All Year"}));
        coreDropDown.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Core", "Not Core"}));
        typeOfModule.setModel((new javax.swing.DefaultComboBoxModel<>(new String[] {"Regular Module", "Dissertation", "Year In Industry"})));

        degreeLevelDropDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDegreesWithLevel();
            }
        });
        getAddLinkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addModuleLink();
            }
        });
        getRemoveLinkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeModuleLink();
            }
        });   
        setBackButtonActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setVisible(false);
                getAdminUI().getModuleMenu().setVisible(true);
                getAdminUI().getDatabaseView().showModules();
            }
        });
        refreshDatabaseView();
        placeComponents();
    }
    private void showDegreesWithLevel() {
        String degreeLevel = (String) degreeLevelDropDown.getSelectedItem();
        System.out.println("degreeLevel = " + degreeLevel);
        switch(degreeLevel) {
            case "Level 1":
                getChildSelectorTable().showDegrees("1");
                break;
            case "Level 2":
                getChildSelectorTable().showDegrees("2");
                break;
            case "Level 3":
                getChildSelectorTable().showDegrees("3");
                break;
            case "Level 4":
                getChildSelectorTable().showDegrees("4");
                break;
            case "Level P":
                getChildSelectorTable().showDegrees("P");
                break;
        }
    }
    private void addModuleLink() {
        String moduleCode = ((String) getParentSelector().getSelectedItem()).substring(0, 7);
        String degreeCode = (String) getChildSelectorTable().getSelectedRow(0);
        String level = ((String) degreeLevelDropDown.getSelectedItem()).substring(6);
        //System.out.println("level = "+level);
        String semester = (String) seasonDropDown.getSelectedItem();
        String coreOrNot = (String) coreDropDown.getSelectedItem();
        String typeOfModuleString = (String) typeOfModule.getSelectedItem();

        if (degreeCode == null) {
            JOptionPane.showMessageDialog(this, "One or more input fields are empty!");
    
        } else if (level.equals("P") ^ typeOfModuleString.equals("Year In Industry")) {
            JOptionPane.showMessageDialog(this, "Can only add placement during a year in industry!");
        
        } else {
            Boolean successfullyAdded = controller.addModuleLink(moduleCode, degreeCode, level, semester, coreOrNot, typeOfModuleString);
            if (successfullyAdded) {
                getAdminUI().getDatabaseView().showModuleLinks();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid infomation added. Wrong infomation could be added" + 
                                                    " or module link could already exist");
            }
        }
    }
    private void removeModuleLink() {
        String degreeCode = getAdminUI().getDatabaseView().getSelectedRow(0);
        String moduleCode = getAdminUI().getDatabaseView().getSelectedRow(2);
        if (degreeCode == null || moduleCode == null) {
            JOptionPane.showMessageDialog(this, "No Module Link selected!");
        } else {
            if (controller.removeModuleLink(degreeCode, moduleCode)) {
                getAdminUI().getDatabaseView().showModuleLinks();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid choice! Can't delete Module Link");
            }

        }
    }
    public void refreshDatabaseView() {
        showDegreesWithLevel();
        setParentSelectorText(controller.getModuleNames());
    }
    protected void placeComponents() {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(degreeLevelLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(getSelectParentLabel(), javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(getParentSelector(), 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(degreeLevelDropDown, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(seasonLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(seasonDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, 87,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10)
                                        .addComponent(coreLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(coreDropDown,javax.swing.GroupLayout.PREFERRED_SIZE,javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(dissertationLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(typeOfModule, javax.swing.GroupLayout.PREFERRED_SIZE, 107,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup().addGap(10, 10, 10)
                                                .addComponent(getAddLinkButton())
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(getRemoveLinkButton())
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(backButton))
                                        .addGroup(layout.createSequentialGroup().addGap(10, 10, 10)
                                                .addComponent(getChildSelectorTable(),
                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup().addGap(10, 10, 10)
                                                .addComponent(getChildTableLabel()))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
                new java.awt.Component[] { getAddLinkButton(), backButton, getRemoveLinkButton() });

        layout.setVerticalGroup(layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(getSelectParentLabel()).addComponent(getParentSelector(),
                                        javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(degreeLevelLabel)
                                .addComponent(degreeLevelDropDown, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(seasonLabel)
                                .addComponent(seasonDropDown, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dissertationLabel)
                                .addComponent(typeOfModule,
                                        javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(coreLabel)
                                .addComponent(coreDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, 
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(getChildTableLabel())
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(getChildSelectorTable(), javax.swing.GroupLayout.PREFERRED_SIZE, 100,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout
                                .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(backButton).addComponent(getAddLinkButton())
                                .addComponent(getRemoveLinkButton()))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    }
}