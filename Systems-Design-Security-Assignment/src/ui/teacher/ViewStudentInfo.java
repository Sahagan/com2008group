package src.ui.teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.sql.controller.StudentController;
import src.sql.controller.TeacherController;

/**
 * Display all information about a student that we hold in the database
 * Also includes all grades from all periods of study displayed in a table
 */
public class ViewStudentInfo extends Menu{
    private javax.swing.JLabel selStu;
    private javax.swing.JLabel foreNameLabel;
    private javax.swing.JLabel yearLabel;
    private javax.swing.JLabel surnameLabel;
    private javax.swing.JLabel regNoLabel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel userNameLabel;
    private javax.swing.JLabel studyLabel;
    private javax.swing.JLabel degreeLabel;
    private javax.swing.JLabel tutorLabel;
    private javax.swing.JTextField forename;
    private javax.swing.JTextField surname;
    private javax.swing.JTextField resgistrationNumber;
    private javax.swing.JTextField email;
    private javax.swing.JTextField userName;
    private javax.swing.JTextField studyLevel;
    private javax.swing.JTextField degree;
    private javax.swing.JTextField tutor;
    private javax.swing.JScrollPane databaseView;
    private javax.swing.JTable databaseTable;
    private javax.swing.JButton backButton;
    private javax.swing.JComboBox<String> studentList;
    private javax.swing.JComboBox<String> yearSelector;
    private StudentController stuController;
    private TeacherController controller;

    public ViewStudentInfo (TeacherGUI teacherui) {
        super(teacherui);
        controller = new TeacherController();
        stuController = new StudentController();
        setVisible(true);
        initComponents();
        placeComponents();
    }

    public void initComponents() {
        selStu = new javax.swing.JLabel();
        studentList = new javax.swing.JComboBox<String>();
        yearSelector = new javax.swing.JComboBox<String>();
        yearLabel = new javax.swing.JLabel();
        foreNameLabel = new javax.swing.JLabel();
        forename = new javax.swing.JTextField();
        surnameLabel = new javax.swing.JLabel();
        surname = new javax.swing.JTextField();
        regNoLabel = new javax.swing.JLabel();
        resgistrationNumber = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        userNameLabel = new javax.swing.JLabel();
        userName = new javax.swing.JTextField();
        studyLabel = new javax.swing.JLabel();
        studyLevel = new javax.swing.JTextField();
        databaseView = new javax.swing.JScrollPane();
        databaseTable = new javax.swing.JTable();
        degreeLabel = new javax.swing.JLabel();
        degree = new javax.swing.JTextField();
        tutorLabel = new javax.swing.JLabel();
        tutor = new javax.swing.JTextField();
        selStu.setText("Select student to view");
        foreNameLabel.setText("Forename");
        surnameLabel.setText("Surname");
        regNoLabel.setText("Registration Number");
        emailLabel.setText("E-Mail Addressl");
        userNameLabel.setText("User Name");
        studyLabel.setText("Study Level");
        yearLabel.setText("Select Year");
        degreeLabel.setText("Degree Title");
        tutorLabel.setText("Tutor");
        backButton = new javax.swing.JButton();
        backButton.setText("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                getTeacherUI().getMainMenu().setVisible(true);
            }
        });

        studentList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    displayInformation(String.valueOf(studentList.getSelectedItem()));
                    refreshDatabase(String.valueOf(yearSelector.getSelectedItem()));
                }
            }
        });

        yearSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    displayInformation(String.valueOf(studentList.getSelectedItem()));
                    refreshDatabase(String.valueOf(yearSelector.getSelectedItem()));
                }
            }
        });

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                                      "Student Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                        new java.awt.Font("Trebuchet MS", 0, 24)));
        displayStudents();
        refreshDatabase(String.valueOf(yearSelector.getSelectedItem()));
        yearSelector.setModel(new javax.swing.DefaultComboBoxModel<>(
            controller.getPeriodsOfStudy(
                Integer.valueOf(String.valueOf(studentList.getSelectedItem())))));
    }

    private void displayStudents() {
        String[] stuList = controller.getStudents();
        studentList.setModel(new DefaultComboBoxModel(stuList));
        displayInformation(String.valueOf(studentList.getSelectedItem()));
        yearSelector.setModel(new javax.swing.DefaultComboBoxModel<>(
            controller.getPeriodsOfStudy(
                Integer.valueOf(String.valueOf(studentList.getSelectedItem())))));
    }

    private void refreshDatabase(String period) {
        //int arg = Integer.parseInt(String.valueOf(studentList.getSelectedItem()));
        String arg = String.valueOf(studentList.getSelectedItem());
        databaseTable.setModel(new javax.swing.table.DefaultTableModel(
                controller.getYearsModules(arg, period),
                new String[] { "Mark Id", "Module Code", "Record ID", "Mark", "Resit Mark" }));
        databaseView.setViewportView(databaseTable);
    }


    /**
     *
     * @param student the student regiatration number
     */
    private void displayInformation(String name) {
        String cond = "WHERE `Registration number` = '" + name  + "'";
        String [] theResults = controller.studentInfo(cond);
        forename.setText(theResults[2]);
        forename.setEditable(false);
        surname.setText(theResults[3]);
        surname.setEditable(false);
        resgistrationNumber.setText(theResults[0]);
        resgistrationNumber.setEditable(false);
        email.setText(theResults[5]);
        email.setEditable(false);
        userName.setText(theResults[4]);
        userName.setEditable(false);
        studyLevel.setText(theResults[6]);
        studyLevel.setEditable(false);
        setTutorText(name);
        setDegreeText(name);
    }

    /**
     *
     * @param student the student regiatration number
     */
    private void setTutorText(String name) {
        String cond = "WHERE `Registration number` = '" + name  + "'";
        String result = controller.getStudentTutor(cond);
        tutor.setText(result);
        tutor.setEditable(false);
    }

    /**
     *
     * @param student the student regiatration number
     */
    private void setDegreeText(String regNo) {
        String query = " WHERE `Registration number` = '" + regNo + "';";
        String res = controller.getDegreeName(regNo);
        degree.setText(res);
        degree.setEditable(false);
    }

    protected void placeComponents() {
        javax.swing.GroupLayout viewStudentInfoLayout = new javax.swing.GroupLayout(this);
        setLayout(viewStudentInfoLayout);
        viewStudentInfoLayout.setHorizontalGroup(
        viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewStudentInfoLayout.createSequentialGroup()
                .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(viewStudentInfoLayout.createSequentialGroup()
                        .addGap(458, 458, 458)
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(viewStudentInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(databaseView, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(viewStudentInfoLayout.createSequentialGroup()
                                .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(selStu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(foreNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(regNoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(emailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(degreeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, viewStudentInfoLayout.createSequentialGroup()
                                        .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(forename, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(resgistrationNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(37, 37, 37)
                                        .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, viewStudentInfoLayout.createSequentialGroup()
                                                .addComponent(surnameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(surname, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, viewStudentInfoLayout.createSequentialGroup()
                                                .addComponent(userNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, viewStudentInfoLayout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(studentList, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, viewStudentInfoLayout.createSequentialGroup()
                                        .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(viewStudentInfoLayout.createSequentialGroup()
                                                .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(37, 37, 37)
                                                .addComponent(studyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(viewStudentInfoLayout.createSequentialGroup()
                                                .addComponent(degree, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(37, 37, 37)
                                                .addComponent(tutorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(studyLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tutor, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addGroup(viewStudentInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(yearLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(yearSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          );

          viewStudentInfoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {degree, email, forename, resgistrationNumber, studentList, studyLevel, surname, tutor, userName});

          viewStudentInfoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {degreeLabel, emailLabel, foreNameLabel, regNoLabel, selStu, studyLabel, surnameLabel, tutorLabel, userNameLabel});

          viewStudentInfoLayout.setVerticalGroup(
              viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(viewStudentInfoLayout.createSequentialGroup()
                  .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                      .addComponent(selStu, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                      .addComponent(studentList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGap(18, 18, 18)
                  .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                      .addComponent(foreNameLabel)
                      .addComponent(forename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                      .addComponent(surnameLabel)
                      .addComponent(surname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGap(18, 18, 18)
                  .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                      .addComponent(regNoLabel)
                      .addComponent(resgistrationNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                      .addComponent(userNameLabel)
                      .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGap(18, 18, 18)
                  .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addGroup(viewStudentInfoLayout.createSequentialGroup()
                          .addComponent(emailLabel)
                          .addGap(18, 18, 18)
                          .addComponent(degreeLabel))
                      .addGroup(viewStudentInfoLayout.createSequentialGroup()
                          .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(studyLabel)
                              .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(studyLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                          .addGap(18, 18, 18)
                          .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(degree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(tutorLabel)
                              .addComponent(tutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                  .addGap(18, 18, 18)
                  .addGroup(viewStudentInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                      .addComponent(yearLabel)
                      .addComponent(yearSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGap(23, 23, 23)
                  .addComponent(databaseView, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addGap(18, 18, Short.MAX_VALUE)
                  .addComponent(backButton))
          );

          viewStudentInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {degree, email, forename, resgistrationNumber, studentList, studyLevel, surname, tutor, userName});

          viewStudentInfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {degreeLabel, emailLabel, foreNameLabel, regNoLabel, selStu, studyLabel, surnameLabel, tutorLabel, userNameLabel});

    }
}
