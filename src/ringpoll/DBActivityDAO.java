/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ringpoll;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author raj
 */
public class DBActivityDAO {

    public static String DB_NAME = "pollDB";
    public static Connection conn = null;
    public static DatabaseMetaData dbm;
    public static DBActivityDAO instance;
    public static Statement stmt = null;

    private int questionId;
    private String question;
    private int optionId;
    private String option;
    private int userId;
    public Map<Integer, String> singleQuestionMap;

    public static DBActivityDAO getinstance() {
        if (instance == null) {
            instance = new DBActivityDAO();
        }
        return instance;
    }

    public void createDatabaseConnection() {
        try {
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            String url = "jdbc:derby:" + DB_NAME + ";create=true";
            Class.forName(driver);
            if (conn == null) {
                try {
                    conn = DriverManager.getConnection(url);
                    dbm = conn.getMetaData();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void createSignUpTable() {

        try {
            if (conn != null) {
                //Statement stmt = statement;
                if (stmt == null) {
                    stmt = conn.createStatement();
                }
                try {
                    //ResultSet tableSet = dbm.getTables(null, null, null, null);
                    //ResultSet tables = dbm.getTables(null, null, (DBConstants.TABLE_RING_USER_SETTINGS).toUpperCase(), null);
                    String sqlCreate = "CREATE TABLE tblSignup"
                            + "("
                            + " ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + " FIRSTNAME VARCHAR(50) NOT NULL,"
                            + " LASTNAME VARCHAR(50) NOT NULL,"
                            + " EMAIL VARCHAR(50) NOT NULL,"
                            + " USERNAME VARCHAR(50) NOT NULL,"
                            + " PASSWORD VARCHAR(50) NOT NULL,"
                            + " PRIMARY KEY (ID),"
                            + " CONSTRAINT unique_user UNIQUE (USERNAME)"
                            + ")";
                    stmt.execute(sqlCreate);
                } catch (Exception e) {
                } finally {
                    if (stmt == null) {
                        try {
                            stmt.close();
                        } catch (SQLException ex) {
                        }
                    }
                }
            }
        } catch (SQLException ex) {
        }

    }

    public void fetchLoginInfoBeforeLogin() {

        try {
            if (conn != null) {
                stmt = conn.createStatement();
                try {
                    String query = "SELECT * FROM tblSignup";
                    ResultSet results = stmt.executeQuery(query);
                    if (!results.isClosed()) {
                        while (results.next()) {
                            Storers.userId = results.getInt(1);
                            Storers.getInstance().firstName = results.getString(2);
                            Storers.getInstance().lastName = results.getString(3);
                            Storers.getInstance().email = results.getString(4);
                            Storers.getInstance().userName = results.getString(5);
                            Storers.getInstance().password = results.getString(6);

                            Storers.getInstance().userNameList.add(Storers.getInstance().userName);
                            Storers.getInstance().passwordList.add(Storers.getInstance().password);
                        }

                    }

                } catch (Exception e) {

                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    public void createQuestionTable() {
        try {
            if (conn != null) {
                if (stmt == null) {
                    stmt = conn.createStatement();
                }
                try {
                    String sqlCreate = "CREATE TABLE tblQuestion"
                            + "("
                            + " QUESTIONID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + " USERID INTEGER NOT NULL,"
                            + " QUESTION VARCHAR(300) NOT NULL,"
                            + " PRIMARY KEY (QUESTIONID) "
                            //+ "CONSTRAINT tblQuestion_FK FOREIGN KEY (USERID) REFERENCES tblSignup(ID)"
                            + ")";
                    stmt.execute(sqlCreate);
                } catch (Exception e) {
                } finally {
                    if (stmt == null) {
                        try {
                            stmt.close();
                        } catch (SQLException ex) {
                        }
                    }
                }
            }
        } catch (SQLException ex) {
        }

    }

    public void createOptionTable() {
        try {
            if (conn != null) {
                if (stmt == null) {
                    stmt = conn.createStatement();
                }
                try {
                    String sqlCreate = "CREATE TABLE tblOption"
                            + "("
                            + " OPTIONID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + " QUESTIONID INTEGER NOT NULL,"
                            + " ALLOPTION VARCHAR(300) NOT NULL,"
                            + " IMAGEURL VARCHAR(200), "
                            + " IMAGENAME VARCHAR(150), "
                            + " PRIMARY KEY (OPTIONID),"
                            + "CONSTRAINT tblOption_FK FOREIGN KEY (QUESTIONID) REFERENCES tblQuestion(QUESTIONID)"
                            + ")";
                    stmt.execute(sqlCreate);
                } catch (Exception e) {
                } finally {
                    if (stmt == null) {
                        try {
                            stmt.close();
                        } catch (SQLException ex) {
                        }
                    }
                }
            }
        } catch (SQLException ex) {
        }

    }

    public void createPollTable() {

        try {
            if (conn != null) {
                if (stmt == null) {
                    stmt = conn.createStatement();
                }
                try {
                    String sqlCreate = "CREATE TABLE tblPoll"
                            + "("
                            + " POLLID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                            + " QUESTIONID INTEGER NOT NULL,"
                            + " OPTIONID INTEGER NOT NULL,"
                            + " USERID INTEGER NOT NULL"
                            //                            + " PRIMARY KEY (POLLID),"
                            //                            + "CONSTRAINT tblPoll_FK FOREIGN KEY (QUESTIONID) REFERENCES tblQuestion(QUESTIONID),"
                            //                            + "CONSTRAINT tblPoll_FK2 FOREIGN KEY (OPTIONID) REFERENCES tblOption(OPTIONID),"
                            //                            + "CONSTRAINT tblPoll_FK3 FOREIGN KEY (USERID) REFERENCES tblSignup(ID),"
                            //                            + "CONSTRAINT unique_tblPoll UNIQUE(QUESTIONID,OPTIONID, USERID )"
                            + ")";
                    stmt.execute(sqlCreate);
                } catch (Exception e) {
                } finally {
                    if (stmt == null) {
                        try {
                            stmt.close();
                        } catch (SQLException ex) {
                        }
                    }
                }
            }
        } catch (SQLException ex) {
        }

    }

    public void fetchQuestionTable() {

        try {
            if (conn != null) {
                stmt = conn.createStatement();
                try {
                    String query = "SELECT * FROM tblQuestion";
                    ResultSet results = stmt.executeQuery(query);
                    if (!results.isClosed()) {
                        while (results.next()) {
                            questionId = results.getInt(1);
                            question = results.getString(3);
                            Storers.getInstance().questionMap.put(questionId, question);

                        }
                    }

                } catch (Exception e) {

                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    public void fetchOptionTable() {
        Storers.getInstance().optionMap.clear();
        try {
            if (conn != null) {
                stmt = conn.createStatement();
                try {
                    String query = "SELECT * FROM tblOption ORDER BY QUESTIONID, OPTIONID";
                    ResultSet results = stmt.executeQuery(query);
                    if (!results.isClosed()) {
                        while (results.next()) {
                            optionId = results.getInt(1);
                            questionId = results.getInt(2);
                            option = results.getString(3);

                            Map<Integer, String> questionMap = Storers.getInstance().optionMap.get(questionId);
                            if (questionMap == null) {
                                questionMap = new HashMap<Integer, String>();
                                Storers.getInstance().optionMap.put(questionId, questionMap);
                            }
                            questionMap.put(optionId, option);
                        }
                    }

                } catch (Exception e) {

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    public void fetchPollTable() {

        try {
            if (conn != null) {
                stmt = conn.createStatement();
                try {
                    String query = "SELECT * FROM tblPoll";
                    ResultSet results = stmt.executeQuery(query);
                    if (!results.isClosed()) {
                        while (results.next()) {
                            questionId = results.getInt(2);
                            optionId = results.getInt(3);
                            userId = results.getInt(4);

                        }
                    }

                } catch (Exception e) {

                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    public int getTotalVote(int questionId) {
        int total = 0;
        try {
            if (conn != null) {
                stmt = conn.createStatement();
                try {
                    String query = "SELECT COUNT(*) AS TOTAL FROM tblPoll WHERE QUESTIONID = " + questionId + "";
                    ResultSet results = stmt.executeQuery(query);

                    if (!results.isClosed()) {
                        if (results.next()) {
                            total = results.getInt("TOTAL");
                        }
                    }
                } catch (Exception e) {
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return total;
    }

    public int getTotalVoteByOption(int optionId, int questionId) {
        int totalByOption = 0;
        try {
            if (conn != null) {
                stmt = conn.createStatement();
                try {
                    String query = "SELECT COUNT(*) AS TOTAL FROM tblPoll WHERE QUESTIONID = " + questionId + " AND OPTIONID = " + optionId + "";
                    ResultSet results = stmt.executeQuery(query);

                    if (!results.isClosed()) {
                        if (results.next()) {
                            totalByOption = results.getInt("TOTAL");
                        }
                    }
                } catch (Exception e) {
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return totalByOption;
    }

    public List<Integer> getUserListByOption(int optionId, int questionId) {
        List<Integer> list = new ArrayList<Integer>();
        try {
            if (conn != null) {
                stmt = conn.createStatement();
                try {
                    String query = "SELECT USERID FROM tblPoll WHERE QUESTIONID = " + questionId + " AND OPTIONID = " + optionId + "";
                    ResultSet results = stmt.executeQuery(query);

                    if (!results.isClosed()) {
                        while (results.next()) {
                            list.add(results.getInt("USERID"));
                        }
                    }
                } catch (Exception e) {
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public String getQuestionfromDB(int questionId) {
        String ques = "";
        try {
            if (conn != null) {
                stmt = conn.createStatement();
                try {
                    String query = "SELECT QUESTION FROM tblQuestion WHERE QUESTIONID = " + questionId + "";
                    ResultSet results = stmt.executeQuery(query);
                    if (!results.isClosed()) {
                        if (results.next()) {
                            ques = results.getString("QUESTION");
                        }
                    }
                } catch (Exception e) {
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ques;
    }

    public String getOptionImageNamefromDB(int questionId, int optionId) {
        String imageNAme = "";
        try {
            if (conn != null) {
                stmt = conn.createStatement();
                try {
                    String query = "SELECT IMAGENAME FROM tblOption WHERE QUESTIONID = " + questionId + " AND OPTIONID = " + optionId + "";
                    ResultSet results = stmt.executeQuery(query);
                    if (!results.isClosed()) {
                        if (results.next()) {
                            imageNAme = results.getString("IMAGENAME");
                        }
                    }
                } catch (Exception e) {
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return imageNAme;
    }

}
