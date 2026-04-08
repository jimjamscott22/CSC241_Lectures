package src.Lecture01;

import java.nio.file.*;
import java.io.*;
import java.util.Scanner;

public class MyClass {
    public static void main(String[] args) throws Exception {
        Path p = Paths.get(System.getProperty("user.dir"),"src","Lecture01","roster.txt");

        // input
        File input = new File(p.toString());

        // output
        File output = new File(p.toString() + ".tmp");
        FileOutputStream fileOutputStream = new FileOutputStream(output);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bw = new BufferedWriter(outputStreamWriter);

        Scanner line = new Scanner(input);

        while(line.hasNext()) {
            String stu = line.nextLine();
            String name, id;
            name = stu.substring(0,stu.lastIndexOf(" "));
            id = stu.substring(stu.lastIndexOf(" ")+1);
            if (name.equals("John")) {
                name = "John2";
                stu = name + " " + id;      // updated student's info
            }
            bw.write(stu);
            bw.newLine();
        }
        bw.close();

//        line.close();                     // try to run uncomment this line and see how differently it runs
        input.delete();
        output.renameTo(new File(p.toString()));
    }
}