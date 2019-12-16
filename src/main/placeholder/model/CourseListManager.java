package placeholder.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CourseListManager extends ListManager<Course> {

    private static Set courses;


    public CourseListManager(String filename) {
        super(filename);
        this.courses = new HashSet();
    }


    public Set<Course> getList() {
        return courses;
    }



    //MODIFIES: this
    //EFFECTS: save each course object in the course list
    @Override
    public void firstSaveHelper(JSONObject objt, Course course) {
        objt.put("Name", course.getCourseName());
        objt.put("Course Number", course.getCourseNum());
    }

    //MODIFIES: this
    //EFFECTS: Save professor list for each course
    @Override
    public void secondSaveHelper(Course course, JSONArray professors) {
        for (Professor professor : course.getProfessor()) {
            JSONObject objc = new JSONObject();
            objc.put("Professor Name", professor.getProfessorName());
            objc.put("Work Number", professor.getWorkNumber());
            objc.put("Department",professor.getProfessorDepartment());
            professors.add(objc);
        }


    }

    //MODIFIES: this
    //EFFECTS: load all data in the file into the course list
    @Override
    public void load() {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("Course.txt"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("Course List");
            for (Object jsonCourse : jsonArray) {
                JSONObject object = (JSONObject) jsonCourse;
                String courseName = (String) object.get("Name");
                int courseNum = Math.toIntExact((long) object.get("Course Number"));
                Set<Professor> courseProfessor = new HashSet<>();
                JSONArray professors = (JSONArray) object.get("Professor List");
                loadProfessorsHelper(professors, courseProfessor);
                Course course = new Course(courseName, courseNum);
                course.setProfessors(courseProfessor);
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: load each course object's professor list
    public void loadProfessorsHelper(JSONArray professors, Set courseProfessor) {
        for (Object jsonProfessor : professors) {
            JSONObject jsonObject1 = (JSONObject) jsonProfessor;
            String professorName = (String) jsonObject1.get("Professor Name");
            int workNum = Math.toIntExact((long) jsonObject1.get("Work Number"));
            String department = (String) jsonObject1.get("Department");
            Professor professor = new Professor(professorName, department, workNum);
            courseProfessor.add(professor);
        }

    }



    //MODIFIES: this
    //EFFECTS: return true if the pass in parameter is correct otherwise return false
    @Override
    public boolean deleteCondition(Course course, String n, int x) {
        if (course.getCourseName().equalsIgnoreCase(n) && course.getCourseNum() == x) {
            return true;
        }
        return  false;
    }



    //MODIFIES: this
    //EFFECTS: return the corresponding course by the pass in parameter
    public Course find(String name, int id) throws ListEmptyException, CantFindException {
        if (courses.isEmpty()) {
            throw  new  ListEmptyException();
        }
        Iterator<Course> iterator = courses.iterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            if (findCondition(course,id,name)) {
                return course;
            }
        }
        throw new CantFindException();
    }


    //MODIFIES: this
    //EFFECTS: return true if course id and name is correct return false otherwise
    public boolean findCondition(Course course, int id, String name) {
        if (course.getCourseNum() == id && course.getCourseName().equalsIgnoreCase(name)) {
            return true;
        }
        return false;
    }



}
