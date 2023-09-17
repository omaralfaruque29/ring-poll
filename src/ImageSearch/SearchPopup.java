/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageSearch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import static java.awt.Image.SCALE_SMOOTH;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

/**
 *
 * @author raj
 */
public class SearchPopup extends JDialog {

    private List<String> list = new ArrayList<>();
    private Map<String, String> imgInfoMap = new HashMap<>();
    private JPanel mainPanel;
    private JPanel imgContainerPanel;
    public Color DEFAULT_COLOR = new Color(0xFAAC58);
    public JLabel loading;
    public boolean isLoading;
    private int imgNo = 40;
    public JPanel imgPanel;
    public String imgUrl;
    public String imgName;

    public SearchPopup(JPanel imgPanel, String imgUrl, String imgName ) {
        this.imgPanel = imgPanel;
        this.imgUrl = imgUrl;
        this.imgName = imgName;

        setVisible(true);
        setSize(500, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                setVisible(false);
            }
        });
        initComponents();
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        JTextArea searchArea = new JTextArea(2, 30);
        searchArea.setBorder(new EmptyBorder(5, 10, 0, 0));
        Font font = new Font("Times New Roman", Font.ROMAN_BASELINE, 17);
        searchArea.setFont(font);
        searchArea.setLineWrap(true);
        searchArea.setEditable(true);

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                imgContainerPanel.removeAll();
                if (searchArea.getText() != null) {
                    String text = searchArea.getText();
                    String text1 = text.replaceAll("\\s+", "");
                    WebSearch webSearch = new WebSearch(text1, 0);
                    imgInfoMap = webSearch.getURL();
                }

                for (Map.Entry<String, String> entrySet : imgInfoMap.entrySet()) {
                    String rndmImageUrl = entrySet.getKey();
                    String rndmImgName = entrySet.getValue();
                    JLabel imgLabel = return_image_with_label(rndmImageUrl, rndmImgName);
                    imgContainerPanel.add(imgLabel);
                    imgContainerPanel.revalidate();
                    imgContainerPanel.repaint();
                }
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setOpaque(false);
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);
        btnPanel.setPreferredSize(new Dimension(84, 45));
        btnPanel.add(btnSearch);
        topPanel.add(searchArea);
        topPanel.add(btnPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        imgContainerPanel = new JPanel(new WrapLayout(WrapLayout.LEFT, 2, 2));
        imgContainerPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(imgContainerPanel) {
            @Override
            public void doLayout() {
                super.doLayout();
                imgContainerPanel.setPreferredSize(imgContainerPanel.getLayout().preferredLayoutSize(imgContainerPanel));
            }
        };
        scrollPane.getVerticalScrollBar().setUnitIncrement(5);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int containerHeight = imgContainerPanel.getHeight();
                final int topHeight = e.getValue();
                int barHeight = scrollPane.getVerticalScrollBar().getHeight();
                int bottomHeight = containerHeight - (topHeight + barHeight);
                int totalCalHeight = (topHeight + barHeight + bottomHeight);
                if (bottomHeight <= 0) {
                    if (searchArea.getText() != null) {
                        String text = searchArea.getText();
                        String text1 = text.replaceAll("\\s+", "");
                        WebSearch webSearch = new WebSearch(text1, imgNo);
                        imgInfoMap = webSearch.getURL();
                    }

                    if (imgInfoMap != null && imgInfoMap.size() > 0) {
                        for (Map.Entry<String, String> entrySet : imgInfoMap.entrySet()) {
                            String rndmImageUrl = entrySet.getKey();
                            String rndmImgName = entrySet.getValue();
                            JLabel imgLabel = return_image_with_label(rndmImageUrl, rndmImgName);
                            imgContainerPanel.add(imgLabel);
                            imgContainerPanel.revalidate();
                            imgContainerPanel.repaint();
                        }

                    }
                    if (imgNo > 20) {
                        imgNo = imgNo + 20;
                    }

                }

            }

        });
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        JButton btnCancel = new JButton("Cancel");
        JPanel btnMainpanel = new JPanel(new BorderLayout());
        btnMainpanel.setBackground(DEFAULT_COLOR);
        btnMainpanel.add(btnCancel, BorderLayout.EAST);

        mainPanel.add(btnMainpanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    public JLabel addLoading() {
        JLabel loadingLabel = new JLabel();
        loadingLabel.setIcon(return_image("Loading_60x60.gif"));
        loadingLabel.setOpaque(false);
        loadingLabel.setBorder(null);
        return loadingLabel;

    }

    public ImageIcon return_image(String image_source) {
        ImageIcon img = null;
        try {
            img = new ImageIcon(new Object() {
            }.getClass().getClassLoader().getResource(image_source));
        } catch (Exception e) {

        }
        return img;
    }

    public JLabel return_image_with_label(String rndmImageUrl, String rndmImgName) {

        Image image = null;
        try {
            URL url = new URL(rndmImageUrl);
            image = ImageIO.read(url);
            //ImageIO.write((RenderedImage) image, "png", new File("F:\\out.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Image mainImage = image;
        JLabel label = new JLabel();

        Image editedImage = image.getScaledInstance(90, 75, SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(editedImage);
        label.setPreferredSize(new Dimension(90, 75));
        label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        label.setBorder(null);
        label.setIcon(newImageIcon);
        label.setOpaque(false);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                label.setToolTipText(rndmImgName);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBorder(null);
                label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                label.setBorder(new MatteBorder(1, 1, 1, 1, Color.WHITE));
                ImagePopup popup = new ImagePopup(rndmImageUrl, rndmImgName, mainImage, imgPanel, imgUrl, imgName);
                System.out.println("" + rndmImageUrl);
            }
        });
        return label;
    }
}
