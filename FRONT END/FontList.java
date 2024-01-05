import java.awt.Font;

public class FontList {
    public static Font standardFont = new Font(Font.SANS_SERIF, Font.BOLD, 25);
    public static Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 80);
    public static Font subtitleFont = new Font(Font.SANS_SERIF, Font.BOLD, 40);
    public static Font massiveTimerFont = new Font(Font.SANS_SERIF, Font.BOLD, 300);
    public static Font getTitleFont() {
        return titleFont;
    }
    public static Font getStandardFont() {
        return standardFont;
    }
}
