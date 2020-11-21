package src.ui.database;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import src.sql.controller.*;
import src.sql.model.AdminDatabaseModel;
import src.sql.tables.*;

import src.ui.admin.AdminUI;

/**
 * Class that displays a table in the UI.
 */
public class DatabaseView extends JScrollPane{

    /**
     * Parent class of the table to ensure it can't be
     * editted.
     */
    public class NonEditableTable extends JTable{

        public NonEditableTable() {
            super();
        }

        public boolean isCellEditable(int row, int column) {
            // all cells false
            return false;
        }
    }

    private JPanel adminUI;
    private NonEditableTable databaseTable;
    private DatabaseViewController controller;

    public DatabaseView(JPanel adminUI) {

        this.adminUI = adminUI;
        controller = new DatabaseViewController();
        databaseTable = new NonEditableTable();

        databaseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        showDepartments();

    }

    public void switchDatabase(String tableName) {
        switch (tableName) {
            case "User Accounts (Staff)" : 
                showUsers();
                break;
            case "User Accounts (Students)":
                showStudents();
                break;
            case "Departments":
                showDepartments();
                break;
            case "Degrees":
                showDegrees();
                break;
            case "Modules":
                showModules();
                break;
            default:
                showUsers();
        }
    }
    
    public void showDepartments() {
        System.out.println("controller.getDepartments() = "+controller.getDepartments());
        databaseTable.setModel(new javax.swing.table.DefaultTableModel(controller.getDepartments(),
                        new String[] { "Department Code", "Department Name"}));
        setViewportView(databaseTable);
    }
    
    public void showDegrees() {
        databaseTable.setModel(new javax.swing.table.DefaultTableModel(controller.getDegrees(""),
                new String[] { "Degree Code", "Degree Name", "Level of Study", "Lead Department"}));
        setViewportView(databaseTable);
    }
    public void showDegrees(String level) {
        System.out.println("level = "+level);
        databaseTable.setModel(
                new javax.swing.table.DefaultTableModel(
                        controller.getDegrees("WHERE `Level of Study` LIKE '%" + level + "%'"),
                        new String[] { "Degree Code", "Degree Name", "Level of Study", "Lead Department" }));
        setViewportView(databaseTable);
    }
    public void showModules() {
        databaseTable.setModel(new javax.swing.table.DefaultTableModel(controller.getModules(),
                new String[] { "Module Code", "Module Name", "Teaching Department"}));
        setViewportView(databaseTable);
    }
    public void showUsers() {
        System.out.println("controller.getUsers() = "+controller.getUsers());
        databaseTable.setModel(new javax.swing.table.DefaultTableModel(controller.getUsers(),
                new String[] { "User Role", "Title", "Forename", "Surname", "Username", "Email"}));
        setViewportView(databaseTable);
    }

    public void showStudents() {
        Object[][] cg = controller.getStudents();
        System.out.println("cg ="+cg);
        databaseTable.setModel(new javax.swing.table.DefaultTableModel(controller.getStudents(),
                new String[] {"Registration Number", "Title", "Forename", "Surname", "Username",
                                "Email", "Level of Study", "Grade", "Degree", "Tutor"}));
        setViewportView(databaseTable);
    }

    public void showDegreeLinks() {
        databaseTable.setModel(new javax.swing.table.DefaultTableModel(controller.getDegreeLinks(),
                new String[] {"Degree Code", "Degree Name", "Department Code", "Department Name"}));
        setViewportView(databaseTable);
    }

    public void showModuleLinks() {
        databaseTable.setModel(new javax.swing.table.DefaultTableModel(controller.getModuleLinks(),
                new String[] { "Degree Code", "Degree Name", "Module Code", "Module Name", "Level", "Semester", "Credits", "Core or Not"}));
        setViewportView(databaseTable);
    }
    public void showRegisterdStudents(String period){
        databaseTable.setModel(new javax.swing.table.DefaultTableModel(controller.getRegisteredRecords(period),
                new String[] {"Record ID", "Average", "Honour", "Registration Number","Period of study","Registered status"}));
        setViewportView(databaseTable);
    }
    public void showUnRegisterdStudents(String period){
        databaseTable.setModel(new javax.swing.table.DefaultTableModel(controller.getUnRegisteredRecords(period),
                new String[] {"Record ID", "Average", "Honour", "Registration Number","Period of study","Registered status"}));
        setViewportView(databaseTable);
    }
    public void showStudentsModules(int recordId){
         databaseTable.setModel(new javax.swing.table.DefaultTableModel(controller.getStudentsModules(recordId),
                new String[] {"Mark Id", "Module Code", "Record ID", "Mark","Resit Mark","Credits","Core"}));
         setViewportView(databaseTable);
    }

    /**
     * Returns selected field in the selected row
     * @param col - col index of selected row you want to return
     * @return
     */
    public String getSelectedRow(int col) { 
        int row = databaseTable.getSelectedRow();
        if (row == -1)
            return null;
        return databaseTable.getModel().getValueAt(row, col).toString();
     }
}