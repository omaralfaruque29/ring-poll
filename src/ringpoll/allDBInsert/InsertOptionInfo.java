/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ringpoll.allDBInsert;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import ringpoll.DBActivityDAO;
import ringpoll.Storers;

/**
 *
 * @author raj
 */
public class InsertOptionInfo extends Thread {

    private int questionId;
    private String ans1;
    private String ans2;
    private String ans3;
    private String ans4;
    private String ans5;
    private String ans6;
    private ArrayList optionList = new ArrayList();
    private int indx = 0;
    private String defaultPollText = " +Add Poll Option";

    public InsertOptionInfo(
            int questionId,
            String ans1,
            String ans2,
            String ans3,
            String ans4,
            String ans5,
            String ans6) {
        this.questionId = questionId;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ans4 = ans4;
        this.ans5 = ans5;
        this.ans6 = ans6;

    }

    @Override
    public void run() {

        optionList.add(ans1);
        optionList.add(ans2);
        optionList.add(ans3);
        optionList.add(ans4);
        optionList.add(ans5);
        optionList.add(ans6);

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
                for (int i = 0; i < optionList.size(); i++) {
                    String option = (String) optionList.get(i);
                    if (option != null && !option.equalsIgnoreCase(defaultPollText)) {
                        String sql1 = "INSERT INTO tblOption"
                                + " ( QUESTIONID, ALLOPTION, IMAGEURL, IMAGENAME)"
                                + " VALUES"
                                + " ("
                                + "" + questionId + ","
                                + "'" + option + "',"
                                + "'" + (String) Storers.getInstance().tempImgUrlList.get(indx) + "',"
                                + "'" + (String) Storers.getInstance().tempImgNameList.get(indx) + "'"
                                + ")";
                        stmt.execute(sql1);
                        indx++;

                    }

                }
//                Iterator itr = optionList.iterator();
//                while (itr.hasNext()) {
//                    if (itr.next() != null && !itr.next().equals(defaultPollText)) {
//                        String sql1 = "INSERT INTO tblOption"
//                                + " ( QUESTIONID, ALLOPTION, IMAGEURL, IMAGENAME)"
//                                + " VALUES"
//                                + " ("
//                                + "" + questionId + ","
//                                + "'" + itr.next() + "',"
//                                + "'" + (String) Storers.getInstance().tempImgUrlList.get(indx) + "',"
//                                + "'" + (String) Storers.getInstance().tempImgNameList.get(indx) + "'"
//                                + ")";
//                        stmt.execute(sql1);
//                        indx++;
//                    }
//
//                }
                Storers.getInstance().tempImgUrlList.clear();
                Storers.getInstance().tempImgNameList.clear();
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
