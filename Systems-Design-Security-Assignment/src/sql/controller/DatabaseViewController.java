package src.sql.controller;

import src.sql.model.AdminDatabaseModel;
import src.sql.model.RegistrarDatabaseModel;
import src.sql.tables.*;

/**
 * Controller for the DatabaseView class, which fetches infomation
 * from the database to display on the UI.
 */
public class DatabaseViewController {

    private AdminDatabaseModel databaseModel;
    private RegistrarDatabaseModel registrarDatabaseModel;

    public DatabaseViewController() {
        databaseModel = new AdminDatabaseModel();
        registrarDatabaseModel = new RegistrarDatabaseModel();
    }
    
    /**
     * @return Returns 2d array representing departments database table
     */
    public Object[][] getDepartments() {
        Departments departments = databaseModel.getDepartments("*","");
        return departments.getTable();
    }

    /**
     * @return Returns 2d array representing degrees database table
     */
    public Object[][] getDegrees(String condition) {
        Degrees degrees = databaseModel.getDegrees("*", condition);
        return degrees.getTable();
    }

    /**
     * @return Returns 2d array representing modules database table
     */
    public Object[][] getModules() {
        Modules modules = databaseModel.getModules("*","");
        return modules.getTable();   
    }

    /**
     * @return Returns 2d array representing users database table
     */
    public Object[][] getUsers() {
        Users users = databaseModel.getUsers("*","WHERE Role <> 'Student'");
        return users.getTable();
    }

    /**
     * @return Returns 2d array representing students database table
     */
    public Object[][] getStudents() {
        Students students = databaseModel.getStudents("*","");
        return students.getTable();
    }

    /**
     * @return Returns 2d array representing degree links with departments.
     */
    public Object[][] getDegreeLinks() {
        DegreeLinks degreeLinks = databaseModel.getDegreeLinks("*","");
        return degreeLinks.getTable();
    }

    /**
     * @return Returns 2d array representing module links with degrees.
     */
    public Object[][] getModuleLinks() {
        ModuleLinks moduleLinks = databaseModel.getModuleLinks("*", "");
        return moduleLinks.getTable();
    }

    /**
     * @return Returns 2d array representing registered records for students.
     */
    public Object[][] getRegisteredRecords(String period) {
        Record registeredRecords = registrarDatabaseModel.getRecordsByRegStatus("yes", period);
        return registeredRecords.getTable();
    }

    /**
     * @return Returns 2d array representing unregistered records for students.
     */
    public Object[][] getUnRegisteredRecords(String period) {
        Record registeredRecords = registrarDatabaseModel.getRecordsByRegStatus("no", period);
        return registeredRecords.getTable();
    }

    /**
     * @return Returns 2d array representing a students modules.
     */
    public Object[][] getStudentsModules(int recordId) {
        Mark studentsModules = registrarDatabaseModel.getStudentsModules(recordId);
        return studentsModules.getTable();
    }
    
}