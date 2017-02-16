package controller;
import model.Model;
import view.PictureView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Aaron on 2/11/2017.
 */

public class Controller{
    Model m;

    public Controller(Model m){
        this.m = m;
    }
    public ActionListener getNextImageListener () {
        return new ActionListener() {
            @Override public void actionPerformed (ActionEvent e) {
                nextImage();
            }
        };
    }
    public ActionListener getPreviousImageListener () {
        return new ActionListener() {
            @Override public void actionPerformed (ActionEvent e) {
                previousImage();
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
    public MouseListener imageClickedListener(){
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nextImage();
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
    public MouseWheelListener wheelScrollListener(){
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
                    nextImage();
                } else {
                    previousImage();
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
    public ActionListener slideshowListener(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSlideshow();
            }
        };
    }

    public void selectFolder(JFileChooser chooser, Component comp){
        chooser.showOpenDialog(comp);
        m.setDirectory(chooser.getSelectedFile());
    }
    public void nextImage(){
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
    public void previousImage(){
        if(m.getIcon() != null) {
            int index = m.getIndex();
            int numFiles = m.getNumFiles();
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
    public void updateImageList(){
         System.out.println("update list");
         m.setImageList(m.getDirectory().listFiles(m.getFilter()));
    }
    public void setImage(){
        if(m.getDirectory()!= null && m.getNumFiles()!= 0){
            ImageIcon icon = new ImageIcon(m.getImageList()[m.getIndex()].getAbsolutePath());
            System.out.println("set image");
            m.setIcon(icon);
        }
    }
    public void startSlideshow(){

    }
    public void stopSlideshow(){

    }
}
