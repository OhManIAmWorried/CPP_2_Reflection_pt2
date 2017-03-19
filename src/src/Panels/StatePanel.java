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

    private HashMap<String,Object> hashMapGlobal;
    private String directoryGlobal;
    private Method[] methodsGlobal;

    public StatePanel(String directoryGlobal) {
        hashMapGlobal = new HashMap<>();
        this.directoryGlobal = directoryGlobal;
        methodsGlobal = new Method[0];

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
                readObjects(directoryGlobal, hashMapGlobal);
                updateObjectListLabel();
                updateObjectComboBox();
                if (objectComboBox.getItemCount() != 0) {
                    objectComboBox.setSelectedIndex(0);
                    updateMethodComboBox();
                }
                if (methodComboBox.getItemCount() != 0) {
                    methodComboBox.setSelectedIndex(0);
                    updatePropsListLabel();
                }
            }
        });

        objectComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMethodComboBox();
                updatePropsListLabel();
            }
        });

        consoleField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                invokeMethod();
            }
        });
    }

    private void invokeMethod() {
        Method m = methodsGlobal[methodComboBox.getSelectedIndex()];
        if (m.getReturnType().equals(Void.TYPE)) {
            Object obj = hashMapGlobal.get(objectComboBox.getSelectedItem().toString().split(" ")[1]);
            String[] strings = consoleField.getText().split("\\(")[1].replaceAll("\\);","").split(",");
            Object[] objects = InputPanel.getObjects(hashMapGlobal,strings,m.getParameterTypes());

            if ((objects.length == 1) && (objects[0] == null)) objects = null;

            try {
                m.invoke(obj,objects);
                updatePropsListLabel();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        } else {
            String[] leftArr = consoleField.getText().split("=")[0].split(" ");
            String name = leftArr[leftArr.length - 1];
            Class<?> classType = m.getReturnType();
            Object[] objects = InputPanel.getObjects(hashMapGlobal,consoleField.getText().split("\\(")[1].replaceAll("\\);","").split(","),m.getParameterTypes());
            try {
                Object obj1 = m.invoke(objects);
                hashMapGlobal.put(name,objects);
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            }
        }
        updatePropsListLabel();
    }

    private void updateMethod(Object object) {
        methodsGlobal = object.getClass().getMethods();
    }

    protected static void readObjects(String dir, HashMap<String,Object> hashMap) {
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

        if (sb.length() == 0) return;

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
        ArrayList arrayList = new ArrayList(hashMapGlobal.entrySet());
        for (int i = 0; i < hashMapGlobal.size(); i++) {
            try {
                sb.append(Class.forName(hashMapGlobal.get(arrayList.get(i).toString().split("=")[0]).getClass().getName()).getSimpleName())
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
        Object obj = hashMapGlobal.get(stringArr[stringArr.length - 1]);

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
            for (Method m : methodsGlobal) {
                sb.append("#")
                        .append(i++)
                        .append(" ")
                        .append(Modifier.toString(m.getModifiers()))
                        .append(" ")
                        .append(m.getName())
                        .append("(");
                Class<?>[] params = m.getParameterTypes();
                for (int j = 0; i < params.length; j++) {
                    sb.append("arg")
                            .append(j)
                            .append(" ")
                            .append(params.getClass());
                    if ((j + 1) != params.length) sb.append(", ");
                }

                sb.append("): ")
                        .append(m.getReturnType().getCanonicalName())
                        .append(";<br>");
            }
        }
        propsListLabel.setText(sb.toString());
    }

    private void updateObjectComboBox() {
        objectComboBox.removeAllItems();
        ArrayList arrayList = new ArrayList(hashMapGlobal.entrySet());
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < hashMapGlobal.size(); i++) {
            try {
                sb.append(Class.forName(hashMapGlobal.get(arrayList.get(i).toString().split("=")[0]).getClass().getName()).getSimpleName())
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
        if (methodsGlobal.length != 0) {
            methodComboBox.removeAllItems();
            String[] stringArr = objectComboBox.getSelectedItem().toString().split(" ");
            Object object = hashMapGlobal.get(stringArr[stringArr.length - 1]);
            updateMethod(object);

            StringBuilder sb = new StringBuilder();
            Class<?>[] parameters;
            for (int i = 0; i < methodsGlobal.length; i++) {
                sb.setLength(0);
                /*
                parameters = methodsGlobal[i].getParameterTypes();
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
                        .append(methodsGlobal[i].getReturnType().getSimpleName())
                        .insert(0,"...")
                        .insert(0,"(")
                        .insert(0, methodsGlobal[i].getName())
                    /*
                    .insert(0," ")
                    .insert(0, Modifier.toString(methodsGlobal[i].getModifiers()))
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
