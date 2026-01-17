package petFeeder;

import javax.swing.*;
import java.awt.*;

/**
 * Control panel with buttons to control the simulation
 */
public class ControlPanel extends JPanel {
    private final FeederGUI gui;

    public ControlPanel(FeederGUI gui) {
        this.gui = gui;

        setLayout(new GridLayout(3, 1, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Stop button
        JButton stopButton = new JButton("⏹️ Stop Simulation");
        stopButton.setFont(new Font("Arial", Font.BOLD, 14));
        stopButton.setBackground(new Color(220, 80, 80));
        stopButton.setForeground(Color.WHITE);
        stopButton.setFocusPainted(false);
        stopButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to stop the simulation?",
                    "Stop Simulation",
                    JOptionPane.YES_NO_OPTION
            );
            if (result == JOptionPane.YES_OPTION) {
                gui.stopSimulation();
            }
        });

        // About button
        JButton aboutButton = new JButton("ℹ️ About");
        aboutButton.setFont(new Font("Arial", Font.PLAIN, 12));
        aboutButton.setFocusPainted(false);
        aboutButton.addActionListener(e -> showAboutDialog());

        // Help button
        JButton helpButton = new JButton("❓ Help");
        helpButton.setFont(new Font("Arial", Font.PLAIN, 12));
        helpButton.setFocusPainted(false);
        helpButton.addActionListener(e -> showHelpDialog());

        add(stopButton);
        add(aboutButton);
        add(helpButton);
    }

    private void showAboutDialog() {
        String message = "<html>" +
                "<h2>Smart Pet Feeder Simulation</h2>" +
                "<p><b>Version:</b> 1.0</p>" +
                "<p><b>Author:</b> Pet Feeder Team</p>" +
                "<br>" +
                "<p>A concurrent simulation demonstrating:</p>" +
                "<ul>" +
                "<li>🐱 Cat process (eating and hunger)</li>" +
                "<li>🤖 Feeder process (dispensing and jamming)</li>" +
                "<li>🔧 Repairman process (detecting and fixing)</li>" +
                "</ul>" +
                "<br>" +
                "<p>Based on Promela verification model</p>" +
                "</html>";

        JOptionPane.showMessageDialog(
                this,
                message,
                "About Smart Pet Feeder",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showHelpDialog() {
        String message = "<html>" +
                "<h2>How to Use</h2>" +
                "<br>" +
                "<p><b>Simulation Components:</b></p>" +
                "<ul>" +
                "<li><b>Cat:</b> Checks for food every 1 second</li>" +
                "<li><b>Feeder:</b> Dispenses food every 3 seconds</li>" +
                "<li><b>Repairman:</b> Fixes jams within 2 seconds</li>" +
                "</ul>" +
                "<br>" +
                "<p><b>What You'll See:</b></p>" +
                "<ul>" +
                "<li>😸 Happy cat when food is available</li>" +
                "<li>😿 Hungry cat when bowl is empty</li>" +
                "<li>🟢 Working feeder dispensing food</li>" +
                "<li>🔴 Jammed feeder (random 20% chance)</li>" +
                "<li>🔧 Repairman fixing jams</li>" +
                "</ul>" +
                "<br>" +
                "<p><b>Event Log:</b> Shows all actions in real-time</p>" +
                "</html>";

        JOptionPane.showMessageDialog(
                this,
                message,
                "Help - Smart Pet Feeder",
                JOptionPane.QUESTION_MESSAGE
        );
    }
}