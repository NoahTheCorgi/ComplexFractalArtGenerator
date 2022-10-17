package generator;
// adding functionality to JPanel as KeyListner
// ... as well as a few drawing functionalities probabily...

// probabily will be needing all of this (may not java.io.*)
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.Raster;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Display extends JPanel implements KeyListener {

    public Fractal fractal;
    protected byte[] displayByteData;
    protected boolean nKeyPressed = false;

    public Display() {
        addKeyListener(this);
    }

    // apparently must override this...
    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("key of code: " + e.getKeyCode() + " was typed...");
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        System.out.println("the key code, " + keyCode + ", was Pressed...");
        // the N key, which controls the depth precision in combination with up and down arrow keys
        if (keyCode == 78) {
            nKeyPressed = true;
        }
        // // key I
        // else if (keyCode == 73) {
        //     if (fractal.fractalPlotMethod == Fractal.PlotMethod.IMAGINARY_EXPONENT) {
        //         fractal.fractalPlotMethod = Fractal.PlotMethod.IMAGINARY_EXPONENT_PROBABILISTIC;
        //     }
        //     else {
        //         fractal.fractalPlotMethod = Fractal.PlotMethod.IMAGINARY_EXPONENT;
        //     }
        // }
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
            ColorsTheme.generateColorsTheme();
        }
        // key T, for the toAddOption toggle::
        else if (keyCode == 84) {
            if (fractal.toAddOption == false) {
                fractal.toAddOption = true;
            } 
            else {
                fractal.toAddOption = false;
            }
        }
        // other basic exploration functionalities::
        else {
            explorationKeyPressed(e);
        }
        repaint();
    }

    public void explorationKeyPressed(KeyEvent e) {
        double movementAmount = 20;
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
        // up arrow key:: zoom in or increase precision Fractal.n
        else if (e.getKeyCode() == 38) {
            if (nKeyPressed == true) {
                // the n key is mainly for adjusting Fractal.n, which is the depth precision for destabilization tests
                // since up arrow key is pressed...
                fractal.n += 5;
            }
            else {
                fractal.zoomOutAmount *= 0.99;
                // fractal.x += (newcenterX - oldcenterX) * fractal.zoomOutAmount / getWidth();
                // fractal.y += (newcenterY - oldcenterY) * fractal.zoomOutAmount / getHeight();
                // fractal.x += ((newcenterX - oldcenterX) * fractal.zoomOutAmount * fractal.zoomOutAmount) / getWidth();
                // fractal.y += ((newcenterY - oldcenterY) * fractal.zoomOutAmount * fractal.zoomOutAmount) / getHeight();
                fractal.x += ((fractal.zoomOutAmount * getWidth() * 0.01) / 2) / getWidth();
                fractal.y += ((fractal.zoomOutAmount * getHeight() * 0.01) / 2) / getHeight();
            }
        }
        // down arrow key:: zoom out or decrease precision Fractal.n
        else if (e.getKeyCode() == 40) {
            if (nKeyPressed == true) {
                // the n key is mainly for adjusting Fractal.n, which is the depth precision for destabilization tests
                // since down arrow key is pressed...
                if (fractal.n - 5 > 0) {
                    fractal.n -= 5;
                }
            }
            else {
                int oldcenterX = (int) ((getWidth() / fractal.zoomOutAmount)) / 2;
                int oldcenterY = (int) ((getHeight() / fractal.zoomOutAmount)) / 2;
                fractal.zoomOutAmount *= 1.01;
                int newcenterX = (int) ((getWidth() / fractal.zoomOutAmount)) / 2;
                int newcenterY = (int) ((getHeight() / fractal.zoomOutAmount)) / 2;
                // fractal.x += (newcenterX - oldcenterX) * fractal.zoomOutAmount / getWidth();
                // fractal.y += (newcenterY - oldcenterY) * fractal.zoomOutAmount / getHeight();
                // fractal.x += ((newcenterX - oldcenterX) * fractal.zoomOutAmount * fractal.zoomOutAmount) / getWidth();
                // fractal.y += ((newcenterY - oldcenterY) * fractal.zoomOutAmount * fractal.zoomOutAmount) / getHeight();
                fractal.x -= ((fractal.zoomOutAmount * getWidth() * 0.01) / 2) / getWidth();
                fractal.y -= ((fractal.zoomOutAmount * getHeight() * 0.01) / 2) / getHeight();
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // the N key, which controls the depth precision in combination with up and down arrow keys
        if (e.getKeyCode() == 78) {
            nKeyPressed = false;
        }
        repaint();
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
