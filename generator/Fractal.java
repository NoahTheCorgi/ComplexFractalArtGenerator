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

    // zoom in amount
    public double zoom = 1/3;

    public int displayWidth;
    public int displayHeight;
    public double minUnit;

    // how fast a point in question destabilizes...
    public static int destabilizingTime;

    // precision for complex field data points
    public int n = 100;

    public void setDisplaySize(int width, int height) {
        // e.g. 1000 * 1000
        displayWidth = width;
        displayHeight = height;
        // for squarization of the window if rectangle::
        minUnit = 1/Math.min(width, height);
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
                double dx = (i*minUnit)/zoom;
                double dy = (j*minUnit)/zoom;
                destabilizingTime = destabilizationTestDefault(new Complex(x+dx, y+dy), new Complex(x+dx, y+dy), n);
                // if within n precision/steps, the point destablized
                if (destabilizingTime < n) {
                    // update the display with the plot
                    System.out.println("destablizingTime:: " + destabilizingTime);
                    d.updateDisplay(i, j, new Color(0, 0, 200));
                }
            }
        }
    }

    // there is a huge bug here...
    // maybe cuz z is being passed in by value i think...
    // need to create a reference to it...
    public int destabilizationTestDefault(Complex z, Complex c, int n) {
        int count = 0;
        if (z.real*z.real + z.imaginary*z.imaginary > 4) {
            // if already destabilized, immediate report zero
            return n;
        }
        while (count < n) {
            z.toThePowerOfInteger(2);
            z.add(c);
            count += 1;
            if (z.real*z.real + z.imaginary*z.imaginary > 4) {
                System.out.println("testing:: was here!!!!!!!!!!!!!!!!!");
                // if already destabilized, immediate report zero
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
