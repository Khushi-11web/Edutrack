package Edutrack1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EventFrame extends JFrame {
    private Teacher teacher;
    private JPanel content;
    private DefaultListModel<String> eventModel;
    
    public EventFrame(Teacher teacher) {
        this.teacher = teacher;
        initUI();
    }
    
    private void initUI() {
        setTitle("🎉 Events - " + teacher.name);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        
        // Title
        JLabel title = new JLabel("📅 School Events Dashboard", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(52, 73, 94));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        JLabel eventLabel = new JLabel("Event Title:");
        JTextField eventField = new JTextField(25);
        eventField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        JButton addEventBtn = new JButton("➕ Add Event");
        addEventBtn.setBackground(new Color(76, 175, 80));
        addEventBtn.setForeground(Color.WHITE);
        addEventBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addEventBtn.setFocusPainted(false);
        
        inputPanel.add(eventLabel);
        inputPanel.add(eventField);
        inputPanel.add(addEventBtn);
        
        // ✅ SHARED EVENT LIST - SYNCED WITH ADMIN
        eventModel = new DefaultListModel<>();
        loadEvents();  // Load from EventsManager
        
        JList<String> eventList = new JList<>(eventModel);
        eventList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        eventList.setBackground(new Color(248, 250, 252));
        eventList.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        JScrollPane scroll = new JScrollPane(eventList);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // ✅ SHARED EVENTS - ADMIN & TEACHER SEE SAME EVENTS
        addEventBtn.addActionListener(e -> {
            String eventTitle = eventField.getText().trim();
            if (!eventTitle.isEmpty()) {
                // ✅ SAVE TO SHARED DATABASE
                EventsManager.addEvent(eventTitle, "2026-03-" + (10 + EventsManager.getEventCount()));
                
                eventField.setText("");
                loadEvents();  // Refresh list
                JOptionPane.showMessageDialog(this, "✅ Event '" + eventTitle + "' added! (Admin dashboard updated)");
            }
        });
        
        JButton refreshBtn = new JButton("🔄 Refresh");
        refreshBtn.setBackground(new Color(33, 150, 243));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        refreshBtn.setFocusPainted(false);
        refreshBtn.addActionListener(e -> loadEvents());
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(refreshBtn);
        
        content.add(title, BorderLayout.NORTH);
        content.add(inputPanel, BorderLayout.NORTH);
        content.add(scroll, BorderLayout.CENTER);
        content.add(buttonPanel, BorderLayout.SOUTH);
        add(content);
        setVisible(true);
    }
    
    // ✅ LOAD SHARED EVENTS (same as Admin dashboard)
    private void loadEvents() {
        eventModel.clear();
        ArrayList<String> events = EventsManager.getEvents();
        for (String event : events) {
            eventModel.addElement(event.split("\\|")[0]);  // Show only title
        }
    }
    
    public JPanel getContent() { return content; }
}
