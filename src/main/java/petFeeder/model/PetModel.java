package petFeeder.model;

import java.util.Random;

public class PetModel {
    private int foodInBowl = 0;
    private boolean catIsHappy = true;
    private boolean dispenserJammed = false;
    private final Random rand = new Random();

    // Getters
    public int getFoodInBowl() { return foodInBowl; }
    public boolean isCatHappy() { return catIsHappy; }
    public boolean isDispenserJammed() { return dispenserJammed; }

    // Logic Methods (The "Transitions" from your Automaton)
    public synchronized void catTriesToEat() {
        if (foodInBowl > 0) {
            foodInBowl--;
            catIsHappy = true;
        } else {
            catIsHappy = false;
        }
    }

    public synchronized String feederTriesToDispense() {
        if (!dispenserJammed) {
            if (rand.nextInt(4) == 0) { // 25% jam chance
                dispenserJammed = true;
                return "JAMMED";
            } else {
                foodInBowl += 2;
                return "DISPENSED";
            }
        }
        return "IDLE_JAMMED";
    }

    public synchronized void repair() {
        dispenserJammed = false;
    }
}
