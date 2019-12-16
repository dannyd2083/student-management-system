package placeholder;

import placeholder.model.CantFindException;
import placeholder.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import placeholder.model.ListEmptyException;
import placeholder.model.Professor;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    Course course;
    Professor professor;

    @BeforeEach
    public void runBefore () {
        course = new Course("CPSC",110);
        professor = new Professor("Cindy","CS",2222);
    }

    @Test
    public void testConstructor() {
        assertEquals("CPSC", course.getCourseName());
        assertEquals(110, course.getCourseNum());
    }

    @Test
    //this is from OverFlow https://stackoverflow.com/questions/4449728/how-can-i-do-unit-test-for-hashcode
    public void testEquals_Symmetric() {
        Course x = new Course("Math",200);  // equals and hashCode check name field value
        Course y = new Course("Math", 200);
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }


    @Test
    public void testAddProfessor() {
        course.addProfessor(professor);
        assertTrue(course.getProfessor().size()==1);
        assertTrue(professor.getCourses().contains(course));
    }

    @Test
    void testRemoveProfess() throws ListEmptyException, CantFindException {
        course.addProfessor(professor);
        assertTrue(course.getProfessor().size()==1);
        assertTrue(professor.getCourses().contains(course));
        course.removeProfessor(2222);
        assertFalse(course.getProfessor().contains(professor));
        assertFalse(professor.getCourses().contains(course));

    }

    @Test
    void testRemoveProfessEmptyException() throws ListEmptyException, CantFindException {
        try {
            course.removeProfessor(2222);
            fail("didn't throw exception");
        } catch (ListEmptyException e) {
            System.out.println("pass");
        } catch (CantFindException e) {
            fail("wrong one");
        }
    }

    @Test
    void testRemoveProfessCantFindException() throws ListEmptyException, CantFindException {
        try {
            course.addProfessor(professor);
            course.removeProfessor(2234);
            fail("didn't throw exception");
        } catch (ListEmptyException e) {
            fail("wrong one");
        } catch (CantFindException e) {
            System.out.println("pass");
        }
    }




}
