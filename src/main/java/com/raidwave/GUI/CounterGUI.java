package com.raidwave.GUI;

import com.raidwave.Backend.AtomicCounter;
import com.raidwave.Backend.Counter;
import com.raidwave.Backend.SynchronizedCounter;
import com.raidwave.Threading.CounterThread;

import javax.swing.*;
import java.awt.*;

public class CounterGUI {
    private JFrame frame;
    private JLabel resultLabel;
    private JTextField inputField;
    private JComboBox<String> methodSelector;

    public CounterGUI() {
        setupGUI();
    }

    private void setupGUI() {
        frame = new JFrame("Counter");

        JLabel instructionLabel = new JLabel("Enter the number of increments:");
        inputField = new JTextField(10);
        JButton startButton = new JButton("Start Counting");
        resultLabel = new JLabel("Result: 0", SwingConstants.CENTER);
        methodSelector = new JComboBox<>(new String[]{"Synchronised", "Atomic"});

        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        frame.add(instructionLabel);
        frame.add(inputField);
        frame.add(methodSelector);
        frame.add(startButton);
        frame.add(resultLabel);

        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        startButton.setMnemonic('S');

        startButton.addActionListener(_ -> startCounting());
    }

    private void startCounting() {
        int increments;
        try {
            increments = Integer.parseInt(inputField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a number",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String method = (String) methodSelector.getSelectedItem();
        assert method != null;
        Counter counter = method.equals("Synchronised") ? new SynchronizedCounter() : new AtomicCounter();

        Thread t1 = new Thread(new CounterThread(counter, increments));
        Thread t2 = new Thread(new CounterThread(counter, increments));
        Thread t3 = new Thread(new CounterThread(counter, increments));
        Thread t4 = new Thread(new CounterThread(counter, increments));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            resultLabel.setText("Error");
            return;
        }

        resultLabel.setText("Result: " + counter.getValue());
    }
}
