package Lecture06;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import Utility.Benchmark;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonValue;

public class MyClass {
    static Properties prop;

    public static final Boolean SEARCH_NAME = true;
    public static final Boolean SEARCH_GRADE = false;
    public static final Boolean SEARCH_FINAL = false;

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

    // Linear search for student by name
    public static aStudent linearSearch(GradeBook gradeBook, String name) {
        for (aStudent student: gradeBook.students) {
            Benchmark.increaseCounterLS();                  // count repetitions of the loop
            if (student.name.equals(name))
                return student;
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        // load properties
        prop = new Properties();
        InputStream cofIS = new FileInputStream("config.properties");
        prop.load(cofIS);

        GradeBook gradeBook = createGradeBook("grade");
        gradeBook.printGradeBook();

        Scanner input = new Scanner(System.in);
        if (SEARCH_NAME) {
            System.out.print("Enter student's name: ");
            String name = input.nextLine();
            aStudent theStudent = linearSearch(gradeBook, name);
            if (theStudent == null) {
                System.out.println(name + " is not in the class.");
            } else {
                System.out.printf("%-12s%-7s%-1s\n", "Name", "Final", "Grade");
                theStudent.printGrade();
            }
        }
        if (SEARCH_GRADE) {
            System.out.print("Enter Grade: ");
            String grade = input.nextLine();
            // TODO: challenge 1
            //  Search students those gpa is greater than the input
        }
        if (SEARCH_FINAL) {
            System.out.print("Enter Final: ");
            String grade = input.nextLine();
            // TODO: challenge 2
            //  Search students those final is greater than the input
        }
        System.out.println("Number of repetitions: " + Benchmark.getCounterLS());
    }
}