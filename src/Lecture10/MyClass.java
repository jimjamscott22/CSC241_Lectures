package Lecture10;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class MyClass {
    static Properties prop;
    
    public static void main(String[] args) throws Exception {
        // Properties
        prop = new Properties();
        InputStream is = new FileInputStream("config.properties");
        prop.load(is);

        // creating a class object by using default constructor
        aClass classDS = new aClass();
        createClass(classDS, "roster");
        printData(classDS);

        // change student's name in the aClass's object - classDS
        Scanner reader = new Scanner(System.in);
        System.out.print("\nChoose One [1: add a new student | 2: remove a student | 3: modify a student] ");
        String option = reader.nextLine();
        if(option.compareTo("1") == 0 ) {
            System.out.print(" Enter name of the new student: ");
            String name = reader.nextLine();
            System.out.print(" Enter age of the new student: ");
            // TODO: challenge 1
            //  Check whether the input is numeric
            int age = Integer.parseInt(reader.nextLine());
            System.out.print(" Enter id of the new student: ");
            String id = reader.nextLine();
            System.out.print(" Enter year of the new student: ");
            // TODO: challenge 2
            //  Check whether the input is numeric
            int year = Integer.parseInt(reader.nextLine());
            System.out.print(" Enter major of the new student: ");
            String major = reader.nextLine();
            Student student = new Student(name, age, id, major, year);
            classDS.addStudent(student);
        }
        else if (option.compareTo("2") == 0) {
            System.out.print(" Enter name of the student to remove: ");
            String name = reader.nextLine();
            if (!classDS.removeStudent(name)) {
                System.out.println("Student " + name + " is not in the class.");
                System.exit(0);
            }
        }
        else if (option.compareTo("3") == 0) {
            System.out.print(" Enter name of the student to remove: ");
            System.out.println(" TODO: challenge 3 - implement modify method in aClass.java");
            // TODO: challenge 3
            //  Complete modifyStudent(name) in aClass.java
        }
        else {
            System.out.println("Unknown option number");
            System.exit(0);
        }
        printData(classDS);
        updateData(classDS, "roster");
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
        theClass.schedule = new Time(Integer.parseInt(times[0]),Integer.parseInt(times[1]),Integer.parseInt(times[2]));

        // adding instructor from json data
        JsonObject joInstructor = jsonObject.getJsonObject("instructor");
        Instructor instructor = new Instructor(joInstructor.getString("name"),joInstructor.getInt("age"),
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


    // TODO: challenge 1
    //  Update the original file
    public static void updateData(aClass theClass, String dataName) throws Exception{
        // preparing jsonwriter
        String packageName = MyClass.class.getPackage().getName();
        String filePath = prop.getProperty("filepath") + File.separator + packageName + File.separator + "Data" + File.separator + dataName + ".json";
        OutputStream outputStream = new FileOutputStream(filePath);
        JsonWriter jsonWriter = Json.createWriter(outputStream);

        // creating objectbuilder and arraybuilder to write data to json file
        JsonObjectBuilder joBuilder = Json.createObjectBuilder();

        // adding general class info
        joBuilder.add("crn", theClass.crn);
        joBuilder.add("title", theClass.title);
        joBuilder.add("time", theClass.schedule.toString());
        joBuilder.add("capacity", theClass.getCapacity());
        joBuilder.add("number of students", theClass.countStudents());

        // adding instructor info
        JsonObjectBuilder joInstructorBuilder = Json.createObjectBuilder();
        joInstructorBuilder.add("name", theClass.instructor.name);
        joInstructorBuilder.add("age", theClass.instructor.getAge());
        joInstructorBuilder.add("id", theClass.instructor.getID());
        joInstructorBuilder.add("major", theClass.instructor.major);
        joInstructorBuilder.add("title", theClass.instructor.title);
        joInstructorBuilder.add("year", theClass.instructor.getYear());
        joBuilder.add("instructor",joInstructorBuilder);

        // adding students info
        JsonArrayBuilder jaBuilder = Json.createArrayBuilder();
        for (Student student: theClass.listStudents()) {
            if (student != null) {
                JsonObjectBuilder joStudentBuilder = Json.createObjectBuilder();
                joStudentBuilder.add("name", student.name);
                joStudentBuilder.add("age", student.getAge());
                joStudentBuilder.add("id", student.getID());
                joStudentBuilder.add("year", student.getYear());
                joStudentBuilder.add("major", student.major);
                jaBuilder.add(joStudentBuilder);
            }
        }
        joBuilder.add("students",jaBuilder);
        JsonObject jsonObject = joBuilder.build();

        // Configuring format for Json file
        Map<String, Boolean> config = new HashMap<String, Boolean>();
        config.put(JsonGenerator.PRETTY_PRINTING,true);
        JsonWriterFactory factory = Json.createWriterFactory(config);
        jsonWriter = factory.createWriter(outputStream);

        jsonWriter.write(jsonObject);
        jsonWriter.close();
        outputStream.close();
    }

    public static void printData(aClass theClass) {
        System.out.println("crn: " + theClass.crn);
        System.out.println("title: " + theClass.title);
        System.out.println("time: " + theClass.schedule);
        System.out.println("capacity: " + theClass.getCapacity());
        System.out.println("enrollment: " + theClass.countStudents());
        System.out.println("instructor: " + theClass.instructor.name);
        int index = 0;
        for(Student student: theClass.listStudents()) {
            if(student != null)
                System.out.println("student" + ++index + ": " + student.name + "(" + student.major + ")");
        }
    }
}
