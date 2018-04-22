package engine.object;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;
import engine.events.polaczenia.polaczenieStop.PolaczenieKoniec;
import engine.exception.PolaczenieOdrzucone;

public class Centrala extends BasicSimObj
{

    private int iloscAbonamentow = 0;
    public Kanal[] kanaly;
    private int idCentrali = 0;
    private int iloscWykonanychPolaczen = 0;
    public static int iloscPrzerwanychPolaczen = 0;
    private MonitoredVar wykorzystanie = new MonitoredVar();
    private MonitoredVar iloscRozmow = new MonitoredVar();
    public static boolean ifALot = false;
    public Centrala(int iloscAbonentow, int idCentrali)
    {
        this.iloscAbonamentow = iloscAbonentow;
        for(int i=0;i<iloscAbonentow;i++)
        {
            new Abonent(this);
        }
        kanaly = new Kanal[SiecTelefoniczna.iloscCentral];
        this.idCentrali = idCentrali;
    }


    public void zacznijPolaczenieLokalne(Abonent abonent)
    {
        zajmijKanal(idCentrali, abonent);
    }

    public void zacznijPolaczenieMiedzystrefowe(Abonent abonent)
    {
        zajmijKanal(abonent.getIdCentraliPolaczenia(), abonent);
    }

    public boolean zajmijKanal(int idCetraliDocelowej, Abonent abonent)
    {
        try {
            kanaly[idCetraliDocelowej].zajmijKanal();
            iloscWykonanychPolaczen++;
        } catch(PolaczenieOdrzucone e)
        {
            iloscPrzerwanychPolaczen++;

            abonent.generujEventStartPolaczenia();
            return false;
        }
        try {
            double wykonanych = 0;
            double max = 0;
            for(int i=0;i<kanaly.length;i++)
            {
                wykonanych+=kanaly[i].getIloscZajetychPolaczen();
                max += kanaly[i].getMaxZajetychKanalow();
            }
            if(!ifALot)
            wykorzystanie.setValue(wykonanych/max,simTime());
            SiecTelefoniczna.iloscRozmow++;
            if(!ifALot)
            SiecTelefoniczna.iloscRozmowMon.setValue(SiecTelefoniczna.iloscRozmow,simTime());
            if(!ifALot)
            iloscRozmow.setValue(iloscWykonanychPolaczen,simTime());
            new PolaczenieKoniec(this,Generator.LosujCzasDoZakonczeniaRozmowy(),abonent);
        } catch (SimControlException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void zwolnijKanal(int idCentraliDocelowej)
    {
        kanaly[idCentraliDocelowej].zwolnijKanal();
        SiecTelefoniczna.iloscRozmow--;
        if(!ifALot)
        SiecTelefoniczna.iloscRozmowMon.setValue(SiecTelefoniczna.iloscRozmow,simTime());
        iloscWykonanychPolaczen--;
        if(!ifALot)
        iloscRozmow.setValue(iloscWykonanychPolaczen,simTime());
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }

    public int getId() {
        return idCentrali;
    }

    public void ustawKanalNaCentrale(Kanal kanal, int idCentrali)
    {
        kanaly[idCentrali] = kanal;
    }

    public Kanal getKanal(int idKanalu)
    {
        return kanaly[idKanalu];
    }

    public int getIloscWykonanychPolaczen() {
        return iloscWykonanychPolaczen;
    }

    public int getIloscPrzerwanychPolaczen() {
        return iloscPrzerwanychPolaczen;
    }

    public MonitoredVar getWykorzystanie() {
        return wykorzystanie;
    }

    public MonitoredVar getIloscRozmow() {
        return iloscRozmow;
    }
}
