import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JTextArea;

public class DisplayElement {
    protected JComponent component;

    protected String type;
    protected String id;

    protected int xPosition;
    protected int yPosition;
    protected int height;
    protected int width;
    protected int depth;

    public JComponent getComponent() {
        return component;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isVisible() {
        return isVisible;
    }

    protected boolean isVisible;

    public DisplayElement(String type, String id, int xPosition, int yPosition, int height, int width, int depth) {
        this.type = type;
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    public void display() {
            component.setBounds(xPosition, yPosition, width, height);
            component.validate();
            component.setVisible(isVisible);
    }

    public void makeVisible() {
        isVisible = true;
        display();
    }

    public void makeInvisible() {
        isVisible = false;
        display();
    }
}

class TextDisplay extends DisplayElement {
    protected String[] textSource;
    protected Font font;
    public TextDisplay(String type, String id, int xPosition, int yPosition, int height, int width, int depth, String[] textSource, Font font) {
        super(type, id, xPosition, yPosition, height, width, depth);
        this.textSource = textSource;
        this.font = font;
        this.component = new JTextArea(textSource[0]);
        makeVisible();
    }

}

