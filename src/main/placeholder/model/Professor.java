package placeholder.model;

import java.io.Serializable;
import java.util.*;

public class Professor {
    private String name;
    private String department;
    private int workNumber;
    private Set<Course> courses;

    public Professor(String name, String department,int workNumber) {
        this.name = name;
        this.department = department;
        this.workNumber = workNumber;
        this.courses = new HashSet<>();
    }


    // EFFECTS: returns the student name
    public String getProfessorName() {
        return name; //stub
    }

    // EFFECTS: returns the student number
    public String getProfessorDepartment() {
        return department; //stub
    }

    public int getWorkNumber() {
        return workNumber;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }


    public Set<Course> getCourses() {
        return courses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setWorkNumber(int workNumber) {
        this.workNumber = workNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Professor professor = (Professor) o;
        return workNumber == professor.workNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(workNumber);
    }

    //Modified: courses
    //EFFECTS: add course that the professor teaches
    public void addCourse(Course c) {
        if (!courses.contains(c)) {
            courses.add(c);
            c.addProfessor(this);
        }
    }


    //MODIFIES: this
    //EFFECTS: remove a course from the course list
    public void removeCourse(String courseName, int courseNum) throws ListEmptyException, CantFindException {
        if (courses.isEmpty()) {
            throw new ListEmptyException();
        }
        for (Course course : courses) {
            if (course.getCourseName().equalsIgnoreCase(courseName) && course.getCourseNum() == courseNum) {
                courses.remove(course);
                course.getProfessor().remove(this);
            } else {
                throw new CantFindException();
            }
        }
    }

}
