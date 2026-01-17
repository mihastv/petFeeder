package petFeeder;

import javax.swing.*;
import java.awt.*;

/**
 * Status bar panel showing real-time simulation statistics
 */
public class StatusPanel extends JPanel {
    private final SharedState state;
    private final JLabel catStatusLabel;
    private final JLabel foodLabel;
    private final JLabel feederStatusLabel;
    private final JLabel uptimeLabel;

    private long startTime;

    public StatusPanel(SharedState state) {
        this.state = state;
        this.startTime = System.currentTimeMillis();

        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(240, 240, 245));
        setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(200, 200, 200)));

        Font labelFont = new Font("Arial", Font.BOLD, 12);

        // Cat status
        catStatusLabel = new JLabel();
        catStatusLabel.setFont(labelFont);
        add(catStatusLabel);

        add(new JLabel("|"));

        // Food count
        foodLabel = new JLabel();
        foodLabel.setFont(labelFont);
        add(foodLabel);

        add(new JLabel("|"));

        // Feeder status
        feederStatusLabel = new JLabel();
        feederStatusLabel.setFont(labelFont);
        add(feederStatusLabel);

        add(new JLabel("|"));

        // Uptime
        uptimeLabel = new JLabel();
        uptimeLabel.setFont(labelFont);
        add(uptimeLabel);

        updateStatus();
    }

    public void updateStatus() {
        // Cat status
        if (state.isCatHappy()) {
            catStatusLabel.setText("😸 Cat: Happy");
            catStatusLabel.setForeground(new Color(50, 150, 50));
        } else {
            catStatusLabel.setText("😿 Cat: Hungry");
            catStatusLabel.setForeground(new Color(200, 100, 50));
        }

        // Food count
        int food = state.getFoodInBowl();
        foodLabel.setText("🍚 Food: " + food + " units");
        if (food > 3) {
            foodLabel.setForeground(new Color(50, 150, 50));
        } else if (food > 0) {
            foodLabel.setForeground(new Color(200, 150, 50));
        } else {
            foodLabel.setForeground(new Color(200, 50, 50));
        }

        // Feeder status
        if (state.isDispenserJammed()) {
            feederStatusLabel.setText("🔴 Feeder: JAMMED");
            feederStatusLabel.setForeground(new Color(200, 50, 50));
        } else {
            feederStatusLabel.setText("🟢 Feeder: Working");
            feederStatusLabel.setForeground(new Color(50, 150, 50));
        }

        // Uptime
        long uptime = (System.currentTimeMillis() - startTime) / 1000;
        long minutes = uptime / 60;
        long seconds = uptime % 60;
        uptimeLabel.setText(String.format("⏱️ Uptime: %d:%02d", minutes, seconds));
        uptimeLabel.setForeground(new Color(80, 80, 100));
    }
}