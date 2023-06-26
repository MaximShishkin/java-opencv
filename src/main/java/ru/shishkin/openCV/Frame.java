package ru.shishkin.opencv;

import javax.swing.*;

public class Frame extends JFrame {
    public Frame() {
        setTitle("aa");
        setBounds(50,50,400,150);
        add(new Panel());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
