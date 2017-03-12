package src.Panels;

import src.MainFrame;
import src.NamedObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Oly on 05.03.2017.
 */
public class InputPanel extends JPanel {

    private GridBagConstraints c;

    private JLabel nameLabel;
    private MyTextField nameField;
    private JButton resetButton;

    private JLabel constructorsLabel;
    private JScrollPane scrollPane;
    private JLabel mainLabel;
    private JLabel noteLabel;

    private JRadioButton isArrayRButton;
    private JRadioButton isObjectRButton;
    private ButtonGroup buttonGroup;

    private JLabel arrLengthLabel;
    private JTextField arrLengthField;

    private JLabel comboBoxLabel;
    private JComboBox comboBox;

    private JTextField consoleField;
    private JLabel consoleLabel;
    private JButton addInputButton;

    private JPanel header;
    private JPanel body;
    private JPanel footer;
    private JPanel footerTopPanel;
    private JPanel footerBottomPanel;

    private String[] constrStringArray;
    private Constructor<?>[] constrArray;
    private Class<?> classType;
    private HashMap<String,Object> hashMap;
    private String directory;

    public InputPanel(String directory, HashMap<String,Object> hashMap) {

        /*Initialization==============================================================================================*/
        nameLabel = new JLabel("Name: ");
        nameField = new MyTextField(35);
        resetButton = new JButton("Res");

        constructorsLabel = new JLabel("Constructors: ");
        mainLabel = new JLabel();
        noteLabel = new JLabel("<html><pre>Choose the constructor and make sure you have all the classes<br>you need to pass into your constructor parameters.<br>" +
                "WARNING: creating anonymous classes in parameters is forbidden!<br>Press ready when everything is ready for using the constructor.<br>" +
                "Insert the full name of the primary class to open the initialization<br>window.</pre><html>");
        scrollPane = new JScrollPane(mainLabel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        isArrayRButton = new JRadioButton("is Array");
        isObjectRButton = new JRadioButton("is Object",true);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(isArrayRButton);
        buttonGroup.add(isObjectRButton);

        arrLengthLabel = new JLabel("Array length: ");
        arrLengthField = new JTextField(4);
        comboBoxLabel = new JLabel("Constructor #");
        comboBox = new JComboBox();

        consoleLabel = new JLabel("Console: ");
        consoleField = new JTextField("*ConsoleField*");
        addInputButton = new JButton("Add");

        header = new JPanel();
        body = new JPanel();
        footer = new JPanel();
        footerTopPanel = new JPanel();
        footerBottomPanel = new JPanel();

        this.hashMap = hashMap;

        /*Building====================================================================================================*/
        c = new GridBagConstraints();
        setLayout(new GridBagLayout());

        header.setLayout(new GridBagLayout());
        body.setLayout(new GridBagLayout());
        footer.setLayout(new GridBagLayout());
        footerTopPanel.setLayout(new GridBagLayout());
        footerBottomPanel.setLayout(new GridBagLayout());

        /**Header-----------------------------------------------------------------------------------------------------*/
        /*NameLabel*/
        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0; c.weightx = 0.1; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; header.add(nameLabel,c);
        /*NameField*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1; c.gridy = 0; c.weightx = 0.8; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; header.add(nameField,c);
        /*ResetButton*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.NONE;
        c.gridx = 2; c.gridy = 0; c.weightx = 0.1; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; header.add(resetButton,c);
        /*ConstructorsLabel*/
        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.NONE;
        c.gridx = 0; c.gridy = 1; c.weightx = 0.1; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; header.add(constructorsLabel,c);
        /**Body-------------------------------------------------------------------------------------------------------*/
        /*ScrollPane*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.BOTH;
        c.gridx = 0; c.gridy = 0; c.weightx = 1; c.weighty = 5; c.gridwidth = 1; c.gridheight = 1; body.add(scrollPane,c);
        /*NoteLabel*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 1; c.weightx = 1; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; body.add(noteLabel,c);
        /**Footer-----------------------------------------------------------------------------------------------------*/
        /**FooterTop*/
        /*IsArrayRButton*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.NONE;
        arrLengthLabel.setHorizontalAlignment(JLabel.RIGHT);
        c.gridx = 0; c.gridy = 0; c.weightx = 0.4; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; footerTopPanel.add(isArrayRButton,c);
        /*IsObjectRButton*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.NONE;
        arrLengthLabel.setHorizontalAlignment(JLabel.RIGHT);
        c.gridx = 0; c.gridy = 1; c.weightx = 0.4; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; footerTopPanel.add(isObjectRButton,c);

        /*ArrLengthLabel*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.NONE;
        arrLengthLabel.setHorizontalAlignment(JLabel.RIGHT);
        c.gridx = 1; c.gridy = 0; c.weightx = 0.4; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; footerTopPanel.add(arrLengthLabel,c);
        /*ComboBoxLabel*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.NONE;
        comboBoxLabel.setHorizontalAlignment(JLabel.RIGHT);
        c.gridx = 1; c.gridy = 1; c.weightx = 0.4; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; footerTopPanel.add(comboBoxLabel,c);

        /*ArrLengthField*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.NONE;
        c.gridx = 2; c.gridy = 0; c.weightx = 0.2; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; footerTopPanel.add(arrLengthField,c);
        /*ComboBox*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.NONE;
        c.gridx = 2; c.gridy = 1; c.weightx = 0.2; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; footerTopPanel.add(comboBox,c);
        /**FooterBottom*/
        /*ConsoleLabel*/
        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.HORIZONTAL;
        consoleLabel.setHorizontalAlignment(JLabel.RIGHT);
        c.gridx = 0; c.gridy = 0; c.weightx = 0.2; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; footerBottomPanel.add(consoleLabel,c);
        /*ConsoleField*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1; c.gridy = 0; c.weightx = 0.7; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; footerBottomPanel.add(consoleField,c);
        /*AddInputButton*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.NONE;
        c.gridx = 2; c.gridy = 0; c.weightx = 0.1; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; footerBottomPanel.add(addInputButton,c);
        /**Footers*/
        /*FooterTop*/
        c.anchor = GridBagConstraints.NORTH; c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 0; c.weightx = 1; c.weighty = 2; c.gridwidth = 1; c.gridheight = 1; footer.add(footerTopPanel,c);
        /*FooterBottom*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 1; c.weightx = 1; c.weighty = 1; c.gridwidth = 1; c.gridheight = 1; footer.add(footerBottomPanel,c);
        /**MainPanel--------------------------------------------------------------------------------------------------*/
        /*Header*/
        c.anchor = GridBagConstraints.NORTH; c.fill = GridBagConstraints.BOTH;
        c.gridx = 0; c.gridy = 0; c.weightx = 1; c.weighty = 0.2; c.gridwidth = 1; c.gridheight = 1; add(header,c);
        /*Body*/
        c.anchor = GridBagConstraints.CENTER; c.fill = GridBagConstraints.BOTH;
        c.gridx = 0; c.gridy = 1; c.weightx = 1; c.weighty = 0.6; c.gridwidth = 1; c.gridheight = 1; add(body,c);
        /*Footer*/
        c.anchor = GridBagConstraints.SOUTH; c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0; c.gridy = 2; c.weightx = 1; c.weighty = 0.2; c.gridwidth = 1; c.gridheight = 1; add(footer,c);

        /*Presets=====================================================================================================*/
        this.directory = directory;
        nameField.grabFocus();
        switchState(false);
        /*Listeners===================================================================================================*/

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                if (nameField.isEnabled()) nameField.grabFocus();
            }
        });

        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = nameField.getText();
                try {
                    nameField.setEnabled(false);
                    classType = Class.forName(string);
                    constrArray = classType.getDeclaredConstructors();
                    constrStringArray = getConstrStringArray(constrArray);
                    comboBox.removeAllItems();
                    for (int i = 0; i < constrArray.length; i++) comboBox.addItem(Integer.toString(i));
                    showConstructors(constrStringArray);
                    switchState(true);
                    MainFrame.renameInputPanel(classType.getSimpleName());
                } catch (ClassNotFoundException ex) {
                    mainLabel.setText("No class found");
                    switchState(false);
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                mainLabel.setText("<html><pre>");
                switchState(false);
                nameField.grabFocus();
            }
        });

        isArrayRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arrLengthField.setEnabled(true);
                arrLengthLabel.setEnabled(true);
                comboBox.setEnabled(false);
                comboBoxLabel.setEnabled(false);
            }
        });

        isObjectRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox.setEnabled(true);
                comboBoxLabel.setEnabled(true);
                arrLengthField.setEnabled(false);
                arrLengthLabel.setEnabled(false);
            }
        });

        arrLengthField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                arrLengthField.setText("");}

            @Override
            public void focusLost(FocusEvent e) {}
        });

        consoleField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {consoleField.setText("");}

            @Override
            public void focusLost(FocusEvent e) {}
        });

        consoleField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAnInstance();
                resetButton.doClick();
            }
        });

        addInputButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.addInputPanel();
            }
        });
    }

    private void switchState(boolean state) {
        //state = false
        //DONOTTOUCH! XD
        nameField.setEnabled(!state);
        isArrayRButton.setEnabled(state);
        isObjectRButton.setEnabled(state);
        isArrayRButton.setSelected(!state);
        isObjectRButton.setSelected(state);
        noteLabel.setVisible(state);
        arrLengthField.setEnabled(false);
        arrLengthLabel.setEnabled(false);
        comboBox.setEnabled(state);
        resetButton.setEnabled(state);
        consoleField.setEnabled(state);
        comboBoxLabel.setEnabled(state);
        consoleLabel.setEnabled(state);
        constructorsLabel.setEnabled(state);
    }

    protected static Object[] getObjects(HashMap<String,Object> hashMap, String[] strings, Class<?>[] parTypes) {
        Object[] objects = new Object[strings.length];

        for (int i = 0; i < parTypes.length; i++) {
            if (hashMap.containsKey(strings[i])) objects[i] = hashMap.get(strings[i]);
            else {
                System.out.println("i = " + i);
                System.out.println(parTypes[i]);

                if (parTypes[i] == Integer.TYPE) objects[i] = Integer.parseInt(strings[i]);
                if (parTypes[i] == Double.TYPE) objects[i] = Double.parseDouble(strings[i]);
                if (parTypes[i] == Float.TYPE) objects[i] = Float.parseFloat(strings[i]);
                if (parTypes[i] == Character.TYPE) objects[i] = strings[i].toCharArray()[0];
                if (parTypes[i] == Boolean.TYPE) objects[i] = Boolean.parseBoolean(strings[i]);
                if (parTypes[i] == Byte.TYPE) objects[i] = Byte.parseByte(strings[i]);
                if (parTypes[i] == Short.TYPE) objects[i] = Short.parseShort(strings[i]);
                if (parTypes[i] == Long.TYPE) objects[i] = Long.parseLong(strings[i]);

                if ((objects[i] == null) && (strings[i].startsWith("\"")) && (strings[i].endsWith("\""))) objects[i] = strings[i].replaceAll("\"","");
            }
        }
        return objects;
    }

    private void createAnInstance() {
        //TODO then writing the class via some sort of Serialization to the file, some sort of class array in the source code, and so on
        //TODO and at last, making the StatePanel with the list of classes and methods for each class
        Constructor<?> constructor = constrArray[comboBox.getSelectedIndex()];
        System.out.println("Selected index: " + comboBox.getSelectedIndex());
        String input = consoleField.getText();
        String[] parStrings = input
                .split("\\(")[1]
                .replaceAll("\\);","")
                .replaceAll(" ","")
                .split(",");
        System.out.println(parStrings[0]);
        Class<?>[] parTypes = constructor.getParameterTypes();
        System.out.println("parTypes length = " + parTypes.length);

        Object[] objects = getObjects(hashMap,parStrings,parTypes);

        try {
            String[] stringArr = input.split("=")[0].split(" ");
            String name = stringArr[stringArr.length - 1];
            Object obj = constructor.newInstance(objects);
            hashMap.put(name,obj);
            System.out.println(hashMap.size());
            LinkedList<NamedObject> linkedList = new LinkedList<NamedObject>();
            linkedList.addLast(new NamedObject(obj,name));
            writeToFile(directory,parTypes,parStrings,obj,name);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String dir, Class<?>[] parTypes, String[] strings, Object obj, String name) {
        StringBuilder sb = new StringBuilder("<0>");
        sb.append(obj.getClass().getCanonicalName())
                .append(":")
                .append(name)
                .append(";");
        for (int i = 0; i < parTypes.length; i++) {
            sb.append(parTypes[i].getName())
                    .append(";");
        }
        sb.append("<1>");
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i])
                    .append(";");

        }
        try (FileWriter fw = new FileWriter(dir, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] getConstrStringArray(Constructor<?>[] constructors) {
        Class<?>[] parameters;
        StringBuilder stringBuilderInner = new StringBuilder();
        StringBuilder stringBuilderOuter = new StringBuilder();
        String[] array = new String[constructors.length];
        if (constructors.length != 0) {
            for (int i = 0; i < constructors.length; i++) {
                stringBuilderOuter.append("#").append(i).append(" ");
                parameters = constructors[i].getParameterTypes();
                for (int j = 0; j < parameters.length; j++) {
                    stringBuilderInner.append(getParamType(parameters[j])).append(" ").append("arg").append(j);
                    if (j + 1 != parameters.length) stringBuilderInner.append(", ");
                }
                array[i] = stringBuilderOuter
                        .append(Modifier.toString(constructors[i].getModifiers()))
                        .append(" ")
                        .append(constructors[i].getDeclaringClass().getSimpleName())
                        .append("(")
                        .append(stringBuilderInner.toString())
                        .append(");")
                        .toString();
                stringBuilderOuter.setLength(0);
                stringBuilderInner.setLength(0);
            }
        }
        return array;
    }

    private void showConstructors(String[] array) {
        StringBuilder sb = new StringBuilder("<html><pre>");
        if (array.length != 0)
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
                sb.append("<br>");
            }
        else sb.append("No Constructors");
        mainLabel.setText(sb.toString());
    }

    public void setClass(Class<?> aClass) {
        nameField.setEnabled(true);
        nameField.setText(aClass.getName());
        nameField.doAction();
    }

    private String getParamType(Class<?> that) {
        if (that.isArray()) {
            return that.getSimpleName();
        } else return that.getSimpleName();
    }
}
