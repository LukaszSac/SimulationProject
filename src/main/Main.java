package main;

import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters;
import engine.events.zbieranieDanych.KoniecSymulacjiIZbierzDane;
import engine.events.zbieranieDanych.ZbierzDane;
import engine.object.SiecTelefoniczna;
import guiengine.GuiEngine;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Main
{
    public static void main(String[] args) throws SimControlException
    {
        new GuiEngine();
    }

    public static void wypiszZdarzenie(double czas, String opis)
    {
        NumberFormat formatter = new DecimalFormat("#0.00");
        //System.out.println(formatter.format(czas)+ " " + opis);
    }
}
