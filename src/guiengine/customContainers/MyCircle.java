package guiengine.customContainers;

import javax.swing.*;
import java.awt.*;

public class MyCircle extends JLabel implements SimConnectionG
{

    private Color color;
    private float perc = (float) 0.00;
    public MyCircle() {
        color = Color.GREEN;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0,0,30,30);
        g.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(30, 30);
    }

    @Override
    public void changeColor(float perc)
    {
        if(isNan(perc)) perc = 0;
        this.perc = perc;
        color = new Color((int)(255*perc),(int)(255-255*perc),0);
        repaint();
    }

    private boolean isNan(float val) {
        return (val != val);
    }
}
