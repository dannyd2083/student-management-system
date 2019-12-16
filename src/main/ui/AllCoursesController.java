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

public class AllCoursesController extends Observable implements Initializable, Observer {

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
    public Button edit;
    public Button addProfButton;
    public Button addCourse;
    public Button delete;
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
    //EFFECTS: load a selected Course's professor
    // list and show it in the side table, if list is  empty show the warning.
    public void openSelectedCourseProfInformation()  {
        Course course = courseTableView.getSelectionModel().selectedItemProperty().getValue();
        if (course == null) {
            courseTableView.getSelectionModel().selectFirst();
            course = courseTableView.getSelectionModel().selectedItemProperty().getValue();
        }
        ObservableList<Professor> professors = FXCollections.observableArrayList();
        if (!course.getProfessor().isEmpty()) {
            openSelectedCourseProfInfoHelper(course, professors);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Course doesn't have professor");
            alert.showAndWait();
        }
    }

    //MODIFIES: this
    //EFFECTS:  load all information from a Course's professor list to Observable list in order
    // to show content in the side table view
    private void openSelectedCourseProfInfoHelper(Course course, ObservableList<Professor> professors) {
        for (Professor professor : course.getProfessor()) {
            professors.add(professor);
            profNameColumn.setCellValueFactory((new PropertyValueFactory<>("name")));
            profWorkNumberColumn.setCellValueFactory((new PropertyValueFactory<>("workNumber")));
            profDepartmentColumn.setCellValueFactory((new PropertyValueFactory<>("department")));
            profTableView.setItems(professors);
        }
    }




    //MODIFIES: CourseListManager.courses
    //EFFECTS: try edit the Course other wise show the warning window
    public void edit() {
        try {
            editCourseInformation();
        } catch (InvalidStringInputException | ListEmptyException | CantFindException  | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please check your input");
            alert.showAndWait();
        }
    }

    //MODIFIES: CourseListManager.courses
    //EFFECTS: get the selected Course from the Courses list and edit its information by the user input
    public void editCourseInformation() throws
            ListEmptyException, CantFindException, InvalidStringInputException, NumberFormatException {
        int num = Integer.parseInt(courseNumber.getText());
        if (isString(courseName.getText())) {
            Course course = courseTableView.getSelectionModel().selectedItemProperty().getValue();
            Main.courseListManager
                    .find(course.getCourseName(), course.getCourseNum()).setCourseName(courseName.getText());
            Main.courseListManager
                    .find(course.getCourseName(), course.getCourseNum()).setCourseNum(num);
            courseTableView.setItems(getCourses());
            courseTableView.refresh();
        } else {
            throw new InvalidStringInputException();
        }
    }



    //MODIFIES: CourseListManager.courses
    //EFFECTS:  try add professor in the selected Course otherwise show the warning window
    public void addProf() {
        try {
            addSelectCourseProf();
        } catch (InvalidStringInputException | ListEmptyException | CantFindException  | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please check your input");
            alert.showAndWait();
        }
    }

    //MODIFIES: CourseListManager.courses
    //EFFECTS: get the selected Course from the Courses list and add a new prof in its professor list
    public void addSelectCourseProf() throws
            ListEmptyException, CantFindException, InvalidStringInputException, NumberFormatException {
        int num = Integer.parseInt(profWorkNumber.getText());
        if (isString(profName.getText()) && isString(profDepartment.getText())) {
            Course course = courseTableView.getSelectionModel().selectedItemProperty().getValue();
            Professor professor = new Professor(profName.getText(),profDepartment.getText(),num);
            Main.courseListManager
                    .find(course.getCourseName(), course.getCourseNum())
                    .addProfessor(professor);
            Main.professorListManager.add(professor,Main.professorListManager.getList());
            ownedProfListHelper(course);
            profTableView.refresh();
            setChanged();
            notifyObservers();
        } else {
            throw new InvalidStringInputException();
        }
    }

    private void ownedProfListHelper(Course course) {
        ObservableList<Professor> professors = FXCollections.observableArrayList();
        openSelectedCourseProfInfoHelper(course, professors);
    }




    //MODIFIES: CourseListManager.courses
    //EFFECTS: get the selected Course from the Courses list and remove it from the course list
    public void deleteSelectCourse() {
        ObservableList<Course> coursesSelected;
        ObservableList<Course> allCourses;
        Course course = courseTableView.getSelectionModel().selectedItemProperty().getValue();
        Main.courseListManager.getList().remove(course);
        allCourses = courseTableView.getItems();
        coursesSelected = courseTableView.getSelectionModel().getSelectedItems();
        coursesSelected.forEach(allCourses::remove);
        profTableView.getItems().clear();
    }



    //MODIFIES: CourseListManager.courses
    //EFFECTS:  try add course to the course list otherwise show the warning window
    public void addCourse() {
        try {
            addNewCourse();
        } catch (InvalidStringInputException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please check your input");
            alert.showAndWait();
        }
    }


    //MODIFIES: CourseListManager.courses
    //EFFECTS:  add new course in the course list
    public void addNewCourse() throws InvalidStringInputException, NumberFormatException {
        int num1 = Integer.parseInt(courseNumber.getText());
        if (isString(courseName.getText())) {
            Course course = new Course(courseName.getText(), num1);
            Main.courseListManager.add(course, Main.courseListManager.getList());
            courseTableView.setItems(getCourses());
            courseTableView.refresh();
        } else {
            throw new InvalidStringInputException();
        }

    }



    //MODIFIES: this
    //EFFECTS: check if the input is a String
    public boolean isString(String x) {
        if (!x.matches("[a-zA-Z_]+")) {
            return false;
        }
        return true;
    }




    public ObservableList<Course> getCourses() {
        ObservableList<Course> courses = FXCollections.observableArrayList();
        for (Course course : Main.courseListManager.getList()) {
            courses.add(course);
        }
        return courses;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("courseNum"));
        courseTableView.setItems(getCourses());

    }

    //MODIFIES: this
    //EFFECTS: refresh the page when use add a course in the Professor ui
    @Override
    public void update(Observable o, Object arg) {
        courseTableView.setItems(getCourses());
        courseTableView.refresh();
    }
}
