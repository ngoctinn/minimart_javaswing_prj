package org.example;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.example.GUI.LoginGUI;
import org.example.GUI.MenuFrame;

import javax.swing.*;

import static org.example.GUI.MenuFrame.setupUIManagerProperties;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
            setupUIManagerProperties();
            new MenuFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
