package engine.events.polaczenia.polaczenieStart;

import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import engine.exception.PolaczenieOdrzucone;
import engine.object.Abonent;
import engine.object.Centrala;
import main.Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public abstract class PolaczenieStart extends BasicSimEvent <Centrala,Abonent>
{
    private Abonent abonent = null;
    private static MonitoredVar wykonanychPolaczen = new MonitoredVar();
    public PolaczenieStart(Centrala entity, double delay, Abonent abonent) throws SimControlException {
        super(entity, delay,abonent);
        this.abonent = abonent;
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException
    {

    }

    @Override
    public Abonent getEventParams() {
        return abonent;
    }
    @Override
    protected void stateChange() throws SimControlException
    {
        Main.wypiszZdarzenie(simTime(), "Zaczeto wykonywanie polaczenia " + getRodzajPolaczenia() + " Dla abonenta ID: " + getEventParams().getId());
        if(abonent.getPolaczenie() instanceof PolaczenieMiedzystrefoweStart)
        {
            getSimObj().zacznijPolaczenieMiedzystrefowe(abonent);
        }
        else
        {
            getSimObj().zacznijPolaczenieLokalne(abonent);
        }
        //Main.wypiszZdarzenie(simTime(), "Polaczenie wykonane " + getRodzajPolaczenia() + " Dla abonenta ID: " + getEventParams().getId());
        //Main.wypiszZdarzenie(simTime(),"Polaczenie przerwane dla abonenta ID: " + getEventParams().getId());
    }

    protected abstract String getRodzajPolaczenia();
    protected abstract Method pobierzMetode();
}
