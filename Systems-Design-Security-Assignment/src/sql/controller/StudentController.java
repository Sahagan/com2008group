package src.sql.controller;

import java.util.ArrayList;

import src.sql.model.RegistrarDatabaseModel;
import src.sql.model.StudentDatabaseModel;
import src.sql.tables.Mark;

/***
 * Controller for student UI, handling the logic between
 * the UI and the SQL statements.
 */
public class StudentController {

    StudentDatabaseModel stDatabaseModel;
    RegistrarDatabaseModel regDatabaseModel;

    public StudentController() {
        stDatabaseModel = new StudentDatabaseModel();
        regDatabaseModel = new RegistrarDatabaseModel();
    }
    public ArrayList<String[]> getStudentYearInfo(String studentUsername) {
        String studentRegNum = stDatabaseModel.getStudentRegNum(studentUsername);
        return stDatabaseModel.getStudentYearInfo(studentRegNum);
    }
    public String getPeriodOfStudyLabel(String studentUsername, String periodOfStudy) {

        ArrayList<String[]> studentPeriodsOfStudy = getStudentYearInfo(studentUsername);

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

    public String[] getPeriodsOfStudy(String studentUsername) {

        ArrayList<String[]> studentYearInfo = getStudentYearInfo(studentUsername);
        int numOfYears = studentYearInfo.size();
        String[] periodsOfStudy = new String[numOfYears];

        for (int c = 0; c < numOfYears; c++) {
            periodsOfStudy[c] = studentYearInfo.get(c)[1];
        }
        return periodsOfStudy;
    }

    public String getYearsGrade(String studentUsername, String periodOfStudy) {

        String studentRegNum = stDatabaseModel.getStudentRegNum(studentUsername);
        String peroidOfStudyLabel = getPeriodOfStudyLabel(studentUsername, periodOfStudy);
        return stDatabaseModel.getGrade(studentRegNum, peroidOfStudyLabel);
    }

    public Object[][] getYearsModules(String studentUsername, String periodOfStudy) {
        System.out.println("studentUsername = "+studentUsername+", periodOfStudy = "+periodOfStudy);
        String studentRegNum = stDatabaseModel.getStudentRegNum(studentUsername);

        String peroidOfStudyLabel = getPeriodOfStudyLabel(studentUsername, periodOfStudy);
        int recordID = regDatabaseModel.getRecordId(studentRegNum, peroidOfStudyLabel);
        Mark marks = regDatabaseModel.getStudentsModules(recordID);
        return marks.getTable();
    }
}