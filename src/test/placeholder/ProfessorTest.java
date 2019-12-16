package placeholder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import placeholder.model.CantFindException;
import placeholder.model.Course;
import placeholder.model.ListEmptyException;
import placeholder.model.Professor;

import static org.junit.jupiter.api.Assertions.*;

public class ProfessorTest {
    Course course;
    Professor professor;

    @BeforeEach
    public void runBefore () {
        course = new Course("CPSC",110);
        professor = new Professor("Cindy","CS",1234);
    }

    @Test
    public void testConstructor() {
        assertEquals("Cindy", professor.getProfessorName());
        assertEquals("CS", professor.getProfessorDepartment());
    }

    @Test
    //this is from OverFlow https://stackoverflow.com/questions/4449728/how-can-i-do-unit-test-for-hashcode
    public void testEquals_Symmetric() {
        Professor x = new Professor("Peter","Math",2200);  // equals and hashCode check name field value
        Professor y = new Professor("Peter", "Math",2200);
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }

    @Test
    public void testAddCourse() {
        professor.addCourse(course);
        assertTrue(professor.getCourses().size()==1);
    }

    @Test
    void testRemoveCourse() throws ListEmptyException, CantFindException {
        professor.addCourse(course);
        assertTrue(professor.getCourses().contains(course));
        assertTrue(course.getProfessor().contains(professor));
        professor.removeCourse("CPSC", 110);
        assertFalse(course.getProfessor().contains(professor));
        assertFalse(professor.getCourses().contains(course));

    }

    @Test
    void testRemoveCourseEmptyException() throws ListEmptyException, CantFindException {
        try {
            professor.removeCourse("CPSC",110);
            fail("didn't throw exception");
        } catch (ListEmptyException e) {
            System.out.println("pass");
        } catch (CantFindException e) {
            fail("wrong one");
        }
    }

    @Test
    void testRemoveCourseCantFindException() throws ListEmptyException, CantFindException {
        try {
            professor.addCourse(course);
            professor.removeCourse("AAAAAAA",3938);
            fail("didn't throw exception");
        } catch (ListEmptyException e) {
            fail("wrong one");
        } catch (CantFindException e) {
            System.out.println("pass");
        }
    }



}
