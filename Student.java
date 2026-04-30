package Edutrack1;

import java.util.*;

public class Student {
    public String id;
    public String name;
    public String password = "123";
    public ArrayList<Integer> marks = new ArrayList<>();
    public ArrayList<String> events = new ArrayList<>();
    public int presentClasses = 0;
    public int totalClasses = 0;
    
    // ✅ ALL CONSTRUCTORS
    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.password = "123";
        for(int i = 0; i < 4; i++) marks.add(0);
    }
    
    public Student(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        for(int i = 0; i < 4; i++) marks.add(0);
    }
    
    // ✅ MISSING METHODS
    public void markAttendance(boolean present) {
        totalClasses++;
        if(present) presentClasses++;
    }
    
    public void addMark(int mark) {
        if(marks.size() < 4) {
            marks.add(mark);
        } else {
            marks.set(0, mark);  // Replace first mark
        }
    }
    
    public double getAverageResult() {
        if(marks.isEmpty()) return 0;
        int sum = 0;
        for(int mark : marks) sum += mark;
        return (double)sum / marks.size();
    }
    
    // ✅ ALL GETTERS
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public int getPresentClasses() { return presentClasses; }
    public int getTotalClasses() { return totalClasses; }
    
    public double getAttendancePercentage() {
        return totalClasses > 0 ? (double)presentClasses/totalClasses * 100 : 0;
    }
    
    public String getGrade() {
        double avg = getAverageResult();
        if(avg >= 90) return "A";
        if(avg >= 80) return "B";
        if(avg >= 70) return "C";
        return "D";
    }
    
    public boolean needsAlert() {
        return getAttendancePercentage() < 75;
    }
    
    public void saveToDatabase() {
        DatabaseManager.saveStudent(this);
    }
}
