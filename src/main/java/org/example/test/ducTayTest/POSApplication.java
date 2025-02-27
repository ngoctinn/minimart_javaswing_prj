package org.example.test.ducTayTest;

import javax.swing.*;
import java.awt.*;

public class POSApplication {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            JFrame splashScreen = new SplashScreen();
            splashScreen.setVisible(true);
            
            // Simulate loading time
            Timer timer = new Timer(2000, e -> {
                splashScreen.dispose();
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
            });
            timer.setRepeats(false);
            timer.start();
        });
    }
}