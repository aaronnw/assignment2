package view;
import controller.Controller;
import model.Model;

import javax.swing.*;
import java.awt.*;


/**
 * Created by Aaron on 2/11/2017.
 */
public class PictureView extends JFrame {
    private JPanel contentPane = new JPanel();
    private JPanel picPanel;
    private JPanel bottomPanel;
    private JPanel controlPanel;
    private JMenuBar menuBar;
    private JMenu mFile;
    private JMenuItem mItemSelect;
    private JMenuItem mItemNext;
    private JMenuItem mItemPrevious;
    private JMenuItem mItemExit;
    private JButton bPrevious;
    private JButton bNext;
    private JLabel directoryLabel;
    private JFileChooser chooser;
    private JLabel imageLabel;
    private ImageIcon icon;
    private ImageIcon scaledImage;

    public PictureView(final Model m, final Controller c) {


    }
}
