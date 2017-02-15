package controller;
import model.Model;
import view.PictureView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    public ActionListener getExitListener(PictureView v){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Select folder method
                v.dispose();
            }
        };
    }

    public void selectFolder(JFileChooser chooser, Component comp){
        chooser.showOpenDialog(comp);
        m.setDirectory(chooser.getSelectedFile());
    }
    public void nextImage(){

    }
    public void previousImage(){

    }
    public void updateImageList(){
         m.setImageList(m.getDirectory().listFiles(m.getFilter()));
    }
    public void setImage(){
        if(m.getDirectory()!= null && m.getNumFiles()!= 0){
            ImageIcon icon = new ImageIcon(m.getImageList()[m.getIndex()].getAbsolutePath());
            m.setIcon(icon);
        }
    }
}
