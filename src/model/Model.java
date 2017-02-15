package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.FileFilter;

/**
 * Created by Aaron on 2/11/2017.
 */
public class Model extends java.util.Observable{
    private int index = 0;
    private int numFiles = 0;
    private File directory;
    private File[] imageList;
    private FileFilter filter;
    private final String[] validextensions = ImageIO.getReaderFileSuffixes();

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        System.out.println("setting icon");
        setChanged();
        notifyObservers("newIcon");
    }

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

    public File[] getImageList() {
        return imageList;
    }

    public void setImageList(File[] imageList) {
        this.imageList = imageList;
        this.numFiles = imageList.length;
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

}
