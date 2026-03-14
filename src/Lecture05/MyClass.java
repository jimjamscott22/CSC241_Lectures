package Lecture05;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Time;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonValue;

public class MyClass {
    static Properties prop;

    // Test cases - 1) types of objects for parent/children class, 2) abstract class, 3) interface
    public static final Boolean DEBUG_PERSON = true;
    public static final Boolean DEBUG_ROOM = false;
    public static final Boolean DEBUG_BOOK = false;

    public static void main(String[] args) throws Exception {
        // load properties
        prop = new Properties();
        InputStream is = new FileInputStream("config.properties");
        prop.load(is);

        // Parent/Child class: Person and Instructor/Student
        // TODO: Challenge 1 - Uncomment lines below, find the reason for the errors, and fix them
        if (DEBUG_PERSON) {
            System.out.println("- Test for parent/children classes");
            // creating a class object by using default constructor
            aClass classDS = new aClass();
            createClass(classDS, "roster");

            // instantiating an array of people including instructor and students
            int numOfPeople = classDS.countStudents() + 1, pIndex = 0;
            Person[] peopleInClass = new Person[numOfPeople];
            peopleInClass[pIndex] = new Instructor("Dr.Laker", 99);
            peopleInClass[pIndex].identify();
            System.out.println(" - age: " + (peopleInClass[pIndex]).getAge());
//            System.out.println(" - title: " + (peopleInClass[pIndex]).title);

            for (++pIndex; pIndex < numOfPeople; pIndex++) {
                peopleInClass[pIndex] = classDS.listStudents()[pIndex-1];
                peopleInClass[pIndex].identify();
            }
            System.out.println("student 1: " + peopleInClass[1].name + "(" + peopleInClass[1].getAge() + ")");
//            Student bestStudent = peopleInClass[2];
//            System.out.println("student 2: " + student.name + "(" + student.getID() + ")");
        }

        // Abstract class: Room and Classroom
        // TODO: challenge 2 - uncomment the lines below, find the reason for errors, and fix them
        if(DEBUG_ROOM) {
            System.out.println("\n- Test for abstract classes");

            String roomNum = "172";
            int roomSize = 48, classTimes = 10;
            Room classroom = new Classroom(roomNum, roomSize);
            classroom.initializeSchedule(classTimes);
            classroom.setSchedule(1);
            classroom.setSchedule(7);
            classroom.unsetSchedule(7);

            System.out.println("[Classroom " + roomNum + "]");
            for (int i = 0; i < classroom.scheduled.length; i++) {
                if (classroom.scheduled[i])
                    System.out.println("Time " + (i + 1) + ": scheduled");
                else
                    System.out.println("Time " + (i + 1) + ": not scheduled");
            }
//            if (classroom.locked) {
//                System.out.println("Classroom is currently locked");
//            }
//            else {
//                System.out.println("Classroom is currently unlocked");
//            }
        }

        // Interface: Book and textbook
        // TODO: challenge 3 - uncomment the line below, find the reason for the error, and fix it
        if(DEBUG_BOOK) {
            System.out.println("\n-Test for interface");

            int numOfTextbooks = 3;
            Book[] textbooks = new Book[numOfTextbooks];
            Book textbook1 = new Textbook("Data Structure in Java", "Dr. Laker", "1234-3253", 999);
//            textbook1.setNumOfPages(1000);
            textbooks[0] = textbook1;
            Book textbook2 = new Textbook("Programming Methodology", "Dr. Marvel", "1357-2468", 777);
            textbooks[1] = textbook2;
            Book textbook3 = new Textbook("Intro to Java", "Captain America", "2357-2358", 789);
            textbooks[2] = textbook3;
            for(Book textbook: textbooks) {
                textbook.showTitle();
                textbook.showISBN();
                textbook.showPublisher();
                textbook.showNumOfPages();
            }
        }
    }

    public static void createClass(aClass theClass, String dataName) throws Exception {
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
        theClass.crn = jsonObject.getString("crn");
        theClass.title = jsonObject.getString("title");
        String[] times = jsonObject.getString("time").split(":");
        int capacity = jsonObject.getInt("capacity");
        int numOfStudents = jsonObject.getInt("number of students");

        // creating aClass object
        //Time classTime = new Time(Integer.parseInt(times[0]),Integer.parseInt(times[1]),Integer.parseInt(times[2]));
        theClass.schedule = new Time(Integer.parseInt(times[0]),Integer.parseInt(times[1]),Integer.parseInt(times[2]));

        // adding instructor from json data
        JsonObject joInstructor = jsonObject.getJsonObject("instructor");
        Instructor instructor = new Instructor(joInstructor.getString("name"), joInstructor.getInt("age"),
                joInstructor.getString("id"), joInstructor.getString("major"),
                joInstructor.getString("major"), joInstructor.getInt("year"));
        theClass.addInstructor(instructor);

        // adding students from json data
        JsonArray jaStudents = jsonObject.getJsonArray("students");
        for (JsonValue jvStudent : jaStudents) {
            Student student = new Student(jvStudent.asJsonObject().getString("name"), jvStudent.asJsonObject().getInt("age"),
                    jvStudent.asJsonObject().getString("id"), jvStudent.asJsonObject().getString("major"), jvStudent.asJsonObject().getInt("year"));
            theClass.addStudent(student);
        }
    }
}