package src.ui.registrar;

import java.awt.List;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import src.sql.controller.RegistrarController;
import src.ui.database.DatabaseView;

/**
 * UI for adding/removing students
 */
public class ManageStudents extends JPanel {

    private javax.swing.JButton addStudentButton;
    private javax.swing.JButton backButton;
    private javax.swing.JComboBox<String> degreeName;
    private javax.swing.JLabel degreeNameLabel;
    private javax.swing.JPanel displayArea;
    private javax.swing.JTable displayTable;
    private javax.swing.JTextField firstName;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JTextField lastName;
    private javax.swing.JLabel lastNameLabel;
    private javax.swing.JLabel mainLabel;
    private javax.swing.JTextField tutor;
    private javax.swing.JLabel tutorLabel;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JTextField levelOfStudy;
    private javax.swing.JButton removeStudentButton;
    private DatabaseView scrollTable;
    private javax.swing.JPanel userPanel;
    private javax.swing.JPanel mainPanel;
    private RegistrarUI registrarUI;
    private javax.swing.JButton logOffButton;
    private RegistrarController controller;

    public ManageStudents(RegistrarUI registrarUI) {
        this.registrarUI = registrarUI;
        initComponents();
        
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        controller = new RegistrarController();
        userPanel = new javax.swing.JPanel();
        mainPanel = new javax.swing.JPanel();
        addStudentButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        firstNameLabel = new javax.swing.JLabel();
        lastNameLabel = new javax.swing.JLabel();
        firstName = new javax.swing.JTextField();
        lastName = new javax.swing.JTextField();
        removeStudentButton = new javax.swing.JButton();
        tutorLabel = new javax.swing.JLabel();
        String[] array = {"1","4"};
        tutor = new javax.swing.JTextField();
        degreeName = new JComboBox<>();
        degreeNameLabel = new javax.swing.JLabel();
        displayArea = new javax.swing.JPanel();
        scrollTable = new DatabaseView(this);
        displayTable = new javax.swing.JTable();
        mainLabel = new javax.swing.JLabel();
        logOffButton = new javax.swing.JButton();
        levelLabel = new javax.swing.JLabel();
        levelOfStudy = new javax.swing.JTextField();
        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                "Manage Students", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Trebuchet MS", 0, 17)));

        addStudentButton.setFont(new java.awt.Font("Arial", 0, 14));
        addStudentButton.setText("Add Student");
        addStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStudentButtonActionPerformed(evt);
            }
        });
        
        backButton.setFont(new java.awt.Font("Arial", 0, 14));
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        mainLabel.setFont(new java.awt.Font("Arial", 1, 18));
        mainLabel.setText("Registrar");

        logOffButton.setText("Sign Out");
        logOffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOffButtonActionPerformed(evt);
            }
        });

        firstNameLabel.setFont(new java.awt.Font("Arial", 0, 18));
        firstNameLabel.setText("First Name");

        lastNameLabel.setFont(new java.awt.Font("Arial", 0, 18));
        lastNameLabel.setText("Last Name");

        removeStudentButton.setFont(new java.awt.Font("Arial", 0, 14));
        removeStudentButton.setText("Remove Selected Student");
        removeStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeStudentButtonActionPerformed(evt);
            }
        });

        tutorLabel.setFont(new java.awt.Font("Arial", 0, 18));
        tutorLabel.setText("Tutor");

        degreeNameLabel.setFont(new java.awt.Font("Arial", 0, 18));
        degreeNameLabel.setText("Degreee Name");

        levelLabel.setFont(new java.awt.Font("Arial", 0, 18));
        levelLabel.setText("Period of study e.g. 2018");

//        displayTable.setFont(new java.awt.Font("Arial", 0, 18));
//        displayTable.setModel(new javax.swing.table.DefaultTableModel(
//            new Object [][] {
//
//            },
//            new String [] {
//                "Title 1", "Title 2", "Title 3", "Title 4"
//            }
//        ));
//        scrollTable.setViewportView(displayTable);
        scrollTable.showStudents(); 
        
        degreeName.setModel(new javax.swing.DefaultComboBoxModel<>(controller.getDegreeCodes()));
        
        mainLabel.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        mainLabel.setText("Registrar");

        placeComponents();
    }

    private RegistrarUI getRegistrarUI() {
        return registrarUI;
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        getRegistrarUI().showRegistrarMenu();
    }
    
    private void addStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        if(lastName.getText().length()==0 && firstName.getText().length()==0) {
            JOptionPane.showMessageDialog(this, "Some of the fields are empty!");
        } else if(isInteger(levelOfStudy.getText())) {
            JOptionPane.showMessageDialog(this, "Valid infomation, adding student (may take a minute)");
            controller.addStudent(firstName.getText(),lastName.getText(),degreeName.getSelectedItem().toString(),tutor.getText(),levelOfStudy.getText());
            scrollTable.showStudents();
            JOptionPane.showMessageDialog(this, "Created with password of '123'");
        } else {
            JOptionPane.showMessageDialog(this, "Period of study must be a vaild year!");
        }
        
        
    }
    
    private void removeStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        //controller.removeStudent(firstName.getText(),lastName.getText());
        removeStudent();
    }
    
    private void removeStudent (){
        String username = scrollTable.getSelectedRow(4);
        
        if (username == null) JOptionPane.showMessageDialog(this, "No user selected!");
        else{
            controller.removeStudent(username);
            scrollTable.showStudents();
        }
    }

    private void logOffButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
    
    public static boolean isInteger(String s) {
        
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        
        return true;
        
    }

    private void placeComponents() {
        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lastNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(firstNameLabel))
                        .addGap(18, 18, 18)
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lastName, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(firstName))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tutorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(degreeNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tutor, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(degreeName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(levelLabel)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(levelOfStudy, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE))
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(addStudentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(removeStudentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        userPanelLayout.setVerticalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(userPanelLayout.createSequentialGroup()
                                .addComponent(levelLabel)
                                .addGap(2, 2, 2))
                            .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(tutor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(userPanelLayout.createSequentialGroup()
                                    .addGap(2, 2, 2)
                                    .addComponent(levelOfStudy, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(degreeName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(firstNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tutorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(degreeNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addStudentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeStudentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        displayArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        javax.swing.GroupLayout displayAreaLayout = new javax.swing.GroupLayout(displayArea);
        displayArea.setLayout(displayAreaLayout);
        displayAreaLayout.setHorizontalGroup(
            displayAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollTable)
        );
        displayAreaLayout.setVerticalGroup(
            displayAreaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayAreaLayout.createSequentialGroup()
                .addComponent(scrollTable, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(displayArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(userPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
            ).addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(mainLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(displayArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        ));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
);
    }

}
