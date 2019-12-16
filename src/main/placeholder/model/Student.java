package placeholder.model;

import java.io.Serializable;
import java.util.*;

public abstract class Student implements Serializable {

    protected int age;
    protected int studentNumber;
    protected String studentName;
    protected Set<Course> courses;
    public String status;


    public Student(String name, int number, int age) {
        this.studentName = name;
        this.studentNumber = number;
        this.age = age;
        this.courses = new HashSet<>();
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // EFFECTS: returns the student number
    public int getStudentNumber() {
        return studentNumber;
    }

    // EFFECTS: returns the student name
    public String getStudentName() {
        return studentName;
    }

    // EFFECTS: returns the student number
    public int getAge() {
        return age;
    }

    // EFFECTS: return the student status.
    public String getStatus() {
        return status;
    }

    // EFFECTS: return the courses set
    public Set<Course> getCourses() {
        return courses;
    }

    //MODIFIED: courses
    //EFFECTS: set the set of courses
    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }


    //MODIFIED: courses
    //EFFECT:add a course in the set of course
    public void addCourse(Course c) {
        courses.add(c);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return studentNumber == student.studentNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber);
    }




}
