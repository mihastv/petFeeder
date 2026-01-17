package petFeeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.SwingUtilities;

/**
 * Main application to run the Smart Pet Feeder simulation with GUI
 */
public class SmartPetFeederApp {
    private static final Logger logger = LoggerFactory.getLogger(SmartPetFeederApp.class);

    public static void main(String[] args) {
        logger.info("=================================================");
        logger.info("  Smart Pet Feeder Simulation Starting");
        logger.info("=================================================");
        logger.info("");

        // Create shared state
        SharedState sharedState = new SharedState();

        // Create and show GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            FeederGUI gui = new FeederGUI(sharedState);
            gui.setVisible(true);

            // Start simulation threads after GUI is visible
            Thread catThread = new Thread(new Cat(sharedState, gui), "Cat-Thread");
            Thread feederThread = new Thread(new Feeder(sharedState, gui), "Feeder-Thread");
            Thread repairmanThread = new Thread(new Repairman(sharedState, gui), "Repairman-Thread");

            catThread.start();
            feederThread.start();
            repairmanThread.start();

            logger.info("Simulation threads started");
            gui.addEvent("All simulation threads started!", new java.awt.Color(50, 150, 250));
        });
    }
}