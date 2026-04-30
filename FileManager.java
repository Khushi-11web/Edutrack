package Edutrack1;

import java.io.*;

public class FileManager {
    public static void saveData(Teacher teacher, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(teacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static Teacher loadData(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Teacher) ois.readObject();
        } catch (Exception e) {
            return new Teacher("Default");
        }
    }
}
