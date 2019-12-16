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

public class ShowAllStudentController implements Initializable, Observer {

    private Scene studentHintScene;
    @FXML
    public TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, String> studentNameColumn;
    @FXML
    private TableColumn<Student, Integer> studentNumberColumn;
    @FXML
    private TableColumn<Student, String> studentStatusColumn;
    @FXML
    private TableColumn<Student,Integer> studentAgeColumn;
    @FXML
    private TableView<Course> studentCourseTableView;
    @FXML
    private TableColumn<Course, String> courseNameColumn;
    @FXML
    private TableColumn<Course,String> courseNumberColumn;
    public TextField studentName;
    public TextField studentNumber;
    public TextField studentAge;
    public TextField courseName;
    public TextField courseNumber;
    public Button edit;
    public Button addCourseButton;
    public Button delete;
    public Button back;



    public void setStudentHintScene(Scene studentHintScene) {
        this.studentHintScene = studentHintScene;
    }


    //MODIFIES: this
    //EFFECTS: change the scene to studentHint Scene
    public void openStudentHintScene(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(studentHintScene);
    }


    public ObservableList<Student> getStudents() {
        ObservableList<Student> students = FXCollections.observableArrayList();
        for (Student student : Main.studentListManager.getList()) {
            students.add(student);
        }
        return students;
    }


    //MODIFIES: this
    //EFFECTS: load a selected Student's course
    // list and show it in the side table, if list is  empty show the warning.
    public void openSelectedStudentCourseInformation()  {
        Student student = studentTableView.getSelectionModel().selectedItemProperty().getValue();
        if (student == null) {
            studentTableView.getSelectionModel().selectFirst();
            student = studentTableView.getSelectionModel().selectedItemProperty().getValue();
        }
        ObservableList<Course> courses = FXCollections.observableArrayList();
        if (!student.getCourses().isEmpty()) {
            courseListHelper(student, courses);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Student doesn't have course");
            alert.showAndWait();
        }
    }


    private void courseListHelper(Student student, ObservableList<Course> courses) {
        for (Course course : student.getCourses()) {
            courses.add(course);
            courseNameColumn.setCellValueFactory((new PropertyValueFactory<>("courseName")));
            courseNumberColumn.setCellValueFactory((new PropertyValueFactory<>("courseNum")));
            studentCourseTableView.setItems(courses);
        }
    }




    //MODIFIES: StudentListManagement.students
    //EFFECTS: try edit the Student other wise show the warning window
    public void editS() {
        try {
            editStudentInformation();
        } catch (InvalidStringInputException | ListEmptyException | CantFindException  | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please check your input");
            alert.showAndWait();
        }
    }

    //MODIFIES: StudentListManager.students
    //EFFECTS: get the selected Student from the Students list and edit its information by the user input
    public void editStudentInformation() throws ListEmptyException, CantFindException, InvalidStringInputException {
        int num = Integer.parseInt(studentNumber.getText());
        int age = Integer.parseInt(studentAge.getText());
        if (isString(studentName.getText())) {
            Student student = studentTableView.getSelectionModel().selectedItemProperty().getValue();
            studentTableView.setItems(getStudents());
            Main.studentListManager
                    .find(student.getStudentNumber(), student.getStatus(), Main.studentListManager.getList())
                    .setStudentName(studentName.getText());
            Main.studentListManager
                    .find(student.getStudentNumber(), student.getStatus(), Main.studentListManager.getList())
                    .setStudentNumber(num);
            Main.studentListManager
                    .find(student.getStudentNumber(), student.getStatus(), Main.studentListManager.getList())
                    .setAge(age);
            studentTableView.refresh();
        } else {
            throw new InvalidStringInputException();
        }
    }


    //MODIFIES: StudentListManager.courses
    //EFFECTS:  try add course in the selected Student otherwise show the warning window
    public void addCourse() {
        try {
            addSelectStudentCourse();
        } catch (InvalidStringInputException | NumberFormatException | ListEmptyException | CantFindException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please check your input");
            alert.showAndWait();
        }
    }


    //MODIFIES: StudentListManager.courses
    //EFFECTS: get the selected Student from the Student list and add a new course in its course list
    public void addSelectStudentCourse() throws ListEmptyException, CantFindException,InvalidStringInputException {
        int num = Integer.parseInt(courseNumber.getText());
        if (isString(courseName.getText())) {
            Student student = studentTableView.getSelectionModel().selectedItemProperty().getValue();
            Main.studentListManager
                    .find(student.getStudentNumber(), student.getStatus(), Main.studentListManager.getList())
                    .addCourse(new Course(courseName.getText(), num));

            ObservableList<Course> courses = FXCollections.observableArrayList();
            courseListHelper(student, courses);
            studentCourseTableView.refresh();
        } else {
            throw new InvalidStringInputException();
        }
    }


    //MODIFIES: StudentListManager.students
    //EFFECTS: get the selected Student from the Student list and remove it from the student list
    public void deleteSelectStudent() {
        ObservableList<Student> studentSelected;
        ObservableList<Student> allStudents;
        Student student = studentTableView.getSelectionModel().selectedItemProperty().getValue();
        Main.studentListManager.getList().remove(student);
        allStudents = studentTableView.getItems();
        studentSelected = studentTableView.getSelectionModel().getSelectedItems();
        studentSelected.forEach(allStudents::remove);
        studentCourseTableView.getItems().clear();
    }



    //MODIFIES: this
    //EFFECTS: check if the input is a String
    public boolean isString(String x) {
        if (!x.matches("[a-zA-Z_]+")) {
            return false;
        }
        return true;
    }

    // https://www.youtube.com/watch?v=uh5R7D_vFto
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        studentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
        studentAgeColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        studentTableView.setItems(getStudents());
    }

    //MODIFIES: this
    //EFFECTS: refresh the page when use add a student in the addStudent ui
    @Override
    public void update(Observable o, Object arg) {
        studentTableView.setItems(getStudents());
        studentTableView.refresh();

    }
}
