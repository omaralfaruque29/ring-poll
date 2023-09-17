/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ringpoll.allDBInsert;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import ringpoll.DBActivityDAO;

/**
 *
 * @author raj
 */
public class DeletePollInfo {

    private int questionId;
    private int optionId;
    private int userId;

    public DeletePollInfo(int questionId, int optionId, int userId) {
        this.questionId = questionId;
        this.optionId = optionId;
        this.userId = userId;
        init();
    }

    private void init() {
        try {
            if (DBActivityDAO.conn != null) {
                Statement stmt = DBActivityDAO.conn.createStatement();
                try {
                    String query = "DELETE FROM tblPoll WHERE QUESTIONID = " + questionId + " AND OPTIONID = " + optionId + " AND USERID = " + userId + "";
                    stmt.executeUpdate(query);

                } catch (Exception e) {

                } finally {
                    try {
                        stmt.close();
                    } catch (SQLException ex) {
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
