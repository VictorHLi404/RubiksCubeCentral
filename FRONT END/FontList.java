import java.awt.Font;

public class FontList {
    public static Font standardFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(25, 1440));
    public static Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(80, 1440));
    public static Font subtitleFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(40,1440));
    public static Font massiveTimerFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(300,1440));

    public static Font getTitleFont() {
        return titleFont;
    }
    public static Font getStandardFont() {
        return standardFont;
    }

    public static int resize(double initial, double frameSize) { // assume 1920
        double ratio = initial/1920;
        return (int) (ratio*frameSize);
    }
}
