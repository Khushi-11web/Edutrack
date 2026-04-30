package Edutrack1;

import javax.swing.*;
import java.awt.*;

public class MarksFrame extends JFrame {
    private Teacher teacher;
    
    public MarksFrame(Teacher teacher) {
        this.teacher = teacher;
        initUI();
    }
    
    private void initUI() {
        setTitle("Results - " + teacher.name);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("Enter Student Marks", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        
        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField(10);
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(idLabel, gbc);
        gbc.gridx = 1;
        form.add(idField, gbc);
        
        JLabel markLabel = new JLabel("Mark (0-100):");
        JTextField markField = new JTextField(10);
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(markLabel, gbc);
        gbc.gridx = 1;
        form.add(markField, gbc);
        
        JButton addMarkBtn = new JButton("Add Mark");
        addMarkBtn.setBackground(new Color(33,150,243));
        addMarkBtn.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        form.add(addMarkBtn, gbc);
        
        JTextArea results = new JTextArea(10, 30);
        results.setEditable(false);
        JScrollPane scroll = new JScrollPane(results);
        
        addMarkBtn.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                int mark = Integer.parseInt(markField.getText().trim());
                
                for (Student s : teacher.students) {
                    if (s.getId().equals(id)) {
                        s.addMark(mark);
                        idField.setText("");
                        markField.setText("");
                        updateResults(results);
                        break;
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });
        
        updateResults(results);
        
        main.add(title, BorderLayout.NORTH);
        main.add(form, BorderLayout.CENTER);
        main.add(scroll, BorderLayout.SOUTH);
        add(main);
        setVisible(true);
    }
    
    private void updateResults(JTextArea area) {
        area.setText("Student Results:\n\n");
        for (Student s : teacher.students) {
            area.append(s.getId() + " - " + s.getName() + ": " + 
                       String.format("%.1f%%", s.getAverageResult()) + "\n");
        }
    }
}
