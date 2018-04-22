package engine.object;

import dissimlab.random.SimGenerator;

public class Generator
{
    private static SimGenerator gen = new SimGenerator();



    public static double jednostajny(int a, int b)
    {
        return gen.uniform(a,b);
    }
    public static double losujPrzedzialZeroJeden() {return gen.nextDouble();}

    public static double losujDlaCzasuPomiedzyZgloszeniami()
    {
        return gen.exponential(6);
    }

    public static int pobierzIdCentrali()
    {
        int id = 0;
        id = (int) gen.uniform(0,SiecTelefoniczna.iloscCentral);
        while(id<0||id>SiecTelefoniczna.iloscCentral-1) id = (int) gen.uniform(0,SiecTelefoniczna.iloscCentral);
        return id;
    }

    public static double LosujCzasDoZakonczeniaRozmowy()
    {
        return gen.exponential(3);
    }
}
