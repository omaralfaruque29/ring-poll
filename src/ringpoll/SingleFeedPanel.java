/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ringpoll;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.MatteBorder;
import ringpoll.allDBInsert.DeletePollInfo;
import ringpoll.allDBInsert.InsertPollInfo;

/**
 *
 * @author raj
 */
public class SingleFeedPanel extends JPanel {

    private int questionId;
    private String question;
    private JCheckBox optionCheckBox;
    private int max = 1;
    public Color DEFAULT_COLOR = new Color(0xFAAC58);

    private Map<Integer, String> singleQuestionMap;

    private List<Integer> optionIdList = new ArrayList<Integer>();
    private List<String> optionList = new ArrayList<String>();
    public List<SingleFeedOptionPanel> panelList = new ArrayList<SingleFeedOptionPanel>();

    public SingleFeedPanel(
            int questionId,
            Map<Integer, String> singleQuestionMap) {
        this.questionId = questionId;
        this.singleQuestionMap = singleQuestionMap;
        question = DBActivityDAO.getinstance().getQuestionfromDB(questionId);
        for (Map.Entry<Integer, String> entry : singleQuestionMap.entrySet()) {
            optionIdList.add(entry.getKey());
            optionList.add("" + entry.getValue());
        }

        setLayout(new BorderLayout());
        setOpaque(false);
        initComponents();

    }

    private void initComponents() {

        JLabel questionLabel = new JLabel(question);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 0));

        JPanel questionPanel = new JPanel();
        questionPanel.setBackground(DEFAULT_COLOR);
        questionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 1));
        questionPanel.add(questionLabel);

        JPanel feedwrapperPanel = new JPanel();
        feedwrapperPanel.setLayout(new BoxLayout(feedwrapperPanel, BoxLayout.Y_AXIS));
        feedwrapperPanel.setOpaque(true);
        feedwrapperPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 135));
        feedwrapperPanel.add(questionPanel);

        Collections.sort(optionIdList);
        for (int i = 0; i < optionIdList.size(); i++) {
            String opt = optionList.get(i);
            int optId = optionIdList.get(i);
            SingleFeedOptionPanel singleOptionPanel = new SingleFeedOptionPanel(questionId, optId, opt);

            feedwrapperPanel.add(singleOptionPanel);
            panelList.add(singleOptionPanel);
        }

        Storers.getInstance().AllFeedPanelMap.put(questionId, (ArrayList<SingleFeedOptionPanel>) panelList);

        optionIdList.clear();
        optionList.clear();

        add(feedwrapperPanel, BorderLayout.CENTER);

    }

}
