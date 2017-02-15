package driver;

import model.Model;
import view.PictureView;
import controller.Controller;

/**
 * Created by Aaron on 2/11/2017.
 */
public class Driver {
    public static void main(String[] args){
        Model myModel = new Model();
        Controller controller  = new Controller(myModel);
        PictureView pView = new PictureView(myModel, controller);
        myModel.addObserver(pView);
        pView.setVisible(true);

    }
}
