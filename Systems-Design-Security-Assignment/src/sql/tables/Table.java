package src.sql.tables;

import java.util.ArrayList;

/**
 * Abstract class representing a table
 */
public abstract class Table {

    private ArrayList<Object[]> tableList;

    public Table() {
        tableList = new ArrayList<Object[]>();
    }
    public void addRow(Object[] row) {
        tableList.add(row);
    }
    public Object[][] getTable() {
        Object[][] table = new Object[tableList.size()][];
        table = tableList.toArray(table);
        return table;
    }
    public Object[] getColumn(int columnNum) {
        int rowNum = tableList.size();
        Object[] columnValues = new String[rowNum];
    
        for (int c = 0; c < rowNum; c++) {
            Object[] row = tableList.get(c);
            columnValues[c] = row[columnNum];
        }
        return columnValues;
    }
    /**
     * Method returns bool reflecting if a string occurs in a given column.
     * @param String targetValue - value we testing to see if it occurs in a given column
     * @param int columnNum - index of column we are searching. Index starts at 0.
     */
    public Boolean occursInTable(String targetValue, int columnNum) {
        for (String s: (String[]) getColumn(columnNum)) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }
    public ArrayList<Object[]> getTableList() { return tableList; }
    public int getNumOfRows() { return tableList.size(); }
}