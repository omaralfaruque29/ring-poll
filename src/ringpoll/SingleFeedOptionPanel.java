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
import java.awt.Image;
import static java.awt.Image.SCALE_SMOOTH;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
public class SingleFeedOptionPanel extends JPanel {

    private int questionId;
    private int optionId;
    private String optionText;
    public JProgressBar progressBar;

    public JLabel pollLabel;

    public SingleFeedOptionPanel(int questionId, int optionId, String optionText) {
        this.questionId = questionId;
        this.optionId = optionId;
        this.optionText = optionText;
        setLayout(new BorderLayout());
        setOpaque(false);
        initContents();
    }

    private void initContents() {

        JPanel CheckBoxPanel = new JPanel(new BorderLayout());
        CheckBoxPanel.setOpaque(false);
        JPanel answerMainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JCheckBox optionCheckBox = new JCheckBox();
        optionCheckBox.setOpaque(false);
        CheckBoxPanel.add(optionCheckBox, BorderLayout.CENTER);

        List<Integer> list = DBActivityDAO.getinstance().getUserListByOption(optionId, questionId);

        if (list.contains(Storers.userId)) {
            optionCheckBox.setSelected(true);
        } else {
            optionCheckBox.setSelected(false);
        }

        pollLabel = new JLabel();
        pollLabel.setPreferredSize(new Dimension(20, 30));
        pollLabel.setOpaque(false);
        pollLabel.addMouseListener(new MouseAdapter() {
            Font original;

            @Override
            public void mouseEntered(MouseEvent e) {
                original = pollLabel.getFont();
                Map attributes = original.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                pollLabel.setFont(original.deriveFont(attributes));
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                List<Integer> list = DBActivityDAO.getinstance().getUserListByOption(optionId, questionId);
                if (list.size() > 0) {
                    UserPopup userPopup = new UserPopup(list);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pollLabel.setFont(original);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

        });

        JPanel pollPanel = new JPanel();
        pollPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pollPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        pollPanel.setOpaque(false);
        pollPanel.add(pollLabel);

        String imgName = DBActivityDAO.getinstance().getOptionImageNamefromDB(questionId, optionId);
        File sourceimage = new File("C:\\Users\\Raj_raj\\Desktop\\ringPoll\\ringPoll\\tempImages\\" + imgName + ".jpg");
        Image image = null;
        try {
            image = ImageIO.read(sourceimage);
            if (image == null) {
                image = ImageIO.read(new File("E:\\ringPoll\\ringPoll\\tempImages\\DefaultImage.jpg"));
            }

        } catch (IOException ex) {

        }
        Image editedImage = image.getScaledInstance(60, 60, SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(editedImage);

        JLabel imageLabel = new JLabel(newImageIcon);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        //imagePanel.setPreferredSize(new Dimension(45, 40));
        //imagePanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));

        JLabel optionLabel = new JLabel(optionText);
        JPanel optionPanel = new JPanel(new BorderLayout());
        optionPanel.add(optionLabel, BorderLayout.CENTER);
        optionPanel.setPreferredSize(new Dimension(450, 40));
        optionPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        optionPanel.setOpaque(false);
        progressBar = getProgressBar();
        progressBar.setBorder(null);
        setProgressValue(optionId, progressBar, pollLabel);
        progressBar.add(optionPanel, BorderLayout.CENTER);

        optionCheckBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    new InsertPollInfo(questionId, optionId, Storers.userId);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    new DeletePollInfo(questionId, optionId, Storers.userId);
                }

                setProgressValue(optionId, progressBar, pollLabel);
                List<SingleFeedOptionPanel> feedOptionPanelList = new ArrayList<SingleFeedOptionPanel>();
                feedOptionPanelList = Storers.getInstance().AllFeedPanelMap.get(questionId);
                if (feedOptionPanelList != null && feedOptionPanelList.size() > 0) {
                    for (int i = 0; i < feedOptionPanelList.size(); i++) {
                        SingleFeedOptionPanel singleFeedOptionPanel = feedOptionPanelList.get(i);
                        singleFeedOptionPanel.setProgressValue(singleFeedOptionPanel.optionId, singleFeedOptionPanel.progressBar, singleFeedOptionPanel.pollLabel);
                    }
                }

            }
        });

        answerMainPanel.setBackground(Color.WHITE);
        answerMainPanel.add(CheckBoxPanel);
        answerMainPanel.add(imagePanel);
        answerMainPanel.add(progressBar);
        answerMainPanel.add(pollPanel);
        add(answerMainPanel, BorderLayout.CENTER);

    }

    private JProgressBar getProgressBar() {
        JProgressBar progressBar = new JProgressBar();
        progressBar.setLayout(new BorderLayout());
        progressBar.setPreferredSize(new Dimension(450, 30));
        progressBar.setBorder(null);
        //progressBar.setBorder(new MatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        progressBar.setOpaque(false);
        return progressBar;
    }

    public void setProgressValue(int optionId, JProgressBar progressBar, JLabel pollLabel) {
        int totalVote = DBActivityDAO.getinstance().getTotalVote(questionId);
        int totalVoteByOptionID = DBActivityDAO.getinstance().getTotalVoteByOption(optionId, questionId);
        pollLabel.setText(totalVoteByOptionID + "");
        progressBar.setValue(totalVote > 0 ? ((totalVoteByOptionID * 100) / totalVote) : 0);

    }

}
