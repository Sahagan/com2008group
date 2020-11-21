/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.sql.tables;

/**
 *
 * @author aca17mss
 */
public class Period extends Table {
    
    public Period(){
        super();
    }
    public void addRow (String label, String start, String end) {
        super.addRow(new Object[] {label,start,end});
    }
    public String[] getPeriodLabels(){
        return (String[]) super.getColumn(0);
    }
    
}
