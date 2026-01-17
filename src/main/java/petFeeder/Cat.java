package petFeeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;

/**
 * Cat thread: Eats food if available, gets hungry if bowl is empty
 */
public class Cat implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Cat.class);
    private final SharedState state;
    private final FeederGUI gui;
    private static final int CHECK_INTERVAL_MS = 1000; // Check every second

    public Cat(SharedState state, FeederGUI gui) {
        this.state = state;
        this.gui = gui;
    }

    @Override
    public void run() {
        logger.info("🐱 Cat process started");
        if (gui != null) {
            gui.addEvent("🐱 Cat process started", new Color(255, 180, 100));
        }

        while (state.isRunning()) {
            try {
                if (state.getFoodInBowl() > 0) {
                    // Cat eats food
                    state.eatFood();
                    String msg = String.format("🐱 CAT: *Nom nom nom* Cat is happy! (Food: %d)", state.getFoodInBowl());
                    logger.info(msg);
                    if (gui != null) {
                        gui.addEvent(msg, new Color(100, 200, 100));
                    }
                } else {
                    // Bowl is empty
                    if (state.isCatHappy()) {
                        state.setCatHappy(false);
                        String msg = "🐱 CAT: *Meow* I am hungry!";
                        logger.warn(msg);
                        if (gui != null) {
                            gui.addEvent(msg, new Color(200, 100, 50));
                        }
                    }
                }

                Thread.sleep(CHECK_INTERVAL_MS);
            } catch (InterruptedException e) {
                logger.info("🐱 Cat process interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }

        logger.info("🐱 Cat process stopped");
        if (gui != null) {
            gui.addEvent("🐱 Cat process stopped", new Color(150, 150, 150));
        }
    }
}