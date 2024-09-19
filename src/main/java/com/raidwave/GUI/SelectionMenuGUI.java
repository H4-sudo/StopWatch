package com.raidwave.GUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SelectionMenuGUI {
    private JFrame frame;

    public SelectionMenuGUI() {
        setupGUI();
    }

    private void setupGUI() {
        frame = new JFrame("App Selection Menu");
        JButton counterButton = new JButton("Launch Counter App");
        JButton timerButton = new JButton("Launch Timer App");

        frame.setLayout(null);
        counterButton.setBounds(50, 50, 300, 40);
        timerButton.setBounds(50, 120, 300, 40);

        frame.add(counterButton);
        frame.add(timerButton);

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        counterButton.addActionListener(_ -> {
            frame.dispose();
            new CounterGUI();
        });

        timerButton.addActionListener(_ -> {
            frame.dispose();
            new TimerGUI();
        });
    }
}
