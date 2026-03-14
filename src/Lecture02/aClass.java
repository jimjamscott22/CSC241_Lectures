package Lecture02;

import java.sql.Time;

class aClass {
    String[] names;
    private int numStudents;
    String crn;
    Time schedule;

    aClass(String crn, Time schedule) {
        this.crn = crn;
        this.schedule = schedule;
        names = new String[24];
        numStudents = 0;
    }

    aClass(int numStudents) {

        this.numStudents = numStudents;
    }

    int countStudents(){

        return numStudents;
    }

    void addStudent(String name) {

        names[numStudents++] = name;
    }

    boolean modifyStudent(String name, String newName) {
        for(int i = 0; i < numStudents; i++) {
            if (names[i].equals(name)) {
                names[i] = newName;
                return true;
            }
        }
        return false;
    }
}
