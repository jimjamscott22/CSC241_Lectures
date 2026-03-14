package Lecture03;

import java.io.File;
import java.sql.Time;
import java.util.Scanner;
import java.util.Properties;
import java.io.FileInputStream;

public class MyClass {
    public static void main(String[] args) throws Exception {
        // Properties
        Properties prop = new Properties();
        FileInputStream fisConfig = new FileInputStream("config.properties");
        prop.load(fisConfig);

        // input
        String packageName = MyClass.class.getPackage().getName();
        String dataName = "roster";
        String filePath = prop.getProperty("filepath") + File.separator + packageName + File.separator + "Data" + File.separator + dataName + ".txt";
        File input = new File(filePath);

        // class
        Time classTime = new Time(11,30,0);
        aClass classDS = new aClass("12345", "data structure", classTime);
        Person instructor = new Instructor("Dr. Laker", 29);
        instructor.setAge(39);
        // TODO: challenging-1
        //  Update information of department, title, age and year
        //  Uncomment the line below and run. Does it work? If not, fix it.
//        instructor.major = "SE";
        classDS.instructor = (Instructor)instructor;

        int numberOfStudents = 3;
        int index = 0;
        Student[] students = new Student[numberOfStudents];

        Scanner line = new Scanner(input);                  // reading data from input file

        while(line.hasNext()) {
            String stu = line.nextLine();
            String name, id;
            name = stu.substring(0,stu.lastIndexOf(" "));
            id = stu.substring(stu.lastIndexOf(" ")+1);
            Student student = new Student(name, 19);
            student.setID(id);
            classDS.addStudent(student);
        }

        System.out.println("Class CRN: " + classDS.crn);
        System.out.println("Class Time: " + classDS.schedule);
        System.out.println("Instructor: " + classDS.instructor.name);
//        System.out.println("Number of Students: " + classDS.numOfStudents);       // Uncomment it, and check whether it can run
        System.out.println("Number of Students: " + classDS.countStudents());

        classDS.instructor.identify();
        for (Student student: classDS.listStudents()) {
            if (student != null)
                student.identify();
        }

        // change student's name in the aClass's object - classDS
        System.out.println("Enter the name you want to change: ");
        Scanner reader = new Scanner(System.in);
        String cName = reader.nextLine();

        if (classDS.modifyStudent(cName,cName+"2"))
            System.out.println(cName + " has Changed to " + cName + "2");
        else
            System.out.println(cName + " does not exist");
        // TODO: challenging-2
        //  Update the changed information in the data file
    }
}
