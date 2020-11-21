package src.sql.model;

import src.sql.tables.Record;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 * Database model, handles all the SQL operations for all student related tasks.
 */
public class StudentDatabaseModel extends AdminDatabaseModel {

    public StudentDatabaseModel() {}

    public String getStudentRegNum(String studentUsername) {
        initConnection();
        initStatement();
        String studentRegNum = null;
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT `Registration number` FROM Student " + 
                                    "WHERE Username='" + studentUsername + "';");
                while (getResult().next()) {
                    studentRegNum = Integer.toString(getResult().getInt(1));
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return studentRegNum;
    }
    public ArrayList<String[]> getStudentYearInfo(String studentRegNum) {
        initConnection();
        initStatement();
        ArrayList<String[]> studentPeriodsOfStudy = new ArrayList<String[]>();
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery(
                        "SELECT pos.Label, pos.`Start date`, pos.`End date` " + 
                        "FROM `Period of study` pos, Record rec " + 
                        "WHERE pos.Label=rec.`Period of study_Label` " +  
                        "AND rec.`Student_Registration number`=" + studentRegNum + ";");
                while(getResult().next()) {
                    String periodOfStudyLabel = getResult().getString(1);
                    String startDate = getResult().getString(2);
                    String endDate = getResult().getString(3);
                    studentPeriodsOfStudy.add(new String[] {periodOfStudyLabel, startDate + " to " + endDate});
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return studentPeriodsOfStudy;
    }
    public String getGrade(String studentRegNum, String periodOfStudyLabel) {
        initConnection();
        initStatement();
        String grade = null;
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT `Honour class` FROM Record " +
                                "WHERE `Student_Registration number`="+ studentRegNum + 
                                " AND `Period of study_Label`='" + periodOfStudyLabel + "';");
                while (getResult().next()) {
                    grade = getResult().getString(1);
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return grade;
    }
}