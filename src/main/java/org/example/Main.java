package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import org.example.GUI.LoginGUI;
import org.example.GUI.MenuFrame;
import org.example.GUI.Panels.hangHoaPanel.SanPhamPanel;

import javax.swing.*;

import static org.example.GUI.MenuFrame.setupUIManagerProperties;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            setupUIManagerProperties();
            MenuFrame menuFrame = new MenuFrame();
            menuFrame.hideActionPanel("sanPham");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
