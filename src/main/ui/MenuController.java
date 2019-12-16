package ui;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MenuController {
    private Scene studentHintScene;
    private Scene allCoursesScene;
    private  Scene allProfsScene;
    public Button manageStudent;
    public Label tittle;
    public Button manageCourse;
    public Button manageProf;



    public void setAllProfsScene(Scene allProfsScene) {
        this.allProfsScene = allProfsScene;
    }

    public void setStudentHintScene(Scene scene) {
        studentHintScene = scene;
    }


    public void setAllCoursesScene(Scene allCoursesScene) {
        this.allCoursesScene = allCoursesScene;
    }


    //MODIFIES: this
    //EFFECTS: change the scene to show all course Scene
    public void openAllCoursesScene(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(allCoursesScene);
    }


    //MODIFIES: this
    //EFFECTS: change the scene to StudentHint Scene
    public void openStudentHintScene(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(studentHintScene);
    }

    //MODIFIES: this
    //EFFECTS: change the scene to show all professor Scene
    public void openAllProfsScene(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(allProfsScene);
    }





}
