package Lecture10;

import java.sql.Time;
import java.util.ArrayList;

class aClass {
    String crn;
    String title;
    Time schedule;
    Instructor instructor;
    private int capacity;                   // maximum students in this class
    private ArrayList<Student> students;    // list of students

    aClass() {
        instructor = new Instructor();
        capacity = 19;
        students = new ArrayList<Student>();    // no need to indicate the number of students
    }

    aClass(String crn, String title, Time schedule) {
        this.crn = crn;
        this.title = title;
        this.schedule = schedule;
        instructor = new Instructor();
        capacity = 19;
        students = new ArrayList<Student>();       // no need to indicate the number of students
    }

    aClass(String crn, String title, Time schedule, int capacity) {
        this.crn = crn;
        this.title = title;
        this.schedule = schedule;
        instructor = new Instructor();
        this.capacity = capacity;
        students = new ArrayList<Student>();
    }

    // managing capacity
    int getCapacity() { return capacity; }

    void setCapacity(int capacity) { this.capacity = capacity; }

    void addInstructor(Instructor instructor) { this.instructor = instructor; }

    // managing number of students
    int countStudents() { return students.size(); }

    ArrayList<Student> listStudents() { return students; }

    // managing students
    void addStudent(Student student) {
        // TODO: challenging-3
        //  What if a new student will be added when the class is full?
        //  Modify this method to handle this exception.
        students.add(student);
    }

    // searching student with name
    Student searchStudent(String name) {
        for(int i = 0; i < students.size(); i++) {
            if (students.get(i).name.equals(name)) {
                return students.get(i);
            }
        }
        return null;
    }

    // removing student with name
    boolean removeStudent(String name) {
        Student student = searchStudent(name);

        if (student != null) {
            students.remove(student);
            return true;
        }
        else {
            return false;
        }
    }

    boolean modifyStudent(String name) {
        // TODO: challenge 3
        //  Complete the method
        return false;
    }
}
