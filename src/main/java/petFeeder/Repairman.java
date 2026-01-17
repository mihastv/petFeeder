package petFeeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;

/**
 * Repairman thread: Detects jams and fixes them
 */
public class Repairman implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Repairman.class);
    private final SharedState state;
    private final FeederGUI gui;
    private static final int CHECK_INTERVAL_MS = 500; // Check every 0.5 seconds
    private static final int REPAIR_TIME_MS = 2000; // Takes 2 seconds to repair

    public Repairman(SharedState state, FeederGUI gui) {
        this.state = state;
        this.gui = gui;
    }

    @Override
    public void run() {
        logger.info("🔧 Repairman process started");
        if (gui != null) {
            gui.addEvent("🔧 Repairman process started", new Color(100, 200, 100));
        }

        while (state.isRunning()) {
            try {
                if (state.isDispenserJammed()) {
                    String msg1 = "🔧 REPAIR: Jam detected! Fixing...";
                    logger.warn(msg1);
                    if (gui != null) {
                        gui.addEvent(msg1, new Color(200, 150, 50));
                    }

                    Thread.sleep(REPAIR_TIME_MS); // Simulate repair time
                    state.fixDispenser();

                    String msg2 = "🔧 REPAIR: Fixed the feeder! Cat can eat again.";
                    logger.info(msg2);
                    if (gui != null) {
                        gui.addEvent(msg2, new Color(50, 200, 50));
                    }
                }

                Thread.sleep(CHECK_INTERVAL_MS);
            } catch (InterruptedException e) {
                logger.info("🔧 Repairman process interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }

        logger.info("🔧 Repairman process stopped");
        if (gui != null) {
            gui.addEvent("🔧 Repairman process stopped", new Color(150, 150, 150));
        }
    }
}