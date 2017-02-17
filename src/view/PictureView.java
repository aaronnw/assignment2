package view;
import controller.Controller;
import model.Model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Observable;


/**
 * Main view class
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
    private JMenuItem mHelp;
    private JButton bPrevious;
    private JButton bNext;
    private JButton bSS;
    private JLabel directoryLabel;
    private JFileChooser chooser;
    private JLabel imageLabel;
    private final Model m;
    private final Controller c;

    public PictureView(final Model m, final Controller c) {
        super("MVC Picture Viewer");
        this.m = m;
        this.c = c;
        setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE);
        initializeComponents();
        createInterface();
        setListeners();
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
    }
    private void initializeComponents(){
        directoryLabel = new JLabel("No directory selected");
        mFile = new JMenu("File");
        mItemSelect = new JMenuItem("Select folder");
        mItemNext = new JMenuItem("Next image");
        mItemPrevious = new JMenuItem("Previous image");
        mHelp = new JMenuItem("Help");
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
    }
    private void createInterface(){
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        mFile.add(mItemSelect);
        mFile.add(mItemNext);
        mFile.add(mItemPrevious);
        mFile.add(mItemExit);
        menuBar.add(mFile);
        menuBar.add(mHelp);
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
    private void setListeners(){
        bNext.addActionListener(c.getNextImageListener(this));
        mItemNext.addActionListener(c.getNextImageListener(this));
        bPrevious.addActionListener(c.getPreviousImageListener(this));
        mItemPrevious.addActionListener(c.getPreviousImageListener(this));
        mItemNext.setAccelerator(KeyStroke.getKeyStroke(39, 0));
        mItemPrevious.setAccelerator(KeyStroke.getKeyStroke(37, 0));
        mItemSelect.addActionListener(c.getSelectFolderListener(chooser, mItemSelect));
        mHelp.addActionListener(c.getHelpListener(this));
        mItemExit.addActionListener(c.getExitListener(this));
        imageLabel.addComponentListener(c.imageResizedListener());
        imageLabel.addMouseListener(c.imageClickedListener(this));
        imageLabel.addMouseWheelListener(c.wheelScrollListener(this));
        picPanel.addMouseListener(c.mouseClickListener(this));
        this.addMouseListener(c.mouseClickListener(this));
        bSS.addActionListener(c.slideshowListener(this));
    }

    /*
    When the model sends a command to its observers to update, this method is called
    The method will be passed a string defining what changed
     */
    @Override
    public void update(Observable obs, Object obj) {
        if(obj.toString().equals("newIcon")){
            //Tell the controller to scale the image inside the picture panel
            imageLabel.setIcon(c.scaleImage(picPanel));
            imageLabel.setText("");
        }
        if(obj.toString().equals("directorySet")){
            directoryLabel.setText(m.getDirectory().toString());
        }
    }

}
