// NoahTheCorgi
// JFrame extension that will be compiled to executable

import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.io.*; // for user interaction through terminal

public class Generate extends JFrame {

    // constructor creates instance of Generate, initializing the main window::
    public Generate() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 1000));
        setTitle("Complex Fractal Art Generator");
        // need to add a type of JPanel ...
        // ...
    }

    // start the animated display::
    public static void main(String[] args) throws IOException {
        Generate generate = new Generate();
    }
}