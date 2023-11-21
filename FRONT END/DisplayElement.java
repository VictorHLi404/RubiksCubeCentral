import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
}

class TextDisplay extends DisplayElement {
    protected JTextArea textArea;
    protected String[] textSource;
    protected Font font;

    public TextDisplay(String type, String id, int xPosition, int yPosition, int height, int width, int depth, String[] textSource, Font font) {
        super(type, id, xPosition, yPosition, height, width, depth);
        this.textSource = textSource;
        this.font = font;
        this.textArea = new JTextArea(textSource[0]);
        this.textArea.setEditable(false);
        this.component = textArea;
        makeVisible();
    }

    public String[] getTextSource() {
        return textSource;
    }
    public Font getFont() {
        return font;
    }
}

class ImageContainer extends DisplayElement {

    protected JLabel image;

    public ImageContainer(String type, String id, int xPosition, int yPosition, int height, int width, int depth, String filePath) throws IOException {
        super(type, id, xPosition, yPosition, height, width, depth);
        System.out.println("Images/" + filePath);
        BufferedImage tempImage = ImageIO.read(getClass().getResource("/Images/" + filePath));
        Image resizedImage = tempImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        this.image = new JLabel(new ImageIcon(resizedImage));
        this.component = image;
        makeVisible();
    }

}

class InteractableObject extends DisplayElement implements ActionListener {

    public InteractableObject(String type, String id, int xPosition, int yPosition, int height, int width, int depth) {
        super(type, id, xPosition, yPosition, height, width, depth);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }

}