package Edutrack1;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ModernPanel extends JPanel {
    private Color primaryColor = new Color(103, 58, 183);
    private Color secondaryColor = new Color(63, 81, 181);
    private Color accentColor = new Color(33, 150, 243);
    
    public ModernPanel() {
        setOpaque(false);
        setBorder(new EmptyBorder(20, 20, 20, 20));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Glass morphism background
        GradientPaint glassGradient = new GradientPaint(0, 0, 
            new Color(255, 255, 255, 20), getWidth(), getHeight(), 
            new Color(255, 255, 255, 100));
        g2d.setPaint(glassGradient);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        
        // Subtle shadow
        g2d.setColor(new Color(0, 0, 0, 30));
        g2d.fillRoundRect(0, 5, getWidth(), getHeight(), 20, 20);
        
        g2d.dispose();
    }
    
    // Modern button
    public static JButton createModernButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(33, 150, 243));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover animation
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(64, 178, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(33, 150, 243));
            }
        });
        return btn;
    }
}
