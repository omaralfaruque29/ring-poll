/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ringpoll;

import ImageSearch.SearchPopup;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

/**
 *
 * @author raj
 */
public class OptionPanel extends JPanel {

    public JTextArea optionText;
    public JPanel imgPanel;
    public Image image;
    public String imgUrl = "test";
    public String imgName;
    private String defaultPollText = " +Add Poll Option";

    public OptionPanel() {
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        initComponents();
    }

    private void initComponents() {
        imgPanel = new JPanel(new BorderLayout());
        imgPanel.setBackground(Color.WHITE);
        imgPanel.setPreferredSize(new Dimension(40, 35));
        imgPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        mouseLiseraction(imgPanel);

        optionText = new JTextArea(1, 30);
        optionText.setBackground(Color.WHITE);
        Color disable_font_color = new Color(0xC4C4C4);
        optionText.setForeground(disable_font_color);
        optionText.setText(defaultPollText);
        optionText.setBorder(new EmptyBorder(2, 10, 0, 0));
        optionText.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        Font font = new Font("Times New Roman", Font.ROMAN_BASELINE, 14);
        optionText.setFont(font);
        //optionText.setVisible(true);
        optionText.setLineWrap(true);
        optionText.setEditable(true);
        mouseLiseraction(optionText);

        add(imgPanel);
        add(optionText);
    }

    private void mouseLiseraction(JComponent component) {
        component.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getSource() == imgPanel) {
                    imgPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getSource() == imgPanel) {
                    imgPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == optionText) {

                    optionText.setText("");
                    optionText.setForeground(Color.BLACK);
                    optionText.setBorder(new EmptyBorder(0, 5, 0, 0));
                } else if (e.getSource() == imgPanel) {

                    SearchPopup searchPopup = new SearchPopup(imgPanel, imgUrl, imgName);

                }

            }
        });

    }

}
