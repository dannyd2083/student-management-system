package placeholder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import placeholder.model.Course;
import placeholder.model.Graduated;
import placeholder.model.Student;
import placeholder.model.UnderGraduated;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTest {

    Student student;
    Student student1;
    Course course;

    @BeforeEach
    public void runBefore () {
       student = new UnderGraduated("Peter",110,22);
       student1 = new Graduated("Cindy",990,11);
       course = new Course("CPSC", 110);
    }


    @Test
    public void testConstructor(){
        assertEquals("Peter",student.getStudentName());
        assertEquals("Cindy",student1.getStudentName());
        assertEquals(110,student.getStudentNumber());
        assertEquals(990,student1.getStudentNumber());
        assertEquals(22,student.getAge());
        assertEquals(11,student1.getAge());
    }

    @Test
    void testAddCourse() {
        student.addCourse(course);
        student.getCourses().contains(course);
    }


    @Test
    //this is from OverFlow https://stackoverflow.com/questions/4449728/how-can-i-do-unit-test-for-hashcode
    public void testEquals_Symmetric() {
        Student x = new UnderGraduated("Peter",1234,22);  // equals and hashCode check name field value
        Student y = new UnderGraduated("Monday", 1234,22);
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}
