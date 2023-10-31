import javax.swing.*;

public class Window {
    protected JPanel window;
    protected String windowID;
    protected int height; 
    protected int width;

    public Window() {
        window = new JPanel();
    }
    public Window (String _windowID, int _height, int _width) {
        this();
        windowID = _windowID;
        height = _height;
        width = _width;
        window.setBounds(0, 0, height, width);
    }

    public JPanel getWindow() {
        return window;
    }

    public String getwindowID() {
        return windowID;
    }
}
