/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ringpoll.allDBInsert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import ringpoll.DBActivityDAO;

/**
 *
 * @author raj
 */
public class InsertQuestionInfo {

    public int id;

    public int init(int userId, String question) {

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

                String sql1 = "INSERT INTO tblQuestion"
                        + " ( USERID, QUESTION)"
                        + " VALUES"
                        + " ("
                        + "" + userId + ","
                        + "'" + question + "'"
                        + ")";

                stmt.execute(sql1, Statement.RETURN_GENERATED_KEYS);
                id = 0;
                ResultSet results = stmt.getGeneratedKeys();
                //ResultSet results = stmt.executeQuery("SELECT IDENTITY_VAL_LOCAL()");
                if (!results.isClosed()) {
                    while (results.next()) {
                        id = results.getInt(1);
                    }
                }
                System.err.println(id);

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
        return id;

    }

}
