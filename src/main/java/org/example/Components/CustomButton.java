package org.example.Components;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    public CustomButton(String text) {
        super(text);
        setBackground(Color.BLUE);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 16));
        setFocusPainted(false);
    }

}
