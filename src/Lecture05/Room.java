package Lecture05;

abstract class Room {
    String roomNum;
    int capacity;
    boolean[] scheduled;                 // lecture schedule

    Room (String roomNum, int capacity) {
        this.roomNum = roomNum;
        this.capacity = capacity;
    }

    void initializeSchedule(int numOfLectures) {
        scheduled = new boolean[numOfLectures];
    }

    abstract void setSchedule(int lectureTime);
    abstract void unsetSchedule(int lectureTime);
}

class Classroom extends Room {
    boolean locked;

    Classroom (String roomNum, int capacity) {
        super(roomNum, capacity);
        locked = true;
    }

    void setSchedule(int lectureTime) {
        scheduled[lectureTime-1] = true;
    }
    void unsetSchedule(int lectureTime) { scheduled[lectureTime-1] = false; }

    void openClassroom() { locked = false; }
    void closeClassroom() { locked = true; }
}

// TODO: Challenge 4 - uncomment the class below, find the reasons for the errors
/*
class Computerlab extends Room implements Lab {
    Computerlab (String roomNum, int capacity) {
        super(roomNum, capacity);
    }

    String getLabNum() { return labNum; }

    int countComputers() { return numComputers; }
}
*/
