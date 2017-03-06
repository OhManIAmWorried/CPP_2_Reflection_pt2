package src.Panels;

import src.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Scanner;

/**
 * Created by Oly on 05.03.2017.
 */
public class InputPanel extends JPanel {
    private JLabel nameLabel;
    private MyTextField nameField;
    private JLabel constructorsLabel;
    private JScrollPane scrollPane;
    private JLabel mainLabel;
    private JLabel noteLabel;
    private GridBagConstraints c;
    private String[] constrArray;
    private JTextField recursionField;
    private JButton readyButton;

    public InputPanel() {
        nameLabel = new JLabel("Name: ");
        nameField = new MyTextField(35);
        constructorsLabel = new JLabel("Constructors: ");
        mainLabel = new JLabel();
        noteLabel = new JLabel("<html><pre>To create an instance of the chosen class<br>" +
                               "write a console command (new Class<?>... in parameters<br>" +
                               "Forbidden!) using one of constructors listed above.<br>" +
                               "AHTUNG! Using complicated parameters with classes<br>" +
                               "involves creating those at first place. TextField<br>" +
                               "below is here to help you find the class you have to make.<br>" +
                               "Type the class name there to enter the InputPanel for it<br></pre><html>");
        scrollPane = new JScrollPane(mainLabel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        recursionField = new JTextField("*AdditionalClassName*");
        readyButton = new JButton("Ready");

        c = new GridBagConstraints();
        setLayout(new GridBagLayout());

        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.NONE;
        c.gridx = 0; c.gridy = 0; c.weightx = 0.3; c.weighty = 0.1; c.gridwidth = 1; add(nameLabel,c);
        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1; c.gridy = 0; c.weightx = 0.9; c.weighty = 0.1; c.gridwidth = 1; add(nameField,c);
        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.NONE;
        c.gridx = 0; c.gridy = 1; c.weightx = 0.6; c.weighty = 0.1; c.gridwidth = 1; add(constructorsLabel,c);
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.BOTH;
        c.gridx = 0; c.gridy = 2; c.weightx = 1.2; c.weighty = 1.0; c.gridwidth = 2; add(scrollPane,c);
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.NONE;
        c.gridx = 0; c.gridy = 3; c.weightx = 1.2; c.weighty = 0.3; c.gridwidth = 2; add(noteLabel,c);
        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.NONE;
        c.gridx = 0; c.gridy = 4; c.weightx = 0.3; c.weighty = 0.1; c.gridwidth = 1; add(readyButton,c);
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1; c.gridy = 4; c.weightx = 0.9; c.weighty = 0.1; c.gridwidth = 1; add(recursionField,c);

        noteLabel.setVisible(false);
        nameField.grabFocus();

        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = nameField.getText();
                try {
                    nameField.setEnabled(false);
                    Class aClass = Class.forName(string);
                    constrArray = getConstrArray(aClass);
                    showConstructors(constrArray);
                    noteLabel.setVisible(true);
                    createAnInstance();
                } catch (ClassNotFoundException ex) {
                    nameField.setEnabled(true);
                    mainLabel.setText("No class found");
                }
            }
        });

        recursionField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                recursionField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {}
        });

        recursionField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Class<?> newClass = Class.forName(recursionField.getText());
                    MainFrame.addInputPanel(newClass);
                } catch (ClassNotFoundException e1) {
                    recursionField.setEnabled(true);
                    recursionField.setText("ClassNotFound, try again");
                    e1.printStackTrace();
                }
            }
        });
    }


    private void createAnInstance() {
        Scanner scanner = new Scanner(System.in);

    }


    private String[] getConstrArray(Class c) {
        Constructor<?>[] constructors;
        constructors = c.getDeclaredConstructors();
        Class<?>[] parameters;
        String string;
        String[] array = new String[constructors.length];
        if (constructors.length != 0) {
            for (int i = 0; i < constructors.length; i++) {
                string = "";
                parameters = constructors[i].getParameterTypes();
                for (int j = 0; j < parameters.length; j++) {
                    string += getParamType(parameters[j]) + " " + "arg" + j;
                    if (j + 1 != parameters.length) string += ", ";
                }
                array[i] = Modifier.toString(constructors[i].getModifiers()) + " " +
                        constructors[i].getDeclaringClass().getSimpleName() + "(" +
                        string + ");";
            }
        }
        return array;
    }

    private void showConstructors(String[] array) {
        mainLabel.setText("<html><pre>");
        if (array.length != 0)
            for (int i = 0; i < array.length; i++)
                mainLabel.setText(mainLabel.getText() + array[i] + "<br>");
        else mainLabel.setText(mainLabel.getText() + "No Constructors");
    }

    public void setClass(Class<?> aClass) {
        nameField.setEnabled(true);
        nameField.setText(aClass.getName());
        nameField.doAction();
    }

    //TODO button making a new instance

    private String getParamType(Class<?> that) {
        if (that.isArray()) {
            return that.getSimpleName();
        } else return that.getSimpleName();
    }
}
