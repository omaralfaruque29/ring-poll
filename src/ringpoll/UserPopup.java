/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ringpoll;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import static ringpoll.DBActivityDAO.conn;
import static ringpoll.DBActivityDAO.stmt;

/**
 *
 * @author raj
 */
public class UserPopup extends JDialog {

    private JScrollPane scrollPane;
    private List<Integer> userList = new ArrayList<Integer>();
    private JPanel votePanel;
    private String firstName = "";
    private String LastName = "";

    public UserPopup(List<Integer> userList) {
        this.userList = userList;
        setVisible(true);
        setSize(200, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {

        votePanel = new JPanel();
        votePanel.setBackground(Color.WHITE);
        votePanel.setLayout(new BoxLayout(votePanel, BoxLayout.Y_AXIS));

        Iterator iterator = userList.iterator();
        while (iterator.hasNext()) {
            String name = getUserName((int) iterator.next());
            JPanel single = singleUserPanel(name);
            votePanel.add(single);
            votePanel.add(Box.createRigidArea(new Dimension(0, 2)));

        }

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(votePanel, BorderLayout.NORTH);
        scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);

        this.add(scrollPane);

    }

    public JPanel singleUserPanel(String name) {
        JPanel singlePanel = new JPanel();
        singlePanel.setBackground(Color.WHITE);
        singlePanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        JLabel voterLabel = new JLabel();
        voterLabel.setText("" + name);
        singlePanel.add(voterLabel);
        
        return singlePanel;

    }

    public String getUserName(int userId) {
        try {
            if (conn != null) {
                stmt = conn.createStatement();
                try {
                    String query = "SELECT FIRSTNAME,LASTNAME FROM tblSignup WHERE ID = " + userId + "";
                    ResultSet results = stmt.executeQuery(query);
                    if (!results.isClosed()) {
                        while (results.next()) {
                            firstName = results.getString("FIRSTNAME");
                            LastName = results.getString("LASTNAME");
                        }
                    }
                } catch (Exception e) {
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return firstName + " " + LastName;

    }

}
