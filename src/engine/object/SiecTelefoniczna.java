package engine.object;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import guiengine.customContainers.SimConnectionG;

public class SiecTelefoniczna extends BasicSimObj
{
    public static int iloscCentral = 0;
    private final SimConnectionG[][] lines;
    private double prawdopodobienstwoP = 0.0;
    private int[][] maksymalnaLiczbaKanalow = null;
    private int[] liczbaAbonentow = null;
    private Centrala[] centrale = null;
    public static MonitoredVar iloscRozmowMon = new MonitoredVar();
    public static int iloscRozmow = 0;
    public SiecTelefoniczna(int iloscCentral, int[][] maksymalnaLiczbaKanalow, int[] liczbaAbonentow, SimConnectionG[][] lines)
    {
        this.iloscCentral = iloscCentral;
        this.maksymalnaLiczbaKanalow = maksymalnaLiczbaKanalow;
        this.liczbaAbonentow = liczbaAbonentow;
        this.lines = lines;
        initCentral();
        initKanalow();
    }

    private void initKanalow()
    {
        for(int i=0;i<iloscCentral;i++)
        {
            for(int j=0;j<=i;j++)
            {
                Kanal kanal = new Kanal(maksymalnaLiczbaKanalow[i][j],lines[i][j]);
                if(i==j)
                {
                    centrale[j].ustawKanalNaCentrale(kanal,i);
                }
                else
                {
                    centrale[j].ustawKanalNaCentrale(kanal,i);
                    centrale[i].ustawKanalNaCentrale(kanal,j);
                }
            }
        }
    }

    private void initCentral()
    {
        centrale = new Centrala[iloscCentral];
        for(int i=0;i<iloscCentral;i++)
        {
            centrale[i] = new Centrala(liczbaAbonentow[i],i);
        }
    }
    public void setP(double p)
    {
        this.prawdopodobienstwoP = p;
    }

    public Centrala[] getCentrale()
    {
        return centrale;
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
