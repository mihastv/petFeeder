import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import petFeeder.model.PetModel;

import static org.junit.jupiter.api.Assertions.*;

class PetModelTest {
    private PetModel model;

    @BeforeEach
    void setUp() {
        model = new PetModel();
    }

    @Test
    void testCatEatingBranches() {
        // Branch 1: Food is 0
        model.catTriesToEat();
        assertFalse(model.isCatHappy());

        // Branch 2: Food is > 0
        // We force food into the bowl by looping the feeder until it dispenses
        while (model.getFoodInBowl() == 0) {
            model.feederTriesToDispense();
            if (model.isDispenserJammed()) {
                model.repair(); // Ensure we don't get stuck in a jam while trying to get food
            }
        }

        int initialFood = model.getFoodInBowl();
        model.catTriesToEat();
        assertTrue(model.isCatHappy());
        assertEquals(initialFood - 1, model.getFoodInBowl());
    }

    @Test
    void testFeederAllBranches() {
        boolean hitDispensed = false;
        boolean hitJammed = false;

        // We loop because the Feeder is non-deterministic (random)
        // This ensures we eventually walk both paths of the 'if' statement
        for (int i = 0; i < 100; i++) {
            String result = model.feederTriesToDispense();
            if (result.equals("DISPENSED")) hitDispensed = true;
            if (result.equals("JAMMED")) hitJammed = true;
            if (hitDispensed && hitJammed) break;
        }

        assertTrue(hitDispensed, "Should have covered the 'Dispensed' branch");
        assertTrue(hitJammed, "Should have covered the 'Jammed' branch");

        // Test the "Already Jammed" branch (Idle_Jammed)
        assertEquals("IDLE_JAMMED", model.feederTriesToDispense());
    }

    @Test
    void testRepairLogic() {
        // Force the model into a jammed state
        while (!model.isDispenserJammed()) {
            model.feederTriesToDispense();
        }

        model.repair();
        assertFalse(model.isDispenserJammed());
    }
}