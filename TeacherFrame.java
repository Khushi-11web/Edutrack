package Edutrack1;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

public class TeacherFrame extends JFrame {
    private Teacher teacher;
    private CardLayout cardLayout;
    private JPanel mainContent;
    private JTable table;
    private JTable attTable;
    private DefaultTableModel attModel;
    
    public TeacherFrame(Teacher t) {
        teacher = t != null ? t : new Teacher("Teacher", "123");
        initUI();
    }
    
    private void initUI() {
        setTitle("👨‍🏫 EduTrack - " + teacher.name);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        setLayout(new BorderLayout());
        
        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(new Color(40, 150, 80));
        header.setPreferredSize(new Dimension(0, 70));
        JLabel title = new JLabel("👨‍🏫 " + teacher.name + " | Students: " + teacher.students.size());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        header.add(title);
        
        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(52, 73, 94));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        
        JButton logoutBtn = createSidebarButton("🚪 Logout");
        logoutBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });
        
        JButton[] buttons = {
            createSidebarButton("👥 Students"),
            createSidebarButton("📊 Attendance"), 
            createSidebarButton("📈 Marks"),
            createSidebarButton("🎉 Events"),
            createSidebarButton("⚠️ Alerts")
        };
        
        sidebar.add(logoutBtn);
        sidebar.add(Box.createVerticalStrut(20));
        for (JButton btn : buttons) {
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(8));
        }
        
        mainContent = new JPanel(cardLayout = new CardLayout());
        mainContent.add(createStudentsPanel(), "students");
        mainContent.add(createAttendancePanel(), "attendance");
        mainContent.add(createMarksPanel(), "marks");
        mainContent.add(createEventsPanel(), "events");
        mainContent.add(createAlertsPanel(), "alerts");
        
        // Action listeners
        buttons[0].addActionListener(e -> cardLayout.show(mainContent, "students"));
        buttons[1].addActionListener(e -> {
            cardLayout.show(mainContent, "attendance");
            refreshAttendanceTable();
        });
        buttons[2].addActionListener(e -> {
            cardLayout.show(mainContent, "marks");
            refreshMarksTable();
        });
        buttons[3].addActionListener(e -> cardLayout.show(mainContent, "events"));
        buttons[4].addActionListener(e -> {
            cardLayout.show(mainContent, "alerts");
            teacher.checkAlerts();
        });
        
        add(header, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);
        setVisible(true);
    }
    
    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(44, 62, 80));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setMaximumSize(new Dimension(190, 45));
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
    
    private void refreshAllTables() {
        refreshMarksTable();
        refreshAttendanceTable();
    }
    
    private void refreshMarksTable() {
        if (table != null && table.getModel() instanceof DefaultTableModel) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (Student s : teacher.students) {
                Object[] row = {
                    s.getId(), s.getName(),
                    getSafeMark(s, 0), getSafeMark(s, 1),
                    getSafeMark(s, 2), getSafeMark(s, 3),
                    s.getGrade()
                };
                model.addRow(row);
            }
        }
    }
    
    private void refreshAttendanceTable() {
        if (attModel != null) {
            attModel.setRowCount(0);
            for (Student s : teacher.students) {
                attModel.addRow(new Object[]{
                    s.getId(), s.getName(), s.getPresentClasses(),
                    s.getTotalClasses(), String.format("%.1f%%", s.getAttendancePercentage())
                });
            }
        }
    }
    
    private String getSafeMark(Student s, int index) {
        if (s.marks == null || index >= s.marks.size()) return "N/A";
        try {
            Object markObj = s.marks.get(index);
            if (markObj instanceof Integer) {
                return ((Integer)markObj) + "%";
            }
            return "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }
    
    private JPanel createStudentsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        DefaultListModel<Student> model = new DefaultListModel<>();
        for (Student s : teacher.students) model.addElement(s);
        
        JList<Student> list = new JList<>(model);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
        
        JPanel input = new JPanel(new FlowLayout(FlowLayout.LEFT));
        input.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JTextField idField = new JTextField(12);
        JTextField nameField = new JTextField(15);
        JButton addBtn = createSidebarButton("➕ Add Student");
        
        input.add(new JLabel("ID:"));
        input.add(idField);
        input.add(new JLabel("Name:"));
        input.add(nameField);
        input.add(addBtn);
        
        addBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            
            if(id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Enter both ID & Name!");
                return;
            }
            
            // Check for duplicate ID
            for(Student existing : teacher.students) {
                if(existing.getId().equals(id)) {
                    JOptionPane.showMessageDialog(this, "⚠️ Student ID '" + id + "' already exists!");
                    return;
                }
            }
            
            Student s = new Student(id, name);
            teacher.addStudent(s);
            model.addElement(s);
            DatabaseManager.saveStudent(s);
            refreshAllTables();
            
            idField.setText(""); nameField.setText("");
            JOptionPane.showMessageDialog(this, "✅ Student '" + name + "' added!\n📱 Password: 123");
        });
        
        panel.add(new JLabel("👥 Students Management", JLabel.CENTER), BorderLayout.NORTH);
        panel.add(input, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createAttendancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String[] cols = {"ID", "Name", "Present", "Total", "%"};
        attModel = new DefaultTableModel(cols, 0);
        attTable = new JTable(attModel);
        attTable.setGridColor(new Color(230, 230, 230));
        JScrollPane scroll = new JScrollPane(attTable);
        
        refreshAttendanceTable();
        
        JButton markPresent = createSidebarButton("✅ Mark Present");
        JButton markAbsent = createSidebarButton("❌ Mark Absent");
        
        markPresent.addActionListener(e -> markAttendance(true));
        markAbsent.addActionListener(e -> markAttendance(false));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(markPresent);
        buttonPanel.add(markAbsent);
        
        panel.add(new JLabel("📊 Attendance Management", JLabel.CENTER), BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    private void markAttendance(boolean isPresent) {
        int row = attTable.getSelectedRow();
        if (row >= 0) {
            String id = (String) attTable.getValueAt(row, 0);
            for (Student s : teacher.students) {
                if (s.getId().equals(id)) {
                    s.markAttendance(isPresent);
                    DatabaseManager.saveStudent(s);
                    refreshAllTables();
                    JOptionPane.showMessageDialog(this, 
                        (isPresent ? "✅" : "❌") + " " + s.getName() + " marked " + 
                        (isPresent ? "present" : "absent") + "!");
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "❌ Select a student first!");
        }
    }
    
    private JPanel createMarksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String[] cols = {"ID", "Name", "Test 1", "Test 2", "Test 3", "Final", "Grade"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);
        
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JTextField markField = new JTextField(8);
        JButton updateBtn = createSidebarButton("✏️ Update Marks");
        JButton calculateBtn = createSidebarButton("📊 Refresh Grades");
        
        inputPanel.add(new JLabel("Marks (%):"));
        inputPanel.add(markField);
        inputPanel.add(updateBtn);
        inputPanel.add(calculateBtn);
        
        updateBtn.addActionListener(e -> updateStudentMarks(markField));
        calculateBtn.addActionListener(e -> calculateAllGrades());
        
        panel.add(new JLabel("📈 Marks Management", JLabel.CENTER), BorderLayout.NORTH);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        
        refreshMarksTable();
        return panel;
    }
    
    private void calculateAllGrades() {
        refreshMarksTable();
        JOptionPane.showMessageDialog(this, "✅ All grades updated & displayed!");
    }
    
    private void updateStudentMarks(JTextField markField) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            String id = (String) table.getValueAt(row, 0);
            String marksText = markField.getText().trim();
            
            if (marksText.isEmpty() || !marksText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "❌ Enter valid number (0-100)!");
                return;
            }
            
            int mark = Integer.parseInt(marksText);
            if (mark < 0 || mark > 100) {
                JOptionPane.showMessageDialog(this, "❌ Marks must be 0-100!");
                return;
            }
            
            for (Student s : teacher.students) {
                if (s.getId().equals(id)) {
                    s.addMark(mark);
                    s.saveToDatabase();
                    refreshAllTables();
                    JOptionPane.showMessageDialog(this, "✅ " + s.getName() + " marks updated!");
                    break;
                }
            }
            markField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "❌ Select a student first!");
        }
    }
    
    private JPanel createEventsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("🎉 Events Panel - Coming Soon!", JLabel.CENTER), BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createAlertsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("⚠️ Alerts Panel - Coming Soon!", JLabel.CENTER), BorderLayout.CENTER);
        return panel;
    }
}
