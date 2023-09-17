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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import static ringpoll.DBActivityDAO.conn;
import static ringpoll.DBActivityDAO.stmt;
import ringpoll.Storers;
import ringpoll.allDBInsert.InsertLogiinInfo;

/**
 *
 * @author raj
 */
public class SignupPanel extends JPanel {

    private JPanel mainPanel;

    public JTextField firstNameField;
    public JTextField lastNameField;
    public JTextField emailField;
    public JTextField userNameField;
    public JPasswordField passField;
    private JPanel wrapperFirstNamePanel;
    private JPanel wrapperLastNamePanel;
    private JPanel wrapperEmailPanel;
    private JPanel wrapperNamePanel;
    private JPanel wrapperPassPanel;
    private JButton btnBack;
    private JButton btnSignup;
    public JPanel btnPanel;
    private JPanel Imagepanel;
    BufferedImage image;

    public SignupPanel() {
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
            image = ImageIO.read(new File("src/image/ring.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        imageLabel.setIcon(new ImageIcon(image));
        Imagepanel.add(imageLabel);
        Imagepanel.setOpaque(false);

        wrapperFirstNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        wrapperFirstNamePanel.setOpaque(false);
        JLabel FirstNameLabel = new JLabel("First Name");
        firstNameField = new JTextField();
        firstNameField.setPreferredSize(new Dimension(165, 25));
        firstNameField.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        wrapperFirstNamePanel.add(FirstNameLabel);
        wrapperFirstNamePanel.add(firstNameField);

        wrapperLastNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        wrapperLastNamePanel.setOpaque(false);
        JLabel lasetNameLabel = new JLabel("Last Name");
        lastNameField = new JTextField();
        lastNameField.setPreferredSize(new Dimension(165, 25));
        lastNameField.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        wrapperLastNamePanel.add(lasetNameLabel);
        wrapperLastNamePanel.add(lastNameField);

        wrapperEmailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 27, 0));
        wrapperEmailPanel.setOpaque(false);
        JLabel emailLabel = new JLabel("Email");
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(165, 25));
        emailField.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        wrapperEmailPanel.add(emailLabel);
        wrapperEmailPanel.add(emailField);

        wrapperNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 11, 0));
        wrapperNamePanel.setOpaque(false);
        JLabel nameLabel = new JLabel("User Name");
        userNameField = new JTextField();
        userNameField.setPreferredSize(new Dimension(165, 25));
        userNameField.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        wrapperNamePanel.add(nameLabel);
        wrapperNamePanel.add(userNameField);

        wrapperPassPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 13, 0));
        wrapperPassPanel.setOpaque(false);
        JLabel passLabel = new JLabel("Password");
        passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(165, 25));
        passField.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        wrapperPassPanel.add(passLabel);
        wrapperPassPanel.add(passField);

        btnSignup = new JButton("Sign up");
        btnSignup.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSignup.addActionListener(actionListener);

        btnBack = new JButton("Back");
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(actionListener);
        btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 55, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(btnSignup);
        btnPanel.add(btnBack);

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));
        wrapperPanel.add(Imagepanel);
        wrapperPanel.add(wrapperFirstNamePanel);
        wrapperPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        wrapperPanel.add(wrapperLastNamePanel);
        wrapperPanel.add(Box.createRigidArea(new Dimension(5, 5)));
        wrapperPanel.add(wrapperEmailPanel);
        wrapperPanel.add(Box.createRigidArea(new Dimension(5, 5)));

        wrapperPanel.add(wrapperNamePanel);
        wrapperPanel.add(Box.createRigidArea(new Dimension(5, 5)));
        wrapperPanel.add(wrapperPassPanel);
        wrapperPanel.add(Box.createRigidArea(new Dimension(5, 30)));
        wrapperPanel.add(btnPanel);

        mainPanel.add(wrapperPanel, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);
    }
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnSignup) {
                LoginUI.getLoginUI().loadMainContent(LoginUI.MAIN_LOGIN_UI);
                //LoginUI.getLoginUI().loadMainContent(LoginUI.STATUS_UI);
                new InsertLogiinInfo(firstNameField.getText(), lastNameField.getText(), emailField.getText(), userNameField.getText(), passField.getText()).start();
                Storers.getInstance().userNameList.add(userNameField.getText());
                Storers.getInstance().passwordList.add(passField.getText());
                GetCurrentUserId(userNameField.getText());

            } else if (e.getSource() == btnBack) {
                LoginUI.getLoginUI().main_contentPane.removeAll();
                LoginUI.getLoginUI().loadMainContent(LoginUI.MAIN_LOGIN_UI);
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
