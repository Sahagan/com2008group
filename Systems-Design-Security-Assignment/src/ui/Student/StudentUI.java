package src.ui.Student;

import src.sql.controller.StudentController;
import src.ui.MainWindow;
import java.awt.*;
import java.awt.event.*;

/**
 * UI for the student to view their records
 */
public class StudentUI extends javax.swing.JPanel {

    private javax.swing.JPanel StudentUI;
    private javax.swing.JLabel gradeLabel;
    private javax.swing.JLabel gradeOutput;
    private javax.swing.JScrollPane databaseView;
    private javax.swing.JTable databaseTable;
    private javax.swing.JButton logOffButton;
    private javax.swing.JLabel yearLabel;
    private javax.swing.JComboBox<String> yearSelector;
    private StudentController controller;
    private String username;
    private String periodOfStudy;
    private MainWindow mainWindow;

    public StudentUI(MainWindow mainWindow, String username) {
        this.mainWindow = mainWindow;
        this.username = username;
        controller = new StudentController();
        initComponents();
    }

    private void initComponents() {

        String[] periodsOfStudy = controller.getPeriodsOfStudy(username);
        if (periodsOfStudy.length > 0)
            periodOfStudy = periodsOfStudy[0];

        StudentUI = new javax.swing.JPanel();
        databaseView = new javax.swing.JScrollPane();
        databaseTable = new javax.swing.JTable();
        yearSelector = new javax.swing.JComboBox<>();
        yearLabel = new javax.swing.JLabel();
        logOffButton = new javax.swing.JButton();
        gradeLabel = new javax.swing.JLabel();
        gradeOutput = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(
                        javax.swing.BorderFactory.createEtchedBorder(), "Student Page: Welcome user " + username,
                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION,
                        new java.awt.Font("Trebuchet MS", 0, 24))); // NOI18N

        yearSelector.setModel(new javax.swing.DefaultComboBoxModel<>(
                        controller.getPeriodsOfStudy(username)));
        yearSelector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                periodOfStudy = (String) yearSelector.getSelectedItem();
                refreshDatabase();
            }
        });

        yearLabel.setText("Select Year: ");

        logOffButton.setText("Log Off");
        logOffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    mainWindow.showLogInWindow();
            }
        });

        gradeLabel.setText("Grade for that Year: ");

        gradeOutput.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        gradeOutput.setText(controller.getYearsGrade(username, periodOfStudy));
        refreshDatabase();
        placeComponenets();
    }
    private void refreshDatabase() {
        databaseTable.setModel(new javax.swing.table.DefaultTableModel(
                controller.getYearsModules(username, periodOfStudy),
                new String[] { "Mark Id", "Module Code", "Record ID", "Mark", "Resit Mark" }));
        databaseView.setViewportView(databaseTable);
    }

    private void placeComponenets() {
        javax.swing.GroupLayout StudentUILayout = new javax.swing.GroupLayout(this);
        setLayout(StudentUILayout);
        StudentUILayout.setHorizontalGroup(StudentUILayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(StudentUILayout.createSequentialGroup().addContainerGap().addGroup(StudentUILayout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(databaseView, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
                        .addGroup(StudentUILayout.createSequentialGroup()
                                .addGroup(StudentUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(logOffButton)
                                        .addGroup(StudentUILayout.createSequentialGroup().addComponent(yearLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(yearSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 215,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18).addComponent(gradeLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(gradeOutput)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap()));
        StudentUILayout.setVerticalGroup(StudentUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, StudentUILayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(StudentUILayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(yearLabel)
                                .addComponent(yearSelector, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(gradeLabel).addComponent(gradeOutput))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(databaseView, javax.swing.GroupLayout.PREFERRED_SIZE, 357,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(logOffButton)
                        .addContainerGap()));
    }
}
