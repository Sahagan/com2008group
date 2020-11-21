/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.sql.controller;

import java.sql.ResultSet;
import java.util.ArrayList;
import src.sql.model.RegistrarDatabaseModel;
import src.sql.tables.*;


/**
 * Class acts as a interface between the GUI and the SQL commands, handling the
 * logic and processing of the data before its added to the database.
 */
public class RegistrarController {
    
    private RegistrarDatabaseModel databaseModel;
    private SQLValidation validation;
    
    public int getRecordId(String studentRegistrationNumber,String periodOfStudy) {
        return databaseModel.getRecordId(studentRegistrationNumber,periodOfStudy);
    }
    
    public RegistrarController() {
        
        this.databaseModel = new RegistrarDatabaseModel();
        this.validation = new SQLValidation();
        
    }
    
    public String[] getUnregisteredRegistrationNumbers(String periodOfStudy){
        
        Record unregisteredRecords = databaseModel.getRecordsByRegStatus("no",periodOfStudy);
        return unregisteredRecords.getRegistrationNumbers();
        
    }
    
    public String[] getOptionalModulesCodes(int recordId){
        
        String registrationNumber = databaseModel.getRegistrationNumber(recordId);
        String degreeCode = databaseModel.getStudentDegree(registrationNumber);
        String degreeName = databaseModel.getDegreeName(degreeCode);
        String level = databaseModel.getStudentsLevel(registrationNumber);
        ModuleLinks validModules = databaseModel.getValidOptionalCoreModules(degreeCode,degreeName,false,level);
        return  validModules.getModuleCodes();
        
    }
    
    public void addStudent(String firstname, String secondname, String degreeCode, String tutor, String periodOfStudy) {
        
       String levelOfStudy = databaseModel.getFirstDegreeLevel(degreeCode);
       firstname = validation.generalValidation(firstname);
       secondname = validation.generalValidation(secondname);
       degreeCode = validation.generalValidation(degreeCode);
       tutor = validation.generalValidation(tutor);
       periodOfStudy = validation.generalValidation(periodOfStudy);
       
       String title = "Mr.";
       String baseUsername = (firstname.substring(0, 1) + secondname).toLowerCase();
       Users users = databaseModel.getUsers("*","WHERE Username LIKE '" + baseUsername + "%';");
       String username = generateUsername(baseUsername, users.getTableList());
       
       boolean bool = databaseModel.executeBoolQuery("SELECT * FROM `Period of study` WHERE (`Label` = '" + periodOfStudy + "')");
       if(!bool){
           String start = "01.09." + periodOfStudy;
           String end = "01.07." + (Integer.parseInt(periodOfStudy) + 1);
           String posValues = "('" + periodOfStudy + "','" + start  + "','" + end + "')";
           databaseModel.insertIntoDatabase("`Period of study`",posValues);
       }

       String email = username + "@sheffield.ac.uk";
       String role = "Student";
       
       String registrationNumber = generateRegistrationNumber(0);
       byte[] salt = PasswordHasher.generateSalt();
       byte[] hashedPassword = PasswordHasher.generateHashPassword("123", salt);
       
       String values = "('" + registrationNumber + "','" +  degreeCode  + "','" + "','" + tutor  + "','" +  username  + "','" + levelOfStudy + "')";
       
       databaseModel.insertUsers(username, "123", role, email, firstname, title, secondname, salt);
       databaseModel.insertIntoDatabase("Student", values);
       registerStudent(periodOfStudy,registrationNumber);
       
    }
    
    public void registerStudent(String periodOfStudy, String registrationNumber){
        
        periodOfStudy = validation.generalValidation(periodOfStudy);
        registrationNumber = validation.generalValidation(registrationNumber);
        
        createRecord(periodOfStudy, registrationNumber);
        int recordId = databaseModel.getRecordId(registrationNumber,periodOfStudy);
        
        String degreeCode = databaseModel.getStudentDegree(registrationNumber);
        String level = databaseModel.getStudentsLevel(registrationNumber);
        String degreeName = databaseModel.getDegreeName(degreeCode);
        
        ModuleLinks coreModules = databaseModel.getValidOptionalCoreModules (degreeCode,degreeName,true,level);
        
        String [] codes = coreModules.getModuleCodes();
        
        for(int i = 0; i<codes.length; i++){
            System.out.println(codes[i]);
            addMark(recordId,codes[i]);
        }
    }
    
    public void createRecord (String periodOfStudy, String registrationNumber){
        
        periodOfStudy = validation.generalValidation(periodOfStudy);
        registrationNumber = validation.generalValidation(registrationNumber);
        
        String recordId = String.valueOf(generateRecordId(0));
        int average = 0;
        String qualification = "NULL";
        String honour = "NULL";
        String ifRegistered = "No";
        
        String values = "('" + recordId + "','" + registrationNumber  + "','" + periodOfStudy + "','" + average  + "','" + qualification + "','" + ifRegistered + "','" + honour + "')";
        
        databaseModel.insertIntoDatabase("Record", values);
        
    }
    
    public void addMark (int recordId, String moduleCode){
        
        moduleCode = validation.generalValidation(moduleCode);
        
        int markId = generateMarkId(0);
        int paringID = markId;
        int theMark = -1;
        int resitMark = -1;
        String coreOrNot = "NULL";


        
        String values = "('" + markId + "','" + paringID  + "','" + recordId + "','" + theMark + "','" + resitMark + "','" + coreOrNot + "','"+ moduleCode+ "')";
        System.out.println("rc135: values = "+values);
        databaseModel.insertIntoDatabase("Mark", values);
    }
    
    public void pasteMark (int recordId, String moduleCode, String mark, String resitMark){
        
        moduleCode = validation.generalValidation(moduleCode);
        
        int markId = generateMarkId(0);
        
        String values = "('" + markId + "','" + moduleCode  + "','" + recordId + "','" + mark + "','" + resitMark + "' )";
        
        databaseModel.insertIntoDatabase("Mark", values);
    }
    
    public void removeMark (String markId){
        
        markId = validation.generalValidation(markId);
        String conditions = "(`Mark ID` = '" + markId + "')" ;
        databaseModel.removeFromDatabase("Mark",conditions);
        
    }
    
    public void removeStudent (String username){
       
       username = validation.generalValidation(username);
       String registrationNumber = databaseModel.getStudentRegistrationNumber(username);
       ArrayList<Integer> records = databaseModel.getStudentsRecords(registrationNumber);
       
       String conditionsStudent = "(Username = '" + username + "');";
       String conditionsUser = "(Username = '" + username + "');";
       
       for(int i=0;i<records.size();i++) databaseModel.removeFromDatabase("Mark", "(`Record_Record ID` = '" + records.get(i) + "');");
       
       databaseModel.removeFromDatabase("Record", "(`Student_Registration number` = '" + registrationNumber + "');");
       databaseModel.removeFromDatabase("Student", conditionsStudent);
       databaseModel.removeFromDatabase("Users",conditionsUser);
    }
    
    private int generateMarkId(int i){
        
        boolean bool = databaseModel.executeBoolQuery("SELECT * FROM Mark WHERE (`Mark ID` = '" + i + "')");
        if(bool) return generateMarkId(i+1);
        else return i;
        
    }
    
    private int generateRecordId(int i){
        
        boolean bool = databaseModel.executeBoolQuery("SELECT * FROM Record WHERE (`Record ID` = '" + i + "')");
        if(bool) return generateRecordId(i+1);
        else return i;
        
    }
    
    private String generateRegistrationNumber(int i) {
        
        String numberBase = "" + i;
        while(numberBase.length() < 6) numberBase = 0 + numberBase;
        
        boolean bool = databaseModel.executeBoolQuery("SELECT * FROM Student WHERE (`Registration number` = '" + numberBase + "')");
        
        if(bool) return generateRegistrationNumber(i+1);
        else return numberBase;
    }
    
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
    
    public int getCreditsSum(int recordId){
        Mark modules = databaseModel.getStudentsModules(recordId);
        return modules.getSumOfCredits();
    }
    
    public boolean completeRegistration(int recordId){
        
        String registrationNumber = databaseModel.getRegistrationNumber(recordId);
        String level = databaseModel.getStudentsLevel(registrationNumber);
        int requiredCredits = 120;
        if(level.equals("4")) requiredCredits = 180;
        
        if(requiredCredits == getCreditsSum(recordId)){
            databaseModel.updateRegistrationStatus(recordId,"yes");
            return true;
        }
        else{
            System.out.println("Invalid number of credits: " + requiredCredits + " required");
            return false;
        }
        
    }
    
    public String[] getDegreeCodes(){
        Degrees degrees = databaseModel.getDegrees("*", "");
        return degrees.getDegreeCodes();
    }
    
    public String[] getPeriods(){
        Period periods = databaseModel.getPeriods("*", "");
        return periods.getPeriodLabels();
    }
    
}
