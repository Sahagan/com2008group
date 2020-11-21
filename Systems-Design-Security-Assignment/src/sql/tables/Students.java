package src.sql.tables;

import java.util.ArrayList;

/**
 * Class representing a collection of students
 */
public class Students extends Table {

    public Students() {
        super();
    }
    public void addRow(String regNum, String title, String forename, String surname, String username, String email,  String levelOfStudy, String grade, String degree, String tutor) {
        super.addRow(new Object[]  {regNum, title, forename, surname, username, email,
                                    levelOfStudy, grade, degree, tutor});
    }

    public String[] getStudentNames() {
        return (String[]) getColumn(0);
    }

    public String[] getStudentInformation() {
        String[] info = new String [8];
        Object[] row = getTableList().get(0);
        for(int i = 0; i < info.length; i++) {
            info[i] = ((String) row[i]);
        }
        return info;
    }
}
