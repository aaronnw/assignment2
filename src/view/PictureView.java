package view;
import controller.Controller;
import model.Model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;


/**
 * Created by Aaron on 2/11/2017.
 */
public class PictureView extends JFrame implements java.util.Observer {
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
    private JButton bSS;
    private JLabel directoryLabel;
    private JFileChooser chooser;
    private JLabel imageLabel;
    private ImageIcon icon;
    private ImageIcon scaledImage;
    Model m;
    Controller c;

    public PictureView(final Model m, final Controller c) {
        super("MVC Picture Viewer");
        this.m = m;
        this.c = c;
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        initializeComponents();
        createInterface();
        setListeners();
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
    }
    public void initializeComponents(){
        directoryLabel = new JLabel("No directory selected");
        mFile = new JMenu("File");
        mItemSelect = new JMenuItem("Select folder");
        mItemNext = new JMenuItem("Next image");
        mItemPrevious = new JMenuItem("Previous image");
        mItemExit = new JMenuItem("Exit");
        chooser = new JFileChooser();
        menuBar = new JMenuBar();
        bottomPanel = new JPanel();
        bPrevious = new JButton("Previous");
        bSS = new JButton("Slide-show");
        bNext = new JButton("Next");
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        picPanel = new JPanel();
        picPanel.setLayout(new CardLayout());
        controlPanel = new JPanel();
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        imageLabel = new JLabel("");
        imageLabel.setLayout(new BorderLayout(0,0));
        icon = new ImageIcon();
    }
    public void createInterface(){
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        mFile.add(mItemSelect);
        mFile.add(mItemNext);
        mFile.add(mItemPrevious);
        mFile.add(mItemExit);
        menuBar.add(mFile);
        setJMenuBar(menuBar);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        controlPanel.add(bPrevious);
        controlPanel.add(bSS);
        controlPanel.add(bNext);
        bottomPanel.add(controlPanel);
        bottomPanel.add(directoryLabel);
        imageLabel.setText("Use the File menu to select a directory with image files");
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        picPanel.add(imageLabel, BorderLayout.CENTER);
        contentPane.add(picPanel, BorderLayout.CENTER);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        this.setContentPane(contentPane);
        this.pack();
    }
    public void setListeners(){
        bNext.addActionListener(c.getNextImageListener());
        mItemNext.addActionListener(c.getNextImageListener());
        bPrevious.addActionListener(c.getPreviousImageListener());
        mItemPrevious.addActionListener(c.getPreviousImageListener());
        mItemNext.setAccelerator(KeyStroke.getKeyStroke(39, 0));
        mItemPrevious.setAccelerator(KeyStroke.getKeyStroke(37, 0));
        mItemSelect.addActionListener(c.getSelectFolderListener(chooser, mItemSelect));
        mItemExit.addActionListener(c.getExitListener(this));
        imageLabel.addComponentListener(c.imageResizedListener());
        imageLabel.addMouseListener(c.imageClickedListener(this));
        imageLabel.addMouseWheelListener(c.wheelScrollListener());
        this.addMouseListener(c.mouseMovementListener(this));
        bSS.addActionListener(c.slideshowListener(this));
    }

    @Override
    public void update(Observable obs, Object obj) {
        System.out.println("View updated");
        if(obj.toString().equals("newIcon")){
            imageLabel.setIcon(scaleImage(m.getIcon()));
            imageLabel.setText("");
        }
        if(obj.toString().equals("directorySet")){
            directoryLabel.setText(m.getDirectory().toString());
        }
    }
    private ImageIcon scaleImage(ImageIcon old){
        if(old.getImage() != null) {
            //Get the original dimensions
            double oldWidth = old.getIconWidth();
            double oldHeight = old.getIconHeight();
            //Find the aspect ratio
            double ratio = oldWidth/oldHeight;
            //Set the new width and height based on the ratio
            double width  = Math.min(picPanel.getWidth(), picPanel.getHeight()*ratio);
            double height = Math.min(picPanel.getHeight(), picPanel.getWidth()/ratio);
            return new ImageIcon(old.getImage().getScaledInstance((int) width, (int) height, Image.SCALE_FAST));

        }
        return null;
    }
}
