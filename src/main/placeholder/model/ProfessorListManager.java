package placeholder.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class ProfessorListManager extends ListManager<Professor> {

    private static Set<Professor> professors;

    public ProfessorListManager(String filename) {
        super(filename);
        professors = new HashSet<>();
    }

    public static Set<Professor> getList() {
        return professors;
    }



    //MODIFIES: this
    //EFFECTS: save each prof object in the course list

    @Override
    public void firstSaveHelper(JSONObject objt, Professor professor) {
        objt.put("Name", professor.getProfessorName());
        objt.put("Work Number", professor.getWorkNumber());
        objt.put("Department", professor.getProfessorDepartment());
    }

    //MODIFIES: this
    //EFFECTS: Save course list for each prof
    @Override
    public void secondSaveHelper(Professor professor, JSONArray courses) {
        for (Course course : professor.getCourses()) {
            JSONObject objc = new JSONObject();
            objc.put("Course Name", course.getCourseName());
            objc.put("Course Number", course.getCourseNum());
            courses.add(objc);
        }

    }




    //MODIFIES: this
    //EFFECTS: load all data in the file into the professor list
    @Override
    public void load() {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("Professor.txt"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("Professor List");
            for (Object jsonProfessor : jsonArray) {
                JSONObject object = (JSONObject) jsonProfessor;
                String professorName = (String) object.get("Name");
                int workNum = Math.toIntExact((long) object.get("Work Number"));
                String department = (String) object.get("Department");
                Set<Course> pcourses = new HashSet<>();
                JSONArray courses = (JSONArray) object.get("Course List");
                loadCoursesHelper(courses, pcourses);
                loadProfessorHelper(professorName,department,workNum,pcourses);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    //MODIFIES: this
    //EFFECTS: load each prof object in the prof list
    public void loadProfessorHelper(String professorName, String department, int workNum,Set s) {
        Professor professor = new Professor(professorName,department,workNum);
        professor.setCourses(s);
        professors.add(professor);
    }




    //MODIFIES: this
    //EFFECTS: load each professor course  list
    public void loadCoursesHelper(JSONArray courses, Set pcourses) {
        for (Object jsonCourse : courses) {
            JSONObject jsonObject1 = (JSONObject) jsonCourse;
            String courseName = (String) jsonObject1.get("Course Name");
            int courseNum = Math.toIntExact((long) jsonObject1.get("Course Number"));
            Course course = new Course(courseName,courseNum);
            pcourses.add(course);
        }

    }

    //MODIFIES: this
    //EFFECTS: return true if the pass in parameter is correct otherwise return false
    @Override
    public boolean deleteCondition(Professor professor, String n, int x) {
        if (professor.getProfessorName().equalsIgnoreCase(n) && professor.getWorkNumber() == x) {
            return true;
        }
        return false;
    }


    //MODIFIES: this
    //EFFECTS: return true if work id and name is correct return false otherwise
    @Override
    public boolean findCondition(Professor professor, int id, String name) {
        if (professor.getProfessorName().equalsIgnoreCase(name) && professor.getWorkNumber() == id) {
            return true;
        }
        return false;
    }

}