package guiengine;

import guiengine.frames.MainFrame;

import javax.swing.*;
import java.awt.*;

public class GuiEngine
{
    private MainFrame frame = null;
    public GuiEngine()
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                frame = new MainFrame();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
    }


}
