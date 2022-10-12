package generator;
// NoahTheCorgi
// JFrame extension that will be compiled to executable

import java.awt.*;
import javax.swing.*;

import java.io.IOException;

public class Generate extends JFrame {

    // constructor creates instance of Generate, initializing the main window::
    public Generate() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 1000));
        setTitle("Complex Fractal Art Generator");
        // need to add a type of JPanel ...
        // Display class extends JPanel
        Display display = new Display();
        display.fractal = new Fractal();
        add(display);
        display.requestFocusInWindow();
    }

    // start the animated display::
    // (Future Task:: terminal interaction capability to come...)
    public static void main(String[] args) throws IOException {
        Generate generate = new Generate();
        ColorsTheme.generateColorsTheme();
        for (int i=0; i<ColorsTheme.COLOR_DETAIL_SIZE; i++) {
            System.out.println(ColorsTheme.THEME_COLORS[i]);
        }
        while (true) {
            generate.repaint();
        }
    }
}
