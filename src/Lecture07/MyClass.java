package Lecture07;

import Utility.Benchmark;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonValue;

public class MyClass {
    static Properties prop;

    // Linear search for student by name
    public static aStudent linearSearch(GradeBook gradeBook, String name) {
        Benchmark.resetCounterLS();                         // Benchmarking: reset counter for LS
        for (aStudent student: gradeBook.students) {
            Benchmark.increaseCounterLS();                  // Benchmarking: count repetitions of the loop
            if (student.name.equals(name))
                return student;
        }

        return null;
    }

    // Linear Search for students by final score
    public static void findStudentsByFinalScore(GradeBook gradeBook, int finalScore) {
        aStudent keyStudent = new aStudent(finalScore);
        System.out.printf("%-12s%-7s%-1s\n","Name","Final","Grade");

        Benchmark.resetCounterLS();                         // Benchmarking: reset counter for LS
        for (aStudent student: gradeBook.students) {
            Benchmark.increaseCounterLS();                  // Benchmarking: count repetitions of the loop
            if(student.compareTo(keyStudent) >= 0) {
                student.printGrade();
            }
        }
    }

    // Binary Search for students by grade
    public static void findStudentsByGrade(GradeBook gradeBook, double grade) {
        System.out.println("TODO: complete the function - findStudentsByGrade");
        // TODO: challenge 1
        //  Search students those grade is greater than or equal to the input based on binary search
        //  Do not forget counter for BS
    }

    public static GradeBook createGradeBook(String dataName) throws Exception {
        // input
        String packageName = MyClass.class.getPackage().getName();
        String filePath = prop.getProperty("filepath") + File.separator + packageName + File.separator + "data" + File.separator + dataName + ".json";
        InputStream inputStream = new FileInputStream(filePath);

        // making jsonobject for reading json data
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        inputStream.close();

        // reading json data
        String crn = jsonObject.getString("crn");
        JsonArray jaStudents = jsonObject.getJsonArray("students");
        int numOfStudents = jaStudents.size();

        GradeBook rGradeBook = new GradeBook(crn, numOfStudents);

        int index = 0;
        for (JsonValue jvStudent : jaStudents) {
            rGradeBook.students[index] = new aStudent(jvStudent.asJsonObject().getString("name"), jvStudent.asJsonObject().getInt("final"),
                    jvStudent.asJsonObject().getJsonNumber("grade").doubleValue());
            index++;
        }

        return rGradeBook;
    }

    public static void main(String[] args) throws Exception {
        // load properties
        prop = new Properties();
        InputStream cofIS = new FileInputStream("config.properties");
        prop.load(cofIS);

        GradeBook gradeBook = createGradeBook("grade");
        gradeBook.printGradeBook();

        Scanner input = new Scanner(System.in);
        System.out.print("Choose one [1: search by name | 2: search by final score | 3: search by grade]");
        String option = input.nextLine();
        if(option.compareTo("1") == 0 ) {
            System.out.print("Enter student's name: ");
            String name = input.nextLine();
            aStudent theStudent = linearSearch(gradeBook, name);
            if (theStudent == null) {
                System.out.println(name + " is not in the class.");
            } else {
                System.out.printf("%-12s%-7s%-1s\n", "Name", "Final", "Grade");
                theStudent.printGrade();
            }
            System.out.println("Number of repetitions: " + Benchmark.getCounterLS());       // Benchmarking: review the counter for LS
        }
        else if (option.compareTo("2") == 0) {
            System.out.print("Enter final score (>=): ");
            String finalScore = input.nextLine();
            findStudentsByFinalScore(gradeBook, Integer.parseInt(finalScore));
            System.out.println("Number of repetitions: " + Benchmark.getCounterLS());       // Benchmarking: review the counter for LS
        }
        else if (option.compareTo("3") == 0) {
            System.out.print("Enter grade (>=): ");
            String grade = input.nextLine();
            findStudentsByGrade(gradeBook, Double.parseDouble(grade));
            System.out.println("Number of repetitions: " + Benchmark.getCounterBS());       // Benchmarking: review the counter for BS
        }
        else {
            System.out.println("Unknown option number");
        }
    }
}