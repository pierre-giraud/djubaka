package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class JButtonCreator {
    public static JButton createImageButton(String filename, String replacementText, boolean enabled){
        JButton button = new JButton();

        try {
            Image img = ImageIO.read(new File(filename));
            button.setIcon(new ImageIcon(img));
            button.setFocusable(false);
            button.setEnabled(enabled);
        } catch (Exception e) {
            System.out.println(filename + " : this file does not exist or is not an image file");
            button.setText(replacementText);
        }

        return button;
    }
}
