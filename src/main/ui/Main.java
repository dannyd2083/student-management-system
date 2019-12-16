package ui;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.text.Font;
import placeholder.model.*;

import java.io.IOException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static StudentListManager studentListManager = new StudentListManager("Student.txt");
    public static ProfessorListManager professorListManager = new ProfessorListManager("Professor.txt");
    public static CourseListManager courseListManager = new CourseListManager("Course.txt");
    public static Scanner scanner = new Scanner(System.in);
    public static int doAgain = 1;

    FXMLLoader menuLoader = new FXMLLoader((getClass().getResource("Menu.fxml")));
    FXMLLoader studentHintLoader = new FXMLLoader((getClass().getResource("StudentHint.fxml")));
    FXMLLoader showAllStudentLoader = new FXMLLoader((getClass().getResource("ShowAllStudent.fxml")));
    FXMLLoader addStudentLoader = new FXMLLoader((getClass().getResource("AddStudent.fxml")));
    FXMLLoader courseSceneLoader = new FXMLLoader((getClass().getResource("Allcourses.fxml")));
    FXMLLoader profSceneLoader = new FXMLLoader((getClass().getResource("AllProfs.fxml")));

    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // set up program as javafx application
        System.out.println("Welcome to Student Management system!Please Enter choices.");
        studentListManager.load();
        courseListManager.load();
        professorListManager.load();
        launch(args);
        System.out.println("Do you want to enter backstage? 1.yes, 2.no");
        int num = scanner.nextInt();
        if (num == 1) {
            while (doAgain == 1) {
                hint();
                String choice = scanner.next();
                helper(choice);
            }
        } else {
            saveAll();
        }
    }


    private static void hint() {
        System.out.println("1.Student");
        System.out.println("2.Courses");
        System.out.println("3.Professors");
    }

    private static void helper(String choice) {
        switch (choice) {
            case "1": studentHint();
            break;
            case "2": courseHint();
            break;
            default: professorHint();

        }
    }

    private static void saveAll() {
        studentListManager.save(studentListManager.getList(), "Student List", "Course List");
        courseListManager.save(courseListManager.getList(), "Course List", "Professor List");
        professorListManager.save(professorListManager.getList(),"Professor List","Course List");
    }

    private static void studentHint() {
        System.out.println("1. Add student");
        System.out.println("2. See all the student");
        System.out.println("3. Show only Graduated or UnderGraduated");
        System.out.println("4. Delete a student");
        System.out.println("5. Close the system");
        System.out.println("6. Find a student by student Number");
        String choice = scanner.next();
        studentHelper(choice);
    }

    private static void studentHelper(String choice) {
        switch (choice) {
            case "1": addStudent();
                break;
            case "2": showAllStudent();
                break;
            case "3": System.out.println("Please enter 'graduated' or 'undergraduate'");
                showOnlyOneChoice();
                break;
            case "4": removeStudent();
                break;
            case "5": saveAll();
                System.exit(0);
                break;
            default: findStudent();
        }
    }



    private static void professorHint() {
        System.out.println("1. Add Professor");
        System.out.println("2. See all the professors' information");
        System.out.println("3. Delete a professor's information");
        System.out.println("4. Find a professor");
        System.out.println("5. Close the system");
        String choice = scanner.next();
        professorHelper(choice);
    }

    private static void professorHelper(String choice) {
        switch (choice) {
            case "1": addProfessorInPList();
                break;
            case "2": showAllProfessors();
                break;
            case "3": deleteProfessorInPList();
                break;
            case "4": findProfessor();
                break;
            case "5": courseListManager.save(courseListManager.getList(),"Course List","Professor List");
                saveAll();
                System.exit(0);
                break;
            default:
                System.out.println("wrong choice");
        }
    }

    private static void deleteProfessorInPList() {
        try {
            System.out.println("Please enter Professor Work Number");
            int number = scanner.nextInt();
            professorListManager.delete("",number,professorListManager.getList());
        } catch (ListEmptyException e) {
            System.out.println("Remove failing!!!");
        } catch (CantFindException e) {
            System.out.println("Cant find the Course!");
        } finally {
            System.out.println("---------------------------");
        }
    }

    private static void findProfessor() {
        System.out.println("please input Professor's work number and Name");
        int workNum = scanner.nextInt();
        String name = scanner.next();
        try {
            System.out.println("Professor Name: "
                    + professorListManager.find(workNum,name,professorListManager.getList()).getProfessorName());
            System.out.println("Work Number " + professorListManager.find(workNum,name,professorListManager.getList())
                    .getWorkNumber());
            showCoursesInFoHelper(workNum, name);
            editFindProfessor(professorListManager.find(workNum,name,professorListManager.getList()));
        } catch (ListEmptyException e) {
            System.out.println("List is empty");
        } catch (CantFindException e) {
            System.out.println("Cant find the course");
        } finally {
            System.out.println("------------------------");
        }

    }

    private static void showAllProfessors() {
        try {
            showAllProfessorsHelper();
        } catch (ListEmptyException e) {
            System.out.println("List is empty");
        } finally {
            System.out.println("---------------------------");
        }

    }

    private static void showAllProfessorsHelper() throws ListEmptyException {
        if (!professorListManager.getList().isEmpty()) {
            for (Professor professor : professorListManager.getList()) {
                System.out.println("Name:" + professor.getProfessorName());
                System.out.println("Work Number:" + professor.getWorkNumber());
                System.out.println("Department:" + professor.getDepartment());
                for (Course course : professor.getCourses()) {
                    System.out.println("Course Name:" + course.getCourseName());
                    System.out.println("Work Number: " + course.getCourseNum());
                }
                System.out.println("\n");
            }
        } else {
            throw new ListEmptyException();
        }

    }

    private static void addProfessorInPList() {
        System.out.println("Please enter Professor's name.");
        String professorName = scanner.next();
        System.out.println("Please enter Work number");
        int workNumber = scanner.nextInt();
        System.out.println("Please enter department");
        String department = scanner.next();
        Professor professor = new Professor(professorName,department,workNumber);
        professorListManager.add(professor,professorListManager.getList());
    }


    private static void showCoursesInFoHelper(int workNum, String name)
            throws ListEmptyException, CantFindException {
        for (Course course : professorListManager.find(workNum,name,professorListManager.getList()).getCourses()) {
            System.out.println("Course Name: " + course.getCourseName());
            System.out.println("Course Number: " + course.getCourseNum());
        }
    }

    private static void editFindProfessorHelper() {
        System.out.println("Edit this professor information?");
        System.out.println("1.Add course");
        System.out.println("2.Change Name");
        System.out.println("3.Change Work Number");
        System.out.println("4.Change Department");
        System.out.println("5.No");
    }

    private static void editFindProfessor(Professor professor) {
        editFindProfessorHelper();
        String choice = scanner.next();
        switch (choice) {
            case "1": editProfessorCourse(professor);
                break;
            case "2": editProfessorName(professor);
                break;
            case "3": editWorkNumber(professor);
                break;
            case "4": editDepartment(professor);
                break;
            case"5":professorListManager.save(professorListManager.getList(),"Professor List","Course List");
                saveAll();
                System.exit(0);
                break;
            default: System.out.println("Finish");
        }
    }

    private static void editDepartment(Professor professor) {
        System.out.println("New Department name:");
        String name = scanner.next();
        professor.setDepartment(name);
    }

    private static void editWorkNumber(Professor professor) {
        System.out.println("New Work name:");
        int num = scanner.nextInt();
        professor.setWorkNumber(num);
    }

    private static void editProfessorName(Professor professor) {
        System.out.println("New name:");
        String name = scanner.next();
        professor.setName(name);
    }

    private static void editProfessorCourse(Professor professor) {
        System.out.println("Please input Course Name");
        String name = scanner.next();
        System.out.println("Please input Course number");
        int number = scanner.nextInt();
        Course course = new Course(name,number);
        professor.addCourse(course);
        Main.courseListManager.add(course, Main.courseListManager.getList());
    }




    private static void courseHint() {
        System.out.println("1. Add Course");
        System.out.println("2. See all the Courses' information");
        System.out.println("3. Delete a Course");
        System.out.println("4. Find a Course");
        System.out.println("5. Close the system");
        String choice = scanner.next();
        courseHelper(choice);
    }

    private static void courseHelper(String choice) {
        switch (choice) {
            case "1": addCourseInCourseList();
                break;
            case "2": showAllCourses();
                break;
            case "3": deleteCourseInCourseList();
                break;
            case "4": findCourse();
                break;
            case "5": courseListManager.save(courseListManager.getList(),"Course List","Professor List");
                saveAll();
                System.exit(0);
                break;
            default:
                System.out.println("wrong choice");
        }

    }

    private static void deleteCourseInCourseList() {
        try {
            System.out.println("Please enter Course name");
            String name = scanner.next();
            System.out.println("Please enter Course number");
            int number = scanner.nextInt();
            courseListManager.delete(name,number, courseListManager.getList());
        } catch (ListEmptyException e) {
            System.out.println("Remove failing!!!");
        } catch (CantFindException e) {
            System.out.println("Cant find the Course!");
        } finally {
            System.out.println("---------------------------");
        }

    }

    private static void findCourse() {
        System.out.println("please input Course Number");
        int courseNum = scanner.nextInt();
        System.out.println("please input Course Name");
        String name = scanner.next();
        try {
            System.out.println("Course Name: "
                    + courseListManager.find(name,courseNum).getCourseName());
            System.out.println("Course Number " + courseListManager.find(name,courseNum).getCourseNum());
            showProfessorInFoHelper(name,courseNum);
            editFindCourse(courseListManager.find(name,courseNum));
        } catch (ListEmptyException e) {
            System.out.println("List is empty");
        } catch (CantFindException e) {
            System.out.println("Cant find the course");
        } finally {
            System.out.println("------------------------");
        }

    }

    private static void showProfessorInFoHelper(String name, int courseNum)
            throws ListEmptyException, CantFindException {
        for (Professor professor : courseListManager.find(name, courseNum).getProfessor()) {
            System.out.println("Professor Name: " + professor.getProfessorName());
            System.out.println("Work Number: " + professor.getWorkNumber());
            System.out.println("Department: " + professor.getProfessorDepartment());
        }
    }

    private static void editFindCourse(Course course) {
        System.out.println("Edit this course information?");
        System.out.println("1.Add professor");
        System.out.println("2.Change Name");
        System.out.println("3.Change Number");
        System.out.println("4.No");
        String choice = scanner.next();
        switch (choice) {
            case "1": editCourseProfessor(course);
                break;
            case "2":
                editCourseName(course);
                break;
            case "3":
                editCourseNumber(course);
                break;
            default:
                System.out.println("Finish");
        }
    }

    private static void editCourseNumber(Course course) {
        System.out.println("New course name:");
        String name = scanner.next();
        course.setCourseName(name);
    }

    private static void editCourseName(Course course) {
        System.out.println("New course number:");
        int number = scanner.nextInt();
        course.setCourseNum(number);
    }

    private static void editCourseProfessor(Course course) {
        System.out.println("Please input Professor Name");
        String name = scanner.next();
        System.out.println("Please input Professor Work number");
        int number = scanner.nextInt();
        System.out.println("Please input Professor's department");
        String depart = scanner.next();
        Professor professor = new Professor(name,depart,number);
        course.addProfessor(professor);
        professorListManager.add(professor,professorListManager.getList());
    }




    private static void addCourseInCourseList() {
        System.out.println("Please enter Course name.");
        String courseName = scanner.next();
        System.out.println("Please enter Course number");
        int courseNum = scanner.nextInt();
        Course course = new Course(courseName,courseNum);
        courseListManager.getList().add(course);
    }

    private static void showAllCourses() {
        try {
            showAllCoursesHelper();
        } catch (ListEmptyException e) {
            System.out.println("List is empty");
        } finally {
            System.out.println("---------------------------");
        }

    }


    private static void showAllCoursesHelper() throws ListEmptyException {
        if (!courseListManager.getList().isEmpty()) {
            for (Course course : courseListManager.getList()) {
                System.out.println("Name:" + course.getCourseName());
                System.out.println("Course Number:" + course.getCourseNum());
                for (Professor professor : course.getProfessor()) {
                    System.out.println("Professor Name:" + professor.getProfessorName());
                    System.out.println("Work Number: " + professor.getWorkNumber());
                    System.out.println("Department:" + professor.getProfessorDepartment());
                }
                System.out.println("\n");
            }
        } else {
            throw new ListEmptyException();
        }
    }


    private static void removeStudent() {
        try {
            System.out.println("Please enter student name");
            String name = scanner.next();
            System.out.println("Please enter student number");
            int number = scanner.nextInt();
            studentListManager.delete(name, number, studentListManager.getList());
        } catch (ListEmptyException e) {
            System.out.println("Remove failing!!!");
        } catch (CantFindException e) {
            System.out.println("Cant find the student!");
        } finally {
            System.out.println("---------------------------");
        }
    }



    private static void addStudent() {
        System.out.println("Add 1.Undergraduated student 2. Graduated Student");
        String choice = scanner.next();
        switch (choice) {
            case "1": addUnderGraduated();
            break;
            case "2": addGraduated();
            break;
            default:
                System.out.println("Wrong choice!");
        }
    }

    //MODIFIED:StudentList
    //EFFECT: Add UnderGraduated student in student list
    private static void addUnderGraduated() {
        System.out.println("Please enter student name.");
        String studentName = scanner.next();
        System.out.println("Please enter student number");
        int studentNumber = scanner.nextInt();
        System.out.println("Please enter student age");
        int studentAge = scanner.nextInt();
        try {
            Student st = new UnderGraduated(studentName, studentNumber, studentAge);
            studentListManager.checkAge(st);
            System.out.println("Add Course");
            addCourse(st);
            studentListManager.add(st,studentListManager.getList());
        } catch (AgeOutOfRangeException e) {
            System.out.println("This student is too old.");
        }
    }

    //MODIFIED:StudentList
    //EFFECT: Add Graduated student in student list
    private static void addGraduated() {
        System.out.println("Please enter student name.");
        String studentName = scanner.next();
        System.out.println("Please enter student number");
        int studentNumber = scanner.nextInt();
        System.out.println("Please enter student age");
        int studentAge = scanner.nextInt();
        try {
            Student st = new Graduated(studentName, studentNumber, studentAge);
            studentListManager.checkAge(st);
            System.out.println("Add Course");
            addCourse(st);
            studentListManager.add(st,studentListManager.getList());
        } catch (AgeOutOfRangeException e) {
            System.out.println("This student is too old.");
        }
    }



    //EFFECT: output all the student information in the list that in the file
    private static void showAllStudent() {
        try {
            showAllStudentHelper();
        } catch (ListEmptyException e) {
            System.out.println("List is empty");
        } finally {
            System.out.println("---------------------------");
        }
    }

    private static void showAllStudentHelper() throws ListEmptyException {
        if (!studentListManager.getList().isEmpty()) {
            for (Student student : studentListManager.getList()) {
                System.out.println("Name:" + student.getStudentName());
                System.out.println("Age:" + student.getAge());
                System.out.println("Student Number:" + student.getStudentNumber());
                System.out.println("Status:" + student.getStatus());
                for (Course course : student.getCourses()) {
                    System.out.println("Course Name: " + course.getCourseName());
                    System.out.println("Course Number: " + course.getCourseNum());
                }
                System.out.println("\n");
            }
        } else {
            throw new ListEmptyException();
        }
    }

    //EFFECT: user choose which kind of student they want to find
    private static void showOnlyOneChoice() {
        String choice = scanner.next();
        try {
            showGorUStudent(choice);
        } catch (ListEmptyException e) {
            System.out.println("List is empty");
        } finally {
            System.out.println("----------------------");
        }
    }


    //EFFECT: show that kind of student the user want
    private static void showGorUStudent(String s) throws ListEmptyException {
        if (!studentListManager.getList().isEmpty()) {
            for (Student student : studentListManager.getList()) {
                if (s.equalsIgnoreCase(student.getStatus())) {
                    System.out.println("Name:" + student.getStudentName());
                    System.out.println("Age:" + student.getAge());
                    System.out.println("Student Number:" + student.getStudentNumber());
                }
            }
        } else {
            throw new ListEmptyException();
        }
    }

    //MODIFIED: Student
    //EFFECT: add courses for a student
    private static void addCourse(Student student) {
        boolean flag = true;
        while (flag) {
            System.out.println("Please enter course name.");
            String courseName = scanner.next();
            System.out.println("Please enter course number");
            int courseNumber = scanner.nextInt();
            try {
                student.addCourse(courseListManager.find(courseName,courseNumber));
            } catch (CantFindException | ListEmptyException e) {
                Course course = new Course(courseName, courseNumber);
                student.addCourse(course);
            }
            System.out.println("Keep adding?");
            int again = scanner.nextInt();
            if (again == 2) {
                flag = false;
            }
        }
    }


    private static void findStudent() {
        System.out.println("please input StudentNumber and Status");
        int studentNum = scanner.nextInt();
        String status = scanner.next();
        try {
            System.out.println("Student name: "
                    + studentListManager.find(studentNum, status, studentListManager.getList()).getStudentName());
            System.out.println("Age: " + studentListManager
                    .find(studentNum, status, studentListManager.getList()).getAge());
            for (Course course : studentListManager
                    .find(studentNum, status, studentListManager.getList()).getCourses()) {
                System.out.println("Course: " + course.getCourseName());
                System.out.println("Course Number: " + course.getCourseNum());
            }
        } catch (ListEmptyException e) {
            System.out.println("List is empty");
        } catch (CantFindException e) {
            System.out.println("Cant find the student");
        }
    }


    @Override
    public void start(Stage stage) throws Exception {

        Font.loadFont(getClass().getResource("SAORegular.ttf").toExternalForm(),14);
        stage.setTitle("Student Management System");
        // learn how to use JavaFx from Bucky (the best youtuber!!)
        // the way to change scene
        // from https://stackoverflow.com/questions/12804664/how-to-swap-screens-in-a-javafx-application-in-the-controller-class
        // getting loader and a pane for the menu.
        // loader will then give a possibility to get related controller

        Scene menuScene = setScene(menuLoader, 600, 500);

        // getting loader and a pane for the Student scene
//        FXMLLoader studentHintLoader = new FXMLLoader((getClass().getResource("StudentHint.fxml")));
        Scene studentHintScene = setScene(studentHintLoader, 600, 500);

        // getting loader and a pane for the ShowAllStudent scene
//        FXMLLoader showAllStudentLoader = new FXMLLoader((getClass().getResource("ShowAllStudent.fxml")));
        Scene showAllStudentScene = setScene(showAllStudentLoader, 1000, 800);

        // getting loader and a pane for the addStudent scene
//        FXMLLoader addStudentLoader = new FXMLLoader((getClass().getResource("AddStudent.fxml")));
        Scene addStudentScene = setScene(addStudentLoader, 600, 500);

        //getting loader and a pane for the Course Scene
//        FXMLLoader courseSceneLoader = new FXMLLoader((getClass().getResource("Allcourses.fxml")));
        Scene allCoursesScene = setScene(courseSceneLoader, 1000, 800);

        Scene allProfScene = setScene(profSceneLoader, 1000, 800);

        // injecting studentHint scene into the controller of the menu scene
        menuToOtherScene(menuLoader, studentHintScene, allCoursesScene, allProfScene);

        // injecting menu scene and all studentFunctionScenes into the controller of the studentHint scene
        studentHintToOtherScene(menuScene, showAllStudentScene, addStudentScene);

        // injecting StudentHint scene into the controller of the showAllStudent scene
        ShowAllStudentController showAllStudentController = showAllStudentToOtherScenes(studentHintScene);

        // injecting StudentHint scene into the controller of the showAllStudent scene
        addStudentToOtherScenes(studentHintScene, showAllStudentController);

        // injecting StudentHint scene into the controller of the showAllStudent scene
        allCoursesAndAllProfController(menuScene);


        stage.setScene(menuScene);
        stage.show();

    }

    private Scene setScene(FXMLLoader menuLoader, int i, int i2) throws IOException {
        Parent menu = menuLoader.load();
        return new Scene(menu, i, i2);
    }

    private void allCoursesAndAllProfController(Scene menuScene) {
        AllCoursesController allCoursesController = courseSceneLoader.getController();
        AllProfsController allProfsController = profSceneLoader.getController();

        allCoursesController.setMenuScene(menuScene);
        allCoursesController.addObserver(allProfsController);

        allProfsController.setMenuScene(menuScene);
        allProfsController.addObserver(allCoursesController);
    }

    private void addStudentToOtherScenes(Scene studentHintScene, ShowAllStudentController showAllStudentController) {
        AddStudentController addStudentController = addStudentLoader.getController();
        addStudentController.addObserver(showAllStudentController);
        addStudentController.setStudentHintScene(studentHintScene);
    }

    private ShowAllStudentController showAllStudentToOtherScenes(Scene studentHintScene) {
        ShowAllStudentController showAllStudentController = showAllStudentLoader.getController();
        showAllStudentController.setStudentHintScene(studentHintScene);
        return showAllStudentController;
    }

    private void studentHintToOtherScene(Scene menuScene, Scene showAllStudentScene, Scene addStudentScene) {
        StudentHintController studentHintController = studentHintLoader.getController();
        studentHintController.setMenuScene(menuScene);
        studentHintController.setShowAllStudent(showAllStudentScene);
        studentHintController.setAddStudentScene(addStudentScene);
    }

    private void menuToOtherScene(FXMLLoader menuLoader,
                                  Scene studentHintScene, Scene allCoursesScene, Scene allProfScene) {
        MenuController menuController = menuLoader.getController();
        menuController.setStudentHintScene(studentHintScene);
        menuController.setAllCoursesScene(allCoursesScene);
        menuController.setAllProfsScene(allProfScene);
    }


}



