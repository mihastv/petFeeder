package petFeeder.controller;

import petFeeder.model.PetModel;
import petFeeder.view.PetView;

import javax.swing.SwingUtilities;

public class PetController {
    private PetModel model;
    private PetView view;

    public PetController(PetModel model, PetView view) {
        this.model = model;
        this.view = view;

        // Hook up the button
        this.view.setRepairAction(e -> {
            model.repair();
            view.addLog("SYSTEM: Manual Repair Completed.");
            refresh();
        });
    }

    public void startSimulation() {
        // Process: Cat (Every 2 seconds)
        new Thread(() -> {
            while (true) {
                try { Thread.sleep(2000); } catch (InterruptedException e) {}
                model.catTriesToEat();
                view.addLog(model.isCatHappy() ? "CAT: *Nom*" : "CAT: *Meow!*");
                refresh();
            }
        }).start();

        // Process: Feeder (Every 5 seconds)
        new Thread(() -> {
            while (true) {
                try { Thread.sleep(5000); } catch (InterruptedException e) {}
                String result = model.feederTriesToDispense();
                if (result.equals("JAMMED")) view.addLog("!!! FEEDER JAMMED !!!");
                if (result.equals("DISPENSED")) view.addLog("FEEDER: Food added.");
                refresh();
            }
        }).start();
    }

    private void refresh() {
        SwingUtilities.invokeLater(() ->
                view.updateDisplay(model.getFoodInBowl(), model.isCatHappy(), model.isDispenserJammed())
        );
    }

    public static void main(String[] args) {
        PetModel m = new PetModel();
        PetView v = new PetView();
        PetController c = new PetController(m, v);

        v.setVisible(true);
        c.startSimulation();
    }
}
