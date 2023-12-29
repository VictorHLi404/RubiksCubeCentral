import java.awt.Font;

public class FontList {
    public static Font standardFont = new Font(Font.SERIF, Font.BOLD, 10);
    public static Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 60);
    public static Font subtitleFont = new Font(Font.SANS_SERIF, Font.BOLD, 40);
    public static Font getTitleFont() {
        return titleFont;
    }
    public static Font getStandardFont() {
        return standardFont;
    }
}
