package Lecture04;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Time;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

public class MyClass {
    static Properties prop;
    
    public static void main(String[] args) throws Exception {
        // Properties
        prop = new Properties();
        InputStream is = new FileInputStream("config.properties");
        prop.load(is);

        // input
        String packageName = MyClass.class.getPackage().getName();
        String dataName = "roster";
        String filePath = prop.getProperty("filepath") + File.separator + packageName + File.separator + "Data" + File.separator + dataName + ".json";
        InputStream inputStream = new FileInputStream(filePath);

        // making jsonobject for reading json data
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        inputStream.close();

         // reading json data
        String crn = jsonObject.getString("crn");
        String title = jsonObject.getString("title");
        String[] times = jsonObject.getString("time").split(":");
        int capacity = jsonObject.getInt("capacity");
        int numOfStudents = jsonObject.getInt("number of students");

        // creating aClass object
        Time classTime = new Time(Integer.parseInt(times[0]),Integer.parseInt(times[1]),Integer.parseInt(times[2]));
        aClass classDS = new aClass(crn, title, classTime);

        // adding instructor from json data
        JsonObject joInstructor = jsonObject.getJsonObject("instructor");
        Instructor instructor = new Instructor (joInstructor.getString("name"),joInstructor.getInt("age"),
                joInstructor.getString("id"), joInstructor.getString("major"),
                joInstructor.getString("major"), joInstructor.getInt("year"));
        classDS.addInstructor(instructor);

        // adding students from json data
        JsonArray jaStudents = jsonObject.getJsonArray("students");
        for (JsonValue jvStudent : jaStudents) {
            Student student = new Student(jvStudent.asJsonObject().getString("name"), jvStudent.asJsonObject().getInt("age"),
                    jvStudent.asJsonObject().getString("id"), jvStudent.asJsonObject().getString("major"), jvStudent.asJsonObject().getInt("year"));
            classDS.addStudent(student);
        }
        printData(classDS);

        // change student's name in the aClass's object - classDS
        System.out.println("\nEnter the name you want to change: ");
        Scanner reader = new Scanner(System.in);
        String name = reader.nextLine();

        if (classDS.modifyStudent(name,name+"2")) {
            System.out.println(name + " has Changed to " + name + "2");
            updateData(classDS,"roster-tmp");
        }
        else {
            System.out.println(name + " does not exist");
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
