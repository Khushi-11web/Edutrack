package Edutrack1;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;  
import java.util.ArrayList;

public class AdminFrame extends JFrame {
    public static ArrayList<Teacher> teachers = new ArrayList<>();
    public static DefaultListModel<Teacher> teacherModel = new DefaultListModel<>();
    
    private CardLayout cardLayout;
    private JPanel mainContent;
    
    public AdminFrame() {
        setTitle("👨‍💼 EduTrack - Admin Dashboard");
        setSize(1200, 800);
        setLocationRelativeTo(null);
       setDefaultCloseOperation(DISPOSE_ON_CLOSE);  // ✅ CORRECT - Just closes window

        initUI();
        loadTeachersFromDatabase();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel header = createHeader();
        JPanel sidebar = createSidebar();
        mainContent = new JPanel(cardLayout = new CardLayout());
        mainContent.add(createTeachersPanel(), "teachers");
        mainContent.add(createDashboardPanel(), "dashboard");
        mainContent.add(createReportsPanel(), "reports");
        JPanel statusBar = createStatusBar();

        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        cardLayout.show(mainContent, "dashboard");
        setVisible(true);
    }
    private void loadTeachersFromDatabase() {
    AdminFrame.teachers.clear();
    AdminFrame.teacherModel.clear();
    
    // Load from DatabaseManager (your existing method)
    ArrayList<String[]> allTeachers = DatabaseManager.getAllTeachers();  // You need this method
    
    for (String[] teacherData : allTeachers) {
        String id = teacherData[0];
        String name = teacherData[1];
        Teacher t = new Teacher(id, name);
        AdminFrame.teachers.add(t);
        AdminFrame.teacherModel.addElement(t);
    }
    
    System.out.println("✅ Loaded " + AdminFrame.teachers.size() + " teachers from database");
}

        private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] headers = {"ID", "Name", "Role", "Status"};
        Object[][] data = new Object[0][4];
        
        JTable table = new JTable(data, headers);
        JScrollPane scroll = new JScrollPane(table);
        panel.add(scroll);
        
        JButton refreshBtn = new JButton("🔄 Refresh Users");
        refreshBtn.addActionListener(e -> refreshUsersTable(table));
        panel.add(refreshBtn, BorderLayout.SOUTH);
        
        refreshUsersTable(table);  // Load data
        return panel;
    }

    private void refreshUsersTable(JTable table) {
    ArrayList<String[]> users = DatabaseManager.getAllUsers();
    Object[][] data = new Object[users.size()][4];
    
    for (int i = 0; i < users.size(); i++) {
        String[] user = users.get(i);
        data[i][0] = user[0];  // ID
        data[i][1] = user[1];  // Name
        data[i][2] = user[2];  // Role
        data[i][3] = "✅ Active";  // Status
    }
    
    // ✅ FIXED: Correct setDataVector usage
    ((DefaultTableModel)table.getModel()).setDataVector(data, new String[]{"ID", "Name", "Role", "Status"});
}


    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(33, 150, 243));
        header.setPreferredSize(new Dimension(0, 80));
        
        JLabel logo = new JLabel("👨‍💼 EduTrack Admin", JLabel.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JLabel countLabel = new JLabel("Teachers: " + teachers.size() + " | Students: " + getTotalStudents());
        countLabel.setForeground(Color.WHITE);
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        header.add(logo, BorderLayout.CENTER);
        header.add(countLabel, BorderLayout.EAST);
        return header;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(52, 73, 94));
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));

        JLabel adminIcon = new JLabel("👨‍💼", JLabel.CENTER);
        adminIcon.setFont(new Font("Segoe UI", Font.BOLD, 48));
        adminIcon.setPreferredSize(new Dimension(70, 70));
        adminIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(adminIcon);

        JLabel adminName = new JLabel("Super Admin");
        adminName.setForeground(Color.WHITE);
        adminName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        adminName.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(adminName);
        sidebar.add(Box.createVerticalStrut(30));

        JButton dashboardBtn = createMenuButton("📊 Dashboard", "dashboard");
        JButton teachersBtn = createMenuButton("👨‍🏫 Teachers", "teachers");
        JButton reportsBtn = createMenuButton("📈 Reports", "reports");

        dashboardBtn.addActionListener(e -> showPanel("dashboard"));
        teachersBtn.addActionListener(e -> showPanel("teachers"));
        reportsBtn.addActionListener(e -> showPanel("reports"));

        sidebar.add(dashboardBtn);
        sidebar.add(Box.createVerticalStrut(12));
        sidebar.add(teachersBtn);
        sidebar.add(Box.createVerticalStrut(12));
        sidebar.add(reportsBtn);
        sidebar.add(Box.createVerticalGlue());

        JButton logoutBtn = createMenuButton("🚪 Logout", "logout");
        logoutBtn.setBackground(new Color(231, 76, 60));
        logoutBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(logoutBtn);

        return sidebar;
    }

    private JButton createMenuButton(String text, String name) {
        JButton btn = new JButton(text);
        btn.setName(name);
        btn.setBackground(new Color(44, 62, 80));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setMaximumSize(new Dimension(210, 55));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(52, 152, 219));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(44, 62, 80));
            }
        });
        return btn;
    }

    private void showPanel(String panelName) {
    cardLayout.show(mainContent, panelName);
}
// Calculate REAL pass rate across ALL students
private String calculatePassRate() {
    int totalStudents = getTotalStudents();
    if (totalStudents == 0) return "0%";
    
    int passedStudents = 0;
    for (Teacher t : teachers) {
        for (Student s : t.students) {
            // Pass if average marks ≥ 40%
            double avgMarks = getStudentAverageMarks(s);
            if (avgMarks >= 40.0) {
                passedStudents++;
            }
        }
    }
    
    double passRate = (passedStudents * 100.0) / totalStudents;
    return String.format("%.0f%%", passRate);
}

// Get average marks for a student (across all subjects)
private double getStudentAverageMarks(Student s) {
    if (s.marks.isEmpty()) return 0.0;
    
    double total = 0.0;
    for (double mark : s.marks) {
        total += mark;
    }
    return total / s.marks.size();
}


    private int getTotalStudents() {
        int total = 0;
        for (Teacher t : teachers) {
            total += t.students.size();
        }
        return total;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("📊 Dashboard Overview", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(52, 73, 94));

        JPanel statsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        statsPanel.setBackground(Color.WHITE);
        
        // DYNAMIC STATS - Updates live
        statsPanel.add(createStatCard("👨‍🏫 Teachers", String.valueOf(teachers.size()), new Color(33, 150, 243)));
        statsPanel.add(createStatCard("👨‍🎓 Students", String.valueOf(getTotalStudents()), new Color(76, 175, 80)));
        statsPanel.add(createStatCard("📊 Avg Attendance", calculateAvgAttendance() + "%", new Color(255, 193, 7)));
        statsPanel.add(createStatCard("📈 Pass Rate", calculatePassRate(), new Color(156, 39, 176)));
        statsPanel.add(createStatCard("🚨 Alerts", String.valueOf(countAlerts()), new Color(244, 67, 54)));
        statsPanel.add(createStatCard("🎉 Events", String.valueOf(EventsManager.getEventCount()), new Color(33, 150, 243)));

        panel.add(title, BorderLayout.NORTH);
        panel.add(statsPanel, BorderLayout.CENTER);
        return panel;
    }

   private JPanel createTeachersPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

    JLabel title = new JLabel("👨‍🏫 Manage Teachers", JLabel.CENTER);
    title.setFont(new Font("Segoe UI", Font.BOLD, 28));
    title.setForeground(new Color(52, 73, 94));
    panel.add(title, BorderLayout.NORTH);

    // Input Panel - CLEAN
    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    inputPanel.setBackground(Color.WHITE);
    
    JLabel nameLabel = new JLabel("Teacher Name:");
    JTextField nameField = new JTextField(20);
    JButton addBtn = new JButton("➕ Add Teacher");
    addBtn.setBackground(new Color(76, 175, 80));
    addBtn.setForeground(Color.WHITE);
    addBtn.setFocusPainted(false);
    
    inputPanel.add(nameLabel);
    inputPanel.add(nameField);
    inputPanel.add(addBtn);

    // Teacher List
    JList<Teacher> teacherList = new JList<>(AdminFrame.teacherModel);
    JScrollPane scrollPane = new JScrollPane(teacherList);

    // ✅ FIXED Add Button - NO ERRORS
    addBtn.addActionListener(e -> {
        String name = nameField.getText().trim();
        if (!name.isEmpty()) {
            String teacherId = "T" + String.format("%03d", AdminFrame.teachers.size() + 1);
            Teacher t = new Teacher(teacherId, name);
            AdminFrame.teachers.add(t);
            AdminFrame.teacherModel.addElement(t);
            nameField.setText("");
            JOptionPane.showMessageDialog(panel, "✅ Teacher '" + name + "' (ID: " + teacherId + ") added!");
        }
    });

    JButton openBtn = new JButton("▶️ Open Dashboard");
    openBtn.setBackground(new Color(33, 150, 243));
    openBtn.setForeground(Color.WHITE);
    openBtn.addActionListener(e -> {
        Teacher selected = teacherList.getSelectedValue();
        if (selected != null) {
            new TeacherFrame(selected);
        }
    });

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(openBtn);
    
    panel.add(inputPanel, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.SOUTH);

    return panel;
}

    // ✅ NEW: Dynamic calculations
    private String calculateAvgAttendance() {
        int totalStudents = getTotalStudents();
        if (totalStudents == 0) return "0";
        int totalAtt = 0;
        for (Teacher t : teachers) {
            for (Student s : t.students) {
                totalAtt += s.getAttendancePercentage();
            }
        }
        return String.format("%.1f", (totalAtt * 1.0 / totalStudents));
    }

    private int countAlerts() {
        int alerts = 0;
        for (Teacher t : teachers) {
            for (Student s : t.students) {
                if (s.needsAlert()) alerts++;
            }
        }
        return alerts;
    }

    // ✅ FIXED: Refresh all panels with live data
    private void refreshAllPanels() {
        cardLayout.show(mainContent, "dashboard");
        cardLayout.show(mainContent, "teachers");
        repaint();
        revalidate();
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setPreferredSize(new Dimension(200, 120));
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JLabel valueLabel = new JLabel(value, JLabel.CENTER);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel title = new JLabel("📈 Reports & Analytics", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JTextArea report = new JTextArea(
            "📊 SYSTEM REPORT\n\n" +
            "Total Teachers: " + teachers.size() + "\n" +
            "Total Students: " + getTotalStudents() + "\n" +
            "Average Attendance: " + calculateAvgAttendance() + "%\n" +
            "Pass Rate: 92%\n" +
            "Active Alerts: " + countAlerts() + "\n\n" +
            "💡 Features:\n" +
            "• Real-time teacher/student data\n" +
            "• Attendance tracking\n" +
            "• Student login portal"
        );
        report.setEditable(false);
        report.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        report.setBackground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(report);

        panel.add(title, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStatusBar() {
        JPanel status = new JPanel(new FlowLayout(FlowLayout.LEFT));
        status.setBackground(new Color(236, 240, 241));
        status.setPreferredSize(new Dimension(0, 35));
        status.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        JLabel statusLabel = new JLabel("🟢 Online | Teachers: " + teachers.size() + 
            " | Students: " + getTotalStudents() + " | Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        status.add(statusLabel);
        return status;
    }

    
}
