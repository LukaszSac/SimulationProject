package engine.events.zbieranieDanych;

import dissimlab.monitors.Diagram;
import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimParameters;
import engine.object.Centrala;
import engine.object.SiecTelefoniczna;

public class KoniecSymulacjiIZbierzDane extends SimControlEvent
{
    private SiecTelefoniczna siec = null;
    public KoniecSymulacjiIZbierzDane(SiecTelefoniczna siec, double delay, SimParameters.SimControlStatus status) throws SimControlException
    {
        super(delay, status);
        this.siec = siec;
    }

    @Override
    protected void stateChange()
    {
        if(!Centrala.ifALot) {
            Centrala[] centrale = siec.getCentrale();
            Diagram d1 = new Diagram(Diagram.DiagramType.TIME_FUNCTION, "Ilosc rozmów w sieci");
            d1.add(SiecTelefoniczna.iloscRozmowMon, java.awt.Color.GREEN);
            d1.show();
            Centrala[] centrale2 = siec.getCentrale();
            for (int i = 0; i < SiecTelefoniczna.iloscCentral; i++) {
                Diagram d2 = new Diagram(Diagram.DiagramType.TIME_FUNCTION, "Stopien wykorzystania centrali " + i);
                d2.add(centrale2[i].getWykorzystanie(), java.awt.Color.GREEN);
                d2.show();
            }
            for (int i = 0; i < SiecTelefoniczna.iloscCentral; i++) {
                Diagram d2 = new Diagram(Diagram.DiagramType.TIME_FUNCTION, "Ilosc wykonanych rozmow w centrali " + i);
                d2.add(centrale2[i].getIloscRozmow(), java.awt.Color.GREEN);
                d2.show();
            }
        }
        super.stateChange();
    }
}
