package Edutrack1;

import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String USERS_FILE = "users_data.txt";
    private static final String DATA_FILE = "students_data.txt";
    
    public static void saveUser(String id, String name, String role, String password) {
        try {
            HashMap<String, String> users = loadAllUsers();
            String data = id + "|" + name + "|" + role + "|" + password;
            users.put(id, data);
            
            PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE));
            for (String line : users.values()) {
                writer.println(line);
            }
            writer.close();
            System.out.println("✅ SAVED USER: " + id + " (" + role + ")");
        } catch (Exception e) {
            System.err.println("❌ USER SAVE ERROR: " + e.getMessage());
        }
    }
    
    public static ArrayList<String[]> getAllUsers() {
        return new ArrayList<>(loadAllUsers().values().stream()
            .map(line -> line.split("\\|"))
            .toList());
    }
    
    public static void saveStudent(Student student) {
        try {
            // Ensure marks has exactly 4 values
            while (student.marks.size() < 4) {
                student.marks.add(0);
            }
            while (student.marks.size() > 4) {
                student.marks.remove(student.marks.size() - 1);
            }
            
            StringBuilder marksData = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                marksData.append(student.marks.get(i)).append(",");
            }
            
            // Format: ID|Name|Password|Present|Total|Marks
            String dataLine = student.getId() + "|" + 
                             student.getName() + "|" + 
                             student.getPassword() + "|" +
                             student.presentClasses + "|" + 
                             student.totalClasses + "|" + 
                             marksData.toString();
            
            HashMap<String, String> students = loadAllStudents();
            students.put(student.getId(), dataLine);
            
            // Write ALL students back to file
            PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE));
            for (String line : students.values()) {
                writer.println(line);
            }
            writer.close();
            
            System.out.println("✅ SAVED: " + dataLine);
        } catch (Exception e) {
            System.err.println("❌ SAVE ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static ArrayList<String[]> getAllTeachers() {
    ArrayList<String[]> teachers = new ArrayList<>();
    try {
        HashMap<String, String> users = loadAllUsers();
        for (String userData : users.values()) {
            String[] parts = userData.split("\\|");
            if (parts.length >= 4 && "teacher".equals(parts[2])) {
                teachers.add(new String[]{parts[0], parts[1]});  // ID, Name
            }
        }
    } catch (Exception e) {
        System.err.println("❌ Error loading teachers: " + e.getMessage());
    }
    return teachers;
}

    
    public static Student getStudent(String id, String password) {
        System.out.println("🔍 Login attempt: ID=" + id + ", Password=" + password);
        
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                System.out.println("❌ No data file found. Add students from Teacher dashboard first.");
                return null;
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);  // Allow empty fields
                System.out.println("📖 Reading: " + line);
                
                if (parts.length >= 1 && parts[0].equals(id)) {
                    System.out.println("✅ STUDENT FOUND!");
                    
                    // Create student with correct password (ID-based login)
                    Student s = new Student(id, parts.length > 1 ? parts[1] : "Unknown", id);
                    
                    // Load attendance data
                    if (parts.length > 3 && !parts[3].isEmpty()) {
                        s.presentClasses = Integer.parseInt(parts[3]);
                    }
                    if (parts.length > 4 && !parts[4].isEmpty()) {
                        s.totalClasses = Integer.parseInt(parts[4]);
                    }
                    
                    // Load marks (exactly 4 values)
                    s.marks.clear();
                    if (parts.length > 5 && !parts[5].isEmpty()) {
                        String[] marksArray = parts[5].split(",");
                        for (String markStr : marksArray) {
                            String trimmed = markStr.trim();
                            if (!trimmed.isEmpty() && trimmed.matches("\\d+")) {
                                s.marks.add(Integer.parseInt(trimmed));
                            } else {
                                s.marks.add(0);
                            }
                        }
                    }
                    // Ensure exactly 4 marks
                    while (s.marks.size() < 4) {
                        s.marks.add(0);
                    }
                    
                    reader.close();
                    System.out.println("✅ LOADED: " + s.getName() + " | Attendance: " + 
                                     s.getAttendancePercentage() + "% | Marks: " + s.marks);
                    return s;
                }
            }
            reader.close();
            System.out.println("❌ Student ID '" + id + "' not found in database.");
        } catch (Exception e) {
            System.err.println("❌ LOAD ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    private static HashMap<String, String> loadAllStudents() {
        HashMap<String, String> students = new HashMap<>();
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                return students;  // New file, empty database
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                if (parts.length > 0 && !parts[0].trim().isEmpty()) {
                    students.put(parts[0].trim(), line);
                }
            }
            reader.close();
        } catch (Exception e) {
            System.err.println("Load error: " + e.getMessage());
        }
        return students;
    }
    private static HashMap<String, String> loadAllUsers() {
        HashMap<String, String> users = new HashMap<>();
        try {
            File file = new File(USERS_FILE);
            if (!file.exists()) return users;
            
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    users.put(parts[0], line);
                }
            }
            reader.close();
        } catch (Exception e) {
            // Empty file OK
        }
        return users;
    }
}

