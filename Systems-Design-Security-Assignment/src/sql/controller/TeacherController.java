package src.sql.controller;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import src.sql.controller.*;
import src.sql.model.TeacherDatabaseModel;
import src.sql.model.RegistrarDatabaseModel;
import src.sql.model.StudentDatabaseModel;
import src.sql.controller.SQLValidation;
import src.sql.controller.RegistrarController;
import src.sql.tables.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controller for teacher UI, handling the logic between the UI and the SQL
 * statements.
 */
public class TeacherController {

    private TeacherDatabaseModel teacherDatabaseModel;
    private RegistrarController registrarController;
    private RegistrarDatabaseModel regDatabaseModel;
    private StudentDatabaseModel stDatabaseModel;
    private SQLValidation validation;

    /**
     *
     */
    public TeacherController() {
        teacherDatabaseModel = new TeacherDatabaseModel();
        stDatabaseModel = new StudentDatabaseModel();
        regDatabaseModel = new RegistrarDatabaseModel();
        registrarController = new RegistrarController();
        validation = new SQLValidation();
    }

    //Get full list of modules and module codes

    /**
     *
     * @return the name/module code of all moduls
     */
    public String[] getModuleNames() {
        Modules modules = teacherDatabaseModel.getModules("*", "");
        String[] moduleNames = modules.getModuleNames();
        if (moduleNames == null || moduleNames.length == 0) {
            return new String[] {"No modules found"};
        } else {
            return moduleNames;
        }
    }

    /**
     *
     * @return An array of all students
     */
    public String[] getStudents() {
        Students students = teacherDatabaseModel.getStudents("*", "");
        return students.getStudentNames();
    }

    //All student information

    /**
     *
     * @param cond the student registation number
     * @return all information regarding a given student
     */
    public String [] studentInfo(String cond) {
        Students theStudent = teacherDatabaseModel.getStudents("*", cond);
        return theStudent.getStudentInformation();
    }

    /**
     *
     * @param cond the student regiatration number
     * @return the tutor of a given student
     */
    public String getStudentTutor(String cond) {
        return teacherDatabaseModel.getTutor(cond);
    }

    /**
     *
     * @param student the student regiatration number
     * @return a list of all modules that a student studies
     */
    public String[] getModuleList(String student) {
        String record = getRecordId(student);
        ArrayList<String> recordList = teacherDatabaseModel.getStudentModuleCode(record);
        String[] asArray = recordList.stream().toArray(String[]::new);
        return asArray;
    }

    /**
     *
     * @param studentUsername the student regiatration number
     * @return an ArrayList containing all student info for a given year
     */
    public ArrayList<String[]> getStudentYearInfo(int studentUsername) {
        return stDatabaseModel.getStudentYearInfo(String.valueOf(studentUsername));
    }

    /**
     *
     * @param studentUsername the student regiatration number
     * @return all periods of study a student has
     */
    public String[] getPeriodsOfStudy(int studentUsername) {

        ArrayList<String[]> studentYearInfo = getStudentYearInfo(studentUsername);
        int numOfYears = studentYearInfo.size();
        String[] periodsOfStudy = new String[numOfYears];

        for (int c = 0; c < numOfYears; c++) {
            periodsOfStudy[c] = studentYearInfo.get(c)[1];
        }
        return periodsOfStudy;
    }

    /**
     *
     * @param student the student regiatration number
     * @param periodOfStudy the period of study
     * @return all modules for a given year of study
     */
    public Object[][] getYearsModules(String student, String periodOfStudy) {
        String peroidOfStudyLabel = getPeriodOfStudyLabel(student, periodOfStudy);
        int recordID = regDatabaseModel.getRecordId(student, peroidOfStudyLabel);
        Mark marks = regDatabaseModel.getStudentsModules(recordID);
        return marks.getTable();
    }

    /**
     *
     * @param student the student regiatration number
     * @param periodOfStudy the period of study
     * @return the label attached to a given period of study
     */
    public String getPeriodOfStudyLabel(String student, String periodOfStudy) {

        ArrayList<String[]> studentPeriodsOfStudy = getStudentYearInfo(Integer.parseInt(student));

        String periodOfStudyLabel = null;
        int numOfYears = studentPeriodsOfStudy.size();
        for (int c = 0; c < numOfYears; c++) {
            String periodOfStudyTest = studentPeriodsOfStudy.get(c)[1];
            if (periodOfStudyTest.equals(periodOfStudy)) {

                periodOfStudyLabel = studentPeriodsOfStudy.get(c)[0];
            }
        }
        return periodOfStudyLabel;
    }

    /**
     *
     * @param cond  the student regiatration number
     * @return the degree name of a given a student
     */
    public String getDegreeName(String cond) {
        return teacherDatabaseModel.getDegreeName(cond);
    }

    //Get grade for a given module

    /**
     *
     * @param cond the student regiatration number
     * @param module the module
     * @param resit whether or not the grade is a resit grade
     * @return the grade for a given module of a student
     */
    public String getGrade(String cond, String module, String resit) {
        return teacherDatabaseModel.getCurrentGrade(cond, module, resit);
    }

    /**
     *
     * @param student the student regiatration number
     * @param module the module
     * @param grade the grade
     * @param resit whether or not it is a resit grade
     * @return whether update grade completed successfully
     */
    public boolean updateGrade(String student, String module, String grade, boolean resit) {
        String validateGrade = validation.generalValidation(grade);
        if(!(validateGrade.equals(grade))) {
            return false;
        }
        if(resit) {
            if(Integer.parseInt(grade) >= 40) {
                grade = "40";
            }else if(Integer.parseInt(grade) < 0) {
                return false;
            }
        }else {
            if(Integer.parseInt(grade) < 0 || Integer.parseInt(grade) > 100) {
                return false;
            }
        }
        teacherDatabaseModel.insertGrade(student, module, grade, resit);
        return true;
    }

    /**
     *
     * @param student the student regiatration number
     */
    public Boolean createPassStudent(String student) {
        if(getLevelOfStudy(student).equals(getDegreeLevels(student))) {
            return false;
        }else {
            ArrayList<Integer> periods = getPeriodsofStudy(student);
            int currentYear = Collections.max(periods);
            updateLevelOfStudy(student);
            if(!checkPeriodOfStudy(currentYear)) createPeriodOfStudy(currentYear);
            registrarController.registerStudent("" + (currentYear+1), student);
            return true;
        }
    }

    /**
     *
     * @param student the student regiatration number
     */
    public void createFailStudent(String student) {

        ArrayList<Integer> periods = getPeriodsofStudy(student);
        int currentYear = Collections.max(periods);
        String currentLevel = getLevelOfStudy(student);
        String query = "";
        if(getDegreeType(student).equals("B") && getLevelOfStudy(student).equals("3")) {
            query = "UPDATE Record SET Registered = 'Gradduated' WHERE "
                + "`Student_Registration number` = '" + student + "' AND "
                + "`Period of study_Label` = '" + currentYear + "';";
        }else if(getDegreeType(student).equals("M") && getLevelOfStudy(student).equals("4")){
            query = "UPDATE Record SET Registered = 'Graduated' WHERE "
                + "`Student_Registration number` = '" + student + "' AND "
                + "`Period of study_Label` = '" + currentYear + "';";
        }else {
            query = "UPDATE Record SET Registered = 'Failed' WHERE "
                + "`Student_Registration number` = '" + student + "' AND "
                + "`Period of study_Label` = '" + currentYear + "';";
        }
        teacherDatabaseModel.updateQuery(query);

        if(periods.size() > 1) {
            int previousRecord = regDatabaseModel.getRecordId(student, "" + (currentYear - 1));
            String status = regDatabaseModel.getRecordRegStatus("" + previousRecord);
            if(!status.equals("Failed")) repeatYear(student,currentYear);
        }
        else repeatYear(student,currentYear);
    }
    /**
    *
    * @param student the student regiatration number
    * @param currentYear the current year of study
    */
    private void repeatYear(String student, int currentYear){

        if(!checkPeriodOfStudy(currentYear)) createPeriodOfStudy(currentYear);
        Mark marksToClone = regDatabaseModel.getStudentsModules(Integer.parseInt(getRecordId(student)));
        registrarController.createRecord("" + (currentYear+1),student);

        String[] codes = (String []) marksToClone.getColumn(1);
        String[] marks = (String []) marksToClone.getColumn(3);
        String[] resitMarks = (String []) marksToClone.getColumn(4);
        for(int i=0;i<codes.length;i++) registrarController.pasteMark(Integer.parseInt(getRecordId(student)),codes[i],marks[i],resitMarks[i]);

    }

    /**
     *
     * @param student the student regiatration number
     * @return whether or not the student can graduate
     */
    public boolean createGraduate(String student) {
        if(getLevelOfStudy(student).equals(getDegreeLevels(student))) {
            graduateSudent(student);
            return true;
        }else {
            return false;
        }
    }

    /**
     *
     * @param student the student regiatration number
     */
    public void createResitStudent(String student) {
        ArrayList<Integer> periods = getPeriodsofStudy(student);
        int currentYear = Collections.max(periods);
        String query = "UPDATE Record SET Registered = 'Resit' WHERE "
            + "`Student_Registration number` = '" + student + "' AND "
            + "`Period of study_Label` = '" + currentYear + "';";
        teacherDatabaseModel.updateQuery(query);
        updatePeriodOfStudy(student);
    }

    /**
     *
     * @param student the student regiatration number
     */
    public void graduateSudent(String student) {
        ArrayList<Integer> periods = getPeriodsofStudy(student);
        int currentYear = Collections.max(periods);
        double theGrade = getOverallGrade(student);
        String levels = getDegreeLevels(student);
        String finalGrade = "";
        if(getPeriodsofStudy(student).size() ==  1) {
            finalGrade = getOneYearResult(theGrade);
        }else if(levels.equals("3")) {
            finalGrade = getBachelorResult(theGrade);
        }else if(levels.equals("4")) {
            finalGrade = getMasterResult(theGrade);
        }
        if(getLevelOfStudy(student).equals("4")) {
            if(getWeightedMean(student) <= 49.5) {
                finalGrade = "Bachelors " + getBachelorResult(getFailGrade(student));
            }else {
                finalGrade = "Masters " + finalGrade;
            }
        }else if(getLevelOfStudy(student).equals("3")) {
            finalGrade = "Bachelors " + finalGrade;
        }else {
            finalGrade = "One Year " + finalGrade;
        }
        String updateHonours = "UPDATE Student SET grade = '" + finalGrade +
        "' WHERE `Registration number` = '" + student + "';";
        String query = "UPDATE Record SET Registered = 'Graduated' WHERE "
            + "`Student_Registration number` = '" + student + "' AND "
            + "`Period of study_Label` = '" + currentYear + "';";
        teacherDatabaseModel.updateQuery(query);
        teacherDatabaseModel.updateQuery(updateHonours);
    }

    /**
     *
     * @param student the student regiatration number
     * @return the final grade of a failed masters degree
     */
    public double getFailGrade(String student) {
        String query = "SELECT Average FROM Record WHERE "
            + "`Student_Registration number` = '" + student + "';";
        ArrayList<Double> grades = teacherDatabaseModel.getAllMeanGrades(query);
        double total = 0.0;
        total += grades.get(1) * (1.0 / 3);
        total += grades.get(2) * (2.0 / 3);
        return total;
    }

    /**
     *
     * @param student the student regiatration number
     * @return the final grade of a completed degree
     */
    public double getOverallGrade(String student) {
        String query = "SELECT Average FROM Record WHERE "
            + "`Student_Registration number` = '" + student + "';";
        ArrayList<Double> grades = teacherDatabaseModel.getAllMeanGrades(query);
        double total = 0.0;
        for(int i = 0; i < grades.size(); i++) {
            total += grades.get(i);
        }
        return total;
    }

    /**
     *
     * @param student the student regiatration number
     * @return the levels of study of a given degree
     */
    public String getDegreeLevels(String student) {
        String getCode = "SELECT Degree_DegreeCode FROM Student WHERE "
            + "`Registration number` = '" + student + "';";
        String theDegreeCode = teacherDatabaseModel.stringQuery(getCode);
        String getLevels = "SELECT `Level of study` FROM Degree WHERE "
            + "DegreeCode = '" + theDegreeCode + "';";
        String result = teacherDatabaseModel.stringQuery(getLevels);
        return result.substring((result.length() - 1), result.length());
    }

    /**
     *
     * @param student the student regiatration number
     * @param period the period to be created
     */
    public void createPeriodOfStudy(String student, String period) {
        registrarController.registerStudent(student, period);
    }

    /**
     *
     * @param student the student regiatration number
     */
    public void updateLevelOfStudy(String student) {
        String currentLevel = getLevelOfStudy(student);
        String query = "UPDATE Student SET `Level of study` = '" + findNextLevelOfStudy(currentLevel,student)
            + "' WHERE `Registration number` = '" + student + "';";
        teacherDatabaseModel.updateQuery(query);
    }

    private String findNextLevelOfStudy(String currentLevel, String student){

        String degreeLevels = teacherDatabaseModel.getDegreeType(student);
        for(int i =0; i<degreeLevels.length()-1;i++) if(("" + degreeLevels.charAt(i)).equals(currentLevel)) return "" + degreeLevels.charAt(i+1);
        return currentLevel;
    }

    /**
     *
     * @param student the student regiatration number
     */
    public void updatePeriodOfStudy(String student) {
        ArrayList<Integer> periods = getPeriodsofStudy(student);
        int currentYear = Collections.max(periods);
        if(!checkPeriodOfStudy(currentYear)) {
            createPeriodOfStudy(currentYear);
        }else {
            String query = "UPDATE Record SET `Period of study_Label` = '" + (currentYear + 1)
                + "' WHERE `Student_Registration number` = '" + student + "';";
            teacherDatabaseModel.updateQuery(query);
        }
    }

    /**
     *
     * @param period the student regiatration number
     * @return true if period of study exists, false otherwise
     */
    public boolean checkPeriodOfStudy(int period) {
        String query = "SELECT * FROM `Period of study` WHERE `Label` = '" + (period + 1) + "';";
        return teacherDatabaseModel.executeBoolQuery(query);
    }

    /**
     *
     * @param period the period of study to be created
     */
    public void createPeriodOfStudy(int period) {
        String table = "`Period of study`";
        String start = "01.09." + (period + 1);
        String end = "01.07." + (period + 1);
        String values = "('" + (period + 1) + "','" + start + "','" + end + "')";
        teacherDatabaseModel.insertIntoDatabase(table, values);
    }

    /**
     *
     * @param student the student regiatration number
     * @return the final result of a degree
     */
    public String theDegreeResult(String student) {
        String initialResult = getDegreeResult(student);
        ArrayList<Integer> allGrades = new ArrayList<Integer>();
        allGrades = teacherDatabaseModel.getGradeList(student);
        if(getDegreeType(student).equalsIgnoreCase("One Year Msc")) {
            return postgradResult(student);
        }
        if(initialResult.equalsIgnoreCase("fail")) {
            if(!getLevelOfStudy(student).equals("4")) {
                int creditsEarned = creditsAchieved(student);
                if(checkMinCreditsReq(student) && creditsAchieved(student) != 120
                    && modulesPassed(student) == (numberOfModules(student) - 1) ) {
                    return "Conceded Pass";
                }else {
                    return "Fail";
                }
            }else if(getDegreeType(student).equalsIgnoreCase("One Year Msc")) {
                return postgradResult(student);
            }else {
                int creditsEarned = creditsAchieved(student);
                if(creditsEarned == 120) {
                    return getBachelorResult(getWeightedMean(student));
                }else if(creditsEarned == 180) {
                    return getMasterResult(getWeightedMean(student));
                }else if(checkMinCreditsReq(student) && creditsAchieved(student) != 180
                    && modulesPassed(student) == (numberOfModules(student) - 1)) {
                    return "Conceded Pass";
                }else {
                    return studentFailed(student);
                }
            }
        }else {
            if(getLevelOfStudy(student).equals("4")) {
                if(creditsAchieved(student) == 180) {
                    return initialResult;
                }else if(creditsAchieved(student) >= 165) {
                    return getMasterResult(getWeightedMean(student));
                }else {
                    if(creditsAchieved(student) >= 120) {
                        return postgradResult(student);
                    }else {
                        return "Fail";
                    }
                }
            }else {
                if(creditsAchieved(student) == 120) {
                    return initialResult;
                }else if(creditsAchieved(student) <= 100 &&
                    modulesPassed(student) == (numberOfModules(student) - 1)) {
                    int minGrade = allGrades.indexOf(Collections.min(allGrades));
                    if(minGrade > 29.5) {
                        return "Conceded Pass";
                    }
                }else {
                    return "Fail";
                }
            }
        }
        return "Error";
    }

    /**
     *
     * @param student the student regiatration number
     * @return the result of a postgraduate degree
     */
    public String postgradResult(String student) {
        int credits = creditsAchieved(student);
        int dissertation = checkDissertationGrade(student);
        if(dissertation == 1) {
            ArrayList<Integer> allGrades = new ArrayList<Integer>();
            allGrades = teacherDatabaseModel.getGradeList(student);
            int minGrade = (Collections.min(allGrades));
            if(creditsAchieved(student) == 180) {
                return "Proceed to graduation";
            }else if(minGrade >= 49.5 && creditsAchieved(student) >= 165) {
                return "Graduate with bachelors";
            }else if(minGrade >= 39.5 && creditsAchieved(student) >= 165) {
                return "Conceded Pass";
            }else {
                return "Fail";
            }
        }else if(dissertation == 0) {
            if(creditsAchieved(student) >= 120) {
                return "Acheieved Postgraduate Ceritificate";
            }else {
                return "Fail";
            }
        }else {
            return "Fail";
        }
    }

    //Calculate the degree result for a given year

    /**
     *
     * @param student the student regiatration number
     * @return the degree result
     */
    public String getDegreeResult(String student) {
        final double theMeanGrade = getWeightedMean(student);
        if(theMeanGrade <= 39.5) {
            return "FAIL";
        }else {
            String type = getDegreeType(student);
            if(type.startsWith("B")) {
                return getBachelorResult(theMeanGrade);
            }else if(type.startsWith("M")) {
                return getMasterResult(theMeanGrade);
            }else {
                return getOneYearResult(theMeanGrade);
            }
        }
    }

    //Check if degree is bachelor/master/1 year MSc

    /**
     *
     * @param student the student regiatration number
     * @return the degree type
     */
    public String getDegreeType(String student) {
        String theLength = teacherDatabaseModel.getDegreeType(student);
        if(theLength.equals("123") || theLength.equals("12P3")) {
            return "B";
        }else if(theLength.equals("1234") || theLength.equals("123P4")) {
            return "M";
        }else {
            return "One Year Msc";
        }
    }

    //Get the degree result for a given year

    /**
     *
     * @param theGrade the mean grade
     * @return the result
     */
    public String getBachelorResult(final double theGrade) {
        if(theGrade < 39.5) {
            return "Fail";
        }else if(theGrade <= 44.4) {
            return "Pass (non-honours)";
        }else if((theGrade >= 44.5) && (theGrade <= 49.4)) {
            return "Third Class";
        }else if((theGrade >= 49.5) && (theGrade <= 59.4)) {
            return "Lower Second";
        }else if((theGrade >= 59.5) && (theGrade <= 69.4)) {
            return "Upper Second";
        }else {
            return "First Class";
        }
    }

    /**
     *
     * @param theGrade the weighted mean grade
     * @return the degree result
     */
    public String getMasterResult(final double theGrade) {
        if(theGrade <= 49.4) {
            return "FAIL";
        }else if((theGrade >= 49.5) && (theGrade <= 59.4)) {
            return "Lower Second";
        }else if((theGrade >= 59.5) && (theGrade <= 69.4)) {
            return "Upper Second";
        }else {
            return "First Class";
        }
    }

    /**
     *
     * @param theGrade the weighted mean grade
     * @return the degree result
     */
    public String getOneYearResult(final double theGrade) {
        if(theGrade <= 49.4) {
            return "FAIL";
        }else if((theGrade >= 49.5) && (theGrade <= 59.4)) {
            return "Pass";
        }else if((theGrade >= 59.5) && (theGrade <= 69.4)) {
            return "Merit";
        }else {
            return "Distinction";
        }
    }

    /**
     *
     * @param name the student regiatration number
     * @return the result given they have failed
     */
    public String studentFailed(String name) {
        String type = getDegreeType(name);
        String level = getLevelOfStudy(name);
        if(level.equals("4")) {
            return "Pass with bachelor’s degree";
        }else if(level.equals("3") && type.startsWith("B")
            || type.startsWith("M")) {
            return "Resit for pass(non-honours) degree";
        }else {
            return "Null";
        }
    }

    /**
     *
     * @param theName the student regiatration number
     * @return the current level of study
     */
    public String getLevelOfStudy(String theName) {
        return teacherDatabaseModel.getLevelOfStudy(theName);
    }

    /**
     *
     * @param student the student regiatration number
     * @return the weighted mean grade for the current period
     */
    public double getWeightedMean(String student) {
        double weightedMean = 0;
        ArrayList<Integer> allGrades = new ArrayList<Integer>();
        ArrayList<Integer> allResitGrades = new ArrayList<Integer>();
        ArrayList<Integer> theModules = new ArrayList<Integer>();
        allGrades = teacherDatabaseModel.getGradeList(student, "The mark");
        allResitGrades = teacherDatabaseModel.getGradeList(student, "Resit mark");
        theModules = getCreditValue(student);
        double divisor = 0;
        if(getLevelOfStudy(student).equals("4")) {
            divisor = 180;
        }else{
            divisor = 120;
        }
        for(int i = 0; i < allGrades.size(); i++) {
            if(allResitGrades.get(i) != -1) {
                weightedMean += (allResitGrades.get(i) * (theModules.get(i) / divisor));
            }else {
                weightedMean += (allGrades.get(i) * (theModules.get(i) / divisor));
            }
        }
        updateWeightedMean(student, roundResults(weightedMean));
        return roundResults(weightedMean);
    }

    //Get a list of all periods of study

    /**
     *
     * @param student the student regiatration number
     * @return an ArrayList of all periods of study
     */
    public ArrayList<Integer> getPeriodsofStudy(String student) {
        String query = "SELECT `Period of study_Label` FROM Record WHERE "
                + "`Student_Registration number` = '" + student + "';";
        System.out.println("714: student = "+student);
        ArrayList<String> results = new ArrayList<String>();
        results = teacherDatabaseModel.getPeriodOfStudy(query);
        System.out.println("716: results = "+results);
        ArrayList<Integer> resultAsInt = new ArrayList<Integer>(results.size()) ;
        for (String temp : results) {
            resultAsInt.add(Integer.valueOf(temp));
        }
        return resultAsInt;
    }

    //Add weighted mean to database

    /**
     *
     * @param student the student regiatration number
     * @param mean the weighted mean grade
     */
    public void updateWeightedMean(String student, double mean) {
        ArrayList<Integer> periods = getPeriodsofStudy(student);
        System.out.println("periods = "+periods);
        int currentYear = Collections.max(periods);
        String query = "UPDATE Record SET Average = '" + mean + "' WHERE "
            + "`Period of study_Label` = '" + currentYear + "' AND "
            + "`Student_Registration number` = '" + student + "';";
        teacherDatabaseModel.updateQuery(query);
    }

    /**
     *
     * @param student the student regiatration number
     * @return the credit value of all modules for a given student
     */
    public ArrayList<Integer> getCreditValue(String student) {
        String record = getRecordId(student);
        ArrayList<String> moduleCodes = new ArrayList<String>();
        moduleCodes = teacherDatabaseModel.getStudentModuleCode(record);
        ArrayList<Integer> creditValues = new ArrayList<Integer>();
        for(int i = 0; i < moduleCodes.size(); i++) {
            creditValues.add(teacherDatabaseModel.getCredits(student, moduleCodes.get(i)));
        }
        return creditValues;
    }

    /**
     *
     * @param student the student regiatration number
     * @return the record ID for a given student
     */
    public String getRecordId(String student) {
        String query = "WHERE `Student_Registration number` = '" + student + "';";
        return teacherDatabaseModel.getRecordId(query);
    }

    /**
     *
     * @param student the student regiatration number
     * @return wheter the student has achieved above the min grade in all modules
     */
    public boolean checkMinGradeReq(String student) {
        ArrayList<Integer> allGrades = new ArrayList<Integer>();
        allGrades = teacherDatabaseModel.getGradeList(student);
        System.out.println("allgrade = "+allGrades);
        if(allGrades == null || allGrades.size() == 0){
            System.out.println("tttttttttttttttttttttttttt");
            return true;
        }else{
            int minGrade = allGrades.indexOf(Collections.min(allGrades));
            if(!getLevelOfStudy(student).equals("4")) {
                return (minGrade < 29.5);
            }else {
                return (minGrade < 39.5);
            }
        }

    }

    /**
     *
     * @param student the student regiatration number
     * @return true if student has achieved min number of credits, false otherwise
     */
    public boolean checkMinCreditsReq(String student) {
        int totalCreds = creditsAchieved(student);
        if(checkMinGradeReq(student)) {
            return false;
        }else {
            if(getLevelOfStudy(student).equals("4")) {
                return (totalCreds >= 165);
            }else {
                return (totalCreds >= 100);
            }
        }
    }

    /**
     *
     * @param student the student regiatration number
     * @return number of credits achieved by the student at the current period
     */
    public int creditsAchieved(String student) {
        ArrayList<Integer> credits = new ArrayList<Integer>();
        credits = getCreditValue(student);
        ArrayList<Integer> allGrades = new ArrayList<Integer>();
        allGrades = teacherDatabaseModel.getGradeList(student);
        int totalCreds = 0;
        for(int i = 0; i < allGrades.size(); i++) {
            if(!getLevelOfStudy(student).equals("4")) {
                if(allGrades.get(i) >= 39.5) {
                    totalCreds += credits.get(i);
                }
            }else {
                if(allGrades.get(i) >= 49.5) {
                    totalCreds += credits.get(i);
                }
            }
        }
        return totalCreds;
    }

    /**
     *
     * @param student the student regiatration number
     * @return 1 1 to be considered for PGDip 0 for PGCert, -1 for fail
     */
    public int checkDissertationGrade(String student) {
        ArrayList<String> possibleModules = teacherDatabaseModel.getDissertationModules();
        ArrayList<String> stuModules = teacherDatabaseModel.getStudentModuleCode(getRecordId(student));
        String theModule = "";
        for(int i = 0; i < possibleModules.size(); i++) {
            if(stuModules.get(i).equals(possibleModules.get(i))) {
                theModule = possibleModules.get(i);
            }
            if(possibleModules.size() == (i - 1)) {
                return -1;
            }
        }
        double grade = Double.parseDouble(teacherDatabaseModel.getCurrentGrade(student, theModule, "false"));
        if(grade >= 49.5) {
            return 1;
        }else {
            if(creditsAchieved(student) >= 60) {
                return 0;
            }else {
                return -1;
            }
        }
    }

    /**
     *
     * @param student the student regiatration number
     * @return the final degree result
     */
    public String calculateFinalResult(String student) {
        ArrayList<Integer> studyPeriods = getPeriodsofStudy(student);
        double currentYear = Collections.max(studyPeriods);
        String query = "SELECT Average FROM Record WHERE "
        + "`Student_Registration number` = '" + student + " AND "
        + "``Period of study_Label` = '" + currentYear + "';";
        int thisYearResult = teacherDatabaseModel.getWeightedMean(query);
        query ="SELECT Average FROM Record WHERE "
        + "`Student_Registration number` = '" + student + " AND "
        + "``Period of study_Label` = '" + (currentYear - 1) + "';";
        double lastYearResult = teacherDatabaseModel.getWeightedMean(query);
        if(getLevelOfStudy(student).equals("3")) {
            double totalAverage = (thisYearResult * (2.0/3)) + (lastYearResult * (1.0/3));
            totalAverage = roundResults(totalAverage);
            return getBachelorResult(totalAverage);
        }else if(getLevelOfStudy(student).equals("4")) {
            query ="SELECT Average FROM Record WHERE "
            + "`Student_Registration number` = '" + student + " AND "
            + "``Period of study_Label` = '" + (currentYear - 2) + "';";
            double twoYearRes = teacherDatabaseModel.getWeightedMean(query);
            double totalAverage = (thisYearResult * (2.0/5)) + (lastYearResult * (2.0/5))
                + (twoYearRes * (1.0/5));
            totalAverage = roundResults(totalAverage);
            return getMasterResult(totalAverage);
        }else {
            return getOneYearResult(thisYearResult);
        }
    }

    /**
     *
     * @param student the student regiatration number
     * @return the number of modules the student has passed
     */
    public int modulesPassed(String student) {
        ArrayList<Integer> credits = new ArrayList<Integer>();
        credits = getCreditValue(student);
        ArrayList<Integer> allGrades = new ArrayList<Integer>();
        allGrades = teacherDatabaseModel.getGradeList(student);
        int total = 0;
        for(int i = 0; i < allGrades.size(); i++) {
            if(!getLevelOfStudy(student).equals("4")) {
                if(allGrades.get(i) >= 39.5) {
                    total ++;
                }
            }else {
                if(allGrades.get(i) >= 49.5) {
                    total++;
                }
            }
        }
        return total;
    }

    /**
     *
     * @param student the student regiatration number
     * @return the number of modules the student is currently studying
     */
    public int numberOfModules(String student) {
        ArrayList<Integer> allGrades = new ArrayList<Integer>();
         return (teacherDatabaseModel.getGradeList(student).size());

    }

    /**
     *
     * @param value the value to be rounded
     * @return the value rounded to 1 decimal place
     */
    private double roundResults (double value) {
        int scale = (int) Math.pow(10, 1);
        return (double) Math.round(value * scale) / scale;
    }

}
