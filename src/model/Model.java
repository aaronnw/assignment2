package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;

/**
 * Created by Aaron on 2/11/2017.
 */
public class Model extends java.util.Observable{
    private int index = 0;
    private int numFiles = 0;
    private File directory;
    private File[] fileList;
    private Image[] imageList;
    private FileFilter filter;
    private final String[] validextensions = ImageIO.getReaderFileSuffixes();
    private int prevWindowState;
    private long delay = 0;


    private final String helpMessage = "" +
            "Start by selecting a folder with image icons from the File menu \n \n" +
            "Once you have selected a folder, there are 6 ways to progress through the images: \n\n" +
            "1. Click the next or previous buttons at the bottom of the screen\n" +
            "2. Select the next or previous options from the File menu\n" +
            "3. Click on the image to move to the next one\n" +
            "4. Scroll down on the images to move to the next image. Scroll up to move to the previous image.\n" +
            "5. Use the left and right arrow keys on your keyboard.\n" +
            "6. Start a slideshow by clicking the slideshow button then click anywhere to stop." ;


    ImageIcon icon;

    public Model(){
        filter = new FileFilter(){
            @Override
            public boolean accept(File pathname){
                String extension = getExtension(pathname);
                //Check if the extension is in the valid list
                for(String validext:validextensions){
                    if(validext.equals(extension)){
                        return true;
                    }
                }
                return false;
            }
        };
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getNumFiles() {
        return numFiles;
    }

    public void setNumFiles(int numFiles) {
        this.numFiles = numFiles;
    }

    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
        setChanged();
        notifyObservers("directorySet");
    }

    public File[] getFileList() {
        return fileList;
    }

    public void setFileList(File[] fileList) {
        this.fileList = fileList;
        this.numFiles = fileList.length;
    }

    public FileFilter getFilter() {
        return filter;
    }

    public String getExtension(File f){
        String name = f.toString();
        if(!name.contains(".")){
            return null;
        }
        return name.substring(name.lastIndexOf(".") +1);
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        setChanged();
        notifyObservers("newIcon");
    }

    public int getPrevWindowState() {
        return prevWindowState;
    }

    public void setPrevWindowState(int prevWindowState) {
        this.prevWindowState = prevWindowState;
    }

    public String getHelpMessage() {
        return helpMessage;
    }

}
