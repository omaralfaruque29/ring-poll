/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginSignup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author raj
 */
public class UnitFrame extends JFrame {

    public JPanel main_contentPane;

       {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 720);
        this.setLocationRelativeTo(null);
        this.setTitle("RingPoll");
        main_contentPane = (JPanel)getContentPane();
        main_contentPane.setBackground(Color.WHITE);
      }

}
