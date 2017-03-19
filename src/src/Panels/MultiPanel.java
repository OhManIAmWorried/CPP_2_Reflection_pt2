package src.Panels;

import src.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Oly on 18.03.2017.
 */
public class MultiPanel extends JPanel {
    private JLabel classLabel;
    private JTextField classField;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel dimensionsLabel;
    private JTextField dimensionsField;
    private JLabel cellIndexLabel;
    private JTextField cellIndexField;
    private JLabel valueLabel;
    private JTextField valueField;

    private JLabel resultLabel;
    private JScrollPane scrollPane;
    private JLabel consoleLabel;
    private JButton closeButton;
    private JButton resetButton;
    private JButton initButton;

    private JPanel body;
    private JPanel footer;

    private Class classType;
    private String name;
    private int[] dimensions;
    private Object tempArray;
    private HashMap<String,Object> hashMap;

    public MultiPanel(HashMap<String,Object> hashMap) {
        /*Initialization==============================================================================================*/
        classLabel = new JLabel("Class: ");
        nameLabel = new JLabel("Name: ");
        dimensionsLabel = new JLabel("Dimensions n: ");
        cellIndexLabel = new JLabel("Cell index: ");
        valueLabel = new JLabel("Value: ");
        classField = new JTextField();
        nameField = new JTextField();
        dimensionsField = new JTextField();
        cellIndexField = new JTextField();
        valueField = new JTextField();

        resultLabel = new JLabel("Result:");
        consoleLabel = new JLabel();
        scrollPane = new JScrollPane(consoleLabel,ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        closeButton = new JButton("Close");
        resetButton = new JButton("Reset");
        initButton = new JButton("Initialize");

        body = new JPanel(new GridBagLayout());
        footer = new JPanel(new GridBagLayout());

        this.hashMap = hashMap;
        /*Construction================================================================================================*/
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        /**Body=======================================================================================================*/
        /*ClassLabel*/
        c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1; c.weightx = 0.5; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.CENTER; body.add(classLabel,c);
        /*ClassField*/
        c.gridx = 1; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1; c.weightx = 0.5; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.CENTER; body.add(classField,c);
        /*NameLabel*/
        c.gridx = 0; c.gridy = 1; c.gridheight = 1; c.gridwidth = 1; c.weightx = 0.5; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.CENTER; body.add(nameLabel,c);
        /*NameField*/
        c.gridx = 1; c.gridy = 1; c.gridheight = 1; c.gridwidth = 1; c.weightx = 0.5; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.CENTER; body.add(nameField,c);
        /*DimensionsLabel*/
        c.gridx = 0; c.gridy = 2; c.gridheight = 1; c.gridwidth = 1; c.weightx = 0.5; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.CENTER; body.add(dimensionsLabel,c);
        /*DimensionsField*/
        c.gridx = 1; c.gridy = 2; c.gridheight = 1; c.gridwidth = 1; c.weightx = 0.5; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.CENTER; body.add(dimensionsField,c);
        /*CellIndexLabel*/
        c.gridx = 0; c.gridy = 3; c.gridheight = 1; c.gridwidth = 1; c.weightx = 0.5; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.CENTER; body.add(cellIndexLabel,c);
        /*CellIndexField*/
        c.gridx = 1; c.gridy = 3; c.gridheight = 1; c.gridwidth = 1; c.weightx = 0.5; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.CENTER; body.add(cellIndexField,c);
        /*ValueLabel*/
        c.gridx = 0; c.gridy = 4; c.gridheight = 1; c.gridwidth = 1; c.weightx = 0.5; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.CENTER; body.add(valueLabel,c);
        /*ValueField*/
        c.gridx = 1; c.gridy = 4; c.gridheight = 1; c.gridwidth = 1; c.weightx = 0.5; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.CENTER; body.add(valueField,c);

        /**Footer=====================================================================================================*/
        /*ResultLabel*/
        c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1; c.weightx = 1; c.weighty = 1;
        c.fill = GridBagConstraints.NONE; c.anchor = GridBagConstraints.WEST; footer.add(resultLabel,c);
        /*ScrollPane*/
        c.gridx = 0; c.gridy = 1; c.gridheight = 1; c.gridwidth = 3; c.weightx = 3; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.CENTER; footer.add(scrollPane,c);
        /*CloseButton*/
        c.gridx = 0; c.gridy = 2; c.gridheight = 1; c.gridwidth = 1; c.weightx = 1; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.SOUTH; footer.add(closeButton,c);
        /*ResetButton*/
        c.gridx = 1; c.gridy = 2; c.gridheight = 1; c.gridwidth = 1; c.weightx = 1; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.SOUTH; footer.add(resetButton,c);
        /*InitButton*/
        c.gridx = 2; c.gridy = 2; c.gridheight = 1; c.gridwidth = 1; c.weightx = 1; c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL; c.anchor = GridBagConstraints.SOUTH; footer.add(initButton,c);

        /**Main=======================================================================================================*/
        /*Body*/
        c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1; c.weightx = 1; c.weighty = 1;
        c.fill = GridBagConstraints.BOTH; c.anchor = GridBagConstraints.CENTER; add(body,c);
        /*Footer*/
        c.gridx = 0; c.gridy = 1; c.gridheight = 1; c.gridwidth = 1; c.weightx = 1; c.weighty = 1;
        c.fill = GridBagConstraints.BOTH; c.anchor = GridBagConstraints.NORTH; add(footer,c);

        /*Defaults====================================================================================================*/
        reset();

        /*Listeners===================================================================================================*/
        /**Step1:ClassType*/
        classField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    classType = Class.forName(classField.getText());
                    classField.setEnabled(false);
                    classLabel.setEnabled(false);
                    nameField.setEnabled(true);
                    nameLabel.setEnabled(true);
                    resultLabel.setEnabled(true);
                    consoleLabel.setText(consoleLabel.getText() + classType.getSimpleName());
                    resetButton.setEnabled(true);
                    nameField.grabFocus();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        /**Step2:Name*/
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = nameField.getText();
                nameField.setEnabled(false);
                nameLabel.setEnabled(false);
                dimensionsField.setEnabled(true);
                dimensionsLabel.setEnabled(true);
                consoleLabel.setText(consoleLabel.getText() + " " + name);
                dimensionsField.grabFocus();
            }
        });

        /**Step3:Dimensions*/
        dimensionsField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dimensionsField.setEnabled(true);
                String input = dimensionsField.getText().replaceAll(" ","");
                System.out.println("input" + input);
                dimensionsField.setEnabled(false);
                ArrayBlockingQueue<Integer> dim = getDimensions(input);
                StringBuilder sb = new StringBuilder();
                ArrayBlockingQueue<Integer> dimCopy = new ArrayBlockingQueue<Integer>(dim.size(),true,dim);
                sb.append(getDimString(dimCopy)).append(" = ");
                dimCopy = new ArrayBlockingQueue<Integer>(dim.size(),true,dim);
                cellIndexField.setEnabled(true);
                cellIndexLabel.setEnabled(true);
                consoleLabel.setName(consoleLabel.getText() + sb.toString());
                initTempArray(classType,name,dim);
                updateConsoleLabel();
                initButton.setEnabled(true);
                dimensionsField.setEnabled(false);
                dimensionsLabel.setEnabled(false);
                cellIndexField.grabFocus();
            }
        });

        /**Step4:CellIndex*/
        cellIndexField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cellIndexField.setEnabled(false);
                cellIndexLabel.setEnabled(false);
                valueField.setEnabled(true);
                valueLabel.setEnabled(true);
                valueField.setText("");
                valueField.grabFocus();
            }
        });

        /**Step5:Value*/
        valueField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cellIndexField.setEnabled(true);
                String[] strings = cellIndexField.getText().replaceAll(" ","").replaceAll("]","").replaceFirst("\\[","").split("\\[");
                cellIndexField.setEnabled(false);
                int[] indexx = new int[strings.length];
                for (int i = 0; i < strings.length; i++) {
                    indexx[i] = Integer.parseInt(strings[i]);
                }
                strings = new String[]{valueField.getText().replaceAll(" ", "")};
                System.out.println("I'm about to set the cell");
                consoleLabel.setText(insertMarkingPoint(consoleLabel.getText(),dimensions,indexx));
                setCell(tempArray,strings[0],indexx);
                System.out.println("I've set the cell");
                valueField.setEnabled(false);
                valueLabel.setEnabled(false);
                cellIndexField.setEnabled(true);
                cellIndexLabel.setEnabled(true);
                cellIndexField.setText("");
                cellIndexField.grabFocus();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.closeMultiPanel();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
    }

    private void updateConsoleLabel() {
        StringBuilder sb = new StringBuilder(consoleLabel.getText().split("=")[0]);
        sb.append(getQuotes(tempArray));
        consoleLabel.setText(sb.toString());
        System.out.println(sb.toString());
    }

    private String getQuotes(Object obj) {
        StringBuilder sb = new StringBuilder("{");
        if (obj instanceof Object[]) {
            Object[] array = (Object[]) obj;
            for (int i = 0; i < array.length; i++) {
                if (array[i] != null) sb.append(getQuotes(array[i]));
                if (i != array.length - 1) sb.append(",");
            }
        }
        return sb.append("}").toString();
    }

    private String insertMarkingPoint(String string, int[] dimensions, int ... indexx) {
        StringBuilder sb = new StringBuilder();
        char[] array = string.toCharArray();
        int n = 0;
        System.out.println("indexx " + indexx[0] + " " + indexx[1]);
        for (int i = 0; i < indexx.length; i++) {
            int multiplication = indexx[i];
            System.out.println("dim.length == " + dimensions.length);
            if (i < dimensions.length - 1) for (int j = i + 1; j < dimensions.length; j++) {multiplication *= dimensions[j];}
            n += multiplication;
        }
        int k = 0;
        int i = 0;
        while (i < array.length) {
            if ((k < n) && (array[i] == ',')) {
                ++k;
                sb.append(array[i]);
                if (k == n) {
                    if (i != array.length - 1) {
                        int to = string.indexOf(",", i + 1);
                        for (int j = i + 1; j < to; j++) {++i;}
                    }
                    sb.append("<0>");
                }
            }
            else sb.append(array[i]);
            i++;
        }
        return sb.toString();
    }

    private void setCell(Object obj, String value, int ... indexx) {
        if (obj instanceof Object[]) {
            Object[] array = (Object[]) obj;
            int[] indxCopy = new int[indexx.length - 1];
            System.arraycopy(indexx,1,indxCopy,0,indxCopy.length);
            setCell(array[indexx[0]],value,indxCopy);
        }
        else {
            if (hashMap.containsKey(value)) {
                obj = hashMap.get(value);
                consoleLabel.setText(consoleLabel.getText().replaceAll("<0>",value));
            } else {
                String name = classType.getSimpleName();
                switch (name) {
                    case "Integer": {obj = value; break;}
                    case "Double": {obj = value; break;}
                    case "Float": {obj = value; break;}
                    case "Boolean": {obj = value; break;}
                    case "String": {obj = "\"" + value + "\""; break;}
                    case "Character": {obj = "\'" + value + "\'"; break;}
                    case "Long": {obj = value; break;}
                    case "Byte": {obj = value; break;}
                    case "Short": {obj = value; break;}
                }
                if (obj != null) consoleLabel.setText(consoleLabel.getText().replaceAll("<0>",obj.toString()));
                else consoleLabel.setText(consoleLabel.getText().replaceAll("<0>","null"));
            }
        }
    }

    private void initTempArray(Class<?> classType, String name, ArrayBlockingQueue<Integer> dim) {
        System.out.println("dim size" + dim.size());
        int[] arrInt = new int[dim.size()];
        for (int i = 0; i < arrInt.length; i++) {
            arrInt[i] = dim.poll().intValue();
        }
        dimensions = arrInt;
        tempArray = Array.newInstance(classType,arrInt);
    }

    private String getDimString(ArrayBlockingQueue<Integer> queue) {
        StringBuilder sb = new StringBuilder();
        int n = queue.poll();
        String internalString = "";
        if (queue.size() != 0) internalString = getDimString(queue);
        for (int i = 0; i < n; i++) {
            sb.append("\\{");
            sb.append(internalString);
            sb.append("\\}");
            if (i != queue.size() - 1) sb.append(",");
        }
        return sb.toString();
    }

    private ArrayBlockingQueue<Integer> getDimensions(String input) {
        String[] arrString = input.replaceAll(" ","").split(",");
        System.out.println("arrString length" + arrString.length);
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue(arrString.length);
        for (int i = 0; i < arrString.length; i++) {
            queue.add(Integer.parseInt(arrString[i]));
        }
        return queue;
    }

    private void reset() {
        classField.setText("");
        nameField.setText("");
        dimensionsField.setText("");
        cellIndexField.setText("");
        valueField.setText("");
        consoleLabel.setText("");
        classField.setEnabled(true);
        classLabel.setEnabled(true);
        nameField.setEnabled(false);
        nameLabel.setEnabled(false);
        dimensionsField.setEnabled(false);
        dimensionsLabel.setEnabled(false);
        cellIndexField.setEnabled(false);
        cellIndexLabel.setEnabled(false);
        valueField.setEnabled(false);
        valueLabel.setEnabled(false);
        resultLabel.setEnabled(false);
        resetButton.setEnabled(false);
        initButton.setEnabled(false);
        classField.grabFocus();
    }
}
