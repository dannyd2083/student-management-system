package ui;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StudentHintController {
    private Scene menuScene;
    private Scene showAllStudentScene;
    private Scene addStudentScene;
    public Button backToMenu;
    public Button addStudent;


    public void setMenuScene(Scene menuScene) {
        this.menuScene = menuScene;
    }

    public void setShowAllStudent(Scene addStudentScene) {
        this.showAllStudentScene = addStudentScene;
    }

    public void setAddStudentScene(Scene addStudentScene) {
        this.addStudentScene = addStudentScene;
    }


    //MODIFIES: this
    //EFFECTS: change the scene to menu Scene
    public void openMenuScene(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(menuScene);
    }

    //MODIFIES: this
    //EFFECTS: change the scene to show all student Scene
    public void openShowAllStudentScene(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(showAllStudentScene);
    }

    //MODIFIES: this
    //EFFECTS: change the scene to addStudent Scene
    public void openAddStudentScene(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(addStudentScene);
    }



//        System.out.println("1. Add student");
//        System.out.println("2. See all the student");
//        System.out.println("3. Show only Graduated or UnderGraduated");
//        System.out.println("4. Delete a student");
//        System.out.println("5. Close the system");
//        System.out.println("6. Find a student by student Number");

}
