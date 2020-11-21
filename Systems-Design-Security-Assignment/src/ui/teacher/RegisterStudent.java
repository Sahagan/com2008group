package src.ui.teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.sql.controller.TeacherController;

/**
* Register Students UI allows students to be registered for new period of study
* Checks if they can be passed, failed or graduated and validates their final result
*/

public class RegisterStudent extends Menu{
    private javax.swing.JLabel weightedMeanLabel;
    private javax.swing.JLabel finalResultLabel;
    private javax.swing.JLabel selectStuLabel;
    private javax.swing.JTextField weightedMean;
    private javax.swing.JTextField finalResult;
    private javax.swing.JButton passButton;
    private javax.swing.JButton failButton;
    private javax.swing.JButton graduateButton;
    private javax.swing.JButton backButton;
    private javax.swing.JComboBox<String> students;
    private TeacherController controller;

    public RegisterStudent (TeacherGUI teacherui) {
        super(teacherui);
        controller = new TeacherController();
        setVisible(true);
        initComponents();
        placeComponents();
    }

    public void initComponents() {
        selectStuLabel = new JLabel();
        selectStuLabel.setText("Select a student");
        weightedMeanLabel = new javax.swing.JLabel();
        weightedMeanLabel.setText("Weighted mean for current period");
        weightedMean = new javax.swing.JTextField();
        weightedMean.setEditable(false);
        finalResultLabel = new javax.swing.JLabel();
        finalResultLabel.setText("Final result for this period");
        finalResult = new javax.swing.JTextField();
        finalResult.setEditable(false);
        students = new JComboBox<String>();

        failButton = new javax.swing.JButton();
        failButton.setText("Fail Student");
        failButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                failStudent();
            }
        });

        passButton = new javax.swing.JButton();
        passButton.setText("Pass Student");
        passButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passStudent();
            }
        });

        graduateButton = new javax.swing.JButton();
        graduateButton.setText("Graduate Student");
        graduateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                graduateStudent();
            }
        });

        backButton = new javax.swing.JButton();
        backButton.setText("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                getTeacherUI().getMainMenu().setVisible(true);
            }
        });

        students.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    //Change mean/result text everytime new student selected
                    setMeanText();
                    setResultText();
                }
            }
        });

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                                      "Register student", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                        new java.awt.Font("Trebuchet MS", 0, 24)));
        displayStudents();
        setMeanText();
        setResultText();
    }

    private void displayStudents() {
        String[] stuList = controller.getStudents();
        students.setModel(new DefaultComboBoxModel(stuList));
    }

    private void setMeanText() {
        //get and display weighted mean
        double value = controller.getWeightedMean(String.valueOf(students.getSelectedItem()));
        weightedMean.setText(Double.toString(value));
    }

    private void setResultText() {
        String result = controller.theDegreeResult(String.valueOf(students.getSelectedItem()));
        finalResult.setText(result);
    }

    private void failStudent() {
        String theResult = finalResult.getText();
        if(theResult.equalsIgnoreCase("fail") || theResult.equalsIgnoreCase("Resit for pass(non-honours) degree")) {
            String student = String.valueOf(students.getSelectedItem());
            controller.createFailStudent(student);
            JOptionPane.showMessageDialog(this, "Student information has been successfuly updated!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(this, "The selected student has not failed!",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void passStudent() {
        String theResult = finalResult.getText();
        if(theResult.equalsIgnoreCase("First Class") || theResult.equalsIgnoreCase("Upper Second")
            || theResult.equalsIgnoreCase("Lower Second") || theResult.equalsIgnoreCase("Conceded pass")
            || theResult.equalsIgnoreCase("Third Class") || theResult.equalsIgnoreCase("Pass (non-honours)s")) {
            String student = String.valueOf(students.getSelectedItem());
            if(controller.createPassStudent(student)){
                JOptionPane.showMessageDialog(this, "Student information has been successfuly updated!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(this, "The selected student has not passed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(this, "The selected student has not passed!",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void graduateStudent() {
        String theResult = finalResult.getText();
        String student = String.valueOf(students.getSelectedItem());
        //If createGraduate returns true then student has been graduated, else student can't graduate
        if(controller.createGraduate(student)) {
            JOptionPane.showMessageDialog(this, "Student information has been successfuly updated!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(this, "The selected student can not graduate yet!",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void placeComponents() {
        javax.swing.GroupLayout registerStudentLayout = new javax.swing.GroupLayout(this);
        setLayout(registerStudentLayout);
        registerStudentLayout.setHorizontalGroup(
            registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerStudentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(registerStudentLayout.createSequentialGroup()
                        .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(registerStudentLayout.createSequentialGroup()
                                .addComponent(finalResultLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(96, 96, 96))
                            .addGroup(registerStudentLayout.createSequentialGroup()
                                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(weightedMeanLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(selectStuLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(34, 34, 34)
                                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(weightedMean, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(finalResult, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(students, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(39, 39, 39))
                    .addGroup(registerStudentLayout.createSequentialGroup()
                        .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(graduateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(failButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(passButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        registerStudentLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {finalResult, students, weightedMean});

        registerStudentLayout.setVerticalGroup(
            registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registerStudentLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(registerStudentLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(selectStuLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(students))
                .addGap(18, 18, 18)
                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(weightedMean)
                    .addGroup(registerStudentLayout.createSequentialGroup()
                        .addComponent(weightedMeanLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(6, 6, 6)))
                .addGap(18, 18, 18)
                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(registerStudentLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(finalResult))
                    .addComponent(finalResultLabel))
                .addGap(18, 18, 18)
                .addGroup(registerStudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(passButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(failButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(graduateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }
}
