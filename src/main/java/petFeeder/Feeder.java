package petFeeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;

import java.awt.Color;

/**
 * Feeder thread: Dispenses food periodically, but can randomly jam
 */
public class Feeder implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Feeder.class);
    private final SharedState state;
    private final FeederGUI gui;
    private final Random random = new Random();
    private static final int DISPENSE_INTERVAL_MS = 3000; // Dispense every 3 seconds
    private static final int FOOD_PER_DISPENSE = 2;
    private static final double JAM_PROBABILITY = 0.2; // 20% chance of jam

    public Feeder(SharedState state, FeederGUI gui) {
        this.state = state;
        this.gui = gui;
    }

    @Override
    public void run() {
        logger.info("🤖 Feeder process started");
        if (gui != null) {
            gui.addEvent("🤖 Feeder process started", new Color(100, 150, 200));
        }

        while (state.isRunning()) {
            try {
                // Try to dispense food
                if (!state.isDispenserJammed()) {
                    state.dispenseFood(FOOD_PER_DISPENSE);
                    String msg = String.format("🤖 FEEDER: Food dispensed! Bowl has %d units.", state.getFoodInBowl());
                    logger.info(msg);
                    if (gui != null) {
                        gui.addEvent(msg, new Color(50, 150, 200));
                    }
                } else {
                    String msg = "🤖 ERROR: Feeder is jammed!";
                    logger.error(msg);
                    if (gui != null) {
                        gui.addEvent(msg, new Color(200, 50, 50));
                    }
                }

                // Randomly simulate a jam
                if (!state.isDispenserJammed() && random.nextDouble() < JAM_PROBABILITY) {
                    state.setDispenserJammed(true);
                    String msg = "🤖 FEEDER: *CLUNK* Dispenser jammed!";
                    logger.error(msg);
                    if (gui != null) {
                        gui.addEvent(msg, new Color(220, 50, 50));
                    }
                }

                Thread.sleep(DISPENSE_INTERVAL_MS);
            } catch (InterruptedException e) {
                logger.info("🤖 Feeder process interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }

        logger.info("🤖 Feeder process stopped");
        if (gui != null) {
            gui.addEvent("🤖 Feeder process stopped", new Color(150, 150, 150));
        }
    }
}