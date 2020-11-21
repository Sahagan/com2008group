package src.sql.tables;

/**
 * Class representing a collection of degree links
 */
public class DegreeLinks extends Table {

    public DegreeLinks() {
        super();
    }
    public void addRow(String degreeCode, String degreeName, String departmentCode, String departmentName) {
        super.addRow(new Object[] { degreeCode, degreeName, departmentCode, departmentName});
    }
    public Boolean occursInTable(String departmentCode, String degreeCode) {
        return super.occursInTable(degreeCode, 0) && super.occursInTable(departmentCode, 2);
    }
}