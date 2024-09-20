package com.raidwave.GUI;

import com.raidwave.Backend.Timer;
import com.raidwave.Threading.TimerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerGUI {
    public JFrame frame;
    private JTextField timeField;
    private final Timer timer;
    private Thread timerThread;
    private JButton startButton;
    private JButton stopButton;

    public TimerGUI() {
        timer = new Timer();
        setupGUI();
    }

    private void setupGUI() {
        frame = new JFrame("CTU Timer App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel secondsLabel = new JLabel("Time (seconds and milliseconds):");

        startButton = new JButton("Start Timer");
        stopButton = new JButton("Stop Timer");
        timeField = new JTextField(10);
        timeField.setEditable(false);

        timeField.setFont(new Font("Arial", Font.BOLD, 36));
        timeField.setBorder(BorderFactory.createEtchedBorder());

        frame.add(secondsLabel);
        frame.add(timeField);
        frame.add(startButton);
        frame.add(stopButton);

        startButton.setMnemonic('S');
        stopButton.setMnemonic('T');

        frame.pack();
        frame.setSize(400, 250);
        frame.setVisible(true);

        TimerTask task = new TimerTask();
        assert startButton != null;
        startButton.addActionListener(task);
        assert stopButton != null;
        stopButton.addActionListener(task);
    }

    private class TimerTask implements ActionListener {
        private long startTime;

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            if (source == startButton) {
                if (startButton.getText().equals("Start Timer")) {
                    if (timerThread != null && timerThread.isAlive()) {
                        timerThread.interrupt();
                    }
                    timer.reset();
                    timer.start();
                    startTime = System.nanoTime();
                    timerThread = new Thread(new TimerThread(timer));
                    timerThread.start();
                    new Thread(() -> {
                        while (timer.isRunning()) {
                            SwingUtilities.invokeLater(() -> {
                                long elapsedTime = System.nanoTime() - startTime;
                                long seconds = elapsedTime / 1000000000;
                                int milliseconds = (int) ((elapsedTime % 1000000000) / 1000000);
                                timeField.setText(String.format("%d.%03d", seconds, milliseconds));
                            });

                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException ex) {
                                if (!timer.isRunning()) {
                                    break;
                                }
                            }
                        }
                    }).start();
                    startButton.setText("Reset Timer");
                    if (timerThread != null) {
                        startButton.setEnabled(false);
                    }
                } else if (startButton.getText().equals("Reset Timer")) {
                    timer.stop();
                    if (timerThread != null && timerThread.isAlive()) {
                        timerThread.interrupt();
                    }
                    timeField.setText("");
                    startButton.setText("Start Timer");
                }
            } else if (source == stopButton) {
                timer.stop();
                if (timerThread != null && timerThread.isAlive()) {
                    timerThread.interrupt();
                    startButton.setEnabled(true);
                }

                if (timeField.getText().isEmpty()) {
                    startButton.setText("Start Timer");
                }
            }
        }
    }
}
