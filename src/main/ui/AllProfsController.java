package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import placeholder.model.*;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class AllProfsController extends Observable implements Initializable, Observer {

    private Scene menuScene;
    @FXML
    public TableView<Course> courseTableView;
    @FXML
    private TableColumn<Course, String> courseNameColumn;
    @FXML
    private TableColumn<Course,String> courseNumberColumn;
    @FXML
    private TableColumn<Professor, String> profNameColumn;
    @FXML
    private TableColumn<Professor, Integer> profWorkNumberColumn;
    @FXML
    private TableColumn<Professor, String> profDepartmentColumn;
    @FXML
    private TableView<Professor> profTableView;

    public TextField courseName;
    public TextField courseNumber;
    public TextField profDepartment;
    public TextField profName;
    public TextField profWorkNumber;
    public Button editButton;
    public Button addProfButton;
    public Button addCourseButton;
    public Button deleteProfButton;
    public Button back;

    public void setMenuScene(Scene menuScene) {
        this.menuScene = menuScene;
    }


    //MODIFIES: this
    //EFFECTS: change the scene to menu
    public void openMenuScene(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(menuScene);
    }

    //MODIFIES: this
    //EFFECTS: load a selected Professor's courses
    // list and show it in the side table, if list is empty show the warning.
    public void openSelectedProfInformation()  {
        Professor prof = profTableView.getSelectionModel().selectedItemProperty().getValue();
        if (prof == null) {
            profTableView.getSelectionModel().selectFirst();
            prof = profTableView.getSelectionModel().selectedItemProperty().getValue();
        }
        ObservableList<Course> courses = FXCollections.observableArrayList();
        if (!prof.getCourses().isEmpty()) {
            courseListHelper(prof, courses);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Professor doesn't have course");
            alert.showAndWait();
        }
    }


    //MODIFIES: this
    //EFFECTS:  load all information from a Professor's course list to Observable list in order
    // to show content in the side table view
    private void courseListHelper(Professor prof, ObservableList<Course> courses) {
        for (Course course : prof.getCourses()) {
            courses.add(course);
            courseNameColumn.setCellValueFactory((new PropertyValueFactory<>("courseName")));
            courseNumberColumn.setCellValueFactory((new PropertyValueFactory<>("courseNum")));
            courseTableView.setItems(courses);
        }
    }

    //MODIFIES: ProfessorListManager.professors
    //EFFECTS: try edit the selected professors other wise show the warning window
    public void edit() {
        try {
            editProfessorInformation();
        } catch (InvalidStringInputException | ListEmptyException | CantFindException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please check your input");
            alert.showAndWait();
        }
    }

    //MODIFIES: ProfessorListManager.professors
    //EFFECTS: get the selected professor from the professor list and edit its information by the user input
    public void editProfessorInformation() throws
            ListEmptyException, CantFindException, InvalidStringInputException, NumberFormatException {
        int num = Integer.parseInt(profWorkNumber.getText());
        if (isString(profName.getText()) && isString(profDepartment.getText())) {
            Professor professor = profTableView.getSelectionModel().selectedItemProperty().getValue();
            Main.professorListManager
                    .find(professor.getWorkNumber(), professor.getName(),Main.professorListManager.getList())
                    .setName(profName.getText());
            Main.professorListManager
                    .find(professor.getWorkNumber(), professor.getName(),Main.professorListManager.getList())
                    .setDepartment(profDepartment.getText());
            Main.professorListManager
                    .find(professor.getWorkNumber(), professor.getName(),Main.professorListManager.getList())
                    .setWorkNumber(num);
            profTableView.setItems(getProf());
            profTableView.refresh();
        } else {
            throw new InvalidStringInputException();
        }
    }



    //MODIFIES: ProfessorListManager.professors
    //EFFECTS:  try add course in the selected Professor otherwise show the warning window
    public void addCourse() {
        try {
            addSelectProfCourse();
        } catch (InvalidStringInputException | ListEmptyException | CantFindException  | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please check your input");
            alert.showAndWait();
        }
    }

    //MODIFIES: ProfessorListManager.professors
    //EFFECTS: get the selected Prof from the Prof list and add a new course in its course list
    public void addSelectProfCourse() throws
            ListEmptyException, CantFindException, InvalidStringInputException, NumberFormatException {
        int num = Integer.parseInt(courseNumber.getText());
        if (isString(courseName.getText())) {
            Professor prof = profTableView.getSelectionModel().selectedItemProperty().getValue();
            Course course = new Course(courseName.getText(),num);
            Main.professorListManager
                    .find(prof.getWorkNumber(),prof.getName(),Main.professorListManager.getList())
                    .addCourse(course);
            Main.courseListManager.add(course,Main.courseListManager.getList());
            ownedCourseListHelper(prof);
            profTableView.refresh();
            setChanged();
            notifyObservers();
        } else {
            throw new InvalidStringInputException();
        }
    }


    private void ownedCourseListHelper(Professor prof) {
        ObservableList<Course> courses = FXCollections.observableArrayList();
        courseListHelper(prof, courses);
    }



    //MODIFIES: ProfessorListManager.professors
    //EFFECTS: get the selected Prof from the Prof list and remove it from the prof list
    public void deleteSelectProf() {
        ObservableList<Professor> profSelected;
        ObservableList<Professor> allProfessors;
        Professor professor = profTableView.getSelectionModel().selectedItemProperty().getValue();
        Main.professorListManager.getList().remove(professor);
        allProfessors = profTableView.getItems();
        profSelected = profTableView.getSelectionModel().getSelectedItems();
        profSelected.forEach(allProfessors::remove);
        courseTableView.getItems().clear();
    }


    //MODIFIES: ProfessorListManager.courses
    //EFFECTS:  try add prof to the prof list otherwise show the warning window
    public void addProf() {
        try {
            addNewProf();
        } catch (InvalidStringInputException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please check your input");
            alert.showAndWait();
        }
    }


    //MODIFIES: ProfessorListManager.courses
    //EFFECTS:  add new prof in the prof list
    public void addNewProf() throws InvalidStringInputException, NumberFormatException {
        int num1 = Integer.parseInt(profWorkNumber.getText());
        if (isString(profName.getText()) && isString(profDepartment.getText())) {
            Professor professor = new Professor(profName.getText(),profDepartment.getText(),num1);
            Main.professorListManager.add(professor, Main.professorListManager.getList());
            profTableView.setItems(getProf());
            profTableView.refresh();
        } else {
            throw new InvalidStringInputException();
        }

    }





    //MODIFIES: this
    //EFFECTS: check if the input is a String
    private boolean isString(String text) {
        if (!text.matches("[a-zA-Z_]+")) {
            return false;
        }
        return true;
    }


    public ObservableList<Professor> getProf() {
        ObservableList<Professor> professors = FXCollections.observableArrayList();
        for (Professor professor : Main.professorListManager.getList()) {
            professors.add(professor);
        }
        return professors;
    }







    @Override
    public void initialize(URL location, ResourceBundle resources) {
        profNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        profWorkNumberColumn.setCellValueFactory(new PropertyValueFactory<>("workNumber"));
        profDepartmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        profTableView.setItems(getProf());
    }


    //MODIFIES: this
    //EFFECTS: refresh the page when use add a professor in the Course ui
    @Override
    public void update(Observable o, Object arg) {
        profTableView.setItems(getProf());
        profTableView.refresh();
    }
}
