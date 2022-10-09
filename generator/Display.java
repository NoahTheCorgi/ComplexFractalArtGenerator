package generator;
// adding functionality to JPanel as KeyListner
// ... as well as a few drawing functionalities probabily...

// probabily will be needing all of this (may not java.io.*)
import java.io.*;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.Raster;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Display extends JPanel implements KeyListener {

    public Fractal fractal;
    protected byte[] displayByteData;
    protected boolean shiftKeyPressed = false;

    public Display() {
        addKeyListener(this);
    }

    // apparently must override this...
    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("key of code: " + e.getKeyCode() + " was typed...");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        System.out.println("the key code, " + keyCode + ", was Pressed...");
        // shift
        if (keyCode == 16) {
            shiftKeyPressed = true;
        }
        // key O ("Oh") // This is Default
        else if (keyCode == 79) {
            fractal.fractalPlotMethod = Fractal.PlotMethod.DEFAULT;
        }
        // key P
        else if (keyCode == 80) {
            if (fractal.fractalPlotMethod == Fractal.PlotMethod.PROBABILISTIC_1) {
                fractal.fractalPlotMethod = Fractal.PlotMethod.PROBABILISTIC_2;
            }
            else {
                fractal.fractalPlotMethod = Fractal.PlotMethod.PROBABILISTIC_1;
            }
        }
        // key R
        else if (keyCode == 82) {
            // future task::
            // Randomization of the colors used
            // placeholder
        }
        // other basic exploration functionalities::
        else {
            explorationKeyPressed(e);
        }
    }

    public void explorationKeyPressed(KeyEvent e) {
        if (shiftKeyPressed == true) {

        }
        // if shift key is not pressed...
        else {
            double movementAmount = 10;
            // note: (1/getWidth) is the minimum unit horizontal (x)
            double dx = (fractal.zoomOutAmount) * movementAmount / getWidth();
            // note: (1/getHeight) is the minimum unit vertical (y)
            double dy = (fractal.zoomOutAmount) * movementAmount / getHeight();
            // A:: left movement
            if (e.getKeyCode() == 68) {
                // points move right so camera moves left
                fractal.x += dx;
            }
            // D:: right movement
            else if (e.getKeyCode() == 65) {
                // points move left so camera moves right
                fractal.x -= dx;
            }
            // S:: down movement
            else if (e.getKeyCode() == 87) {
                // since increase is downward decrement is lowering camera
                fractal.y -= dy;
            }
            // W:: up movement
            else if (e.getKeyCode() == 83) {
                // since increase is downward increment is moving camera upwards
                fractal.y += dy;
            }
            // up arrow key:: zoom in
            else if (e.getKeyCode() == 38) {
                fractal.zoomOutAmount *= 0.99;
            }
            // down arrow key:: zoom out
            else if (e.getKeyCode() == 40) {
                fractal.zoomOutAmount *= 1.01;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 16) {
            shiftKeyPressed = false;
        }
    }

    // this updates the displayByteData array to be used by paintComponent
    // color is just black and white for now...
    public void updateDisplay(int x, int y, Color color) {
        // if within the windows bounds...
        if ((x+y)>=0 && x<getWidth() && y<getHeight()) {
            // System.out.println("x:: " + x);
            // System.out.println("y:: " + y);
            // for byte array construction::
            // (later) must instantiate displayByteData as size 3*w*3*h accordingl
            int dataLocation = ((3*y)*getWidth()) + (3*x);
            // displayByteData stores 3 data points for each point for TYPE_3BYTE_BGR
            // using bytes will save a lot of memory...
            displayByteData[dataLocation] = (byte) color.getBlue();
            //System.out.println(displayByteData[dataLocation]);
            displayByteData[dataLocation+1] = (byte) color.getGreen();
            //System.out.println(displayByteData[dataLocation+1]);
            displayByteData[dataLocation+2] = (byte) color.getRed();
            //System.out.println(displayByteData[dataLocation+2]);
            // this should work...
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        // 3 for each cuz bgr
        displayByteData = new byte[3*getWidth()*3*getHeight()];
        // the fractal will update the output accordingly first
        fractal.setDisplaySize(getWidth(), getHeight());
        fractal.plot(this); // *** note: updateDisplay is called here by fractal
        // now that the array is updated...
        // sub class of abstract image class, could not use image class directly...
        //System.out.println("washere 1");
        BufferedImage outputImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        // for creating raster (rectangular pixels)
        //System.out.println("washere 2");
        DataBufferByte dbb = new DataBufferByte(displayByteData, displayByteData.length);
        Point point = new Point();
        //System.out.println("washere 3");
        Raster raster = Raster.createRaster(outputImage.getSampleModel(), dbb, point);
        //System.out.println("washere 4");
        outputImage.setData(raster);
        //System.out.println("washere 5");
        // observer not needed for there is no expected asynchronous update
        graphics.drawImage(outputImage, 0, 0, null);
    }
}
