package src;

import src.Panels.InputPanel;
import src.Panels.StatePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Oly on 05.03.2017.
 */
public class MainFrame extends JFrame {
    private static JTabbedPane mainPanel;
    private static InputPanel inputPanel;
    private static StatePanel statePanel;
    private static MainFrame mainFrame;

    public MainFrame() {
        mainPanel = new JTabbedPane();
        inputPanel = new InputPanel();
        statePanel = new StatePanel();
        mainPanel.addTab("Input: Main",inputPanel);
        mainPanel.addTab("State",statePanel);       //TODO tabs
        add(mainPanel);
        setMinimumSize(new Dimension(450,500));
        setSize(450,500);
        setTitle("Reflection pt2");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void addInputPanel(Class<?> aClass) {
        InputPanel panelA = new InputPanel();
        panelA.setClass(aClass);
        mainPanel.addTab("Input: " + aClass.getSimpleName(),panelA);
    }

    public static void main(String[] args) {
        mainFrame = new MainFrame();
    }
}
