package org.example.test.ducTayTest;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JFrame {
    public SplashScreen() {
        setUndecorated(true);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(41, 128, 185));
        
        JLabel titleLabel = new JLabel("POS PRO SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel versionLabel = new JLabel("Version 1.0.0");
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        versionLabel.setForeground(Color.WHITE);
        versionLabel.setHorizontalAlignment(JLabel.CENTER);
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(false);
        progressBar.setPreferredSize(new Dimension(400, 5));
        
        JPanel progressPanel = new JPanel();
        progressPanel.setBackground(new Color(41, 128, 185));
        progressPanel.add(progressBar);
        
        panel.add(titleLabel, BorderLayout.CENTER);
        panel.add(versionLabel, BorderLayout.NORTH);
        panel.add(progressPanel, BorderLayout.SOUTH);
        
        add(panel);
    }
}