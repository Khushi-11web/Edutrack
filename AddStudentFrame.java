package Edutrack1;

import javax.swing.*;
import java.awt.*;

public class AddStudentFrame extends JFrame {
    private Teacher teacher;
    private JPanel content;
    
    public AddStudentFrame(Teacher teacher) {
        this.teacher = teacher;
        initUI();
    }
    
    private void initUI() {
        setTitle("Add Students - " + teacher.name);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("Add New Student", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(33,150,243));
        
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField(15);
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(idLabel, gbc);
        gbc.gridx = 1;
        form.add(idField, gbc);
        
        JLabel nameLabel = new JLabel("Student Name:");
        JTextField nameField = new JTextField(15);
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(nameLabel, gbc);
        gbc.gridx = 1;
        form.add(nameField, gbc);
        
        JButton addBtn = new JButton("Add Student");
        addBtn.setBackground(new Color(76,175,80));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        form.add(addBtn, gbc);
        
        addBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            if (!id.isEmpty() && !name.isEmpty()) {
                Student s = new Student(id, name);
                teacher.addStudent(s);
                JOptionPane.showMessageDialog(this, "Student added successfully!");
                idField.setText("");
                nameField.setText("");
            }
        });
        
        content.add(title, BorderLayout.NORTH);
        content.add(form, BorderLayout.CENTER);
        add(content);
        setVisible(true);
    }
    
    public JPanel getContent() { return content; }
}
