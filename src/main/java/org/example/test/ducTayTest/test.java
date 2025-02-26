package org.example.test.ducTayTest;

import org.example.Components.CustomButton;

import javax.swing.*;
import java.awt.*;

public class test extends JFrame {

    public test (){
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500,500);
        setLayout(new FlowLayout());
        JButton b1 = new JButton();
        CustomButton b2 = new CustomButton("tttasdiuf");
        add(b1);
        add(b2);
        setVisible(true);
    }

    public static void main(String[] args) {
        new test();
    }
}

