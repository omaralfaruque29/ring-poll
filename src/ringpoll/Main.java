/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ringpoll;

import LoginSignup.LoginUI;
import java.sql.SQLException;
import java.sql.Statement;
import ringpoll.DBActivityDAO;

/**
 *
 * @author raj
 */
public class Main {

    public static void main(String[] args) {
        LoginUI.getLoginUI().loadMainContent(LoginUI.MAIN_LOGIN_UI);
        if (DBActivityDAO.conn == null) {
            DBActivityDAO.getinstance().createDatabaseConnection();
            DBActivityDAO.getinstance().createSignUpTable();
            DBActivityDAO.getinstance().createQuestionTable();
            DBActivityDAO.getinstance().createOptionTable();
            DBActivityDAO.getinstance().createPollTable();

        }
        DBActivityDAO.getinstance().fetchLoginInfoBeforeLogin();

    }
}
