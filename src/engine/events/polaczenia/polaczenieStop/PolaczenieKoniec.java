package engine.events.polaczenia.polaczenieStop;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import engine.object.Abonent;
import engine.object.Centrala;
import main.Main;

public class PolaczenieKoniec extends BasicSimEvent <Centrala,Abonent>
{
    private Abonent abonent;
    public PolaczenieKoniec(Centrala entity, double delay, Abonent abonent) throws SimControlException {
        super(entity, delay, abonent);
        this.abonent = abonent;
    }

    @Override
    protected void stateChange() throws SimControlException
    {
        getSimObj().zwolnijKanal(getEventParams().getActualRecall());
        getEventParams().generujEventStartPolaczenia();
        Main.wypiszZdarzenie(simTime(), "Zakonczona wykonywania polaczenia dla abonenta ID: " + getEventParams().getId());
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }

    @Override
    public Abonent getEventParams() {
        return abonent;
    }
}
