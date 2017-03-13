package src.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Oly on 05.03.2017.
 */
public class StatePanel extends JPanel {
    private JScrollPane leftScrollPane;
    private JScrollPane rightScrollPane;
    private JComboBox objectComboBox;
    private JComboBox methodComboBox;
    private JLabel objectLabel;
    private JLabel consoleLabel;
    private JLabel objectListLabel;
    private JLabel propsListLabel;
    private JTextField consoleField;

    private JPanel leftPanel;
    private JPanel footerPanel;

    private HashMap<String,Object> hashMap;
    private String directory;
    private Method[] method;

    public StatePanel(String directory) {
        hashMap = new HashMap<>();
        this.directory = directory;

        objectListLabel = new JLabel("<html><pre>");
        propsListLabel = new JLabel("<html><pre>");
        leftScrollPane = new JScrollPane(objectListLabel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        rightScrollPane = new JScrollPane(propsListLabel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        objectComboBox = new JComboBox();
        methodComboBox = new JComboBox();
        objectLabel = new JLabel("Objects:");
        consoleLabel = new JLabel("Console: ");
        consoleField = new JTextField();

        leftPanel = new JPanel(new GridBagLayout());
        footerPanel = new JPanel(new GridBagLayout());

        /**Building===================================================================================================*/

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        /**MainPanel*/
        /*LeftPanel*/
        c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1; c.weightx = 0.1; c.weighty = 9;
        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.BOTH; add(leftPanel,c);
        /*RightScrollPane*/
        c.gridx = 1; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1; c.weightx = 0.9; c.weighty = 9;
        c.anchor = GridBagConstraints.EAST; c.fill = GridBagConstraints.BOTH; add(rightScrollPane,c);
        /*FooterPanel*/
        c.gridx = 0; c.gridy = 1; c.gridwidth = 2; c.gridheight = 1; c.weightx = 1; c.weighty = 1;
        c.anchor = GridBagConstraints.SOUTH; c.fill = GridBagConstraints.HORIZONTAL; add(footerPanel,c);
        /**LeftPanel*/
        /*ObjectLabel*/
        c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1; c.weightx = 1; c.weighty = 1;
        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.HORIZONTAL; leftPanel.add(objectLabel);
        /*LeftScrollPane*/
        c.gridx = 0; c.gridy = 1; c.gridwidth = 1; c.gridheight = 1; c.weightx = 1; c.weighty = 7;
        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.BOTH; leftPanel.add(leftScrollPane,c);
        /*ObjectComboBox*/
        c.gridx = 0; c.gridy = 2; c.gridwidth = 1; c.gridheight = 1; c.weightx = 1; c.weighty = 1;
        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.HORIZONTAL; leftPanel.add(objectComboBox,c);
        /*MethodComboBox*/
        c.gridx = 0; c.gridy = 3; c.gridwidth = 1; c.gridheight = 1; c.weightx = 1; c.weighty = 1;
        methodComboBox.setPrototypeDisplayValue("----------------------------------");
        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.HORIZONTAL; leftPanel.add(methodComboBox,c);
        /**Footer*/
        /*ConsoleLabel*/
        c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1; c.weightx = 3; c.weighty = 1;
        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.NONE; footerPanel.add(consoleLabel,c);
        /*ConsoleField*/
        c.gridx = 1; c.gridy = 0; c.gridwidth = 1; c.gridheight = 1; c.weightx = 7; c.weighty = 1;
        c.anchor = GridBagConstraints.WEST; c.fill = GridBagConstraints.HORIZONTAL; footerPanel.add(consoleField,c);

        /**Defaults===================================================================================================*/

        /**Listeners==================================================================================================*/

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                readObjects(directory);
                updateObjectListLabel();
                updateObjectComboBox();
                objectComboBox.setSelectedIndex(0);
                updateMethodComboBox();
                methodComboBox.setSelectedIndex(0);
                updatePropsListLabel();
            }
        });

        objectComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMethodComboBox();
                updatePropsListLabel();
            }
        });
    }

    private void updateMethod(Object object) {
        method = object.getClass().getMethods();
    }

    private void readObjects(String dir) {
        hashMap.clear();

        StringBuilder sb = new StringBuilder();
        try{
            FileReader inputFile = new FileReader(dir);
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String line;
            while ((line = bufferReader.readLine()) != null)   {
                sb.append(line);
            }
            sb.delete(0,3);
            System.out.println(sb.toString());
            bufferReader.close();
        } catch(Exception e){
            e.printStackTrace();
        }

        String[] stringArr = sb.toString().split("<0>");
        ArrayList<Object> arrayList = new ArrayList();

        for (String string : stringArr) {
            String[] rightArray;
            if (string.split("<1>").length > 1) rightArray = string.split("<1>")[1].split(";");
            else rightArray = new String[0];
            String[] leftArray = string.split("<1>")[0].split(";"); //Note: the first index stays for "Class:name"

            Class<?>[] parTypes = new Class[leftArray.length - 1];
            for (int i = 1; i < leftArray.length; i++) {

                if (leftArray[i].equals("int")) parTypes[i - 1] = Integer.TYPE;
                if (leftArray[i].equals("double")) parTypes[i - 1] = Double.TYPE;
                if (leftArray[i].equals("float")) parTypes[i - 1] = Float.TYPE;
                if (leftArray[i].equals("char")) parTypes[i - 1] = Character.TYPE;
                if (leftArray[i].equals("boolean")) parTypes[i - 1] = Boolean.TYPE;
                if (leftArray[i].equals("byte")) parTypes[i - 1] = Byte.TYPE;
                if (leftArray[i].equals("short")) parTypes[i - 1] = Short.TYPE;
                if (leftArray[i].equals("long")) parTypes[i - 1] = Long.TYPE;

                if (parTypes[i - 1] == null) {
                    try {
                        parTypes[i - 1] = Class.forName(leftArray[i]);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            Object[] objects = InputPanel.getObjects(hashMap,rightArray,parTypes);
            try {
                hashMap.put(leftArray[0].split(":")[1],Class.forName(leftArray[0].split(":")[0]).getConstructor(parTypes).newInstance(objects));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateObjectListLabel() {
        StringBuilder sb = new StringBuilder("<html><pre>");
        ArrayList arrayList = new ArrayList(hashMap.entrySet());
        for (int i = 0; i < hashMap.size(); i++) {
            try {
                sb.append(Class.forName(hashMap.get(arrayList.get(i).toString().split("=")[0]).getClass().getName()).getSimpleName())
                        .append(" ")
                        .append(arrayList.get(i).toString().split("=")[0])
                        .append("<br>");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(arrayList.get(i).toString());
        }
        objectListLabel.setText(sb.toString());
    }

    private void updatePropsListLabel() {
        StringBuilder sb = new StringBuilder("<html><pre>");
        String[] stringArr = objectComboBox.getSelectedItem().toString().split(" ");
        Object obj = hashMap.get(stringArr[stringArr.length - 1]);

        System.out.println("updating fields");

        /*Fields*/
        Field[] fields = obj.getClass().getDeclaredFields();
        sb.append("*Fields*<br>");
        for (Field f : fields) {
            try {
                f.setAccessible(true);
                sb.append(Modifier.toString(f.getModifiers()))
                        .append(" ")
                        .append(f.getType().getCanonicalName())
                        .append(" ")
                        .append(f.getName())
                        .append(" = ")
                        .append(f.get(obj))
                        .append(";<br>");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        /*Methods*/
        sb.append("*Methods*<br>");
        {
            int i = 0;
            for (Method m : method) {
                sb.append("#")
                        .append(i++)
                        .append(" ")
                        .append(Modifier.toString(m.getModifiers()))
                        .append(" ")
                        .append(m.getName())
                        .append("(")
                        .append(m.getParameters())
                        .append("): ")
                        .append(m.getReturnType())
                        .append(";<br>");
            }
        }
        propsListLabel.setText(sb.toString());
    }

    private void updateObjectComboBox() {
        objectComboBox.removeAllItems();
        ArrayList arrayList = new ArrayList(hashMap.entrySet());
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < hashMap.size(); i++) {
            try {
                sb.append(Class.forName(hashMap.get(arrayList.get(i).toString().split("=")[0]).getClass().getName()).getSimpleName())
                .append(" ")
                .append(arrayList.get(i).toString().split("=")[0]);
                objectComboBox.addItem(sb.toString());
                sb.setLength(0);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (objectComboBox.getItemCount() != 0) updateMethodComboBox();
    }

    private void updateMethodComboBox() {
        methodComboBox.removeAllItems();
        String[] stringArr = objectComboBox.getSelectedItem().toString().split(" ");
        Object object = hashMap.get(stringArr[stringArr.length - 1]);
        updateMethod(object);

        if (method.length != 0) {
            StringBuilder sb = new StringBuilder();
            Class<?>[] parameters;
            for (int i = 0; i < method.length; i++) {
                sb.setLength(0);
                /*
                parameters = method[i].getParameterTypes();
                for (int j = 0; j < parameters.length; j++) {
                    sb.append(" ")
                            .append(getParamType(parameters[j]))
                            .append(" ")
                            .append("arg")
                            .append(j);
                    if (j + 1 != parameters.length) sb.append(", ");
                }
                */
                methodComboBox.addItem(sb.append("): ")
                        .append(method[i].getReturnType().getSimpleName())
                        .insert(0,"...")
                        .insert(0,"(")
                        .insert(0,method[i].getName())
                    /*
                    .insert(0," ")
                    .insert(0, Modifier.toString(method[i].getModifiers()))
                    */
                        .insert(0," ")
                        .insert(0,i)
                        .insert(0,"#")
                        .toString());

            }
        }
    }

    private String getParamType(Class<?> that) {
        if (that.isArray()) {
            return that.getSimpleName();
        } else return that.getSimpleName();
    }
}
