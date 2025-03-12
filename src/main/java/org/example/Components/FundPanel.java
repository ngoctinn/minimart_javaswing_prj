package org.example.Components;

import javax.swing.*;
import java.awt.*;

public class FundPanel extends JPanel {
    public FundPanel() {
        setBackground(Color.MAGENTA);
        add(new JLabel("Số quỹ", SwingConstants.CENTER));
    }
}
