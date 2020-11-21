package src.ui.teacher;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.sql.controller.TeacherController;
import java.util.ArrayList;

/**
 * Edit Grades UI for adding/updating grades
 * Shows all students and their registered modules, allows resit grades to be entered
 */
public class EditGrades extends Menu {
    private javax.swing.JLabel select;
    private javax.swing.JLabel resitLabel;
    private javax.swing.JLabel currentGradeLabel;
    private javax.swing.JLabel selectLabel;
    private javax.swing.JLabel newGradeLabel;
    private javax.swing.JLabel currentStudentLabel;
    private javax.swing.JTextField currentGrade;
    private javax.swing.JTextField newGrade;
    private javax.swing.JTextField currentStudent;
    private javax.swing.JButton submit;
    private javax.swing.JButton backButton;
    private javax.swing.JComboBox<String> moduleList;
    private javax.swing.JComboBox<String> studentList;
    private javax.swing.JComboBox<String> resit;
    private TeacherController controller;

    public EditGrades (TeacherGUI teacherui) {
        super(teacherui);
        controller = new TeacherController();
        setVisible(true);
        initComponents();
        placeComponents();
    }

    public void initComponents() {
        select = new JLabel();
        select.setText("Select Module");
        resitLabel = new JLabel();
        resitLabel.setText("Resit?");
        moduleList = new JComboBox<String>();
        currentGradeLabel = new JLabel();
        currentGradeLabel.setText("Current Grade");
        currentGrade = new JTextField();
        currentGrade.setEditable(false);
        newGradeLabel = new JLabel();
        newGradeLabel.setText("Enter new grade");
        newGrade = new JTextField();
        submit = new JButton();
        currentStudentLabel = new JLabel();
        currentStudentLabel.setText("Student Name");
        currentStudent = new JTextField();
        currentStudent.setText("Student Name");
        currentStudent.setEditable(false);
        selectLabel = new JLabel();
        selectLabel.setText("Select Student");
        studentList = new JComboBox<String>();
        backButton = new JButton();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        resit = new JComboBox(model);
        model.addElement("false");
        model.addElement("true");
        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(),
                                      "Add or Update Grades", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                        new java.awt.Font("Trebuchet MS", 0, 24)));

        submit.setText("Save Changes");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                commitChange();
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                getTeacherUI().getMainMenu().setVisible(true);
            }
        });

        displayStudents();
        displayModule();
        getCurrentGrade();

        studentList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    displayModule();
                    getCurrentGrade();
                }
            }
        });

        moduleList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    getCurrentGrade();
                }
            }
        });

        resit.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    getCurrentGrade();
                }
            }
        });
    }

    //Get and display all modules in the combo box
    private void displayModule() {
        moduleList.setModel(new javax.swing.DefaultComboBoxModel<String>(controller.getModuleList(studentList.getSelectedItem().toString())));
    }

    private void displayStudents() {
        String[] stuList = controller.getStudents();
        studentList.setModel(new DefaultComboBoxModel(stuList));
        getCurrentGrade();
    }

    private void getCurrentGrade() {
        String module = String.valueOf(moduleList.getSelectedItem());
        String theModule = module;
        if(module.length() >= 7) theModule = module.substring(0, 7);
        String name = String.valueOf(studentList.getSelectedItem());
        String result = controller.getGrade(name, theModule, resit.getSelectedItem().toString());
        if(result.equals("-1")) {
            result = " ";
        }
        currentGrade.setText(result);
    }

    private void commitChange() {
        //Get info needed to change grade them commit changes
        String grade = newGrade.getText();
        String module = String.valueOf(moduleList.getSelectedItem());
        String student = String.valueOf(studentList.getSelectedItem());
        boolean resitGrade = (Boolean.parseBoolean(String.valueOf(resit.getSelectedItem())));
        //If input is valid then show information has been updated, else show error message
        if(controller.updateGrade(student, module, grade, resitGrade)) {
            JOptionPane.showMessageDialog(this, "Student grade has been successfuly updated!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }else {
            JOptionPane.showMessageDialog(this, "The grade entered is not valid!",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
        getCurrentGrade();
        newGrade.setText("");
    }

    protected void placeComponents() {
        javax.swing.GroupLayout editGradeLayout = new javax.swing.GroupLayout(this);
        setLayout(editGradeLayout);
        editGradeLayout.setHorizontalGroup(
            editGradeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editGradeLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(submit, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editGradeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editGradeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(currentGradeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newGradeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(editGradeLayout.createSequentialGroup()
                        .addGroup(editGradeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(selectLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(select, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(19, 19, 19))
                    .addComponent(resitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editGradeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(resit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(currentGrade, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newGrade, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(moduleList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(studentList, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(67, 67, 67))
        );
        editGradeLayout.setVerticalGroup(
            editGradeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editGradeLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(editGradeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editGradeLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(studentList))
                    .addComponent(selectLabel))
                .addGap(25, 25, 25)
                .addGroup(editGradeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(editGradeLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(moduleList))
                    .addComponent(select, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(editGradeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(editGradeLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(resit))
                    .addComponent(resitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(editGradeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editGradeLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(currentGrade))
                    .addComponent(currentGradeLabel))
                .addGap(18, 18, 18)
                .addGroup(editGradeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newGrade)
                    .addGroup(editGradeLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(newGradeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(editGradeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }
}
