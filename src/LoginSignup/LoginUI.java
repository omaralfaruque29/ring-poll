/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginSignup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author raj
 */
public class LoginUI extends UnitFrame {

    public static LoginUI loginUI;
    public MainLoginPanel mainLoginPanel;
    public SignupPanel signupPanel;
    public StatusPanel statusPanel;

    public final static int MAIN_LOGIN_UI = 1;
    public final static int SIGN_UP_UI = 2;
    public final static int STATUS_UI = 3;

    public static LoginUI getLoginUI() {
        if (loginUI == null) {
            loginUI = new LoginUI();
        }
        return loginUI;
    }

    public LoginUI() {
        setVisible(true);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void loadMainContent(int type) {
        main_contentPane.removeAll();
        if (type == MAIN_LOGIN_UI) {
            mainLoginPanel = new MainLoginPanel();
            main_contentPane.add(mainLoginPanel);
        } else if (type == SIGN_UP_UI) {
            signupPanel = new SignupPanel();
            main_contentPane.add(signupPanel);
        } else if (type == STATUS_UI) {
            statusPanel = new StatusPanel();
            main_contentPane.add(statusPanel);
        }
        main_contentPane.revalidate();
        main_contentPane.repaint();

    }

}
