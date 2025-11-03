package gui.fenetresimages;

import javax.swing.*;

public class FenetreImageInstrument extends JDialog {
    public FenetreImageInstrument(ImageIcon imageInstrument) {
        add(new JLabel(imageInstrument));
        setSize(imageInstrument.getIconWidth() + 50, imageInstrument.getIconHeight() + 50);
        setLocationRelativeTo(null);
    }
}
