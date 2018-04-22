package engine.object;

import dissimlab.monitors.MonitoredVar;
import engine.exception.PolaczenieOdrzucone;
import guiengine.customContainers.SimConnectionG;

public class Kanal
{
    private SimConnectionG line;
    private int pojemnoscKanalu = 0;
    private int aktualnaLiczbaZajetychKanalow = 0;
    private int maxZajetychKanalow = 0;
    private static MonitoredVar odrzuconych = new MonitoredVar();
    public Kanal(int pojemnoscKanalu, SimConnectionG line)
    {
        this.pojemnoscKanalu = pojemnoscKanalu;
        this.line = line;
    }

    public void zajmijKanal()
    {
        if(aktualnaLiczbaZajetychKanalow+1>pojemnoscKanalu)
            throw new PolaczenieOdrzucone();
        else
        {
            aktualnaLiczbaZajetychKanalow++;
            if(aktualnaLiczbaZajetychKanalow>maxZajetychKanalow)maxZajetychKanalow=aktualnaLiczbaZajetychKanalow;
            line.changeColor((float)((double)aktualnaLiczbaZajetychKanalow/(double)pojemnoscKanalu));
        }
    }

    public void zwolnijKanal()
    {
        aktualnaLiczbaZajetychKanalow--;
        if(aktualnaLiczbaZajetychKanalow<0)aktualnaLiczbaZajetychKanalow =0;
        line.changeColor((float)((double)aktualnaLiczbaZajetychKanalow/(double)pojemnoscKanalu));
    }

    public int getIloscZajetychPolaczen()
    {
        return aktualnaLiczbaZajetychKanalow;
    }

    public int getMaxZajetychKanalow() {
        return maxZajetychKanalow;
    }

    public int getPojemnoscKanalu()
    {
        return pojemnoscKanalu;
    }
}
