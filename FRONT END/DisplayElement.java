import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;

public class DisplayElement {
    protected JComponent component;

    protected String type;
    protected String id;

    protected int xPosition;
    protected int yPosition;
    protected int height;
    protected int width;
    protected int depth;

    protected Color background = Color.WHITE;
    public static Color standardBackgroundColor = Color.getHSBColor((float) 0.608, (float) 0.39, (float) 0.99);

    public boolean isVisible() {
        return isVisible;
    }

    protected boolean isVisible;

    public DisplayElement(String id, int xPosition, int yPosition, int height, int width, int depth, Color background) {
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.background = background;
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

    public void setBackground(Color background) {
        this.background = background;
    }  

    public static Color StringToColor(String color) { //TODO make in less monkey way
        if (color == null) {
            return Color.GRAY;
        }
        else if (color.equals("ORANGE")) {
            return Color.ORANGE;
        }
        else if (color.equals("RED")) {
            return Color.RED;
        }
        else if (color.equals("YELLOW")) {
            return Color.YELLOW;
        }
        else if (color.equals("WHITE")) {
            return Color.WHITE;
        }
        else if (color.equals("GREEN")) {
            return Color.GREEN;
        }
        else if (color.equals("BLUE")) {
            return Color.BLUE;
        }
        System.out.println("COLOR INPUT NOT VALID");
        return null;
    }
}

class TextDisplay extends DisplayElement {
    protected JTextArea textArea;
    protected String[] textSource;
    protected Font font;

    public TextDisplay(String id, int xPosition, int yPosition, int height, int width, int depth, Color background, String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background);
        this.type = "TextDisplay";
        this.textSource = textSource;
        this.font = font;
        this.textArea = new JTextArea(textSource[0]);
        this.textArea.setEditable(false);
        this.textArea.setFont(font);
        this.textArea.setBackground(background);
        this.component = textArea;
        makeVisible();
    }

    public String[] getTextSource() {
        return textSource;
    }
    public Font getFont() {
        return font;
    }

    public void updateTextDisplay(String newText) {
        this.textSource[0] = newText;
        this.textArea.setText(newText);
        makeVisible();
    }

    @Override
    public String toString() {
        return "TextDisplay [textArea=" + textArea + ", textSource=" + Arrays.toString(textSource) + ", font=" + font
                + "]";
    }
}

class ImageContainer extends DisplayElement {

    protected JLabel image;

    public ImageContainer(String id, int xPosition, int yPosition, int height, int width, int depth, Color background, String filePath) throws IOException {
        super(id, xPosition, yPosition, height, width, depth, background);
        this.type = "ImageContainer";
        System.out.println("Images/" + filePath);
        BufferedImage tempImage = ImageIO.read(getClass().getResource("/Images/" + filePath));
        Image resizedImage = tempImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        this.image = new JLabel(new ImageIcon(resizedImage));
        this.component = image;
        makeVisible();
    }

}

class InteractableObject extends DisplayElement {

    protected ActionListener actionListener;

    public InteractableObject(String id, int xPosition, int yPosition, int height, int width, int depth, Color background, ActionListener actionListener) {
        super(id, xPosition, yPosition, height, width, depth, background);
        this.type = "InteractableObject";
        this.actionListener = actionListener;
    }

}

class InteractableTextField extends InteractableObject {

    protected String[] textSource;
    protected Font font;

    public InteractableTextField(String id, int xPosition, int yPosition, int height, int width, int depth, Color background, ActionListener actionListener,
            String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener);
        this.type = "InteractableTextField";
        this.textSource = textSource;
        this.font = font;
    }

}

class Button extends InteractableTextField {

    protected JButton button;

    public Button(String id, int xPosition, int yPosition, int height, int width, int depth, Color background, ActionListener actionListener,
            String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);

        this.type = "Button";
        this.button = new JButton(textSource[0]);
        button.setBackground(background);
        button.setFont(font);
        button.addActionListener(actionListener);
        this.component = button;
        makeVisible();
    }

    public JButton getButton() {
        return button;
    }
}

class WindowChangeButton extends Button {
    protected String targetWindow;

    public WindowChangeButton(String id, int xPosition, int yPosition, int height, int width, int depth, Color background, ActionListener actionListener,
            String[] textSource, Font font, String targetWindow) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "WindowChangeButton";
        this.targetWindow = targetWindow;
        button.setActionCommand("CHANGE WINDOW " + targetWindow);
        makeVisible();
    }

}

class ScrambleViewButton extends WindowChangeButton {
    protected String scramble;

    public ScrambleViewButton(String id, int xPosition, int yPosition, int height, int width, int depth,
            Color background, ActionListener actionListener, String[] textSource, Font font, String targetWindow, String scramble) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font, targetWindow);
        this.type = "ScrambleViewButton";
        this.scramble = scramble;
        button.setActionCommand("LOAD SCRAMBLE VIEW WITH SCRAMBLE " + scramble);
        makeVisible();
    }

    public String getScramble() {
        return scramble;
    }

    public void setScramble(String scramble) {
        this.scramble = scramble;
    }
}

class QuitButton extends Button {

    public QuitButton(String id, int xPosition, int yPosition, int height, int width, int depth, Color background,
            ActionListener actionListener, String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "QuitButton";
        button.setActionCommand("QUIT");
        makeVisible();
    }

}

