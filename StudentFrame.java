package Edutrack1;

import javax.swing.*;
import java.awt.*;

public class StudentFrame extends JFrame {
    private Student student;
    
    public StudentFrame(Student student) {
        this.student = student;
        initUI();
    }
    
    private void initUI() {
        setTitle("Student Profile - " + student.getName());
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);
        
        // TITLE
        JLabel title = new JLabel("👨‍🎓 " + student.getName(), JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(40, 150, 80));
        
        // RESULTS & INFO PANEL
        JTextArea resultArea = new JTextArea();
        resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(245, 245, 245));
        resultArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // PERSONAL INFO
        resultArea.append("📋 STUDENT PROFILE\n");
        resultArea.append("==================\n\n");
        resultArea.append("ID: " + student.getId() + "\n");
        resultArea.append("📊 Average Result: " + String.format("%.1f%%", student.getAverageResult()) + "\n");
        resultArea.append("📋 Attendance: " + String.format("%.1f%%", student.getAttendancePercentage()) + "\n\n");
        
        // ATTENDANCE ALERT
        if (student.needsAlert()) {
            resultArea.append("🚨 LOW ATTENDANCE ALERT! (<75%)\n");
            resultArea.append("💡 Attend more classes!\n\n");
        }
        
        // SUBJECT MARKS
        resultArea.append("📚 SUBJECT MARKS\n");
        resultArea.append("===============\n");
        String[] subjects = {"Maths", "Science", "English", "SST"};
        for (int i = 0; i < student.marks.size(); i++) {
            resultArea.append("• " + subjects[i] + ": " + student.marks.get(i) + "%\n");
        }
        resultArea.append("\n");
        
        // EVENTS SECTION - FIXED!
        resultArea.append("📅 UPCOMING EVENTS\n");
        resultArea.append("=================\n");
        if (student.events.isEmpty()) {
            resultArea.append("No events scheduled\n");
        } else {
            for (int i = 0; i < Math.min(5, student.events.size()); i++) {
                resultArea.append("• " + student.events.get(i) + "\n");
            }
            if (student.events.size() > 5) {
                resultArea.append("... and " + (student.events.size() - 5) + " more\n");
            }
        }
        
        JScrollPane scroll = new JScrollPane(resultArea);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // BUTTONS
        JPanel buttonPanel = new JPanel();
        JButton backBtn = new JButton("🏠 Back to Login");
        backBtn.setBackground(new Color(200, 50, 50));
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });
        buttonPanel.add(backBtn);
        
        main.add(title, BorderLayout.NORTH);
        main.add(scroll, BorderLayout.CENTER);
        main.add(buttonPanel, BorderLayout.SOUTH);
        add(main);
        setVisible(true);
    }
}
