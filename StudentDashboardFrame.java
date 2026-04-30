package Edutrack1;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultListModel;

public class StudentDashboardFrame extends JFrame {
    private Student student;
    private CardLayout cardLayout;
    private JPanel mainContent;
    
    public StudentDashboardFrame(Student student) {
    this.student = student;
    
    // Password verification
    PasswordDialog dialog = new PasswordDialog(null, student.getName() + " Dashboard", student.getPassword());
    dialog.setVisible(true);
    
    if(!dialog.isAuthenticated()) {
        JOptionPane.showMessageDialog(null, "❌ Session Expired!");
        return;  // Exit if wrong password
    }
    
    initUI();
}

    
    private void initUI() {
    // ✅ SHARP UI - Anti-aliasing OFF + Better fonts
    System.setProperty("sun.java2d.opengl", "false");
    
    setTitle("🎓 EduTrack - " + student.getName());
    setSize(1200, 800);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    
    // ✅ SHARP RENDERING
    getContentPane().setFont(new Font("Segoe UI", Font.PLAIN, 14));
    
    setLayout(new BorderLayout());
    add(createHeader(), BorderLayout.NORTH);
    add(createSidebar(), BorderLayout.WEST);
    
    mainContent = new JPanel(cardLayout = new CardLayout());
    mainContent.setBackground(Color.WHITE);
    mainContent.add(createProfilePanel(), "profile");
    mainContent.add(createResultsPanel(), "results");
    mainContent.add(createAttendancePanel(), "attendance");
    mainContent.add(createEventsPanel(), "events");
    
    add(mainContent, BorderLayout.CENTER);
    add(createStatusBar(), BorderLayout.SOUTH);
    
    cardLayout.show(mainContent, "profile");
    setVisible(true);
}

    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(63, 81, 181));
        header.setPreferredSize(new Dimension(0, 80));
        
        JLabel logo = new JLabel("🎓 Student Portal", JLabel.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JLabel info = new JLabel(student.getId() + " | " + new SimpleDateFormat("MMM dd, yyyy").format(new Date()));
        info.setForeground(Color.WHITE);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        header.add(logo, BorderLayout.CENTER);
        header.add(info, BorderLayout.EAST);
        return header;
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(52, 73, 94));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
        
        // Profile header
        JLabel icon = new JLabel("👨‍🎓", JLabel.CENTER);
        icon.setFont(new Font("Segoe UI", Font.BOLD, 48));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(icon);
        
        JLabel nameLabel = new JLabel(student.getName());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(nameLabel);
        
        sidebar.add(Box.createVerticalStrut(30));
        
        // Navigation buttons
        JButton[] buttons = {
            createSidebarBtn("👤 Profile"),
            createSidebarBtn("📊 Results"),
            createSidebarBtn("📈 Attendance"),
            createSidebarBtn("🎉 Events"),
            createSidebarBtn("🚪 Logout")
        };
        
        for (JButton btn : buttons) {
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(12));
        }
        
        // Actions
        buttons[0].addActionListener(e -> cardLayout.show(mainContent, "profile"));
        buttons[1].addActionListener(e -> cardLayout.show(mainContent, "results"));
        buttons[2].addActionListener(e -> {
            cardLayout.show(mainContent, "attendance");
            checkAttendanceAlert();
        });
        buttons[3].addActionListener(e -> cardLayout.show(mainContent, "events"));
        buttons[4].addActionListener(e -> {
            new LoginFrame();
            dispose();
        });
        
        return sidebar;
    }
    
    private JButton createSidebarBtn(String text) {
    JButton btn = ModernPanel.createModernButton(text);  // ✅ MODERN BUTTON!
    btn.setMaximumSize(new Dimension(180, 50));
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
    return btn;
}

    // ✅ HELPER: Safe mark extraction (same as TeacherFrame)
    // ✅ FIXED: Handle Integer directly (NO SubjectMark needed)
private String getSafeMark(int index) {
    if (student.marks == null || index >= student.marks.size()) {
        return "N/A";
    }
    try {
        Integer markInt = (Integer) student.marks.get(index);  // ✅ INTEGER!
        return markInt != null ? markInt + "%" : "N/A";
    } catch (Exception e) {
        return "N/A";
    }
}

    
    private JPanel createProfilePanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
    
    // Profile card - SHARP
    JPanel profileCard = new JPanel(new GridBagLayout());  // ✅ Better layout
    profileCard.setBackground(new Color(33, 150, 243));
    profileCard.setPreferredSize(new Dimension(400, 250));
    profileCard.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
    
    JLabel icon = new JLabel("👨‍🎓");
    icon.setFont(new Font("Segoe UI Emoji", Font.BOLD, 64));
    icon.setForeground(Color.WHITE);
    
    JLabel name = new JLabel(student.getName());
    name.setForeground(Color.WHITE);
    name.setFont(new Font("Segoe UI", Font.BOLD, 28));
    
    JLabel id = new JLabel("ID: " + student.getId());
    id.setForeground(Color.WHITE);
    id.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    
    profileCard.add(icon, new GridBagConstraints());
    profileCard.add(name, new GridBagConstraints());
    profileCard.add(id, new GridBagConstraints());
    
    // Stats - CRISP
    JPanel statsPanel = new JPanel(new GridLayout(2, 2, 15, 15));
    statsPanel.setBackground(Color.WHITE);
    statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
    
    statsPanel.add(createStatBox("📊 Math", getSafeMark(0), new Color(33, 150, 243)));
    statsPanel.add(createStatBox("📈 Attendance", String.format("%.1f%%", student.getAttendancePercentage()), 
        student.getAttendancePercentage() < 75 ? new Color(244, 67, 54) : new Color(76, 175, 80)));
    statsPanel.add(createStatBox("🎯 Grade", "Calculate", Color.decode("#4CAF50")));
    statsPanel.add(createStatBox("📚 Events", student.events != null ? student.events.size() + "" : "0", 
        new Color(155, 89, 182)));
    
    panel.add(profileCard, BorderLayout.NORTH);
    panel.add(statsPanel, BorderLayout.CENTER);
    return panel;
}
private JPanel createStatsGrid() {
    JPanel grid = new JPanel(new GridLayout(2, 2, 20, 20));
    grid.setOpaque(false);
    
    grid.add(createStatCard("📊 Math", getSafeMark(0), new Color(33, 150, 243)));
    grid.add(createStatCard("📈 Attendance", String.format("%.1f%%", student.getAttendancePercentage()), 
        student.getAttendancePercentage() < 75 ? Color.RED : new Color(76, 175, 80)));
    grid.add(createStatCard("🎯 Grade", student.getGrade() != null ? student.getGrade() : "N/A", Color.ORANGE));
    grid.add(createStatCard("📚 Events", student.events != null ? student.events.size() + "" : "0", new Color(155, 89, 182)));
    
    return grid;
}

private JPanel createStatCard(String label, String value, Color color) {
    ModernPanel card = new ModernPanel();
    card.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 200));
    card.setPreferredSize(new Dimension(150, 100));
    
    JLabel lbl = new JLabel(label, JLabel.CENTER);
    JLabel val = new JLabel(value, JLabel.CENTER);
    val.setFont(new Font("Segoe UI", Font.BOLD, 24));
    lbl.setForeground(Color.WHITE);
    val.setForeground(Color.WHITE);
    
    card.setLayout(new BorderLayout());
    card.add(lbl, BorderLayout.NORTH);
    card.add(val, BorderLayout.CENTER);
    return card;
}


    private JPanel createStatBox(String label, String value, Color color) {
        JPanel box = new JPanel(new BorderLayout());
        box.setBackground(color);
        box.setPreferredSize(new Dimension(150, 100));
        box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        JLabel labelLbl = new JLabel(label, JLabel.CENTER);
        JLabel valueLbl = new JLabel(value, JLabel.CENTER);
        
        labelLbl.setForeground(Color.WHITE);
        valueLbl.setForeground(Color.WHITE);
        valueLbl.setFont(new Font("Arial", Font.BOLD, 24));
        
        box.add(labelLbl, BorderLayout.NORTH);
        box.add(valueLbl, BorderLayout.CENTER);
        
        return box;
    }

    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        JLabel title = new JLabel("📊 Your Results", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(52, 73, 94));
        
        // ✅ REAL SUBJECT MARKS FROM TEACHER
        String[] subjects = {"Maths", "Science", "English", "Social Studies"};
        StringBuilder results = new StringBuilder();
        results.append("📚 SUBJECT RESULTS\n\n");
        
        for (int i = 0; i < 4; i++) {
            String mark = getSafeMark(i);
            results.append(subjects[i]).append(": ").append(mark).append("\n");
        }
        
        // ✅ FIXED: Calculate average HERE using same logic as Student class should use
        double avg = calculateAverage();
        results.append("\n📈 OVERALL AVERAGE: ").append(String.format("%.1f%%", avg));
        results.append("\n🎯 GRADE: ").append(student.getGrade() != null ? student.getGrade() : "N/A");
        
        JTextArea resultArea = new JTextArea(results.toString());
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        resultArea.setBackground(Color.WHITE);
        resultArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(resultArea);
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
    
    // ✅ NEW: Local average calculation to match Student class logic
    // ✅ FIXED: Handle Integer directly
private double calculateAverage() {
    if (student.marks == null || student.marks.isEmpty()) {
        return 0.0;
    }
    
    double sum = 0.0;
    int validCount = 0;
    
    for (Object markObj : student.marks) {
        try {
            Integer markInt = (Integer) markObj;  // ✅ INTEGER!
            if (markInt != null) {
                sum += markInt;
                validCount++;
            }
        } catch (Exception e) {
            continue;
        }
    }
    
    return validCount > 0 ? sum / validCount : 0.0;
}

    
    private JPanel createAttendancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        JLabel title = new JLabel("📈 Attendance Overview", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        
        double percentage = student.getAttendancePercentage();
        Color color = percentage < 75 ? Color.RED : Color.GREEN;
        String status = percentage < 75 ? "🚨 LOW ATTENDANCE" : "✅ GOOD ATTENDANCE";
        
        JLabel bigPercentage = new JLabel(String.format("%.1f%%", percentage), JLabel.CENTER);
        bigPercentage.setFont(new Font("Segoe UI", Font.BOLD, 80));
        bigPercentage.setForeground(color);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(bigPercentage, BorderLayout.CENTER);
        centerPanel.add(new JLabel(status + " | Target: 75%", JLabel.CENTER), BorderLayout.SOUTH);
        
        JLabel attendanceDetail = new JLabel("Present: " + student.getPresentClasses() + 
            " / Total: " + student.getTotalClasses(), JLabel.CENTER);
        attendanceDetail.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        attendanceDetail.setForeground(Color.GRAY);
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(attendanceDetail, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createEventsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        JLabel title = new JLabel("🎉 Your Events", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        
        // ✅ REAL EVENTS FROM TEACHER
        DefaultListModel<String> eventsModel = new DefaultListModel<>();
        
        if (student.events != null && !student.events.isEmpty()) {
            for (String event : student.events) {
                eventsModel.addElement("📅 " + event);
            }
        } else {
            eventsModel.addElement("📭 No events scheduled yet");
        }
        
        JList<String> eventList = new JList<>(eventsModel);
        eventList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        eventList.setBackground(Color.WHITE);
        eventList.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        JScrollPane scroll = new JScrollPane(eventList);
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
    
    private void checkAttendanceAlert() {
        if (student.needsAlert()) {
            JOptionPane.showMessageDialog(this, 
                "🚨 ATTENDANCE ALERT!\n\n" +
                "Your current attendance: " + String.format("%.1f%%", student.getAttendancePercentage()) + "\n" +
                "Minimum required: 75%\n\n" +
                "Please attend more classes!", 
                "Low Attendance Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private JPanel createStatusBar() {
        JPanel status = new JPanel(new FlowLayout(FlowLayout.LEFT));
        status.setBackground(new Color(236, 240, 241));
        status.setPreferredSize(new Dimension(0, 35));
        status.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        
        JLabel statusLabel = new JLabel("🟢 Online | Attendance: " + 
            String.format("%.1f%%", student.getAttendancePercentage()) + " | " + student.getName());
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusLabel.setForeground(new Color(52, 73, 94));
        status.add(statusLabel);
        return status;
    }
}
