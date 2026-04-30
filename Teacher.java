package Edutrack1;

import java.util.*;

public class Teacher {
    public String name;
    public String password;
    public ArrayList<Student> students = new ArrayList<>();
    
    // ✅ ORIGINAL 2-PARAMETER CONSTRUCTOR
    public Teacher(String name, String password) {
        this.name = name;
        this.password = password;
    }
    
    // ✅ NEW 1-PARAMETER CONSTRUCTOR (FIXES AdminFrame & FileManager)
    public Teacher(String name) {
        this.name = name;
        this.password = "123";  // Default password
    }
    
    // ✅ MISSING addStudent METHOD
    public void addStudent(Student s) {
        students.add(s);
    }
    
    public void checkAlerts() {
        System.out.println("🔍 Checking alerts for " + students.size() + " students");
    }
    
}
