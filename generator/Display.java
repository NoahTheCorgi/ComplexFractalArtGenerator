package generator;
// adding functionality to JPanel as KeyListner
// ... as well as a few drawing functionalities probabily...

// probabily will be needing all of this (may not java.io.*)
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.Raster;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Display extends JPanel {// implements KeyListener {

    public Fractal fractal;
    public byte[] displayByteData;

    public Display() {
        //addKeyListener(this);
    }

    // this updates the displayByteData array to be used by paintComponent
    public void updateDisplay(int x, int y, Color color) {
        // if within the windows bounds...
        if ((x+y)>=0 && x<getWidth() && y<getHeight()) {
            // for byte array construction::
            // (later) must instantiate displayByteData as size 3*w*3*h accordingl
            int dataLocation = (3*y)*getWidth() + 3*x;
            // displayByteData stores 3 data points for each point for TYPE_3BYTE_BGR
            // using bytes will save a lot of memory...
            displayByteData[dataLocation] = (byte) color.getBlue();
            displayByteData[dataLocation+1] = (byte) color.getGreen();
            displayByteData[dataLocation+2] = (byte) color.getRed();
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
        fractal.plot(this); // note: updateDisplay is called here by fractal
        // now that the array is updated...
        // sub class of abstract image class, could not use image class directly...
        BufferedImage outputImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        // for creating raster (rectangular pixels)
        DataBufferByte dbb = new DataBufferByte(displayByteData, displayByteData.length);
        Raster raster = Raster.createRaster(outputImage.getSampleModel(), dbb, new Point());
        outputImage.setData(raster);
        graphics.drawImage(outputImage, 0, 0);
    }
}
