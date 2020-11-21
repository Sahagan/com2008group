package src.sql.model;
import src.sql.tables.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database model, handles all the SQL operations for all teacher related tasks.
 */
public class TeacherDatabaseModel extends AdminDatabaseModel {
    public TeacherDatabaseModel() {}

    /**
     *
     * @param condition the SQL query
     * @return the tutor of  given student
     */
    public String getTutor(String condition) {
        initConnection();
        initStatement();
        String results = "";
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT Tutor FROM Student " + condition);
                while (getResult().next()) {
                    results = getResult().getString(1);
                }

            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return results;
    }

    /**
     *
     * @param condition the registation number
     * @return the degree name
     */
    public String getDegreeName(String condition) {
        initConnection();
        initStatement();
        String results = "";
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT Degree_DegreeCode FROM  Student WHERE `Registration number` = '"
                    + condition + "';");
                while (getResult().next()) {
                    results = getResult().getString(1);
                }
                String query = "WHERE DegreeCode = '" + results + "';";
                openResultQuery("SELECT `Degree name` FROM Degree " + query);
                while (getResult().next()) {
                    results = getResult().getString(1);
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return results;
    }

    /**
     *
     * @param regNo the registation number of a given student
     * @param moduleCode the module code
     * @param resit true if grade is a resit, false otherwise
     * @return the grade of a given module
     */
    public String getCurrentGrade(String regNo, String moduleCode, String resit) {
        initConnection();
        initStatement();
        String results = "";
        String whichMark = "`The mark`";
        if(resit == "true") whichMark = "`Resit mark`";

        try {
            try {
                openConnection();
                openStatement();
                String query = "WHERE `Student_Registration number` = '" + regNo + "';";
                String record = getRecordId(query);
                openConnection();
                openStatement();
                query = "WHERE `Record_Record ID` = '" + record +
                "' AND Module_ModuleCode = '" + moduleCode + "';";
                openResultQuery("SELECT " + whichMark + " FROM Mark " + query);
                while (getResult().next()) {
                    results = getResult().getString(1);
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return results;
    }

    /**
     *
     * @param cond the SQL query
     * @return the record ID for a given student
     */
    public String getRecordId(String cond) {
        initConnection();
        initStatement();
        String result = "";
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT `Record ID` FROM  Record " + cond);
                while (getResult().next()) {
                    result = getResult().getString(1);
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param name the student registation number
     * @param module the module code
     * @param grade the grade
     * @param resit true if grade is a resit, false otherwise
     */
    public void insertGrade(String name, String module, String grade, boolean resit) {
        initConnection();
        initStatement();
        String theModule = module.substring(0, 7);
        try {
            try {
                openConnection();
                openStatement();
                String query = "WHERE `Student_Registration number` = '" + name + "';";
                String record = getRecordId(query);
                if(resit) {
                    query = "UPDATE Mark SET `Resit mark` = " + grade +
                        " WHERE `Record_Record ID` = " + record + " AND "
                        + "Module_ModuleCode = '" + theModule + "';";

                }else {
                    query = "UPDATE Mark SET `The mark` = " + grade +
                        " WHERE `Record_Record ID` = " + record + " AND "
                        + "Module_ModuleCode = '" + theModule + "';";
                }
                openConnection();
                openStatement();
                int count = getStatement().executeUpdate(query);
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param student the student registation number
     * @return an ArrayList of all grades
     */
    public ArrayList<Integer> getGradeList(String student) {
        initConnection();
        initStatement();
        String query = "WHERE `Student_Registration number` = '" + student + "';";
        ArrayList<Integer> results = new ArrayList<Integer>();
        try {
            try {
                String record = getRecordId(query);
                System.out.println("td198: student = "+student+", record = "+record);
                openConnection();
                openStatement();
                openResultQuery("SELECT `The mark` FROM Mark WHERE "
                + "`Record_Record ID` = '" + record + "';");
                while (getResult().next()) {
                    results.add(Integer.parseInt(getResult().getString(1)));
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return results;
    }

    /**
     *
     * @param student the student registation number
     * @param type The Mark if mark, Resit mark if resit mark
     * @return an ArrayList of all grades
     */
    public ArrayList<Integer> getGradeList(String student, String type) {
        initConnection();
        initStatement();
        String query = "WHERE `Student_Registration number` = '" + student + "';";
        ArrayList<Integer> results = new ArrayList<Integer>();
        try {
            try {
                String record = getRecordId(query);
                openConnection();
                openStatement();
                openResultQuery("SELECT `" + type + "` FROM Mark WHERE "
                + "`Record_Record ID` = '" + record + "';");
                while (getResult().next()) {
                    results.add(Integer.parseInt(getResult().getString(1)));
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return results;
    }

    /**
     *
     * @param student the student registation number
     * @return the current level of study
     */
    public String getLevelOfStudy(String student) {
        initConnection();
        initStatement();
        String result = "";

        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT `Level of study` FROM Student" +
                    " WHERE `Registration number` = '" + student + "';");
                while (getResult().next()) {
                    result = getResult().getString(1);
                    System.out.println("student = "+student+", result = "+result);
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param student the student registation number
     * @return the degree type of a student
     */
    public String getDegreeType(String student) {
        initConnection();
        initStatement();
        String result = "";
        try {
            try {
                openConnection();
                openStatement();
                String query = "SELECT Degree_DegreeCode FROM Student " +
                    "WHERE `Registration number` = '" + student + "';";
                String degreeCode = "";
                openResultQuery(query);
                while (getResult().next()) {
                    degreeCode = getResult().getString(1);
                }
                query = "SELECT `Level of study` FROM Degree " +
                    "WHERE DegreeCode = '" + degreeCode + "';";
                openConnection();
                openStatement();
                openResultQuery(query);
                while (getResult().next()) {
                    result = getResult().getString(1);
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param record the record ID of a student
     * @return and ArrayList with all module codes
     */
    public ArrayList<String> getStudentModuleCode(String record) {
        initConnection();
        initStatement();
        ArrayList<String> theResults = new ArrayList<String>();
        try {
            try {
                openConnection();
                openStatement();
                    String query = "SELECT Module_ModuleCode FROM Mark " +
                    "WHERE `Record_Record ID` = '" + record + "';";
                openResultQuery(query);
                while (getResult().next()) {
                    theResults.add(getResult().getString(1));
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return theResults;
    }

    /**
     *
     * @param student the student registation number
     * @param moduleCode the module code
     * @return the number of credits a given module is worth
     */
    public int getCredits(String student, String moduleCode) {
        initConnection();
        initStatement();
        int result = 0;
        String code = "";
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery("SELECT Degree_DegreeCode FROM  Student WHERE `Registration number` = '"
                    + student + "';");
                while (getResult().next()) {
                    code = getResult().getString(1);
                }
                openConnection();
                openStatement();
                String query = "SELECT credits FROM `Module degree (linking)` "
                    + "WHERE `Degree_DegreeCode` = '" + code + "' AND "
                    + "Module_ModuleCode = '" + moduleCode + "';";
                openResultQuery(query);
                while (getResult().next()) {
                    result = Integer.parseInt(getResult().getString(1));
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @return an ArrayList of all dissertation modules
     */
    public ArrayList<String> getDissertationModules() {
        initConnection();
        initStatement();
        ArrayList<String> modules = new ArrayList<String>();
        try {
            try {
                openConnection();
                openStatement();
                String query = "SELECT Module_ModuleCode FROM `Module degree (linking)` "
                    + "WHERE `credits` = '" + 60 + "';";
                openResultQuery(query);
                while (getResult().next()) {
                    modules.add(getResult().getString(1));
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return modules;
    }

    /**
     *
     * @param query the SQL query
     * @return an ArrayList of all periods of study
     */
    public ArrayList<String> getPeriodOfStudy(String query) {
        initConnection();
        initStatement();
        ArrayList<String> study = new ArrayList<String>();
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery(query);
                while (getResult().next()) {
                    study.add(getResult().getString(1));
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return study;
    }

    /**
     *
     * @param query the SQL query
     * @return the weighted mean
     */
    public int getWeightedMean(String query) {
        initConnection();
        initStatement();
        int result = 0;
        try {
            try {
                openConnection();
                openStatement();
                openResultQuery(query);
                while (getResult().next()) {
                    result = Integer.parseInt(getResult().getString(1));
                }
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param query the SQL query
     */
    public void updateQuery(String query) {
        initConnection();
        initStatement();
        try {
            try {
                openConnection();
                openStatement();
                int count = getStatement().executeUpdate(query);
            } finally {
                closeResultQuery();
                closeStatement();
                closeConnection();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param query the SQL query
     * @return the result of the query
     */
    public String stringQuery(String query) {
       initConnection();
       initStatement();
       String result = "";
       try {
           try {
               openConnection();
               openStatement();
               openResultQuery(query);
               while (getResult().next()) {
                   result = getResult().getString(1);
               }
           } finally {
               closeResultQuery();
               closeStatement();
               closeConnection();
           }
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
       return result;
   }

    /**
     *
     * @param query the SQL query
     * @return an ArrayList of all weighted mean grades
     */
    public ArrayList<Double> getAllMeanGrades(String query) {
       initConnection();
       initStatement();
       ArrayList<Double> result = new ArrayList<Double>();
       try {
           try {
               openConnection();
               openStatement();
               openResultQuery(query);
               while (getResult().next()) {
                   result.add(Double.parseDouble(getResult().getString(1)));
               }
           } finally {
               closeResultQuery();
               closeStatement();
               closeConnection();
           }
       } catch (SQLException ex) {
           ex.printStackTrace();
       }
       return result;
   }
}
