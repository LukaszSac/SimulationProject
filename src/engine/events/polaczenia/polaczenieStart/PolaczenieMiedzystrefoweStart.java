package engine.events.polaczenia.polaczenieStart;

import dissimlab.simcore.SimControlException;
import engine.object.Abonent;
import engine.object.Centrala;

import java.lang.reflect.Method;

public class PolaczenieMiedzystrefoweStart extends PolaczenieStart
{

    public PolaczenieMiedzystrefoweStart(Centrala entity, double delay, Abonent abonent) throws SimControlException {
        super(entity, delay, abonent);
    }

    @Override
    protected String getRodzajPolaczenia() {
        return "miedzystrefowego";
    }

    @Override
    protected Method pobierzMetode()
    {
        try {
            return getSimObj().getClass().getMethod("zacznijPolaczenieMiedzystrefowe", Abonent.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

}
