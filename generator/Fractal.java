// NoahTheCorgi
// Fractal Object that will be interpreted and displayed by Display.java

package generator;

import java.lang.Math;
import java.util.Random;

public class Fractal {
    
    // x and y refer to the coordinate representation of the top left corner
    // note: top left corner is considered (0, 0)
    // ... by implementations, (0, 0) will be transposed to (x, y)
    public double x = -2;
    public double y = -2;

    // zoom in amount
    public double zoom = 3;

    public int displayWidth;
    public int displayHeight;
    public double minUnit;

    public static int destabilizingSpeed;

    // precision for complex field data points
    public int n = 100;

    public void setDisplaySize(int width, int height) {
        // e.g. 1000 * 1000
        displayWidth = width;
        displayHeight = height;
        // for squarization of the window if rectangle::
        minUnit = 1/Math.min(width, height);
    }
}
