package src.ui.registrar;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import src.sql.controller.RegistrarController;
import src.ui.database.DatabaseView;
/**
 * UI for the registrar main menu
 */
public class Registrar extends JPanel {
    private javax.swing.JComboBox<String> dataSelect;
    private javax.swing.JComboBox<String> periodSelect;
    private javax.swing.JTable finishedRegistration;
    private javax.swing.JTable inRegistration;
    private javax.swing.JPanel mainPanel;
    private DatabaseView registerScroll;
    private javax.swing.JButton registerStudent;
    private javax.swing.JLabel studentLabel;
    private javax.swing.JLabel studyLabel;
    private javax.swing.JLabel unregisteredLabel;
    private javax.swing.JLabel registerLabel;
    private DatabaseView studentViewScroll;
    private javax.swing.JButton registerButton;
    private RegistrarUI registrarUI;
    private RegistrarController controller;

    public Registrar(RegistrarUI registrarUI) {
        this.registrarUI = registrarUI;
        this.controller = new RegistrarController();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        registerStudent = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();
        registerScroll = new DatabaseView(this);
        inRegistration = new javax.swing.JTable();
        studentViewScroll = new DatabaseView(this);
        finishedRegistration = new javax.swing.JTable();
        studentLabel = new javax.swing.JLabel();
        registerLabel = new javax.swing.JLabel();
        studyLabel = new javax.swing.JLabel();
        unregisteredLabel = new javax.swing.JLabel();
        dataSelect = new JComboBox<>();
        periodSelect = new JComboBox<>();
        studentLabel.setText("Select a student");
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        registerLabel.setText("Registered Students");
        studyLabel.setText("Select a period");
        unregisteredLabel.setText("Un-Registered Students");

        periodSelect.setModel(new javax.swing.DefaultComboBoxModel<>(controller.getPeriods()));
        periodSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerScroll.showUnRegisterdStudents(periodSelect.getSelectedItem().toString());
                studentViewScroll.showRegisterdStudents(periodSelect.getSelectedItem().toString());
                dataSelect.setModel(new javax.swing.DefaultComboBoxModel<>(controller.getUnregisteredRegistrationNumbers(periodSelect.getSelectedItem().toString())));
            }
        });

        registerScroll.showUnRegisterdStudents(periodSelect.getSelectedItem().toString());
        studentViewScroll.showRegisterdStudents(periodSelect.getSelectedItem().toString());
        dataSelect.setModel(new javax.swing.DefaultComboBoxModel<>(controller.getUnregisteredRegistrationNumbers(periodSelect.getSelectedItem().toString())));

        registerStudent.setText("Register Student");
        registerStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageModulesActionPerformed();
            }
        });
        registerButton.setText("Register a new student");
            registerButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    manageStudentActionPerformed();
                }
        });

        placeComponents();
    }

    private void manageStudentActionPerformed() {
        getRegistrarUI().showManageStudents();
    }

    private void manageModulesActionPerformed() {
        int recordId = controller.getRecordId(dataSelect.getSelectedItem().toString(),periodSelect.getSelectedItem().toString());
        getRegistrarUI().showManageModules(recordId);
    }

    private RegistrarUI getRegistrarUI() { return registrarUI; }

    private void placeComponents() {
        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(this);
        setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registerScroll)
                    .addComponent(studentViewScroll)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(registerStudent)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(registerButton))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(unregisteredLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(studentLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dataSelect, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(studyLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(periodSelect, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(unregisteredLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registerStudent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registerButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dataSelect)
                    .addComponent(studentLabel)
                    .addComponent(studyLabel)
                    .addComponent(periodSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(studentViewScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                .addContainerGap())
        );
    }
}
