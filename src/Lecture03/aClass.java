package Lecture03;

import java.sql.Time;

class aClass {
    String crn;
    String title;
    Time schedule;
    Instructor instructor;
    private int capacity;               // maximum students in this class
    private int numOfStudents;          // number of currently enrolled students
    private Student[] students;

    aClass(String crn, String title, Time schedule) {
        this.crn = crn;
        this.title = title;
        this.schedule = schedule;
        instructor = new Instructor();
        capacity = 19;
        students = new Student[capacity];
        numOfStudents = 0;
    }

    aClass(String crn, String title, Time schedule, int capacity) {
        this.crn = crn;
        this.title = title;
        this.schedule = schedule;
        instructor = new Instructor();
        this.capacity = capacity;
        students = new Student[capacity];
        numOfStudents = 0;
    }

    // managing capacity
    int getCapacity() { return capacity; }

    void setCapacity(int capacity) { this.capacity = capacity; }

    void addInstructor(Instructor instructor) {this.instructor = instructor;}

    // managing number of students
    int countStudents() { return numOfStudents; }

    // managing students
    void addStudent(Student student) { students[numOfStudents++] = student;  }
    // TODO: challenging-3
    //  What if a new student will be added when the class is full?
    //  Modify this method to handle this exception.

    Student[] listStudents() { return students; }

    // searching student with name
    Student searchStudent(String name) {
        for(int i = 0; i < numOfStudents; i++) {
            if (students[i].name.equals(name)) {
                return students[i];
            }
        }
        return null;
    }

    // changing student's name
    boolean modifyStudent(String name, String newName) {
        for (Student student: students) {
            if(student != null && student.name.equals(name)) {
                student.name = newName;
                return true;
            }
        }
        return false;
    }
}
