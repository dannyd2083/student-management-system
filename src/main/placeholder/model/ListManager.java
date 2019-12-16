package placeholder.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class ListManager<T> implements Loadable, Saveable<T> {

    File file;

    public ListManager(String filename)  {
        file = new java.io.File(filename);
    }



    //MODIFIES: CourseListManager,StudentListManager,ProfessorListManager
    //EFFECTS: save different data in to the file base on different data type
    @Override
    public void save(Set<T> s, String outSideListKey, String insideListKey) {
        try {
            FileWriter fileWriter = new FileWriter(file.getName());
            JSONObject obj = new JSONObject();
            JSONArray list = new JSONArray();
            for (T person : s) {
                JSONObject objt = new JSONObject();
                firstSaveHelper(objt,person);
                JSONArray insideList = new JSONArray();
                secondSaveHelper(person,insideList);
                objt.put(insideListKey,insideList);
                list.add(objt);
            }
            obj.put(outSideListKey,list);
            fileWriter.write((obj.toJSONString()));
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //MODIFIES: CourseListManager,StudentListManager,ProfessorListManager
    //EFFECTS: save helper for different dataType
    public abstract void firstSaveHelper(JSONObject objt, T person);

    //MODIFIES: CourseListManager,StudentListManager,ProfessorListManager
    //EFFECTS: save helper for different dataType
    public abstract void secondSaveHelper(T person, JSONArray insideList);




//    @Override
//    public void save() {
//        try {
//            FileWriter fileWriter = new FileWriter(file.getName());
//            JSONObject obj = new JSONObject();
//            JSONArray studentsList = new JSONArray();
//            for (Student student : students) {
//                JSONObject objs = new JSONObject();
//                saveHelperForStudent(objs,student);
//                JSONArray courses = new JSONArray();
//                saveHelperForStudentCourse(student,courses);
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



    //MODIFIES: CourseListManager,StudentListManager,ProfessorListManager
    //EFFECTS: add object in the corresponding type list
    public void add(T t,Set s) {
        s.add(t);
    }


    //MODIFIES: CourseListManager,StudentListManager,ProfessorListManager
    //EFFECTS: delete corresponding type of object in different type lists
    public void delete(String name, int id, Set list) throws ListEmptyException, CantFindException {
        if (list.isEmpty()) {
            throw new ListEmptyException();
        }
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T thisType = iterator.next();
            if (deleteCondition(thisType, name, id)) {
                iterator.remove();
                System.out.println("Remove successfully");
            } else {
                throw new CantFindException();
            }
        }

    }

    //MODIFIES: this
    //EFFECTS: return true or false base on the different type lists have different conditions
    public abstract boolean deleteCondition(T t, String n, int x);


    //MODIFIES: CourseListManager,StudentListManager,ProfessorListManager
    //EFFECTS: find the different types objects in the corresponding type list
    public T find(int id,String name,Set list) throws ListEmptyException, CantFindException {
        if (list.isEmpty()) {
            throw  new  ListEmptyException();
        }
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T person = iterator.next();
            if (findCondition(person,id,name)) {
                return person;
            }
        }
        throw new CantFindException();
    }


    //MODIFIES: this
    //EFFECTS: return true or false base on the different type lists have different conditions
    public abstract boolean findCondition(T t, int id, String name);


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



    @Override
    public abstract void load();







}
