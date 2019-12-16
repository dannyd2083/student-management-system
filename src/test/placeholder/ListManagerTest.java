package placeholder;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import placeholder.model.*;
import org.json.simple.*;


import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ListManagerTest {
    StudentListManager studentListManager;
    CourseListManager courseListManager;
    ProfessorListManager professorListManager;
    Student student;
    Student student1;
    Course course;
    Course course1;
    Professor professor;
    Professor professor1;
    Set courses;

    @BeforeEach
    public void runBefore () {
        studentListManager = new StudentListManager("Student.txt");
        professorListManager = new ProfessorListManager("Professor.txt");
        courseListManager = new CourseListManager("Course.txt");
        student = new UnderGraduated("Danny",12321,19);
        student1 = new Graduated("Cindy",341542,20);
        course = new Course("CPSC",112);
        course1 = new Course("Math",222);
        professor = new Professor("Wendy","Math",2222);
        professor1 = new Professor("Han","Art",2432);
        courses = new HashSet<Course>();
        courses.add(course);
    }

    @Test
    public void testConstructor() {
        assertEquals (0, courseListManager.getList().size());
        assertEquals (0, studentListManager.getList().size());
        assertEquals (0, professorListManager.getList().size());
    }

    @Test
    public void testAddStudent() {
        studentListManager.add(student,studentListManager.getList());
        assertTrue(studentListManager.getList().contains(student));
    }
    @Test
    public void testAddCourse() {
        courseListManager.add(course,courseListManager.getList());
        assertTrue(courseListManager.getList().contains(course));
    }

    @Test
    public void testAddProfessor() {
        professorListManager.add(professor,professorListManager.getList());
        assertTrue(professorListManager.getList().contains(professor));
    }

    @Test
    public void testSavAndLoadForStudentList() {
        studentListManager.add(student,studentListManager.getList());
        studentListManager.add(student1,studentListManager.getList());
        studentListManager.save(studentListManager.getList(),"Student List","Course List");
        StudentListManager newLoadList = new StudentListManager("Student.txt");
        newLoadList.add(student,newLoadList.getList());
        newLoadList.add(student1,newLoadList.getList());
        newLoadList.load();
        assertTrue(newLoadList.getList().equals(studentListManager.getList()));
    }


    @Test
    public void testSavAndLoadForCourseList() {
       courseListManager.add(course,courseListManager.getList());
       courseListManager.add(course1,courseListManager.getList());
       courseListManager.save(courseListManager.getList(),"Course List","Professor List");
       CourseListManager newLoadList = new CourseListManager("Course.txt");
       newLoadList.add(course,newLoadList.getList());
       newLoadList.add(course1,newLoadList.getList());
       newLoadList.load();
       assertTrue(newLoadList.getList().equals(courseListManager.getList()));
    }

    @Test
    public void testSavAndLoadForPList() {
        professorListManager.add(professor,professorListManager.getList());
        professorListManager.add(professor1,professorListManager.getList());
        professorListManager.save(professorListManager.getList(),"Professor List","Course List");
        ProfessorListManager newLoadList = new ProfessorListManager("Professor.txt");
        newLoadList.add(professor,newLoadList.getList());
        newLoadList.add(professor1,newLoadList.getList());
        newLoadList.load();
        assertTrue(newLoadList.getList().equals(professorListManager.getList()));
    }



    @Test
    public void FirstSaveHelperForStudentTest() {
        JSONObject jsonObject = new JSONObject();
        studentListManager.firstSaveHelper(jsonObject,student);
        assertEquals("{\"Status\":\"undergraduate\",\"Student Number\":12321,\"Age\":19,\"Name\":\"Danny\"}",
                jsonObject.toJSONString());
    }

    @Test
    public void FirstSaveHelperForCourseListTest() {
        JSONObject jsonObject = new JSONObject();
        courseListManager.firstSaveHelper(jsonObject,course);
        assertEquals("{\"Course Number\":112,\"Name\":\"CPSC\"}",
                jsonObject.toJSONString());
    }


    @Test
    public void FirstSaveHelperForProfessorListTest() {
        JSONObject jsonObject = new JSONObject();
        professorListManager.firstSaveHelper(jsonObject,professor);
        assertEquals("{\"Department\":\"Math\",\"Work Number\":2222,\"Name\":\"Wendy\"}",
                jsonObject.toJSONString());
    }



    @Test
    void SecondSaveHelperForStudentListTest() {
        JSONArray jsonArray = new JSONArray();
        student.addCourse(course);
        student.addCourse(course1);
        studentListManager.secondSaveHelper(student, jsonArray);
        assertEquals("[{\"Course Number\":112,\"Course Name\":\"CPSC\"},{\"Course Number\":222,\"Course Name\":\"Math\"}]",
                jsonArray.toJSONString());
    }

    @Test
    void SecondSaveHelperForCourseListTest() {
        JSONArray jsonArray = new JSONArray();
        course.addProfessor(professor);
        course.addProfessor(professor1);
        courseListManager.secondSaveHelper(course, jsonArray);
        assertEquals("[{\"Department\":\"Math\",\"Work Number\":2222,\"Professor Name\":\"Wendy\"}," +
                        "{\"Department\":\"Art\",\"Work Number\":2432,\"Professor Name\":\"Han\"}]",
                jsonArray.toJSONString());
    }

    @Test
    void SecondSaveHelperForProfessorListTest() {
        JSONArray jsonArray = new JSONArray();
        professor.addCourse(course);
        professor.addCourse(course1);
        professorListManager.secondSaveHelper(professor, jsonArray);
        assertEquals("[{\"Course Number\":112,\"Course Name\":\"CPSC\"},{\"Course Number\":222,\"Course Name\":\"Math\"}]",
                jsonArray.toJSONString());
    }


    @Test
    void checkAge() {
        try {
            studentListManager.checkAge(new UnderGraduated("Big One", 1234, 900));
            fail("didn't catch");
        } catch (AgeOutOfRangeException e) {
            System.out.println("pass");
        }
        ;
    }


//    @Test
//    void loadCourseHelperTest() {
//        JSONArray jsonArray = new JSONArray();
//        Set<Course> courses = new HashSet<>();
//        courses.add(course);
//        courses.add(course1);
//        for (Course course : courses ) {
//            JSONObject objc = new JSONObject();
//            objc.put("Course Name", course.getCourseName());
//            objc.put("Course Number", course.getCourseNum());
//            jsonArray.add(objc);
//        }
//        Set<Course> loadCourses = new HashSet<>();
//       studentList.loadCourseHelper(jsonArray,loadCourses);
//
//    }


    @Test
    void studentLoadHelperTest() throws ListEmptyException, CantFindException {
        Set <Course> studentCourses = new HashSet<>();
        studentCourses.add(course1);
        studentListManager.loadHelper("Danny", "undergraduate",22,1234,studentCourses);
        assertEquals("Danny",studentListManager.find(1234,"",studentListManager.getList()).getStudentName());
        assertEquals("undergraduate",studentListManager.find(1234,"",studentListManager.getList()).getStatus());
        studentListManager.loadHelper("Cindy", "graduated",22,2222,studentCourses);
        for(Student student : studentListManager.getList()) {
           System.out.println(student.getStudentName());
           System.out.println(student.getStudentNumber());
           System.out.println(student.getStatus());
       }

    }



    @Test
    void professorListLoadProfHelperTest() throws ListEmptyException, CantFindException {
        professorListManager.loadProfessorHelper("Danny","CPSC",1234,courses);
        assertTrue(professorListManager.find(1234,"Danny",professorListManager.getList()).getCourses().equals(courses));
    }

//    @Test
//    void professorListLoadCoursesHelperTest() {
//        JSONArray jsonArray = new JSONArray();
//        JSONObject objc = new JSONObject();
//        objc.put("Course Name", "CPSC");
//        objc.put("Course Number", 112);
//        jsonArray.add(objc);
//        Set<Course> courses = new HashSet<>();
//        professorListManager.loadCoursesHelper(jsonArray,courses);
//        assertEquals(1,courses.size());
//    }


    @Test
    void professorListDeleteConditionTest() {
        assertFalse(professorListManager.deleteCondition(professor,"Shawn",1234));
        assertFalse(professorListManager.deleteCondition(professor,"Wendy",1234));
        assertFalse(professorListManager.deleteCondition(professor,"Shawn",1234));
        assertTrue(professorListManager.deleteCondition(professor,"Wendy",2222));
    }

    @Test
    void professorListFindConditionTest() {
        assertFalse(professorListManager.findCondition(professor,21123,"Wendy"));
        assertFalse(professorListManager.findCondition(professor,21123,"Shawn"));
        assertFalse(professorListManager.findCondition(professor,2222,"Cindy"));
        assertTrue(professorListManager.deleteCondition(professor,"Wendy",2222));
    }


    @Test
    public void testDeleteStudentListEmptyException() {
        try {
            studentListManager.delete("Danny", 123435,studentListManager.getList());
            fail("");
        } catch (ListEmptyException e) {
            System.out.println("pass");
        } catch (CantFindException e) {
            fail();
        }
    }

    @Test
    public void testDeleteStudentListCantFindListException() {
        try {
            Student student = new UnderGraduated("Jon snow",12345,22);
            studentListManager.add(student,studentListManager.getList());
            studentListManager.delete("Danny", 123435,studentListManager.getList());
            fail("");
        } catch (ListEmptyException e) {
            fail();
        } catch (CantFindException e) {
            System.out.println("pass");
        }
    }

    @Test
    public void testFindStudent()throws ListEmptyException,CantFindException {
        studentListManager.add(student,studentListManager.getList());
        assertTrue("Danny".equals(studentListManager.find(12321,"",studentListManager.getList()).getStudentName()));
    }


    @Test
    public void testFindStudentListEmptyException() {
        try {
            studentListManager.find( 123435,"",studentListManager.getList());
            fail("");
        } catch (ListEmptyException e) {
            System.out.println("pass");
        } catch (CantFindException e) {
            fail();
        }
    }


    @Test
    public void testFindStudentListCantFindListException() {
        try {
            Student student = new UnderGraduated("Jon snow",12345,22);
            studentListManager.add(student,studentListManager.getList());
            studentListManager.find( 123435,"",studentListManager.getList());
            fail("");
        } catch (ListEmptyException e) {
            fail();
        } catch (CantFindException e) {
            System.out.println("pass");
        }
    }


    @Test
    public void testFindCourseList() throws ListEmptyException, CantFindException {
        courseListManager.add(course,courseListManager.getList());
        assertTrue("CPSC".equals(courseListManager.find("CPSC",112).getCourseName()));
    }

    @Test
    public void testFindCourseListEmptyException() {
        try {
            courseListManager.find("CPSC",112);
            fail("");
        } catch (ListEmptyException e) {
            System.out.println("pass");
        } catch (CantFindException e) {
            fail();
        }
    }


    @Test
    public void testFindCourseListCantFindListException() {
        try {
            courseListManager.add(course,courseListManager.getList());
            courseListManager.find("WUlala" ,1234);
            fail("");
        } catch (ListEmptyException e) {
            fail();
        } catch (CantFindException e) {
            System.out.println("pass");
        }
    }


    @Test
    void CourseListDeleteConditionTest() {
        assertFalse(courseListManager.deleteCondition(course,"Shawn",1234));
        assertFalse(courseListManager.deleteCondition(course,"CPSC",1234));
        assertFalse(courseListManager.deleteCondition(course,"Shawn",112));
        assertTrue(courseListManager.deleteCondition(course,"CPSC",112));
    }

    @Test
    void CourseListFindConditionTest() {
        assertFalse(courseListManager.findCondition(course,21123,"Wendy"));
        assertFalse(courseListManager.findCondition(course,21123,"Shawn"));
        assertFalse(courseListManager.findCondition(course,2222,"Cindy"));
        assertTrue(courseListManager.deleteCondition(course,"Wendy",2222));
    }

}



