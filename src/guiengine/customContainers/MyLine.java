package guiengine.customContainers;

import javax.swing.*;
import java.awt.*;

public class MyLine extends JLabel implements SimConnectionG
{
    private int x1,y1,x2,y2;
    private int width,height;
    private Color color;
    private float perc = (float) 0.00;
    public MyLine(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        width = Math.abs(x2-x1);
        height = Math.abs(y2-y1);
        color = Color.GREEN;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(7));
        if(x1<=x2&&y1<=y2)
            g2.drawLine(0,0,width,height);
        else if(x1<=x2&&y1>=y2)
            g2.drawLine(0,height,width,0);
        else if(x1>=x2&&y1<=y2)
            g2.drawLine(width,0,0,height);
        else if(x1>=x2&&y1>=y2)
            g2.drawLine(width,height,0,0);
        g.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(x2-x1, y2-y1);
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
