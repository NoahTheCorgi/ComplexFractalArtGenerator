// NoahTheCorgi
// Fractal Object that represents the fractal to be displayed
// ... also containing methods to update the Display JPanel

// future task 1:: experiment and explore the possibility of complex/imaginary exponents
// (the complex exponent function was individually tested, and there was no issue, 
// ... but the time complexity does not allow an efficient computation at the moment...)
// future task 2:: terminal based user input update capability

package generator;

import java.lang.Math;

public class Fractal {
    
    // x and y refer to the coordinate representation of the top left corner
    // note: top left corner is considered (0, 0)
    // ... by implementations, (0, 0) will be transposed to (x, y)
    // (note this would be a upside down cartesian plane, not a big deal for now)
    public double x = -2.05;
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
    public int SAMPLE_SIZE = 1000000;
    
    ////////////////////////////////////////////////////////////////////////////////
    // *** task: adjustable parameters with adjustable precision, using keys *** ///
    ////////////////////////////////////////////////////////////////////////////////
    // adjust for exploration and experimentation::
    public int exponent = 2;
    public boolean toAddOption = false;
    // parameters that define the Julia Set /- similar type art,,,
    public Complex toAddValue; // = new Complex(0.285, 0.01);
    // public Complex imaginaryExponent = new Complex(0, 1);
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////
    
    public void setDisplaySize(int width, int height) {
        // e.g. 1000 * 1000
        displayWidth = width;
        displayHeight = height;
        xyScaleMatchFactor = Math.min(displayWidth, displayHeight);
    }

    public enum PlotMethod{DEFAULT, PROBABILISTIC_1, PROBABILISTIC_2, IMAGINARY_EXPONENT, IMAGINARY_EXPONENT_PROBABILISTIC}
    public PlotMethod fractalPlotMethod = PlotMethod.DEFAULT;

    public void plot(Display d) {
        if (fractalPlotMethod == PlotMethod.PROBABILISTIC_1) {
            plotProbabilisticType1(d);
        }
        else if (fractalPlotMethod == PlotMethod.PROBABILISTIC_2) {
            plotProbabilisticType2(d);
        }
        // else if (fractalPlotMethod == PlotMethod.IMAGINARY_EXPONENT) {
        //     plotImaginaryExponent(d);
        // }
        // else if (fractalPlotMethod == PlotMethod.IMAGINARY_EXPONENT_PROBABILISTIC) {
        //     plotImaginaryExponentProbabilistic(d);
        // }
        else {
            plotDefault(d);
        }
    }

    public void plotDefault(Display d) {
        for (int i=0; i<displayWidth; i++) {
            for (int j=0; j<displayHeight; j++) {
                // 1/xyScaleMatchFactor is the minimum unit within the grid
                // this allows an accurate scaling between the x-axis and y-axis
                // note: due to lack of precision (1/xyScaleMatchFactor) is not viable
                double dx = zoomOutAmount * i / xyScaleMatchFactor;
                double dy = zoomOutAmount * j / xyScaleMatchFactor;
                //Complex complexInput = new Complex(x+dx, y+dy);
                if (toAddOption == false) {
                    destabilizingTime = destabilizationTestDefault(new Complex(x+dx, y+dy), new Complex(x+dx, y+dy), n);
                }
                else {
                    destabilizingTime = destabilizationTestDefault(new Complex(x+dx, y+dy), toAddValue, n);
                }
                //System.out.println("destablizingTime:: " + destabilizingTime);
                // if within n precision/steps, the point destablized
                //d.updateDisplay(i, j, new Color(100, 100, 100));
                if (destabilizingTime < 100) {
                    // update the display with the plot
                    
                    //d.updateDisplay(i, j, new Color(100, 100, 100));
                    //System.out.println("destablizingTime:: " + destabilizingTime);
                    d.updateDisplay(i, j, ColorsTheme.setColorsTheme(n, destabilizingTime));
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
            z.toThePowerOfInteger(exponent);
            z.add(toAdd);
            count += 1;
            //System.out.println(z.print());
            if ((z.real * z.real) + (z.imaginary * z.imaginary) > 4) {
                // if already destabilized, exit loop and report count
                break;
            }
        }
        return count;
    }

    public void plotProbabilisticType1(Display d) {
        for (int i=0; i<displayWidth; i++) {
            for (int j=0; j<displayHeight; j++) {
                double dx = zoomOutAmount * i / xyScaleMatchFactor;
                double dy = zoomOutAmount * j / xyScaleMatchFactor;
                if (toAddOption == false) {
                    destabilizingTime = destabilizationTestPr1(new Complex(x+dx, y+dy), new Complex(x+dx, y+dy), n);
                }
                else {
                    destabilizingTime = destabilizationTestPr1(new Complex(x+dx, y+dy), toAddValue, n);
                }
                if (destabilizingTime < n) {
                    d.updateDisplay(i, j, ColorsTheme.setColorsTheme(n, destabilizingTime));
                }
            }
        }
        //}
    }
    public int destabilizationTestPr1(Complex z, Complex toAdd, int n) {
        int count = 0;
        while (count < n) {
            int prExp = (int) Math.round(exponent + Math.random());
            //Complex prAdd = new Complex(Math.random(), Math.random());
            z.toThePowerOfInteger(prExp);
            //toAdd.add(prAdd);
            z.add(toAdd);
            count += 1;
            if ((z.real * z.real) + (z.imaginary * z.imaginary) > 4) {
                break;
            }
        }
        return count;
    }

    public void plotProbabilisticType2(Display d) {
        for (int i=0; i<displayWidth; i++) {
            for (int j=0; j<displayHeight; j++) {
                double dx = zoomOutAmount * i / xyScaleMatchFactor;
                double dy = zoomOutAmount * j / xyScaleMatchFactor;
                if (toAddOption == false) {
                    destabilizingTime = destabilizationTestPr2(new Complex(x+dx, y+dy), new Complex(x+dx, y+dy), n);
                }
                else {
                    destabilizingTime = destabilizationTestPr2(new Complex(x+dx, y+dy), toAddValue, n);
                }
                if (destabilizingTime < n) {
                    d.updateDisplay(i, j, ColorsTheme.setColorsTheme(n, destabilizingTime));
                }
            }
        }
    }
    public int destabilizationTestPr2(Complex z, Complex toAdd, int n) {
        int count = 0;
        while (count < n) {
            int prExp = (int) Math.round(exponent - Math.random());
            //Complex prAdd = new Complex(Math.random(), Math.random());
            z.toThePowerOfInteger(prExp);
            //toAdd.add(prAdd);
            z.add(toAdd);
            count += 1;
            if ((z.real * z.real) + (z.imaginary * z.imaginary) > 4) {
                break;
            }
        }
        return count;
    }

    // // not viable to use yet...
    // public void plotImaginaryExponent(Display d) {
    //     for (int i=0; i<displayWidth; i++) {
    //         for (int j=0; j<displayHeight; j++) {
    //             double dx = zoomOutAmount * i / xyScaleMatchFactor;
    //             double dy = zoomOutAmount * j / xyScaleMatchFactor;
    //             destabilizingTime = destabilizationTestImaginaryExponent(new Complex(x+dx, y+dy), new Complex(x+dx, y+dy), n);
    //             if (destabilizingTime < n) {
    //                 d.updateDisplay(i, j, ColorsTheme.setColorsTheme(n, destabilizingTime));
    //             }
    //         }
    //     }
    // }
    // public int destabilizationTestImaginaryExponent(Complex z, Complex toAdd, int n) {
    //     int count = 0;
    //     while (count < n) {
    //         // this is where the bug is ...
    //         z.toThePowerOf(imaginaryExponent);
    //         //z.toThePowerOfInteger(exponent + 1);
    //         z.add(toAdd);
    //         count += 1;
    //         if ((z.real * z.real) + (z.imaginary * z.imaginary) > 4) {
    //             break;
    //         }
    //     }
    //     return count;
    // }

    // // not viable to use yet...
    // public void plotImaginaryExponentProbabilistic(Display d) {
    //     for (int i=0; i<displayWidth; i++) {
    //         for (int j=0; j<displayHeight; j++) {
    //             double dx = zoomOutAmount * i / xyScaleMatchFactor;
    //             double dy = zoomOutAmount * j / xyScaleMatchFactor;
    //             destabilizingTime = destabilizationTestImaginaryExponentProbabilistic(new Complex(x+dx, y+dy), new Complex(x+dx, y+dy), n);
    //             if (destabilizingTime < n) {
    //                 d.updateDisplay(i, j, ColorsTheme.setColorsTheme(n, destabilizingTime));
    //             }
    //         }
    //     }
    // }
    // public int destabilizationTestImaginaryExponentProbabilistic(Complex z, Complex toAdd, int n) {
    //     int count = 0;
    //     while (count < n) {
    //         // this is where the bug is...
    //         // z.toThePowerOf(imaginaryExponent);
    //         z.toThePowerOfInteger(exponent + 1);
    //         z.add(toAdd);
    //         count += 1;
    //         if ((z.real * z.real) + (z.imaginary * z.imaginary) > 4) {
    //             break;
    //         }
    //     }
    //     return count;
    // }
}
