package src.sql.tables;

/**
 * Class representing a collection of Module Links
 */
public class ModuleLinks extends Table {

    public ModuleLinks() {
        super();
    }
    public void addRow(String degreeCode, String degreeName, String moduleCode, String moduleName, String level, String semester, String credits, String coreOrNot) {
        super.addRow(new Object[] {degreeCode, degreeName, moduleCode, moduleName, level, semester, credits, coreOrNot});
    }
    public Boolean occursInTable(String moduleCode, String degreeCode) {
        return super.occursInTable(moduleCode, 2) && super.occursInTable(degreeCode, 0);
    }
    public String[] getModuleCodes() {
        int rowNum = getTableList().size();
        String[] moduleCodeList = new String[rowNum];
        for (int c = 0; c < rowNum; c++) {
            Object[] row = getTableList().get(c);
            moduleCodeList[c] = ((String) row[2]);
        }
        return moduleCodeList;
    }
}