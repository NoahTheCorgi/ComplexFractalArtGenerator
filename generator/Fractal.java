// NoahTheCorgi
// Fractal Object that represents the fractal to be displayed
// ... also containing methods to update the Display JPanel

package generator;

import java.lang.Math;
import java.util.Random;
import java.awt.Color;

public class Fractal {
    
    // x and y refer to the coordinate representation of the top left corner
    // note: top left corner is considered (0, 0)
    // ... by implementations, (0, 0) will be transposed to (x, y)
    // (note this would be a upside down cartesian plane, not a big deal for now)
    public double x = -1.50;
    public double y = -1.50;

    // zoom amount
    public double zoomOutAmount = 3;

    public int displayWidth;
    public int displayHeight;
    public int xyScaleMatchFactor;

    // how fast a point in question destabilizes...
    public static int destabilizingTime;

    // precision for complex field data points
    public int n = 100;

    public void setDisplaySize(int width, int height) {
        // e.g. 1000 * 1000
        displayWidth = width;
        displayHeight = height;
        xyScaleMatchFactor = Math.min(displayWidth, displayHeight);
    }

    public enum PlotMethod{DEFAULT, PROBABILISTIC_1, PROBABILISTIC_2}
    public PlotMethod fractalPlotMethod = PlotMethod.DEFAULT;

    public void plot(Display d) {
        if (fractalPlotMethod == PlotMethod.PROBABILISTIC_1) {
            plotProbabilisticType1(d);
        }
        else if (fractalPlotMethod == PlotMethod.PROBABILISTIC_2) {
            plotProbabilisticType2(d);
        }
        else {
            plotDefault(d);
        }
    }

    public void plotDefault(Display d) {
        for (int i=0; i<displayWidth; i++) {
            for (int j=0; j<displayHeight; j++) {
                double dx = zoomOutAmount * i / xyScaleMatchFactor;
                double dy = zoomOutAmount * j / xyScaleMatchFactor;
                //Complex complexInput = new Complex(x+dx, y+dy);
                destabilizingTime = destabilizationTestDefault(new Complex(x+dx, y+dy), new Complex(x+dx, y+dy), n);
                //System.out.println("destablizingTime:: " + destabilizingTime);
                // if within n precision/steps, the point destablized
                d.updateDisplay(i, j, new Color(100, 100, 100));
                if (destabilizingTime < n) {
                    // update the display with the plot
                    //System.out.println("wassss hereeee testing");
                    d.updateDisplay(i, j, new Color(0, 0, 0));
                }
            }
        }
    }

    // there is a huge bug here...
    // maybe cuz z is being passed in by value i think...
    // need to create a reference to it...
    public int destabilizationTestDefault(Complex z, Complex toAdd, int n) {
        //System.out.println(z.print());
        //System.out.println(toAdd.print());
        //System.out.println(minUnit);
        //System.out.println(zoomOut);
        int count = 0;
        // if (z.real*z.real + z.imaginary*z.imaginary > 4) {
        //     // if already destabilized, immediate report zero
        //     return n;
        // }
        while (count < n) {
            z.toThePowerOfInteger(2);
            z.add(toAdd);
            count += 1;
            //System.out.println(z.print());
            if ((z.real * z.real) + (z.imaginary * z.imaginary) > 4) {
                //System.out.println("testing:: was here!!!!!!!!!!!!!!!!!");
                // if already destabilized, exit loop and report count
                break;
            }
        }
        return count;
    }

    public void plotProbabilisticType1(Display d) {

    }

    public void plotProbabilisticType2(Display d) {

    }
}
