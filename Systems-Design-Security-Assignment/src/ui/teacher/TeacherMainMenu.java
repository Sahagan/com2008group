package src.ui.teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.sql.controller.TeacherController;

/**
 * UI for the main menu of teacher
 */
public class TeacherMainMenu extends Menu {
    private javax.swing.JButton editGrades;
    private javax.swing.JButton registerStudent;
    private javax.swing.JButton viewResults;
    private TeacherController controller;

    public TeacherMainMenu (TeacherGUI teacherui) {
        super(teacherui);
        controller = new TeacherController();
        setVisible(true);
        initComponents();
        placeComponents();
    }

    private void initComponents() {
        editGrades = new JButton();
        registerStudent = new JButton();
        viewResults = new JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                                      "Teacher Main Menu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                        new java.awt.Font("Trebuchet MS", 0, 24)));

        editGrades.setText("Add or Update Grades");
        editGrades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editGradesMenu();
            }
        });
        registerStudent.setText("Register student for next period of study");
        registerStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerStudentMenu();
            }
        });
        viewResults.setText("View student results & information");
        viewResults.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewStudentInfo();
            }
        });
    }

    private void editGradesMenu() {
        setVisible(false);
        getTeacherUI().getEditGrade().setVisible(true);
    }

    private void registerStudentMenu() {
        setVisible(false);
        getTeacherUI().getRegisterStudent().setVisible(true);
    }

    private void viewStudentInfo() {
        setVisible(false);
        getTeacherUI().getStudentInfo().setVisible(true);
    }

    protected void placeComponents() {
        javax.swing.GroupLayout mainMenuLayout = new javax.swing.GroupLayout(this);
        setLayout(mainMenuLayout);
        mainMenuLayout.setHorizontalGroup(
            mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(editGrades, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(registerStudent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(viewResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(81, 81, 81))
        );
        mainMenuLayout.setVerticalGroup(
            mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editGrades, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registerStudent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(viewResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(73, 73, 73))
        );
    }
}
