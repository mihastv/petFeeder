package petFeeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Console-only version of the Smart Pet Feeder simulation (no GUI)
 * Use this for headless environments or if you prefer console output
 */
public class SmartPetFeederConsole {
    private static final Logger logger = LoggerFactory.getLogger(SmartPetFeederConsole.class);
    private static final int SIMULATION_DURATION_MS = 30000; // Run for 30 seconds

    public static void main(String[] args) {
        logger.info("=================================================");
        logger.info("  Smart Pet Feeder Simulation (Console Mode)");
        logger.info("=================================================");
        logger.info("");

        // Create shared state
        SharedState sharedState = new SharedState();

        // Create threads for Cat, Feeder, and Repairman (no GUI)
        Thread catThread = new Thread(new Cat(sharedState, null), "Cat-Thread");
        Thread feederThread = new Thread(new Feeder(sharedState, null), "Feeder-Thread");
        Thread repairmanThread = new Thread(new Repairman(sharedState, null), "Repairman-Thread");

        // Start all threads
        catThread.start();
        feederThread.start();
        repairmanThread.start();

        // Run simulation for specified duration
        try {
            logger.info("Simulation running for {} seconds...", SIMULATION_DURATION_MS / 1000);
            logger.info("");
            Thread.sleep(SIMULATION_DURATION_MS);
        } catch (InterruptedException e) {
            logger.error("Simulation interrupted", e);
            Thread.currentThread().interrupt();
        }

        // Stop the simulation
        logger.info("");
        logger.info("=================================================");
        logger.info("  Stopping simulation...");
        logger.info("=================================================");
        sharedState.stop();

        // Wait for all threads to finish
        try {
            catThread.join(2000);
            feederThread.join(2000);
            repairmanThread.join(2000);
        } catch (InterruptedException e) {
            logger.error("Error waiting for threads to finish", e);
            Thread.currentThread().interrupt();
        }

        logger.info("Simulation complete!");
    }
}
