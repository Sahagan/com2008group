package src.sql.tables;

/**
 * Class representing a collection of degrees
 */
public class Degrees extends Table{

    public Degrees() {
        super();
    }
    public void addRow(String code, String name, String levelOfStudy, String leadDepartment) {
        super.addRow(new Object[] {code, name, levelOfStudy, leadDepartment});
    }
    public String[] getDegreeNames() {
        return (String[]) super.getColumn(1);
    }
    public String[] getDegreeCodes() {
        return (String[]) super.getColumn(0);
    }
}