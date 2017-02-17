import model.Model;
import view.PictureView;
import controller.Controller;

/**
 * Aaron Wilson
 * HCI Assignment 2
 * MVC Java Swing Photo Viewer
 */
class Driver {
    public static void main(String[] args){
        Model myModel = new Model();
        Controller controller  = new Controller(myModel);
        PictureView pView = new PictureView(myModel, controller);
        //Lets the view observe changes in the model
        myModel.addObserver(pView);
        pView.setVisible(true);

    }
}
