package src.Panels;

import javax.swing.*;

public class MyTextField extends JTextField {
    public MyTextField(int i) {super(i);}
    public void doAction() {super.fireActionPerformed();}
}
