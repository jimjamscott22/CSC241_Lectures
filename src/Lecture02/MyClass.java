package Lecture02;

import java.io.*;
import java.nio.file.*;
import java.sql.Time;
import java.util.Scanner;

public class MyClass {
    public static void main(String[] args) throws Exception {
        Path p = Paths.get(System.getProperty("user.dir"),"src","Lecture02","roster.txt");

        // input
        File input = new File(p.toString());

        // class
        Time timeDS = new Time(10,0,0);
        Lecture02.aClass classDS = new Lecture02.aClass("12345", timeDS);

        Scanner line = new Scanner(input);                  // reading data from input file

        while(line.hasNext()) {
            String stu = line.nextLine();
            String name, id;
            name = stu.substring(0,stu.lastIndexOf(" "));
            id = stu.substring(stu.lastIndexOf(" ")+1);
            classDS.addStudent(name);
        }

        System.out.println("Class CRN: " + classDS.crn);
        System.out.println("Class Time: " + classDS.schedule);
   //     System.out.println("Number of Students: " + classDS.numOfStudents);       // Uncomment it, and check whether it can run
        System.out.println("Number of Students: " + classDS.countStudents());

        // change student's name in the aClass's object - classDS
        System.out.println("Enter the name you want to change: ");
        Scanner reader = new Scanner(System.in);
        String cName = reader.nextLine();

        if (classDS.modifyStudent(cName,cName+"2"))
            System.out.println(cName + " has Changed to " + cName + "2");
        else
            System.out.println(cName + " does not exist");
    }
}
