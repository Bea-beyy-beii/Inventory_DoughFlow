import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private int radius;

    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setOpaque(false);
        setFocusPainted(false);   // removes the focus outline
        setContentAreaFilled(false); // removes default button background
        setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20)); // inner padding
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // changes color when hovered/pressed
        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g2.setColor(getBackground().brighter());
        } else {
            g2.setColor(getBackground());
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground().darker()); // border matches background
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2.dispose();
    }
}