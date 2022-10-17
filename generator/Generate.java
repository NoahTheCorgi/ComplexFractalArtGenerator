package generator;
// NoahTheCorgi
// JFrame extension that will be compiled to executable

import java.awt.*;
import javax.swing.*;

import java.io.IOException;

public class Generate extends JFrame {

    public static Display display;

    // constructor creates instance of Generate, initializing the main window::
    public Generate() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 1000));
        setTitle("Complex Fractal Art Generator");
        // need to add a type of JPanel ...
        // Display class extends JPanel
        display = new Display();
        display.fractal = new Fractal();
        // make this a initial user input variable::
        display.fractal.toAddValue = new Complex(0.285, 0.01);
        add(display);
        display.requestFocusInWindow();
    }

    // start the animated display::
    // (Future Task:: terminal interaction capability to come...)
    public static void main(String[] args) throws IOException {
        Generate generate = new Generate();
        ColorsTheme.generateColorsTheme();
        // for (int i=0; i<ColorsTheme.COLOR_DETAIL_SIZE; i++) {
        //     System.out.println(ColorsTheme.THEME_COLORS[i]);
        // }
        if (args.length == 1) {
            System.out.println("Only 1 out of 2 necessary arguments were provided for the toAddValue parameter...");
        }
        if (args.length == 2) {
            System.out.println("Custom toAddValue parameter mode has been activated...");
            display.fractal.toAddValue = new Complex(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
        }
        while (true) {
            generate.repaint();
        }
    }
}
