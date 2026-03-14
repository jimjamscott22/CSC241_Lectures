package Lecture07;

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