import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.*;

public class RubiksCubeNet {
    
}

class ColorSwatch extends Button {
    protected String color;
    public ColorSwatch(String id, int xPosition, int yPosition, int height, int width, int depth, Color background,
            ActionListener actionListener, String[] textSource, Font font, String color) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.color = color;
        this.type = "ColorSwatch";
        this.background = DisplayElement.StringToColor(color);
        button.setActionCommand("SWITCH CURRENT COLOR TO " + color);
        button.setBackground(this.background);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        makeVisible();
    }
}

class BlockFace extends Button {
    protected String color;

    public BlockFace(String id, int xPosition, int yPosition, int height, int width, int depth, Color background,
            ActionListener actionListener, String[] textSource, Font font) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "BlockFace";
        this.color = null;
        this.background = DisplayElement.StringToColor(color);
        button.setBackground(this.background);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        button.setActionCommand("CHANGE CURRENT COLOR OF BLOCKFACE");
        makeVisible();
    }

    public void updateColor(String color) {
        this.color = color;
        this.background = DisplayElement.StringToColor(color);
        button.setBackground(this.background);
    }
}

class NonEditableBlockFace extends BlockFace {

    public NonEditableBlockFace(String id, int xPosition, int yPosition, int height, int width, int depth, Color background,
            ActionListener actionListener, String[] textSource, Font font, String color) {
        super(id, xPosition, yPosition, height, width, depth, background, actionListener, textSource, font);
        this.type = "NonEditableBlockFace";
        this.color = color;
        this.background = DisplayElement.StringToColor(color);
        button.setBackground(this.background);
        button.setActionCommand("DO NOT CHANGE CURRENT COLOR OF BLOCKFACE");
    }
}
