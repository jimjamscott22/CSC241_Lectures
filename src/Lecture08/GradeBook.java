package Lecture08;

import Utility.Benchmark;

class GradeBook {
    String crn;
    aStudent[] students;

    GradeBook (String crn, int numOfStudents) {
        this.crn = crn;
        students = new aStudent[numOfStudents];
    }

    // Selection Sort by Name
    void sortByName() {
        Benchmark.resetCounterLS();
        for (int i = 0; i < students.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < students.length; j++) {
                Benchmark.increaseCounterLS();
                if (students[j].name.compareTo(students[min].name) < 0) {
                    min = j;
                }
            }
            swapStudentsGrade(i, min);
        }
    }

    void sortByFinalScore() {
        System.out.println("CHALLENGE: sort by final score based on bubble sort");
        // TODO: Assignment 5, Challenge 1
        //  Implement insertion sort or bubble sort
        //  For Benchmark, use CounterLS in Benchmark class
    }

    void sortByGrade() {
        System.out.println("CHALLENGE: sort by grade based on merge sort");
        // TODO: Assignment 5, Challenge 2
        //  Implement merge sort or quick sort
        //  For Benchmark, use CounterLS in Benchmark class
    }

    void swapStudentsGrade(int curInd, int minInd) {
        aStudent tmpStudent = students[curInd];
        students[curInd] = students[minInd];
        students[minInd] = tmpStudent;
    }

    void printGradeBook() {
        System.out.printf("%-12s%-7s%-1s\n","Name","Final","Grade");
        for(aStudent student: students) {
            student.printGrade();
        }
    }
}

class aStudent implements Comparable<aStudent>{
    String name;
    private Integer finScore;           // final score
    private Double grade;              // grade

    aStudent (String name, int finalScore, double grade) {
        this.name = name;
        this.finScore = finalScore;
        this.grade = grade;
    }

    aStudent (double grade) {
        name = "student";
        finScore = 80;
        this.grade = grade;
    }

    aStudent (int finalscore) {
        name = "student";
        finScore = finalscore;
        grade = 70.0;
    }

    int getFinalScore() {
        return finScore;
    }

    void updateFinalScore(int newScore) {
        this.finScore += newScore;
    }

    double getGrade() {
        return grade;
    }

    void updateGrade(double newGrade) {
        this.grade = newGrade;
    }

    // comparing students by final score
    public int compareTo(aStudent thatStudent) {
        if (finScore.compareTo(thatStudent.finScore) == 0)          // equal case
            return 0;
        else if (finScore.compareTo(thatStudent.finScore) > 0)      // final score of this student is greater
            return 1;
        else                                                        // final score of this student is less
            return -1;
    }

    void printGrade() {
        System.out.printf("%-12s%-7d%.1f\n",name,finScore,grade);
    }
}