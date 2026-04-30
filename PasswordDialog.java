package Edutrack1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PasswordDialog extends JDialog {
    private JPasswordField passwordField;
    private String storedPassword;
    private boolean authenticated = false;
    
    public PasswordDialog(JFrame parent, String title, String storedPassword) {
        super(parent, title, true);
        this.storedPassword = storedPassword;
        initUI();
    }
    
    private void initUI() {
        setSize(350, 200);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Header
        JLabel header = new JLabel("🔐 Enter Password", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setForeground(new Color(52, 73, 94));
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        // Password field
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setEchoChar('●');
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton verifyBtn = new JButton("Verify");
        JButton cancelBtn = new JButton("Cancel");
        
        verifyBtn.addActionListener(this::verifyPassword);
        cancelBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(verifyBtn);
        buttonPanel.add(cancelBtn);
        
        add(header, BorderLayout.NORTH);
        add(new JScrollPane(passwordField), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        verifyBtn.setDefaultCapable(true);
        getRootPane().setDefaultButton(verifyBtn);
    }
    
    private void verifyPassword(ActionEvent e) {
        String input = new String(passwordField.getPassword());
        if(input.equals(storedPassword)) {
            authenticated = true;
            dispose();
        } else {
            passwordField.setText("");
            JOptionPane.showMessageDialog(this, "❌ Wrong Password!", "Access Denied", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isAuthenticated() {
        return authenticated;
    }
}
