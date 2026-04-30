package Edutrack1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AttendanceFrame extends JFrame {
    private Teacher teacher;
    private JPanel content;
    
    public AttendanceFrame(Teacher teacher) {
        this.teacher = teacher;
        initUI();
    }
    
    private void initUI() {
        setTitle("Attendance - " + teacher.name);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("📊 Mark Attendance Today", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(33,150,243));
        content.add(title, BorderLayout.NORTH);
        
        // Table with checkboxes
        String[] columns = {"Select", "ID", "Name", "Present", "Total", "Attendance %", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Student s : teacher.students) {
            model.addRow(new Object[]{
                false,  // Checkbox
                s.getId(), 
                s.getName(), 
                s.getPresentClasses(), 
                s.getTotalClasses(),
                String.format("%.1f%%", s.getAttendancePercentage()),
                s.needsAlert() ? "🚨 LOW" : "✅ OK"
            });
        }
        
        JTable table = new JTable(model) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : super.getColumnClass(column);
            }
        };
        table.setRowHeight(30);
        JScrollPane scroll = new JScrollPane(table);
        content.add(scroll, BorderLayout.CENTER);
        
        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        
        JButton markPresentBtn = new JButton("✅ Mark Present (Selected)");
        markPresentBtn.setBackground(new Color(76,175,80));
        markPresentBtn.setForeground(Color.WHITE);
        markPresentBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JButton markAbsentBtn = new JButton("❌ Mark Absent (Selected)");
        markAbsentBtn.setBackground(new Color(244,67,54));
        markAbsentBtn.setForeground(Color.WHITE);
        markAbsentBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JButton refreshBtn = new JButton("🔄 Refresh");
        refreshBtn.setBackground(new Color(33,150,243));
        refreshBtn.setForeground(Color.WHITE);
        
        markPresentBtn.addActionListener(e -> markAttendance(table, model, true));
        markAbsentBtn.addActionListener(e -> markAttendance(table, model, false));
        refreshBtn.addActionListener(e -> refreshTable(table, model));
        
        btnPanel.add(markPresentBtn);
        btnPanel.add(markAbsentBtn);
        btnPanel.add(refreshBtn);
        content.add(btnPanel, BorderLayout.SOUTH);
        
        add(content);
        setVisible(true);
    }
    
    private void markAttendance(JTable table, DefaultTableModel model, boolean present) {
        for (int row = 0; row < model.getRowCount(); row++) {
            if ((Boolean) model.getValueAt(row, 0)) {
                String id = (String) model.getValueAt(row, 1);
                for (Student s : teacher.students) {
                    if (s.getId().equals(id)) {
                        s.markAttendance(present);
                        model.setValueAt(s.getPresentClasses(), row, 3);
                        model.setValueAt(s.getTotalClasses(), row, 4);
                        model.setValueAt(String.format("%.1f%%", s.getAttendancePercentage()), row, 5);
                        model.setValueAt(s.needsAlert() ? "🚨 LOW" : "✅ OK", row, 6);
                        model.setValueAt(false, row, 0); // Uncheck
                        break;
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Attendance marked successfully!");
    }
    
    private void refreshTable(JTable table, DefaultTableModel model) {
        for (int row = 0; row < model.getRowCount(); row++) {
            String id = (String) model.getValueAt(row, 1);
            for (Student s : teacher.students) {
                if (s.getId().equals(id)) {
                    model.setValueAt(s.getPresentClasses(), row, 3);
                    model.setValueAt(s.getTotalClasses(), row, 4);
                    model.setValueAt(String.format("%.1f%%", s.getAttendancePercentage()), row, 5);
                    model.setValueAt(s.needsAlert() ? "🚨 LOW" : "✅ OK", row, 6);
                    break;
                }
            }
        }
    }
    
    public JPanel getContent() { return content; }
}
