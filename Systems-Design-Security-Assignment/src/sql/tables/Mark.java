/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.sql.tables;

/**
 * Class representing a collection of marks
 */
public class Mark extends Table {
    
    public Mark() {
        super();
    }
    public void addRow (int markId, String moduleCode, int recordId, String mark, String resitMark, int credits, String coreOrNot) {
        super.addRow(new Object[] {markId,moduleCode,recordId,mark,resitMark,credits,coreOrNot});
    }
    public int getSumOfCredits(){
        
        int rowNum = getTableList().size();
        int sum = 0;
        for (int c = 0; c < rowNum; c++) {
            Object[] row = getTableList().get(c);
            sum += (int)row[5];
        }
        return sum;
    }
}
