package controller;
import model.Model;
import view.PictureView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("ALL")
public class Controller{
    private final Model m;
    private final Timer ssTimer;

    public Controller(Model m){
        //Sets the model the controller will access
        this.m = m;
        //Creates a timer that when started will automatically progress images for the slideshow
        ssTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ssNext();
            }
        });
    }
    /*
        The following listener methods all perform actions in the controller
        based on events in the view
     */
    public ActionListener getNextImageListener (PictureView v) {
        return new ActionListener() {
            @Override public void actionPerformed (ActionEvent e) {
                nextImage(v);
            }
        };
    }
    public ActionListener getPreviousImageListener (PictureView v) {
        return new ActionListener() {
            @Override public void actionPerformed (ActionEvent e) {
                previousImage(v);
            }
        };
    }
    public ActionListener getHelpListener(PictureView v){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(v, m.getHelpMessage(), "Help", JOptionPane.PLAIN_MESSAGE);
            }
        };
    }
    public ActionListener getSelectFolderListener(JFileChooser chooser, Component comp){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFolder(chooser, comp);
                updateImageList();
                setImage();
            }
        };
    }
    public ComponentListener imageResizedListener(){
        return new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                setImage();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        };
    }
    public MouseListener imageClickedListener(PictureView v){
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nextImage(v);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }
    public MouseWheelListener wheelScrollListener(PictureView v){
        return new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                 /*If time since the last movement is >800 ms
                        Prevents scrolling too fast on a touch-pad
                     */
                if(m.getDelay() != 0 && (System.currentTimeMillis() -m.getDelay() <800)){
                    return;
                }
                if (e.getWheelRotation() > 0) {
                    nextImage(v);
                } else {
                    previousImage(v);
                }
                m.setDelay(System.currentTimeMillis());
            }
        };
    }
    public ActionListener getExitListener(PictureView v){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Select folder method
                v.dispose();
            }
        };
    }
    public ActionListener slideshowListener(PictureView v){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ssTimer.isRunning() ){
                    stopSlideshow(v);
                }else{
                    startSlideshow(v);
                }
            }
        };
    }
    public MouseListener mouseClickListener(PictureView v){
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                stopSlideshow(v);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }

    /*Lets the user select the image folder with a given file chooser*/
    private void selectFolder(JFileChooser chooser, Component comp){
        chooser.showOpenDialog(comp);
        m.setDirectory(chooser.getSelectedFile());
    }
    /*Shifts the index and loads the next image*/
    private void nextImage(PictureView v){
        if(ssTimer.isRunning()){
            stopSlideshow(v);
            return;
        }
        if(m.getIcon() != null) {
            int index = m.getIndex();
            int numFiles = m.getNumFiles();
            if (index < numFiles - 1) {
                index++;
                m.setIndex(index);
            } else {
                m.setIndex(0);
            }
            setImage();

        }

    }
    /*Shifts the index and loads the previous image*/
    private void previousImage(PictureView v){
        if(ssTimer.isRunning()){
            stopSlideshow(v);
            return;
        }
        if(m.getIcon() != null) {
            int index = m.getIndex();
            if (index > 0) {
                index--;
                m.setIndex(index);
            } else {
                index = m.getNumFiles() - 1;
                m.setIndex(index);
            }
            setImage();

        }
    }
    /*Sets the list of files in the model to the valid filtered files in a given directory*/
    private void updateImageList(){
         m.setFileList(m.getDirectory().listFiles(m.getFilter()));
    }
    /*Set the current icon based on the current index in the model*/
    private void setImage(){
        if(m.getDirectory()!= null && m.getNumFiles()!= 0){
            ImageIcon icon = new ImageIcon(m.getFileList()[m.getIndex()].getAbsolutePath());
            m.setIcon(icon);
        }
    }
    /*Scale the image and retain the aspect ratio*/
    public ImageIcon scaleImage(JPanel p){
        ImageIcon old = m.getIcon();
        if(old.getImage() != null) {
            //Get the original dimensions
            double oldWidth = old.getIconWidth();
            double oldHeight = old.getIconHeight();
            //Find the aspect ratio
            double ratio = oldWidth/oldHeight;
            //Set the new width and height based on the ratio
            double width  = Math.min(p.getWidth(), p.getHeight()*ratio);
            double height = Math.min(p.getHeight(), p.getWidth()/ratio);
            return new ImageIcon(old.getImage().getScaledInstance((int) width, (int) height, Image.SCALE_FAST));

        }
        return null;
    }
    /*Make the frame full-screen and start the slideshow timer*/
    private void startSlideshow(PictureView v){
        if(m.getIcon() != null) {
            m.setPrevWindowState(v.getExtendedState());
            v.setExtendedState(JFrame.MAXIMIZED_BOTH);
            ssTimer.start();
        }

    }
    /*Stop the slide-show timer and revert the previous frame size*/
    private void stopSlideshow(PictureView v){
        if(ssTimer.isRunning()){
            v.setExtendedState(m.getPrevWindowState());
            ssTimer.stop();
        }
    }
    /*Used for the slideshow method to progress
    The normal nextImage method stops the slideshow if it is running
     */
    private void ssNext(){
        if(m.getIcon() != null) {
            int index = m.getIndex();
            int numFiles = m.getNumFiles();
            if (index < numFiles - 1) {
                index++;
                m.setIndex(index);
            } else {
                m.setIndex(0);
            }
            setImage();
        }
    }
}
