package generator;

import java.awt.*;

public class ColorsTheme {
    
    public static int COLOR_DETAIL_SIZE = 11;

    public static Color[] THEME_COLORS = new Color[COLOR_DETAIL_SIZE];

    // how the colors will be distributed compared to the speed of destabilization::
    public static float[] COLOR_DISTRIBUTION = new float[COLOR_DETAIL_SIZE];

    // where meaningfully randomized colors are generated
    // *** note: must call before starting to display the art::
    public static void generateColorsTheme() {
        for (int i=0; i<COLOR_DETAIL_SIZE; i++) {
            THEME_COLORS[i] = new Color((int) (255*Math.random()), (int) (255*Math.random()), (int) (255*Math.random()));
            //COLOR_DISTRIBUTION[i] = (1/(COLOR_DETAIL_SIZE-1))*i;
            COLOR_DISTRIBUTION[i] = 0.1f*i;
        }
    }

    // create gradient theme using the randomly generated colors...
    public static Color setColorsTheme(int n, int stabilizingTime) {
        float destabilizeLevel = (float) (n-stabilizingTime)/n;
        for (int i=0; i<COLOR_DETAIL_SIZE; i++) {
            // if within certain fast distribution category::
            if (destabilizeLevel < COLOR_DISTRIBUTION[i]) {
                double consecutiveStabilityRatio = (COLOR_DISTRIBUTION[i]-destabilizeLevel)/(COLOR_DISTRIBUTION[i]-COLOR_DISTRIBUTION[i-1]);
                // meaningfully generate a great color for the display, while respecting change in stability::
                return new Color(
                        (int) ((((1-consecutiveStabilityRatio)/2)*THEME_COLORS[(i+1)%COLOR_DETAIL_SIZE].getRed())
                                + (consecutiveStabilityRatio*THEME_COLORS[i].getRed())
                                        + (((1-consecutiveStabilityRatio)/2)*THEME_COLORS[i-1].getRed())),
                        (int) ((((1-consecutiveStabilityRatio)/2)*THEME_COLORS[(i+1)%COLOR_DETAIL_SIZE].getGreen())
                                + (consecutiveStabilityRatio*THEME_COLORS[i].getGreen())
                                        + (((1-consecutiveStabilityRatio)/2)*THEME_COLORS[i-1].getGreen())),
                        (int) ((((1-consecutiveStabilityRatio)/2)*THEME_COLORS[(i+1)%COLOR_DETAIL_SIZE].getBlue())
                                + (consecutiveStabilityRatio*THEME_COLORS[i].getBlue())
                                        + (((1-consecutiveStabilityRatio)/2)*THEME_COLORS[i-1].getBlue()))
                        );
            }
        }
        // if it was never fast enough to destabilize...
        return new Color(0, 0, 0);
    }
}
