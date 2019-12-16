package placeholder.model;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.*;


public class StudentListManager extends ListManager<Student> {

    private static Set<Student> students;

    public StudentListManager(String filename) {
        super(filename);
        students = new HashSet<>();
    }


    //EFFECTS: get studentList
    public Set<Student> getList() {
        return students;
    }


    //EFFECT: record the list into the file
//    @Override
//    public void save() {
//        try {
//            FileWriter fileWriter = new FileWriter(file.getName());
//            JSONObject obj = new JSONObject();
//            JSONArray studentsList = new JSONArray();
//            for (Student student : students) {
//                JSONObject objs = new JSONObject();
//                firstSaveHelper(objs,student);
//                JSONArray courses = new JSONArray();
//                secondSaveHelper(student,courses);
//                objs.put("Course List", courses);
//                studentsList.add(objs);
//            }
//            obj.put("Student List", studentsList);
//            fileWriter.write(obj.toJSONString());
//            fileWriter.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



    //MODIFIES: this
    //EFFECTS: save each student object in the course list
    @Override
    public void firstSaveHelper(JSONObject objs, Student student) {
        objs.put("Name", student.getStudentName());
        objs.put("Age", student.getAge());
        objs.put("Student Number", student.getStudentNumber());
        objs.put("Status", student.getStatus());
    }


    //MODIFIES: this
    //EFFECTS: Save course list for each course
    @Override
    public void secondSaveHelper(Student student, JSONArray courses) {
        for (Course course : student.getCourses()) {
            JSONObject objc = new JSONObject();
            objc.put("Course Name", course.getCourseName());
            objc.put("Course Number", course.getCourseNum());
            courses.add(objc);
        }
    }

    //MODIFIES: this
    //EFFECTS: load all data in the file into the student list
    @Override
    public void load() {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("Student.txt"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("Student List");
            for (Object jsonStudent : jsonArray) {
                JSONObject jobject = (JSONObject) jsonStudent;
                int stuNum = Math.toIntExact((long) jobject.get("Student Number"));
                int age = Math.toIntExact((long) jobject.get("Age"));
                String name = (String) jobject.get("Name");
                String status = (String) jobject.get("Status");
                Set<Course> studentCourses = new HashSet<>();
                JSONArray courses = (JSONArray) jobject.get("Course List");
                loadCourseHelper(courses,studentCourses);
                loadHelper(name, status, age, stuNum,studentCourses);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //MODIFIES: this
    //EFFECTS: load all each student's course list
    public void loadCourseHelper(JSONArray courses, Set studentCourses) {
        for (Object jsonCourse : courses) {
            JSONObject jsonObject1 = (JSONObject) jsonCourse;
            String courseName = (String) jsonObject1.get("Course Name");
            int courseNum = Math.toIntExact((long) jsonObject1.get("Course Number"));
            Course course = new Course(courseName, courseNum);
            studentCourses.add(course);
        }

    }

    //MODIFIES: this
    //EFFECTS: load all student object in the file into the student list
    public void loadHelper(String name, String status,int age,int stuNum,Set studentCourses) {
        if (status.equalsIgnoreCase("undergraduate")) {
            Student student = new UnderGraduated(name, stuNum, age);
            student.setCourses(studentCourses);
            students.add(student);
        }
        if (status.equalsIgnoreCase("Graduated")) {
            Student student = new Graduated(name, stuNum, age);
            students.add(student);
            student.setCourses(studentCourses);
        }

    }


    //MODIFIES: this
    //EFFECT: check if a student age is too old to add
    public void checkAge(Student s) throws AgeOutOfRangeException {
        if (s.getAge() > 100) {
            throw new AgeOutOfRangeException();
        }
    }





    //MODIFIES: this
    //EFFECTS: return true if the pass in parameter is correct otherwise return false
    @Override
    public boolean deleteCondition(Student student, String n, int x) {
        if (student.getStudentName().equalsIgnoreCase(n) && student.getStudentNumber() == x) {
            return true;
        }
        return false;
    }


    //MODIFIES: this
    //EFFECTS: return the corresponding course by the pass in parameter
    @Override
    public boolean findCondition(Student student, int studentNum,String status) {
        if (student.getStudentNumber() == studentNum && student.getStatus().equalsIgnoreCase(status)) {
            return true;
        }
        return false;
    }


//    @Override
//    //EFFECT: Remove a student out of the student list
//    public void delete(String n, int x) throws ListEmptyException, CantFindException {
//        if (students.isEmpty()) {
//            throw new ListEmptyException();
//        }
//        Iterator<Student> iterator = students.iterator();
//        while (iterator.hasNext()) {
//            Student student = iterator.next();
//            if (student.getStudentName().equalsIgnoreCase(n) && student.getStudentNumber() == x) {
//                iterator.remove();
//                System.out.println("Remove successfully");
//            } else {
//                throw new CantFindException();
//            }
//        }
//
//    }



//
//    @Override
//    public Student find(int studentNum) throws ListEmptyException, CantFindException {
//        if (students.isEmpty()) {
//            throw new ListEmptyException();
//        }
//        Iterator<Student> iterator = students.iterator();
//        while (iterator.hasNext()) {
//            Student student = iterator.next();
//            if (student.getStudentNumber() == studentNum) {
//                return student;
//            }
//            throw new CantFindException();
//        }
//        return null;
//    }





}









