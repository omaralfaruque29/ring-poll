/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ringpoll.allDBInsert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import ringpoll.DBActivityDAO;
import ringpoll.Storers;
import LoginSignup.StatusPanel;

/**
 *
 * @author raj
 */
public class InsertPollInfo {

    private int questionId;
    private int optionId;
    private int userId;

    public InsertPollInfo(
            int questionId,
            int optionId,
            int userId
    ) {
        this.questionId = questionId;
        this.optionId = optionId;
        this.userId = userId;
        setPoll();
    }

    public void setPoll() {

        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        String url = "jdbc:derby:" + DBActivityDAO.DB_NAME + ";create=true";

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url);
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            try {

                String sql1 = "INSERT INTO tblPoll"
                        + " ( QUESTIONID, OPTIONID, USERID)"
                        + " VALUES"
                        + " ("
                        + "" + questionId + ","
                        + "" + optionId + ","
                        + "" + userId + ""
                        + ")";

                stmt.execute(sql1);
                conn.commit();

            } catch (SQLException sQLException) {
                sQLException.printStackTrace();
            }

        } catch (Exception e) {
            try {
                e.printStackTrace();
                conn.rollback();
            } catch (Exception ex) {
            }
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
            }
        }

    }
}
