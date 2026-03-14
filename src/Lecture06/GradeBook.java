package Lecture06;

class GradeBook {
    String crn;
    aStudent[] students;

    GradeBook (String crn, int numOfStudents) {
        this.crn = crn;
        students = new aStudent[numOfStudents];
    }

    void printGradeBook() {
        System.out.printf("%-12s%-7s%-1s\n","Name","Final","Grade");
        for(aStudent student: students) {
            student.printGrade();
        }
    }
}

class aStudent {
    String name;
    private int finScore;           // final score
    private double grade;              // grade

    aStudent (String name, int finalScore, double grade) {
        this.name = name;
        this.finScore = finalScore;
        this.grade = grade;
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

    void printGrade() {
        System.out.printf("%-12s%-7d%.1f\n",name,finScore,grade);
    }
}