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
public class Record extends Table {
    
    public Record() {
        super();
    }
    public void addRow (int recordId, double average, String honour, String registrationNumber, String periodOfSuty, String registered) {
        super.addRow(new Object[] {recordId,average,honour,registrationNumber,periodOfSuty,registered});
    }
    public String[] getRegistrationNumbers(){
        return (String[]) super.getColumn(3);
    }
    
}
