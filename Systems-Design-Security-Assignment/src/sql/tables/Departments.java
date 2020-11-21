package src.sql.tables;

import java.util.ArrayList;

/**
 * Class representing a collection of departments
 */
public class Departments extends Table {

    public Departments() {
        super();
    }
    public void addRow(String code, String name) {
        super.addRow(new Object[] {code, name});
    }
    public String[] getDepartmentNames() {
        return (String[]) super.getColumn(1);
    }
}