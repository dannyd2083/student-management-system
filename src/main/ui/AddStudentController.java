package ui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import placeholder.model.*;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class AddStudentController  extends Observable implements Initializable {

    private Scene studentHintScene;
    public Button submitButton;
    public TextField studentName;
    public TextField studentNumber;
    public TextField studentAge;
    public ChoiceBox<String> choiceBox;
    public Button back;


    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    void setStudentHintScene(Scene studentHintScene) {
        this.studentHintScene = studentHintScene;
    }

    //MODIFIES: this
    //EFFECTS: change the scene to menu
    public void openStudentHintScene(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(studentHintScene);
    }


    //MODIFIES: StudentListManager.studentList
    //EFFECTS: add student into the student List
    public void addStudent() {
        try {
            if (choiceBox.getValue().equalsIgnoreCase("Undergraduate")) {
                addUndergraduate();
            }
            if (choiceBox.getValue().equalsIgnoreCase("Graduate")) {
                addGraduate();
            }
            successfulSubmit();
        } catch (NumberFormatException | AgeOutOfRangeException | InvalidStringInputException e) {
            wrongInputWarning();
        }
    }

    //MODIFIES: StudentListManager.studentList
    //EFFECTS: add student who has graduate status into the studentList
    private void addGraduate() throws AgeOutOfRangeException, InvalidStringInputException {
        int num1 = Integer.parseInt(studentNumber.getText());
        int age2 = Integer.parseInt(studentAge.getText());
        if (isString(studentName.getText())) {
            Student st2 = new Graduated(studentName.getText(), num1, age2);
            Main.studentListManager.checkAge(st2);
            Main.studentListManager.add(st2, Main.studentListManager.getList());
            setChanged();
            notifyObservers();
        } else {
            throw new InvalidStringInputException();
        }
    }

    //MODIFIES: StudentListManager.studentList
    //EFFECTS: add student who has undergraduate status into the studentList

    private void addUndergraduate() throws AgeOutOfRangeException, InvalidStringInputException {
        int num = Integer.parseInt(studentNumber.getText());
        int age = Integer.parseInt(studentAge.getText());
        if (isString(studentName.getText())) {
            Student st = new UnderGraduated(studentName.getText(), num, age);
            Main.studentListManager.checkAge(st);
            Main.studentListManager.add(st, Main.studentListManager.getList());
            setChanged();
            notifyObservers();
        } else {
            throw new InvalidStringInputException();
        }
    }


    //MODIFIES: this
    //EFFECTS: open a alert window when submit successfully
    //https://code.makery.ch/blog/javafx-dialogs-official/
    private void successfulSubmit() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successful!");
        alert.setHeaderText(null);
        alert.setContentText("Add successfully");
        alert.showAndWait();
    }

    //MODIFIES: this
    //EFFECTS: Open an alert window when input is not correct
    //https://code.makery.ch/blog/javafx-dialogs-official/
    private void wrongInputWarning() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Please check your input");
        alert.showAndWait();
    }


    //MODIFIES: this
    //EFFECTS: check if the input is a String
    private boolean isString(String x) {
        if (!x.matches("[a-zA-Z_]+")) {
            return false;
        }
        return true;
    }


    //MODIFIES: this
    //EFFECTS: load two options (graduate and undergraduate) for choice box
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBox.getItems().add("Undergraduate");
        choiceBox.getItems().add("Graduate");
        choiceBox.setValue("Undergraduate");
    }



}
