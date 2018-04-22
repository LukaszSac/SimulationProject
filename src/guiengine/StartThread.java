package guiengine;

import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;

public class StartThread extends Thread
{

    private final SimManager sim;

    public StartThread(SimManager sim)
    {
        this.sim = sim;
    }

    @Override
    public void run() {
        try {
            sim.startSimulation();
        } catch (SimControlException e) {
            e.printStackTrace();
        }
    }
}
