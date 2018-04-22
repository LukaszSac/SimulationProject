package engine.events.polaczenia.polaczenieStart;

import dissimlab.simcore.SimControlException;
import engine.object.Abonent;
import engine.object.Centrala;

import java.lang.reflect.Method;

public class PolaczenieLokalneStart extends PolaczenieStart {
    public PolaczenieLokalneStart(Centrala entity, double delay, Abonent abonent) throws SimControlException {
        super(entity, delay, abonent);
    }

    @Override
    protected String getRodzajPolaczenia() {
        return "lokalnego";
    }

    @Override
    protected Method pobierzMetode() {
        try {
            return getSimObj().getClass().getMethod("zacznijPolaczenieLokalne", Abonent.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
