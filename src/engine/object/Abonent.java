package engine.object;

import dissimlab.simcore.SimControlException;
import engine.events.polaczenia.polaczenieStart.PolaczenieLokalneStart;
import engine.events.polaczenia.polaczenieStart.PolaczenieMiedzystrefoweStart;
import engine.events.polaczenia.polaczenieStart.PolaczenieStart;

public class Abonent
{
    private static int id = 0;
    private int thisId = 0;
    private double p = 0.3;
    private Centrala centrala = null;
    private int aktualnePolaczenieDo = -1;
    private PolaczenieStart polaczenie = null;
    public Abonent(Centrala centrala)
    {
        thisId = id++;
        this.centrala = centrala;
        generujEventStartPolaczenia();
    }

    public void generujEventStartPolaczenia()
    {
        double delay = Generator.losujDlaCzasuPomiedzyZgloszeniami();
        if(Generator.losujPrzedzialZeroJeden()<p)
        {
            try {
                polaczenie = new PolaczenieLokalneStart(centrala,delay,this);
                aktualnePolaczenieDo = centrala.getId();
            } catch (SimControlException e) {
                e.printStackTrace();
            }
        }
        else
        {try {
            polaczenie  = new PolaczenieMiedzystrefoweStart(centrala,delay,this);
        } catch (SimControlException e) {
            e.printStackTrace();
        }
        }
    }

    public int getId()
    {
        return thisId;
    }

    public int getIdCentraliPolaczenia()
    {
        int id = Generator.pobierzIdCentrali();
        while(id==centrala.getId()) id = Generator.pobierzIdCentrali();
        aktualnePolaczenieDo = id;
        return id;
    }

    public int getActualRecall()
    {
        return aktualnePolaczenieDo;
    }

    public PolaczenieStart getPolaczenie() {
        return polaczenie;
    }

    public int getAktualnePolaczenieDo() {
        return aktualnePolaczenieDo;
    }

    public void setAktualnePolaczenieDo(int aktualnePolaczenieDo) {
        this.aktualnePolaczenieDo = aktualnePolaczenieDo;
    }
}
