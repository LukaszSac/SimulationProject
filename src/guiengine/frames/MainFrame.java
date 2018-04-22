package guiengine.frames;

import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters;
import engine.events.zbieranieDanych.KoniecSymulacjiIZbierzDane;
import engine.events.zbieranieDanych.ZbierzDane;
import engine.object.Centrala;
import engine.object.SiecTelefoniczna;
import guiengine.GuiEngine;
import guiengine.StartThread;
import guiengine.customContainers.MyCircle;
import guiengine.customContainers.MyLine;
import guiengine.customContainers.SimConnectionG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{
    private JPanel instance = null;
    private int ile = 0;
    private int[][] connections;
    private int[] abonentCount;
    private boolean toUpdateAbon = false, isToUpdateConn = false;
    private double pWyboru = 0.0;
    public MainFrame()
    {
        instance = new JPanel(null);
        instance.setSize(1000,900);
        setResizable(false);
        setSize(new Dimension(1000,900));
        instance.setBackground(Color.WHITE);
        add(instance);
        init();
    }

    private void init() {
        createMainPanel();
    }

    private void createMainPanel()
    {
        JPanel settingsPanel = new JPanel(null);
        settingsPanel.setBackground(Color.WHITE);
        settingsPanel.setSize(1000,900);
        JButton settings = new JButton("Ustawienia");
        settings.setBounds(400,100,200,50);
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                instance.remove(settingsPanel);
                createSettingsPanel(ile);
            }
        });
        settingsPanel.add(settings);
        JButton start = new JButton("Start symulacji");
        start.setBounds(400,150,200,50);
        settingsPanel.add(start);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ile>=2)
                {
                    instance.remove(settingsPanel);
                    createSimPanel(ile);
                }
            }
        });
        instance.add(settingsPanel);
        instance.repaint();
    }

    private void createSimPanel(int ile)
    {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setSize(1000,900);
        JButton exit = new JButton("Wroc");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instance.remove(panel);
                createMainPanel();
            }
        });
        exit.setBounds(900,800,100,100);
        panel.add(exit);
        double kat = 360.0/ile;
        SimConnectionG[][] lines = new SimConnectionG[ile][];
        for(int i=1;i<=ile;i++)
        {
            lines[i-1] = new SimConnectionG[i];
            double tKat = Math.toRadians(kat*i);
            double x = 320*Math.sin(tKat)+500;
            double y = 320*Math.cos(tKat)+415;
            JLabel label = new JLabel(String.valueOf(i));
            label.setBounds((int)x,(int)y,20,20);
            panel.add(label);
            MyCircle rect= new MyCircle();
            lines[i-1][i-1] = rect;
            rect.setBounds((int)(355*Math.sin(tKat)+485),(int)(355*Math.cos(tKat)+400),30,30);
            panel.add(rect);
        }
        for(int i=1;i<=ile;i++) {
            double tKat = Math.toRadians(kat*i);
            double x = 300*Math.sin(tKat)+500;
            double y = 300*Math.cos(tKat)+415;

            for(int j=i+1;j<=ile;j++) {
                int nextI = j;
                tKat = Math.toRadians(kat * nextI);
                double x2 = 300 * Math.sin(tKat) + 500;
                double y2 = 300 * Math.cos(tKat) + 415;
                double minX, minY, maxX, maxY;
                if (x < x2) {
                    minX = x;
                    maxX = x2;
                } else {
                    minX = x2;
                    maxX = x;
                }
                if (y < y2) {
                    minY = y;
                    maxY = y2;
                } else {
                    minY = y2;
                    maxY = y;
                }
                MyLine line = new MyLine((int) x, (int) y, (int) x2, (int) y2);
                lines[j-1][i-1] = line;
                line.setBounds((int) minX, (int) minY, (int) (maxX - minX) + 50, (int) (maxY - minY) + 50);
                panel.add(line);
            }
        }
        SimManager model = SimManager.getInstance();
        SiecTelefoniczna siec = new SiecTelefoniczna(ile,connections,abonentCount,lines);
        siec.setP(pWyboru);
        StartThread thread = null;
        try {
            SimControlEvent stopEvent = new KoniecSymulacjiIZbierzDane(siec, 1000.0, SimParameters.SimControlStatus.STOPSIMULATION);
            new ZbierzDane(siec, 631);
            thread = new StartThread(model);
        } catch (SimControlException e) {
            e.printStackTrace();
        }
        instance.add(panel);
        instance.repaint();
        thread.start();
    }

    private void createSettingsPanel(int ile)
    {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setSize(1000,900);
        JButton exit = new JButton("Zapisz");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instance.remove(panel);
                createMainPanel();
            }
        });
        exit.setBounds(400,500,200,50);
        panel.add(exit);
        JLabel numerSieci = new JLabel("Numer sieci: (Pomiedzy 2-6)");
        numerSieci.setBounds(50,100,200,50);
        panel.add(numerSieci);
        JTextField numerSieciV = new JTextField();
        numerSieciV.setText(String.valueOf(ile));
        numerSieciV.setBounds(250,100,100,50);
        numerSieciV.setFont(numerSieciV.getFont().deriveFont(30f));
        panel.add(numerSieciV);
        JButton iloscPolaczeni = new JButton("Ustaw ilosc polaczen");
        iloscPolaczeni.setBounds(50,150,300,50);
        iloscPolaczeni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instance.remove(panel);
                createNumberConnections(Integer.parseInt(numerSieciV.getText()));
            }
        });
        panel.add(iloscPolaczeni);
        JButton iloscPolaczenA = new JButton("Ustaw ilosc abonentow");
        iloscPolaczenA.setBounds(50,200,300,50);
        iloscPolaczenA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instance.remove(panel);
                createAbonentsCounts(Integer.parseInt(numerSieciV.getText()));
            }
        });
        JButton parametry = new JButton("Parametry");
        parametry.setBounds(50,250,300,50);
        parametry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instance.remove(panel);
                createParametersSettings();
            }
        });
        panel.add(parametry);
        panel.add(iloscPolaczenA);
        instance.add(panel);
        instance.repaint();
    }

    private void createAbonentsCounts(int ile)
    {
        if(toUpdateAbon||this.ile != ile)
        {
            this.ile = ile;
            if(!toUpdateAbon)
                isToUpdateConn = true;
            toUpdateAbon = false;
            abonentCount = new int[ile];
        }

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setSize(1000,700);
        JButton exit = new JButton("Zapisz");
        exit.setBounds(400,500,200,50);
        panel.add(exit);
        JTextField[] fields = new JTextField[ile];
        for(int i=0;i<ile;i++)
        {
            JLabel text = new JLabel(String.valueOf(i)+": ");
            text.setBounds(50,50+50*i,50,50);
            panel.add(text);
            JTextField value = fields[i] =  new JTextField();
            value.setBounds(100,50+50*i,100,50);
            value.setText(String.valueOf(abonentCount[i]));
            panel.add(value);
        }
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instance.remove(panel);
                for(int i=0;i<ile;i++)
                {
                    abonentCount[i] = Integer.parseInt(fields[i].getText());
                }
                createSettingsPanel(ile);
            }
        });
        instance.add(panel);
        instance.repaint();
    }

    private void createParametersSettings()
    {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setSize(1000,700);
        JButton exit = new JButton("Zapisz");
        exit.setBounds(400,500,200,50);
        panel.add(exit);

        JLabel pWybrania = new JLabel("Prawdopodobienstwo wyboru polaczenia (0-1): ");
        pWybrania.setBounds(100,100,300,50);
        panel.add(pWybrania);
        JTextField pWybraniaV = new JTextField();
        pWybraniaV.setBounds(400,100,100,50);
        panel.add(pWybraniaV);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pWyboru = Double.parseDouble(pWybraniaV.getText());
                instance.remove(panel);
                createSettingsPanel(ile);
            }
        });
        instance.add(panel);
        instance.repaint();
    }

    private void createNumberConnections(int ile)
    {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        panel.setSize(1000,700);
        JButton exit = new JButton("Zapisz");
        if(ile != this.ile || isToUpdateConn)
        {
            this.ile = ile;
            connections = new int[ile][ile];
            if(!isToUpdateConn)
                toUpdateAbon = true;
            isToUpdateConn = false;
        }
        JTextField[][] fields = new JTextField[ile][];
        for(int i=0;i<ile;i++)
        {
            fields[i] = new JTextField[i+1];
            for(int j=0;j<=i;j++)
            {
                JLabel number = new JLabel(i+ " "+j);
                number.setBounds(50+150*j,50+50*i,50,50);
                panel.add(number);
                JTextField numberValue = new JTextField();
                numberValue.setText(String.valueOf(connections[j][i]));
                fields[i][j] = numberValue;
                numberValue.setFont(numberValue.getFont().deriveFont(30f));
                numberValue.setBounds(70+150*j,50+50*i,100,50);
                panel.add(numberValue);
            }
        }
        exit.setBounds(400,500,200,50);
        panel.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<ile;i++)
                {
                    for(int j=0;j<=i;j++)
                    {
                        connections[j][i] = connections[i][j] = Integer.parseInt(fields[i][j].getText());
                        if(connections[j][i]>100) Centrala.ifALot = true;
                    }
                }
                instance.remove(panel);
                createSettingsPanel(ile);
            }
        });
        instance.add(panel);
        instance.repaint();
    }
}
