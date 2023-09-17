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

/**
 *
 * @author raj
 */
public class InsertLogiinInfo extends Thread {

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private int ID = 1;

    public InsertLogiinInfo(String firstName,
            String lastName,
            String email,
            String userName,
            String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void run() {

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

                String sql1 = "INSERT INTO tblSignup"
                        + " ( FIRSTNAME, LASTNAME, EMAIL, USERNAME, PASSWORD)"
                        + " VALUES"
                        + " ("
                        + "'" + firstName + "',"
                        + "'" + lastName + "',"
                        + "'" + email + "',"
                        + "'" + userName + "',"
                        + "'" + password + "'"
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
