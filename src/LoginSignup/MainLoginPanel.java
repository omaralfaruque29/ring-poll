/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginSignup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import ringpoll.DBActivityDAO;
import static ringpoll.DBActivityDAO.conn;
import static ringpoll.DBActivityDAO.stmt;
import ringpoll.SingleFeedPanel;
import ringpoll.Storers;

/**
 *
 * @author raj
 */
public class MainLoginPanel extends JPanel {

    private JPanel mainPanel;
    public JTextField nameField;
    public JPasswordField passField;
    private JPanel Imagepanel;
    private JPanel wrapperNamePanel;
    private JPanel wrapperPassPanel;
    private JLabel warningLabel;
    private JButton btnSignin;
    private JButton btnCreate;
    public BufferedImage image;
    public String username;
    public String password;
    private SingleFeedPanel singleFeedPanel;

    public MainLoginPanel() {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        initContents();

    }

    private void initContents() {

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(300, 400, 250, 100));

        Imagepanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 90, 15));
        JLabel imageLabel = new JLabel();
        try {
            image = ImageIO.read(new File("src//image//ring.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imageLabel.setIcon(new ImageIcon(image));
        Imagepanel.add(imageLabel);
        Imagepanel.setOpaque(false);

        warningLabel = new JLabel("Invalid user name or password");
        warningLabel.setForeground(Color.red);
        warningLabel.setVisible(false);
        JPanel warningPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 72, 0));
        warningPanel.setOpaque(false);
        warningPanel.add(warningLabel);

        wrapperNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        wrapperNamePanel.setOpaque(false);
        JLabel nameLabel = new JLabel("User Name");
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(165, 25));
        nameField.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        wrapperNamePanel.add(nameLabel);
        wrapperNamePanel.add(nameField);

        wrapperPassPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        wrapperPassPanel.setOpaque(false);
        JLabel passLabel = new JLabel("Password");
        passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(165, 25));
        passField.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        wrapperPassPanel.add(passLabel);
        wrapperPassPanel.add(passField);

        btnSignin = new JButton("Sign in");
        btnSignin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSignin.addActionListener(actionListener);
        btnCreate = new JButton("Create New");
        btnCreate.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCreate.addActionListener(actionListener);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 38, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(btnSignin);
        btnPanel.add(btnCreate);

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.add(Imagepanel);
        wrapperPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        wrapperPanel.add(warningPanel);
        wrapperPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        wrapperPanel.add(wrapperNamePanel);
        wrapperPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        wrapperPanel.add(wrapperPassPanel);
        wrapperPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        wrapperPanel.add(btnPanel);

        mainPanel.add(wrapperPanel, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnSignin) {
                DBActivityDAO.getinstance().fetchLoginInfoBeforeLogin();
                username = nameField.getText();
                password = passField.getText();
                if (username != null && username.trim().length() > 0
                        && password != null && password.trim().length() > 0
                        && Storers.getInstance().userNameList.contains(username)
                        && Storers.getInstance().passwordList.contains(password)) {
                    GetCurrentUserId(username);
                    LoginUI.getLoginUI().loadMainContent(LoginUI.STATUS_UI);

                } else {
                    warningLabel.setVisible(true);
                }
            } else if (e.getSource() == btnCreate) {
                LoginUI.getLoginUI().main_contentPane.removeAll();
                LoginUI.getLoginUI().loadMainContent(LoginUI.SIGN_UP_UI);
            }

        }

    };

    public int GetCurrentUserId(String username) {

        try {
            Storers.userId = 0;
            if (conn != null) {
                stmt = conn.createStatement();
                try {
                    String query = "SELECT ID FROM tblSignup WHERE USERNAME = '" + username + "'";
                    ResultSet results = stmt.executeQuery(query);
                    if (!results.isClosed()) {
                        while (results.next()) {
                            Storers.userId = results.getInt("ID");
                        }
                    }
                } catch (Exception e) {
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Storers.userId;
    }

}
