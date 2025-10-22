package gui.fenetresImages;

import javax.swing.*;
import java.awt.*;

public class FenetreImageInstrument extends JFrame {
    public FenetreImageInstrument(ImageIcon imageInstrument) {
        add(new JLabel(imageInstrument));
        setSize(imageInstrument.getIconWidth() + 50, imageInstrument.getIconHeight() + 50);
    }
}
