/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ImageSearch;

import java.awt.BorderLayout;
import java.awt.Image;
import static java.awt.Image.SCALE_SMOOTH;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ringpoll.Storers;

/**
 *
 * @author raj
 */
public class ImagePopup extends JDialog {

    private Image image = null;
    public JPanel imgPanel;
    public String imgUrl;
    public String imgName;

    public String rndmImageUrl;
    public String rndmImageName;

    public ImagePopup(String rndmImageUrl, String rndmImageName, Image image, JPanel imgPanel, String imgUrl, String imgName) {
        this.rndmImageUrl = rndmImageUrl;
        this.rndmImageName = rndmImageName;
        this.image = image;
        this.imgPanel = imgPanel;
        this.imgUrl = imgUrl;
        this.imgName = imgName;

        setVisible(true);
        setSize(230, 240);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                setVisible(false);
            }
        });
        initComponents();
    }

    private void initComponents() {
        Image editedImage = image.getScaledInstance(220, 170, SCALE_SMOOTH);
        ImageIcon newImageIcon = new ImageIcon(editedImage);
        JLabel imageLabel = new JLabel(newImageIcon);
        //imageLabel.setPreferredSize(new Dimension(250, 200));
        JPanel imagePanel = new JPanel();
        imagePanel.add(imageLabel);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(new EmptyBorder(0, 15, 5, 15));
        JButton selectButton = new JButton("Select");
        JButton cancelButton = new JButton("Cancel");
        selectButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                imgPanel.removeAll();
                Image editedImage = image.getScaledInstance(40, 35, SCALE_SMOOTH);
                ImageIcon newImageIcon = new ImageIcon(editedImage);
                JLabel imageLabel = new JLabel(newImageIcon);
                imgPanel.add(imageLabel, BorderLayout.CENTER);
                imgPanel.revalidate();
                setVisible(false);
                imgUrl = rndmImageUrl;
                imgName = rndmImageName;
                if (imgUrl != null && imgUrl.length() > 0 && imgName != null && imgName.length() > 0) {
                    Storers.getInstance().tempImgUrlList.add(imgUrl);
                    Storers.getInstance().tempImgNameList.add(imgName);
                    System.out.println("" + imgUrl);
                }

                if (image != null && imgName != null) {
                    try {
                        ImageIO.write((RenderedImage) image, "jpg", new File("C:\\Users\\Raj_raj\\Desktop\\ringPoll\\ringPoll\\tempImages\\" + imgName + ".jpg"));
                    } catch (IOException ex) {
                    }
                }

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        buttonPanel.add(selectButton, BorderLayout.WEST);
        buttonPanel.add(cancelButton, BorderLayout.EAST);
        add(imagePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

}
