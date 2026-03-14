package Lecture05;

class Person {
    String name;
    private int age;

    Person (String name, int age) {
        this.name = name;
        this.age = age;
    }

    // managing age
    int getAge() {
        return age;
    }

    void setAge(int year) {
        age += year;
    }

    void identify() {
        System.out.println("Person " + name + ": age " + age);
    }
}

class Student extends Person {
    private String id;
    private int year;
    String major;

    Student (String name, int age) {
        super(name, age);
        id = "800000000";
        year = 1;
        major = "CS";
    }

    Student (String name, int age, String id, String major, int year) {
        super(name, age);
        this.id = id;
        this.major = major;
        this.year = year;
    }

    // managing ID
    String getID() {
        return id;
    }

    void setID(String id) {
        this.id = id;
    }

    // managing year
    int getYear() {
        return year;
    }

    void setYear(int year) {
        this.year += year;
    }

    void identify() {
        System.out.println("Student " + super.name + ": id " + id + " | major " + major + " | year " + year);
    }
}

class Instructor extends Person{
    private String id;
    String major;
    String title;
    private int year;           // when he started working

    Instructor() {
        super("Instructor", 29);
        id = "700000000";
        major = "CS";
        title = "Professor";
        year = 2000;
    }

    Instructor (String name, int age) {
        super(name, age);
        id = "700000000";
        major = "CS";
        title = "Professor";
        year = 2000;
    }

    Instructor (String name, int age, String id, String major, String title, int year) {
        super(name, age);
        this.id = id;
        this.major = major;
        this.title = title;
        this.year = year;
    }

    // managing ID
    String getID() { return id; }

    void setID(String id) {
        this.id = id;
    }

    // managing year
    int getYear() { return year; }

    void setYear(int year) {
        this.year = year;
    }

    void identify() {
        System.out.println("Instructor: " + title + " " + super.name + " in " + major);
    }
}


