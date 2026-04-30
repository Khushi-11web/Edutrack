package Edutrack1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    
    // ✨ PROFESSIONAL COLOR PALETTE
    private static final Color PRIMARY_GREEN = new Color(34, 197, 94);
    private static final Color SECONDARY_BLUE = new Color(59, 130, 246);
    private static final Color ACCENT_PURPLE = new Color(168, 85, 247);
    private static final Color BACKGROUND_LIGHT = new Color(249, 250, 251);
    private static final Color CARD_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_DARK = new Color(17, 24, 39);
    private static final Color TEXT_LIGHT = new Color(107, 114, 128);
    
    public LoginFrame() {
        setTitle(" EduTrack Pro - Login Portal");
        setSize(520, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initUI();
    }
    
    private void initUI() {
     
        setLayout(new BorderLayout());
        
        JPanel bgPanel = new GradientBackgroundPanel();
        bgPanel.setLayout(new BorderLayout());
        
        JLabel title = new JLabel("Welcome to EduTrack", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(PRIMARY_GREEN);
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        bgPanel.add(title, BorderLayout.NORTH);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(CARD_WHITE);
        tabbedPane.setForeground(TEXT_DARK);
        
        tabbedPane.addTab("Admin", createAdminLoginPanel());
        tabbedPane.addTab("Teacher", createTeacherLoginPanel());
        tabbedPane.addTab("Student", createStudentLoginPanel());
        
        bgPanel.add(tabbedPane, BorderLayout.CENTER);
        add(bgPanel);
        setVisible(true);
    }
    
    // ✅ Admin Panel (ALREADY WORKING)
    private JPanel createAdminLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel adminTitle = new JLabel("Admin Login", JLabel.CENTER);
        adminTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        adminTitle.setForeground(SECONDARY_BLUE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(adminTitle, gbc);
        
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userLabel.setForeground(TEXT_DARK);
        gbc.gridy = 1; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        panel.add(userLabel, gbc);
        
        JTextField userField = new JTextField("admin", 20);
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        userField.setBackground(new Color(248, 250, 252));
        userField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_BLUE, 2),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        gbc.gridx = 1;
        panel.add(userField, gbc);
        
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passLabel.setForeground(TEXT_DARK);
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(passLabel, gbc);
        
        JPasswordField passField = new JPasswordField("123", 20);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passField.setBackground(new Color(248, 250, 252));
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_BLUE, 2),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        gbc.gridx = 1;
        panel.add(passField, gbc);
        
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(SECONDARY_BLUE);
        loginBtn.setForeground(CARD_WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setPreferredSize(new Dimension(150, 50));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginBtn, gbc);
        
        loginBtn.addActionListener(e -> {
            if ("admin".equals(userField.getText()) && "123".equals(new String(passField.getPassword()))) {
                new AdminFrame();
                //dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        return panel;
    }
    
    // ✅ Teacher Panel (FIXED)
    private JPanel createTeacherLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        
        JLabel teacherTitle = new JLabel("Teacher Login", JLabel.CENTER);
        teacherTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        teacherTitle.setForeground(PRIMARY_GREEN);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(teacherTitle, gbc);
        
        JLabel teacherLabel = new JLabel("Teacher Name:");
        teacherLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        teacherLabel.setForeground(TEXT_DARK);
        gbc.gridy = 1; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        panel.add(teacherLabel, gbc);
        
        JTextField teacherField = new JTextField(20);
        teacherField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        teacherField.setBackground(new Color(248, 250, 252));
        teacherField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_GREEN, 2),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        gbc.gridx = 1;
        panel.add(teacherField, gbc);
        
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(PRIMARY_GREEN);
        loginBtn.setForeground(CARD_WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setPreferredSize(new Dimension(150, 50));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginBtn, gbc);
        
        loginBtn.addActionListener(e -> {
            String teacherName = teacherField.getText().trim();
            if (!teacherName.isEmpty()) {
                Teacher teacher = new Teacher(teacherName, "123");
                new TeacherFrame(teacher);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Enter teacher name!");
            }
        });
        
        return panel;
    }
    
    // ✅ Student Panel (FIXED)
    private JPanel createStudentLoginPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(CARD_WHITE);
    panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(20, 20, 20, 20);
    
    JLabel studentTitle = new JLabel("Student Login", JLabel.CENTER);
    studentTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
    studentTitle.setForeground(ACCENT_PURPLE);
    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(studentTitle, gbc);
    
    JLabel idLabel = new JLabel("Student ID:");
    idLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    idLabel.setForeground(TEXT_DARK);
    gbc.gridy = 1; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
    gbc.gridx = 0;
    panel.add(idLabel, gbc);
    
    final JTextField idField = new JTextField(20);
    idField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    idField.setBackground(new Color(248, 250, 252));
    idField.setBorder(BorderFactory.createLineBorder(ACCENT_PURPLE, 2));
    gbc.gridx = 1;
    panel.add(idField, gbc);
    
    JLabel passLabel = new JLabel("Password:");
    passLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    passLabel.setForeground(TEXT_DARK);
    gbc.gridy = 2; gbc.gridx = 0;
    panel.add(passLabel, gbc);
    
    final JPasswordField passField = new JPasswordField(20);
    passField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    passField.setBackground(new Color(248, 250, 252));
    passField.setBorder(BorderFactory.createLineBorder(ACCENT_PURPLE, 2));
    gbc.gridx = 1;
    panel.add(passField, gbc);
    
    JButton loginBtn = new JButton("View Dashboard");
    loginBtn.setBackground(ACCENT_PURPLE);
    loginBtn.setForeground(CARD_WHITE);
    loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
    loginBtn.setFocusPainted(false);
    loginBtn.setBorderPainted(false);
    loginBtn.setPreferredSize(new Dimension(180, 50));
    loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    panel.add(loginBtn, gbc);
    
    // ✅ CLEAN LOGIN LOGIC
   loginBtn.addActionListener(e -> {
    String studentId = idField.getText().trim();
    String password = new String(passField.getPassword()).trim();
    
    if (studentId.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Enter Student ID!");
        return;
    }
    
    Student student = DatabaseManager.getStudent(studentId, password);
    if (student != null) {
        System.out.println("Student login: " + student.getName());
        new StudentDashboardFrame(student);
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, 
            "ID '" + studentId + "' NOT FOUND!\n💡 Add from Teacher first", 
            "Login Failed", JOptionPane.ERROR_MESSAGE);
        idField.setText(""); 
        passField.setText("");
    }
});
   return panel;  // Method ends
}  
    
    // ✨ MAIN CLASS METHOD
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginFrame();
        });
    }
} //✅ MAIN CLASS CLOSED!

// ✨ GRADIENT BACKGROUND CLASS
class GradientBackgroundPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        GradientPaint gp = new GradientPaint(0, 0, new Color(239, 246, 255), 
                                          getWidth(), getHeight(), new Color(248, 250, 252));
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
} // ✅ GRADIENT CLASS CLOSED!
