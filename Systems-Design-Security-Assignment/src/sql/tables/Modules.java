package src.sql.tables;

import java.util.ArrayList;

/**
 * Class representing a collection of Modules
 */
public class Modules extends Table {

    public Modules() {
        super();
    }
    public void addRow(String code, String name, String teachingDepartment) {
        super.addRow(new Object[] {code, name, teachingDepartment});
    }
    public String[] getModuleNames() {
        int rowNum = getTableList().size();
        String[] moduleNameList = new String[rowNum];
        for (int c = 0; c < rowNum; c++) {
            Object[] row = getTableList().get(c);
            moduleNameList[c] = ((String) row[0]) + " " + ((String) row[1]);
        }
        return moduleNameList;
    }
}