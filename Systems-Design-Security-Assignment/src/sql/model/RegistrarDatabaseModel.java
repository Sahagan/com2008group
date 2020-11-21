/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.sql.model;

import java.sql.SQLException;
import java.util.ArrayList;
import src.sql.tables.*;

/**
 * Database model, handles all the SQL operations for all registrar related tasks.
 */
public class RegistrarDatabaseModel extends AdminDatabaseModel{
    
    public int getRecordId(String studentRegistrationNumber, String periodOfStudy){
        
        initConnection();
        initStatement();
        int recordId = 0;
        System.out.println("studentRegistrationNumber = "+studentRegistrationNumber+", periodOfStudy = "+periodOfStudy);
        
        try {
            try {
                System.out.println("00000000000000000");
                openConnection();
                openStatement();
                openResultQuery("SELECT `Record ID` FROM Record WHERE `Student_Registration number` = '" 
                                    + studentRegistrationNumber + "' AND `Period of study_Label` = '" + periodOfStudy +  "' ;");
                while (getResult().next()) {
                    System.out.println("11111111111111111");
                    //recordId = Integer.parseInt(getResult().getString(1));
                    recordId = Integer.parseInt(getResult().getString(1));
                    System.out.println("31: recordId = "+recordId);
                }
            }
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }   
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return recordId;
        
    }
    
    public String getRegistrationNumber(int recordId){
        
        initConnection();
        initStatement();
        String registrationNumber = "";
        System.out.println("recordId = "+recordId);
        
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT `Student_Registration number` FROM Record WHERE `Record ID` = '" + recordId +  "' ;");
                getResult().next();
                //registrationNumber = getResult().getString(1);
                registrationNumber = getResult().getString(1);

            }
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }   
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return registrationNumber;
        
    }
    
    public String getStudentDegree(String studentRegistrationNumber){
        
        initConnection();
        initStatement();
        String degreeCode = "";
        
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT `Degree_DegreeCode` FROM Student WHERE `Registration number` = '" + studentRegistrationNumber +  "' ;");
                getResult().next();
                degreeCode = getResult().getString(1);
            }
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }   
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return degreeCode;
        
    }
    
    public String getStudentRegistrationNumber(String username){
        
        initConnection();
        initStatement();
        String regNumber = "";
        
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT `Registration number` FROM Student WHERE `Username` = '" + username +  "' ;");
                getResult().next();
                regNumber = getResult().getString(1);
            }
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }   
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return regNumber;
        
    }
    
    public String getDegreeName(String degreeCode){
        
        initConnection();
        initStatement();
        String degreeName = "";
        
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT `Degree name` FROM Degree WHERE `DegreeCode` = '" + degreeCode +  "' ;");
                getResult().next();
                degreeName = getResult().getString(1);
            }
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }   
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return degreeName;
        
    }
    
    public ModuleLinks getValidModules (String degreeCode, String degreeName) {
        
        initConnection();
        initStatement();
        ModuleLinks availableModules = new ModuleLinks();
        
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT * FROM `Module degree (linking)` INNER JOIN `Module`  ON `Module degree (linking)`.Module_ModuleCode = `Module`.ModuleCode WHERE Degree_DegreeCode = '" + degreeCode + "';");
                while (getResult().next()) {
                    int pairingId = Integer.parseInt(getResult().getString(1));
                    String moduleCode = getResult().getString(2);
                    String moduleName = getResult().getString(9);
                    String degreeLevel = getResult().getString(4);
                    String season = getResult().getString(5);
                    String credits = getResult().getString(6);
                    String coreOrNot = getResult().getString(7); 
                    availableModules.addRow(degreeCode,degreeName,moduleCode,moduleName,degreeLevel,season,credits,coreOrNot);
                }
            }
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }   
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return availableModules;
        
    }
    
    public ModuleLinks getValidOptionalCoreModules (String degreeCode, String degreeName, boolean isCore, String level) {
        
        initConnection();
        initStatement();
        ModuleLinks availableModules = new ModuleLinks();
        String core;
        if(isCore)core = "'Core'";
        else core = "'Not Core'";
        
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT * FROM `Module degree (linking)` INNER JOIN `Module`  ON `Module degree (linking)`.Module_ModuleCode = `Module`.ModuleCode WHERE Degree_DegreeCode = '" + degreeCode + "' AND CoreOrNot = " + core + " AND Level = '" + level + "' ;");
                while (getResult().next()) {
                    int pairingId = Integer.parseInt(getResult().getString(1));
                    String moduleCode = getResult().getString(2);
                    String moduleName = getResult().getString(9);
                    String degreeLevel = getResult().getString(4);
                    String season = getResult().getString(5);
                    String credits = getResult().getString(6);
                    String coreOrNot = getResult().getString(7); 
                    availableModules.addRow(degreeCode,degreeName,moduleCode,moduleName,degreeLevel,season,credits,coreOrNot);
                }
            }
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }   
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return availableModules;
        
    }
    
    public Record getRecordsByRegStatus(String registeredYesNo, String periodOfStudy){
        
        initConnection();
        initStatement();
        Record records = new Record();
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT * FROM Record WHERE registered = '" + registeredYesNo + "' AND `Period of study_Label` = '" + periodOfStudy + "';");
                while (getResult().next()) {
                    int recordId = Integer.parseInt(getResult().getString(1));
                    double average = Double.parseDouble(getResult().getString(2));
                    String honour = getResult().getString(3);
                    String registrationNumber = getResult().getString(4);
                    records.addRow(recordId, average, honour, registrationNumber, periodOfStudy, registeredYesNo);
                }
            }
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }   
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return records;
        
    }
    
    public String getRecordRegStatus(String recordId){
        initConnection();
        initStatement();
        String reg = "";
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT Registered FROM Record WHERE `Record ID` = '" + recordId + "';");
                getResult().next();
                reg = getResult().getString(1);
                    
            }
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }   
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reg;
    }
    
    public Mark getStudentsModules(int recordId){
        
        initConnection();
        initStatement();
        Mark modules = new Mark();
        String degreeCode = getStudentDegree(getRegistrationNumber(recordId));
        
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT `Mark ID`, Mark.Module_ModuleCode, `Record_Record ID`, `The mark`, `Resit mark`, credits, Mark.coreOrNot\n" +
                                "FROM Mark INNER JOIN `Module degree (linking)` ON `Module degree (linking)`.Module_ModuleCode = Mark.Module_ModuleCode\n" +
                                "WHERE `Record_Record ID` = '" + recordId + "' AND `Degree_DegreeCode` = '" + degreeCode + "' ;" );
                while (getResult().next()) {
                    int markId = Integer.parseInt(getResult().getString(1));
                    String moduleCode = getResult().getString(2);
                    String mark = getResult().getString(4);
                    String resitMark = getResult().getString(5);
                    int credits = Integer.parseInt(getResult().getString(6));
                    String core = getResult().getString(7);
                    modules.addRow(markId,moduleCode,recordId,mark,resitMark,credits,core);
                }
            }
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }   
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return modules;
        
    }
    
    public String getStudentsLevel(String studentRegistrationNumber){
        
        initConnection();
        initStatement();
        String level = "";
        
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT `Level of study` FROM Student WHERE `Registration number` = '" + studentRegistrationNumber +  "' ;");
                getResult().next();
                level = getResult().getString(1);
            }
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }   
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return level;
        
    }
    
    public void updateRegistrationStatus(int recordId, String content){
        
        initConnection();
        initStatement();
        try {
            
            try {
                
                openConnection();
                openStatement();
                String query = "UPDATE Record SET `Registered` = '" + content + "' WHERE `Record ID` = '" + recordId + "';";
                int count = getStatement().executeUpdate(query);
                
            }
            
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
            
        }
        
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public String getFirstDegreeLevel (String degreeCode){
        
        initConnection();
        initStatement();
        String level = "";
        
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT `Level of study` FROM Degree WHERE DegreeCode = '"  + degreeCode +  "' ;");
                getResult().next();
                level = getResult().getString(1);
            }
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }   
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return level.substring(0,1);
        
    }
    
    public ArrayList<Integer> getStudentsRecords(String registrationNumber){
        
        initConnection();
        initStatement();
        ArrayList<Integer> recordId = new ArrayList<Integer>();
        
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT `Record ID` FROM Record WHERE `Student_Registration number` = '" 
                                    + registrationNumber  +  "' ;");
                while (getResult().next()) {
                    recordId.add(Integer.parseInt(getResult().getString(1)));
                }
            }
            finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }   
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return recordId;
        
    }
    
}
