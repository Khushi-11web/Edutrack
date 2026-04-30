package Edutrack1;

import java.util.ArrayList;
import java.io.*;

public class EventsManager {
    private static ArrayList<String> events = new ArrayList<>();
    private static final String EVENTS_FILE = "events.txt";
    
    public static void addEvent(String title, String date) {
        events.add(title + "|" + date);
        saveEvents();
        System.out.println("✅ Event added: " + title);
    }
    
    public static ArrayList<String> getEvents() {
        loadEvents();
        return new ArrayList<>(events);
    }
    
    public static int getEventCount() {
        loadEvents();
        return events.size();
    }
    
    private static void saveEvents() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EVENTS_FILE))) {
            for (String event : events) {
                writer.println(event);
            }
        } catch (Exception e) {
            System.err.println("❌ Save events failed: " + e.getMessage());
        }
    }
    
    private static void loadEvents() {
        try {
            java.io.File file = new java.io.File(EVENTS_FILE);
            if (!file.exists()) return;
            
            events.clear();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    events.add(line);
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Load events failed: " + e.getMessage());
        }
    }
}
