package petFeeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Main GUI window for the Smart Pet Feeder simulation
 */
public class FeederGUI extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(FeederGUI.class);

    private final SharedState state;
    private final VisualizationPanel visualizationPanel;
    private final EventLogPanel eventLogPanel;
    private final ControlPanel controlPanel;
    private final StatusPanel statusPanel;

    private final Timer refreshTimer;

    public FeederGUI(SharedState state) {
        this.state = state;

        setTitle("🐱 Smart Pet Feeder Simulation");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.warn("Could not set system look and feel", e);
        }

        // Create panels
        visualizationPanel = new VisualizationPanel(state);
        eventLogPanel = new EventLogPanel();
        controlPanel = new ControlPanel(this);
        statusPanel = new StatusPanel(state);

        // Layout
        setLayout(new BorderLayout(10, 10));

        // Top: Status bar
        add(statusPanel, BorderLayout.NORTH);

        // Center: Visualization
        add(visualizationPanel, BorderLayout.CENTER);

        // Right: Event log
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(300, 0));
        rightPanel.add(eventLogPanel, BorderLayout.CENTER);
        rightPanel.add(controlPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.EAST);

        // Add window listener for proper shutdown
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stopSimulation();
            }
        });

        // Start refresh timer (30 FPS)
        this.refreshTimer = new Timer(33, e -> {
            visualizationPanel.repaint();
            statusPanel.updateStatus();
        });
        refreshTimer.start();

        logger.info("GUI initialized");
    }

    public void addEvent(String event, Color color) {
        eventLogPanel.addEvent(event, color);
    }

    public void stopSimulation() {
        logger.info("Stopping simulation from GUI");
        state.stop();
        refreshTimer.stop();

        // Wait a moment for threads to finish
        Timer closeTimer = new Timer(1000, e -> {
            dispose();
            System.exit(0);
        });
        closeTimer.setRepeats(false);
        closeTimer.start();
    }

    public SharedState getSharedState() {
        return state;
    }
}