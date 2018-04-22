package engine.events.zbieranieDanych;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import engine.object.Centrala;
import engine.object.SiecTelefoniczna;

public class ZbierzDane extends BasicSimEvent <SiecTelefoniczna, Object>
{
    private double czas = 0;
    public ZbierzDane(SiecTelefoniczna entity, double delay) throws SimControlException {
        super(entity, delay);
        this.czas = delay;
    }

    @Override
    protected void stateChange() throws SimControlException
    {
        int iloscRozmow = 0;
        Centrala[] centrale = getSimObj().getCentrale();
        for(int i=0;i<SiecTelefoniczna.iloscCentral;i++)
        {
            iloscRozmow+=centrale[i].getKanal(i).getIloscZajetychPolaczen();
        }
        System.out.println(iloscRozmow + " rozmow w momencie: " + czas);
        for(int i=0;i<SiecTelefoniczna.iloscCentral;i++)
        {
            int iloscPolaczen = 0;
            int pojemnoscKanalow = 0;
            for(int j=0;j<SiecTelefoniczna.iloscCentral;j++)
            {
                iloscPolaczen += centrale[i].getKanal(j).getIloscZajetychPolaczen();
                pojemnoscKanalow += centrale[i].getKanal(j).getPojemnoscKanalu();
            }
            System.out.println("Stopien wykorzystania centrali " + i + " to: " + (double)iloscPolaczen/pojemnoscKanalow + " w momencie: " + czas);
        }

    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }

    @Override
    public Object getEventParams() {
        return null;
    }
}
