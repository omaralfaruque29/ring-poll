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
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.RowFilter.Entry;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import ringpoll.DBActivityDAO;
import ringpoll.OptionPanel;
import ringpoll.SingleFeedPanel;
import ringpoll.Storers;
import ringpoll.allDBInsert.InsertOptionInfo;
import ringpoll.allDBInsert.InsertQuestionInfo;

/**
 *
 * @author raj
 */
public class StatusPanel extends JPanel {

    private JPanel mainPanel;
    private JPanel btnpanel;
    private JPanel askPanel;
    private JButton btnRingPoll;
    private JButton btnPost;
    private JButton allQuesButton;
    private JButton recentQuesButton;
    private JPanel historyWrapperPanel;
    private JPanel historyPanel;
    public Color disable_font_color = new Color(0xC4C4C4);

    public static StatusPanel statusPanel;
    private SingleFeedPanel singleFeedPanel;
    private JPanel questionPanel;
    private JPanel allOPtionPanel;

    private OptionPanel optionPanel1;
    private OptionPanel optionPanel2;
    private OptionPanel optionPanel3;
    private OptionPanel optionPanel4;
    private OptionPanel optionPanel5;
    private OptionPanel optionPanel6;

    public JPanel rightContentPanel;
    private JButton signOutButton;
    private JPanel feedPanel;
    public JPanel wrapperFeedPanel;
    public String temp;

    public String quesText = "Ask Question";
    //public String defaultPollText = " + Add Poll Options";
    private JTextArea quesTextArea;
    private JScrollPane scrollPane;

    public static StatusPanel getInstance() {
        if (statusPanel == null) {
            statusPanel = new StatusPanel();

        }
        return statusPanel;
    }

    public StatusPanel() {
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        initContents();
    }

    private void initContents() {

        historyPanel = new JPanel();
        historyWrapperPanel = new JPanel();
        historyPanel.setLayout(new BorderLayout());
        historyPanel.setBackground(Color.WHITE);
        historyPanel.setPreferredSize(new Dimension(200, 0));

        allQuesButton = new JButton("All Questions");
        allQuesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        allQuesButton.addActionListener(actionListener);
        recentQuesButton = new JButton("Recent Questions");
        recentQuesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        recentQuesButton.addActionListener(actionListener);
        signOutButton = new JButton("Sign Out");
        signOutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signOutButton.addActionListener(actionListener);

        historyWrapperPanel.setBackground(Color.WHITE);
        historyWrapperPanel.setLayout(new BoxLayout(historyWrapperPanel, BoxLayout.Y_AXIS));
        historyWrapperPanel.add(Box.createRigidArea(new Dimension(25, 80)));
        historyWrapperPanel.add(allQuesButton);
        historyWrapperPanel.add(Box.createRigidArea(new Dimension(25, 10)));
        historyWrapperPanel.add(recentQuesButton);
        historyWrapperPanel.add(Box.createRigidArea(new Dimension(25, 450)));

        historyWrapperPanel.add(signOutButton);
        historyPanel.add(historyWrapperPanel, BorderLayout.NORTH);

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BorderLayout());

        quesTextArea = initialize_quesArea();

        btnRingPoll = new JButton("Add Poll Options");
        btnRingPoll.setBorderPainted(false);
        btnRingPoll.setFocusPainted(false);
        btnRingPoll.setContentAreaFilled(false);
        btnRingPoll.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRingPoll.setVisible(false);
        mouseLiseraction(btnRingPoll);

        btnPost = new JButton("Post");
        btnPost.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPost.addActionListener(actionListener);

        askPanel = new JPanel(new BorderLayout());
        askPanel.setOpaque(false);
        askPanel.add(quesTextArea, BorderLayout.CENTER);
        mouseLiseraction(quesTextArea);

        btnpanel = new JPanel(new BorderLayout());
        btnpanel.setBackground(Color.WHITE);
        btnpanel.setVisible(true);

        btnpanel.add(btnRingPoll, BorderLayout.WEST);
        btnpanel.add(btnPost, BorderLayout.EAST);

        questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(new EmptyBorder(0, 10, 0, 35));
        questionPanel.setOpaque(false);

        questionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        questionPanel.add(askPanel);
        questionPanel.add(Box.createRigidArea(new Dimension(0, 1)));
        questionPanel.add(btnpanel);

        wrapperFeedPanel = new JPanel();
        wrapperFeedPanel.setBorder(new EmptyBorder(0, 80, 0, 0));
        wrapperFeedPanel.setOpaque(false);
        wrapperFeedPanel.setLayout(new BoxLayout(wrapperFeedPanel, BoxLayout.Y_AXIS));

        JPanel wrappper = new JPanel(new BorderLayout());
        wrappper.setOpaque(false);

        rightContentPanel = new JPanel(new BorderLayout());
        rightContentPanel.setOpaque(false);
        rightContentPanel.add(questionPanel, BorderLayout.NORTH);
        rightContentPanel.add(wrapperFeedPanel, BorderLayout.CENTER);

        wrappper.add(rightContentPanel, BorderLayout.NORTH);
        scrollPane = new JScrollPane(wrappper);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(15);
        wrapperPanel.add(historyPanel, BorderLayout.WEST);
        wrapperPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(wrapperPanel, BorderLayout.CENTER);
        this.add(mainPanel, BorderLayout.CENTER);

        loadAllQuestions();
    }

    public void mouseLiseraction(JComponent jc) {

        jc.addMouseListener(new MouseAdapter() {
            Font original;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == quesTextArea) {
                    quesTextArea.setForeground(Color.BLACK);
                    btnRingPoll.setVisible(true);
                    if (quesTextArea.getText().equals(quesText)) {
                        quesTextArea.setText("");
                    }
                }

                if (e.getSource() == btnRingPoll) {

                    btnRingPoll.setVisible(false);
                    
                    optionPanel1 = new OptionPanel();
                    optionPanel2 = new OptionPanel();
                    optionPanel3 = new OptionPanel();
                    optionPanel4 = new OptionPanel();
                    optionPanel5 = new OptionPanel();
                    optionPanel6 = new OptionPanel();


                    allOPtionPanel = new JPanel();
                    allOPtionPanel.setLayout(new BoxLayout(allOPtionPanel, BoxLayout.Y_AXIS));
                    allOPtionPanel.add(optionPanel1);
                    allOPtionPanel.add(optionPanel2);
                    allOPtionPanel.add(optionPanel3);
                    allOPtionPanel.add(optionPanel4);
                    allOPtionPanel.add(optionPanel5);
                    allOPtionPanel.add(optionPanel6);

                    questionPanel.add(allOPtionPanel);
                    questionPanel.add(btnpanel);
                    questionPanel.revalidate();
                    questionPanel.repaint();

                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (e.getSource() == btnRingPoll) {
                    original = e.getComponent().getFont();
                    Map attributes = original.getAttributes();
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                    e.getComponent().setFont(original.deriveFont(attributes));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (e.getSource() == btnRingPoll) {
                    e.getComponent().setFont(original);
                }
            }

        });
    }

    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btnPost) {
                try {

                    questionPanel.remove(allOPtionPanel);
                    btnRingPoll.setVisible(false);

                    InsertQuestionInfo insertQuestionInfo = new InsertQuestionInfo();
                    int questionId = insertQuestionInfo.init(Storers.userId, quesTextArea.getText());

                    new InsertOptionInfo(questionId, optionPanel1.optionText.getText(), optionPanel2.optionText.getText(), optionPanel3.optionText.getText(), optionPanel4.optionText.getText(), optionPanel5.optionText.getText(), optionPanel6.optionText.getText()).start();
                    Thread.sleep(100);
                    loadAllQuestions();

                    quesTextArea.setText(quesText);
                    quesTextArea.setForeground(disable_font_color);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else if (e.getSource() == allQuesButton) {

            } else if (e.getSource() == recentQuesButton) {

            } else if (e.getSource() == signOutButton) {
                LoginUI.getLoginUI().loadMainContent(LoginUI.MAIN_LOGIN_UI);
            }
        }
    };

    public JTextArea initialize_quesArea() {
        JTextArea quesArea = new JTextArea();
        quesArea.setRows(2);
        Color disable_font_color = new Color(0xC4C4C4);
        quesArea.setForeground(disable_font_color);
        quesArea.setText("Ask Question");
        quesArea.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 8));
        Font font = new Font("Times New Roman", Font.ROMAN_BASELINE, 14);
        quesArea.setFont(font);
        quesArea.setLineWrap(true);
        quesArea.setEditable(true);
        return quesArea;
    }

    public void loadAllQuestions() {
        try {

            DBActivityDAO.getinstance().fetchOptionTable();
            Thread.sleep(300);
            wrapperFeedPanel.removeAll();
            for (Map.Entry<Integer, Map<Integer, String>> entry : Storers.getInstance().optionMap.entrySet()) {
                int questionId = entry.getKey();
                Map<Integer, String> singleQuestionMap = entry.getValue();
                singleFeedPanel = new SingleFeedPanel(questionId, singleQuestionMap);
                wrapperFeedPanel.add(singleFeedPanel, 0);
            }

        } catch (Exception ex1) {

        }

    }

}
