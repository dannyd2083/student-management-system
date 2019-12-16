package placeholder.model;

import java.io.Serializable;
import java.util.*;

public class Course implements Serializable {
    private String courseName;
    private int courseNum;
    Set<Professor> professors;

    public Course(String courseName, int courseNum) {
        this.courseName = courseName;
        this.courseNum = courseNum;
        this.professors = new HashSet<>();
    }


    public String getCourseName() {
        return courseName;
    }


    public int getCourseNum() {
        return courseNum;
    }


    public Set<Professor> getProfessor() {
        return professors;
    }

    public void setProfessors(Set<Professor> professors) {
        this.professors = professors;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseNum(int courseNum) {
        this.courseNum = courseNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        return courseNum == course.courseNum
                && Objects.equals(courseName, course.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, courseNum);
    }


    //REQUIRES
    //MODIFIES: this
    //EFFECTS: add a new Professor in to the professor list
    public void addProfessor(Professor p) {
        if (!professors.contains(p)) {
            professors.add(p);
            p.addCourse(this);
        }
    }


    //MODIFIES:this
    //EFFECTS: remove a new Professor in to the professor list
    public void removeProfessor(int workNum) throws ListEmptyException, CantFindException {
        if (professors.isEmpty()) {
            throw new ListEmptyException();
        }
        for (Professor professor: professors) {
            if (professor.getWorkNumber() == workNum) {
                professors.remove(professor);
                professor.getCourses().remove(this);
            } else {
                throw new CantFindException();
            }
        }
    }

}
