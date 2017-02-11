package model;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Aaron on 2/11/2017.
 */
public class Model {
    private int index = 0;
    private int numFiles = 0;
    private File directory;
    private File[] imageList;
    private FileFilter filter;

    public Model(){
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
    }

    public File[] getImageList() {
        return imageList;
    }

    public void setImageList(File[] imageList) {
        this.imageList = imageList;
    }

    public FileFilter getFilter() {
        return filter;
    }

    public void setFilter(FileFilter filter) {
        this.filter = filter;
    }
}
