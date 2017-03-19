package src;

import src.Panels.InputPanel;
import src.Panels.MultiPanel;
import src.Panels.StatePanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by Oly on 05.03.2017.
 */
public class MainFrame extends JFrame {
    private static JTabbedPane mainPanel;
    private static InputPanel inputPanel;
    private static StatePanel statePanel;
    private static MainFrame mainFrame;
    private static String directory;
    private static Boolean multiIsThere;

    private static HashMap<String,Object> hashMap;

    public MainFrame() {
        directory = "file.txt";
        hashMap = new HashMap();
        mainPanel = new JTabbedPane();
        inputPanel = new InputPanel(directory,hashMap);
        statePanel = new StatePanel(directory);
        mainPanel.addTab("Input:",inputPanel);
        mainPanel.addTab("State",statePanel);       //TODO tabs
        add(mainPanel);
        setMinimumSize(new Dimension(500,500));
        setSize(500,500);
        setTitle("Reflection pt2");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
        multiIsThere = false;
    }

    public static void renameInputPanel(String string) {
        mainPanel.setTitleAt(mainPanel.getSelectedIndex(),"Input: " + string);
    }

    public static void addInputPanel() {
        mainPanel.addTab("Input: ",new InputPanel(directory,hashMap));
    }

    public static void addMultiPanel() {
        if (!multiIsThere) {
            mainPanel.addTab("Multi", new MultiPanel(hashMap));
            multiIsThere = true;
        }
    }

    public static void closeInputPanel() {
        if (((mainPanel.getTabCount() >= 3) && (multiIsThere)) || ((mainPanel.getTabCount() > 2) && (!multiIsThere))) {
            mainPanel.remove(mainPanel.getSelectedIndex());
            mainPanel.setSelectedIndex(0);
        }
    }

    public static void closeMultiPanel() {
        if (multiIsThere) {
            mainPanel.remove(mainPanel.getSelectedIndex());
            mainPanel.setSelectedIndex(0);
            multiIsThere = false;
        }
    }

    public static void main(String[] args) {
        mainFrame = new MainFrame();
    }
}
