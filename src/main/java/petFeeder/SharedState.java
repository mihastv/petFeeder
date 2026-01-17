package petFeeder;

/**
 * SharedState holds all the shared variables that the Cat, Feeder, and Repairman access.
 * All methods are synchronized to prevent race conditions.
 */
public class SharedState {
    private int foodInBowl = 0;
    private boolean catIsHappy = true;
    private boolean dispenserJammed = false;
    private volatile boolean running = true;

    public synchronized int getFoodInBowl() {
        return foodInBowl;
    }

    public synchronized void eatFood() {
        if (foodInBowl > 0) {
            foodInBowl--;
            catIsHappy = true;
        }
    }

    public synchronized void dispenseFood(int amount) {
        foodInBowl += amount;
    }

    public synchronized boolean isCatHappy() {
        return catIsHappy;
    }

    public synchronized void setCatHappy(boolean happy) {
        this.catIsHappy = happy;
    }

    public synchronized boolean isDispenserJammed() {
        return dispenserJammed;
    }

    public synchronized void setDispenserJammed(boolean jammed) {
        this.dispenserJammed = jammed;
    }

    public synchronized void fixDispenser() {
        dispenserJammed = false;
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        this.running = false;
    }
}