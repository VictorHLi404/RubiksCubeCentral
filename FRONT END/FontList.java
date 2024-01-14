import java.awt.Font;

public class FontList {

    public static final int frameSize = 1920;
    public static Font standardFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(25, frameSize));
    public static Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(80, frameSize));
    public static Font headerFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(60, frameSize));
    public static Font subtitleFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(40,frameSize));
    public static Font massiveTimerFont = new Font(Font.SANS_SERIF, Font.BOLD, resize(300,frameSize));

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
