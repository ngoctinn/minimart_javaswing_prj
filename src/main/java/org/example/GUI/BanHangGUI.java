package org.example.GUI;

import org.example.Components.RoundedPanel;
import org.example.GUI.Panels.hangHoaPanel.DanhMucPanel;

import javax.swing.*;
import java.awt.*;

public class BanHangGUI extends JPanel {
    // UI Components
    private RoundedPanel topPanel;
    private RoundedPanel bottomPanel;
    private RoundedPanel bottomPanelLeft;
    private RoundedPanel bottomPanelRight;

    public BanHangGUI() {
        initGUI();
    }

    private void initGUI() {
        setupMainPanel();
        createPanels();
        setupTopPanel();
        setupBottomPanelLeft();
        setupBottomPanelRight();
    }

    private void setupMainPanel() {
        this.setLayout(new FlowLayout());
        this.setBackground(new Color(225, 225, 225));
        this.setVisible(true);
    }

    private void createPanels() {
        // Create sub-panels
        topPanel = new RoundedPanel(20);
        bottomPanel = new RoundedPanel(20);
        bottomPanelLeft = new RoundedPanel(20);
        bottomPanelRight = new RoundedPanel(20);

        // Set background colors
        topPanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(new Color(225, 225, 225));
        bottomPanelLeft.setBackground(Color.WHITE);
        bottomPanelRight.setBackground(Color.WHITE);

        // Set panel sizes
        topPanel.setPreferredSize(new Dimension(1270, 50));
        bottomPanel.setPreferredSize(new Dimension(1270, 900));
        bottomPanelLeft.setPreferredSize(new Dimension(1270 * 60 / 100, 900));
        bottomPanelRight.setPreferredSize(new Dimension(1270 * 40 / 100, 900));

        // Set layouts
        topPanel.setLayout(null);
        bottomPanel.setLayout(new BorderLayout(5, 0));
        bottomPanelLeft.setLayout(new FlowLayout());
        bottomPanelRight.setLayout(new BoxLayout(bottomPanelRight, BoxLayout.Y_AXIS));

        add(topPanel);
        add(bottomPanel);
        bottomPanel.add(bottomPanelLeft, BorderLayout.WEST);
        bottomPanel.add(bottomPanelRight, BorderLayout.CENTER);


    }


    private void setupTopPanel() {
    }

    private void setupBottomPanelLeft() {
    }

    private void setupBottomPanelRight() {
    }



    // Hàm main để test giao diện
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.add(new BanHangGUI());
            frame.setVisible(true);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

}
