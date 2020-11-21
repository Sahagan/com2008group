package src.sql.controller;

import java.sql.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import src.ui.admin.*;
import src.sql.controller.PasswordHasher;
import src.sql.model.*;
import src.sql.tables.Departments;
import src.sql.tables.DegreeLinks;
import src.sql.tables.Degrees;
import src.sql.tables.Modules;
import src.sql.tables.Users;
import src.sql.tables.ModuleLinks;

/**
 * Class acts as a interface between the GUI and the SQL commands, handling the logic and
 * processing of the data before its added to the database.
 */
public class AdminController {

    private AdminDatabaseModel databaseModel;
    private SQLValidation validation;
    private int paringIDDegreeCounter = 1;
    private int paringIDModuelCounter = 1;

    public AdminController() {
        databaseModel = new AdminDatabaseModel();
        validation = new SQLValidation();
    }
    
    /**
     * Function generates a code for a new entry in a table.
     * 
     * @param String baseCode - The string code that entries in a table will share
     *               e.g. "COM"
     * @param ArrayList<Object[]> table - table represented as a ArrayList of
     *               Object arrays
     * @param String numCodeLength - length of the unqiue number the code will have
     */
    private String generateUniqueCode(String baseCode, ArrayList<Object[]> table, String numCodeLength) {
        String numCode;
        int rowNum = table.size();
        if (rowNum == 0) {
            numCode = String.format("%0" + numCodeLength + "d", 0);
        } else {
            String prevNumCode = (String) table.get(rowNum - 1)[0];
            int code = Integer.parseInt(prevNumCode.substring(baseCode.length()));
            numCode = String.format("%0" + numCodeLength + "d", (code + 1));
        }
        return baseCode + numCode;
    }
    
    /**
     * Generates unique username.
     * 
     * @param baseUsername - Base value of the username, i.e the combination of a users forename and surname.
     * @param table        - Table for which the username will be unique to.
     * @return Unique username containing the baseUsername and a unique int.
     */
    private String generateUsername(String baseUsername, ArrayList<Object[]> table) {
        int rowNum = table.size();
        if (rowNum == 0) {
            return baseUsername + "1";
        } else {
            String prevName = (String) table.get(rowNum - 1)[4];
            int prevNum = Integer.parseInt(prevName.substring(baseUsername.length()));
            return baseUsername + Integer.toString(prevNum + 1);
        }
    }

    /**
     * Returns array of all the department names.
     * 
     * @return String array containing all the department names.
     */
    public String[] getDepartmentNames() {
        Departments departments = databaseModel.getDepartments("*","");
        return departments.getDepartmentNames();
    }

    /**
     * Returns array of all the degree names.
     * 
     * @return String array containing all the degree names.
     */
    public String[] getDegreeNames() {
        Degrees degrees = databaseModel.getDegrees("*", "");
        return degrees.getDegreeNames();
    }

    /**
     * Returns degree code of a given degree name.
     * 
     * @param degreeName Name of degree for which you want the code.
     * @return The degree code of the named degree.
     */
    public String getDegreeCode(String degreeName) {
        degreeName = validation.generalValidation(degreeName);
        Degrees degrees = databaseModel.getDegrees("*", "WHERE `Degree name`='" + degreeName + "';");
        Object[] degreeCode = degrees.getColumn(0);
        return (String) degreeCode[0];
    }

    /**
     * Returns array of all the module names.
     * 
     * @return String array containing all the module names.
     */
    public String[] getModuleNames() {
        Modules modules = databaseModel.getModules("*","");
        return modules.getModuleNames();
    }

    /**
     * Method adds a department to the database.
     * 
     * @param name - Department name to be added to the database
     * @return Boolean indicating if Department name was already in the database
     */
    public Boolean addDepartment(String name){
        String fullname = name;
        if (name.length() < 3)
            return false;

        name = validation.generalValidation(name); // SQL Injection validation
        String departmentCode = name.substring(0, 3).toUpperCase();

        Departments departments = databaseModel.getDepartments("*","");
        if (departments.occursInTable(departmentCode,0)) {
            return false;
        } else {
            String values = "('" + departmentCode + "','" + name + "','" + fullname +"')";
            System.out.println("ac135 ="+values);
            databaseModel.insertIntoDatabase("Department", values);
            return true;
        }        
    }

    /**
     * Adds a user to the database
     * 
     * @param name Name of user to be added.
     * @param surname Surname of user to be added.
     * @param password Password of user to be added.
     * @param role Role of user to be added.
     */
    public void addUser(String name, String surname, String password, String role) {
        
        // SQL injection prevention
        name = validation.generalValidation(name);
        surname = validation.generalValidation(surname);
        role = validation.generalValidation(role);
        
        //generate unique salt for user
        byte[] salt = PasswordHasher.generateSalt();
        //generate hashed password using salt.
        //byte[] hashedPassword = PasswordHasher.generateHashPassword(password, salt);

        String baseUsername = (name.substring(0, 1) + surname).toLowerCase();
        //fetch users with same baseusername i.e same initials and surname
        Users users = databaseModel.getUsers("*","WHERE Username LIKE '" + baseUsername + "%' " +
                                                  "ORDER BY CHAR_LENGTH(Username);");
        String username = generateUsername(baseUsername, users.getTableList());

        String email = username + "@sheffield.ac.uk";
        String title = "Mr.";
        
        databaseModel.insertUsers(username, password, role, email, name, title, surname, salt);
        
    }

    /**
     * Add link between a department and a degree to the database.
     * 
     * @param departmentCode Code of department to be linked with.
     * @param degreeCode Code of degree to be linked with.
     */
    public void addDegreeLink(String departmentCode, String degreeCode) {
    
        // SQL injection prevention
        departmentCode = validation.generalValidation(departmentCode);
        degreeCode = validation.generalValidation(degreeCode);
        
        //String values = "(NULL, '" + departmentCode + "','" + degreeCode + "')";
        String values = "('"+ paringIDDegreeCounter + "','" + degreeCode + "','" + departmentCode + "')";
        System.out.println("ac185: values = "+values);
        paringIDDegreeCounter = paringIDDegreeCounter + 1;
        databaseModel.insertIntoDatabase("`Department degree (linking)`", values);

    }

    /**
     * Adds link between a department and a degree to the database.
     * 
     * @param degreeName Name of degree to be linked with.
     * @param departmentCode Code of department to be linked with.
     * @param degreeNamePassed If method is passed with this String degree name is being passed
     *                          to add a link rather than degree code.
     * @return Boolean value reflecting if degree link has successfully been added.
     */
    public Boolean addDegreeLink(String degreeName, String departmentCode, String degreeNamePassed) {
        
        // SQL injection prevention
        degreeName = validation.generalValidation(degreeName);
        departmentCode = validation.generalValidation(departmentCode);
        degreeNamePassed = validation.generalValidation(degreeNamePassed);
        
        String degreeCode = getDegreeCode(degreeName);
        DegreeLinks degreeLinks = databaseModel.getDegreeLinks("*","dll.Degree_DegreeCode='" + degreeCode + 
                                                                   "' AND dll.Department_DepartmentCode='" + departmentCode + "'");
        //if degree link already exists, return false
        if (degreeLinks.getNumOfRows() != 0)
            return false;
            
        addDegreeLink(departmentCode, degreeCode);
        return true;
    }

    /**
     * Adds degree to the database.
     * 
     * @param name Name of degree to be added.
     * @param leadDepartment Lead department of degree to be added.
     * @param levelOfStudy Sequence of levels of study of the degree.
     * @return Boolean value reflecting if degree was successfully added.
     */
    public Boolean addDegree(String name, String leadDepartment, String levelOfStudy) {

        //if name is too short return false
        if (name.length() < 3)
            return false;
        
        // SQL injection prevention
        name = validation.generalValidation(name);
        leadDepartment = validation.generalValidation(leadDepartment);
        levelOfStudy = validation.generalValidation(levelOfStudy);
        
        Degrees degrees = databaseModel.getDegrees("*","");
        //if degree already exists return false, else add degree.
        if (degrees.occursInTable(name,1)) {
            return false;
        } else {
            String departmentCode = leadDepartment.substring(0, 3).toUpperCase();
            String degreeCodeChars;
            //decides if to makr a degree as undergraduate or postgraduate
            if (levelOfStudy.charAt(0)=='1') {
                degreeCodeChars = departmentCode + "U";
            } else {
                degreeCodeChars = departmentCode + "P";
            }
            Degrees degree = databaseModel.getDegrees("*", "WHERE DegreeCode LIKE '" + degreeCodeChars + "%'");
            String degreeCode = generateUniqueCode(degreeCodeChars, degree.getTableList(), "2");
            
            String values = "('" + degreeCode + "','" + name +  "','" + levelOfStudy + "','" + leadDepartment + "')";
            databaseModel.insertIntoDatabase("Degree", values);
            //adds degree link with lead department
            addDegreeLink(departmentCode, degreeCode);
            return true;
        }
    }

    /**
     * Method adds module to the database.
     * 
     * @param name Name of module to be added.
     * @param teachingDepartment Name of the teaching department of module to be added.
     * @return Boolean value reflecting if module was successfully added.
     */
    public Boolean addModule(String name, String teachingDepartment) {
        
        //SQL injection prevention
        name = validation.generalValidation(name);
        teachingDepartment = validation.generalValidation(teachingDepartment);
        
        String departmentCode = teachingDepartment.substring(0, 3).toUpperCase();
        Modules modules = databaseModel.getModules("*", "WHERE ModuleCode LIKE '" + departmentCode + "%'");
        //generates new unique module code
        String moduleCode = generateUniqueCode(departmentCode, modules.getTableList(), "4");

        //check if module name exists already and return false if so
        Modules duplicateModules = databaseModel.getModules("*", "WHERE `Full name`='" + name + "'");
        if (duplicateModules.getNumOfRows() != 0)
            return false;

        String values = "('"+ moduleCode + "','" + name + "','" + teachingDepartment + "')";
        databaseModel.insertIntoDatabase("Module", values);
        return true;
    }
    
    /**
     * Method adds link between a module and a degree.
     * 
     * @param moduleCode   Module code of module to be linked with.
     * @param degreeCode   Degree code of degree to be linked with.
     * @param level        Level of the degree for which the module will be added
     *                     to.
     * @param semester     Semester for which the module will taught for.
     * @param coreOrNot    String on whether module is core to degree or not.
     * @param typeOfModule String on what type of module module is, i.e year in
     *                     industry/dissertation/regular module.
     * @return Boolean value on if the module link was successfully added.
     */
    public Boolean addModuleLink(String moduleCode, String degreeCode, String level, String semester, String coreOrNot, String typeOfModule) {

        //check if module link already exists
        ModuleLinks moduleLinks = databaseModel.getModuleLinks("*","mdl.Module_ModuleCode='" + moduleCode + 
                                                                    "' AND mdl.Degree_DegreeCode='" + degreeCode + "'");
        if (moduleLinks.getNumOfRows() != 0)
            return false;

        //decide number of credits based on module type and degree level
        String credits;
        if (typeOfModule.equals("Year In Industry")) {
            credits = "120";
            coreOrNot = "Core";
        } else {
            if (level.equals("4")) {
                if (typeOfModule.equals("Dissertation"))
                    credits = "60";
                else
                    credits = "15";
            } else {
                if (typeOfModule.equals("Dissertation"))
                    credits = "40";
                else
                    credits = "20";
            }
        }   

        String values = "('"+ paringIDModuelCounter+ "','" + moduleCode + "','" + degreeCode + "','" + level + "','" + semester + "','" + credits + "','" + coreOrNot + "')";
        paringIDModuelCounter = paringIDModuelCounter + 1;
        databaseModel.insertIntoDatabase("`Module degree (linking)`", values);
        return true;
    }

    /**
     * Method removes module from database.
     * 
     * @param moduleCode module code of module to be removed from the database
     * @return Boolean on if module was successfully removed.
     */
    public Boolean removeModule(String moduleCode) {
        
        moduleCode = validation.generalValidation(moduleCode);
        
        String conditions = "(ModuleCode = '" + moduleCode + "');";
        Boolean successfullyRemoved = databaseModel.executeWithBool("DELETE FROM Module WHERE " + conditions + ";");
        return successfullyRemoved;
    }

    /**
     * Method removes department via departmentCode
     * 
     * @param departmentCode - code of department to be deleted
     * @return Boolean on if department was successfully removed.
     */
    public Boolean removeDepartment(String departmentCode){
        
        departmentCode = validation.generalValidation(departmentCode);
        
        String conditions = "(DepartmentCode = '" + departmentCode + "');";
        Boolean successfullyRemoved = databaseModel.executeWithBool("DELETE FROM Department WHERE " + conditions + ";");
        return successfullyRemoved;
    }

    /**
     * Method deletes a user from database.
     * 
     * @param username Username of user to be deleted.
     */
    public void removeUser(String username){
        
        username = validation.generalValidation(username);
        
        String conditions = "(Username = '" + username + "');";
        databaseModel.removeFromDatabase("Users",conditions);
    }

    /**
     * Method deletes degree from database.
     * 
     * @param degreeCode Degree code of degree to be deleted.
     * @return Boolean on if degree was successfully removed.
     */
    public Boolean removeDegree(String degreeCode){
        
        degreeCode = validation.generalValidation(degreeCode);
        
        databaseModel.removeFromDatabase("`Department degree (linking)`", "(Degree_DegreeCode='" + degreeCode + "');");
        Boolean successfullyRemoved = databaseModel.executeWithBool("DELETE FROM Degree WHERE (DegreeCode = '" + degreeCode + "');");
        return successfullyRemoved;
    }

    /**
     * Method deletes degree link with department from the database
     * 
     * @param degreeCode     degree code of the degree in the link to be deleted.
     * @param departmentCode department code of the department in the link to be
     *                       deleted.
     * @return Boolean on if degree link was successfully removed.
     */
    public Boolean removeDegreeLink(String degreeCode, String departmentCode) {
        
        degreeCode = validation.generalValidation(degreeCode);
        departmentCode = validation.generalValidation(departmentCode);

        if (!degreeCode.equals("") && !departmentCode.equals("")) {

            //find lead department of the degree and ensures the link between a degree and its lead department isn't deleted
            Departments departments = databaseModel.getDepartments("*", "WHERE DepartmentCode='" + departmentCode + "'");
            String departmentName = (String) departments.getDepartmentNames()[0];
            Degrees degrees = databaseModel.getDegrees("*", "WHERE DegreeCode='" + degreeCode + "'");
            String leadDepartment = (String) degrees.getColumn(3)[0];

            if (!departmentName.equals(leadDepartment)) {
                databaseModel.removeFromDatabase("`Department degree (linking)`", "Degree_DegreeCode='" + degreeCode + "' AND " + 
                                                "Department_DepartmentCode='" + departmentCode + "';");
                return true;
            }
        }

        return false;
    }

    /**
     * Method deletes module link with a degree from the database
     * 
     * @param moduleCode     module code of the module in the link to be deleted.
     * @param degreeCode degree code of the degree in the link to be
     *                       deleted.
     * @return Boolean on if module link was successfully removed.
     */
    public Boolean removeModuleLink(String degreeCode, String moduleCode) {

        degreeCode = validation.generalValidation(degreeCode);
        moduleCode = validation.generalValidation(moduleCode);

        if (!degreeCode.equals("") && !moduleCode.equals("")) {

            databaseModel.removeFromDatabase("`Module degree (linking)`", "Degree_DegreeCode='" + degreeCode
                        + "' AND " + "Module_ModuleCode='" + moduleCode + "';");
            return true;
        }
        return false;
    }
}