import javax.swing.*;
import java.awt.*;

public class RoundedTextField extends JTextField {

    private int radius;

    public RoundedTextField(int radius) {
        super();
        this.radius = radius;
        setOpaque(false); // makes background transparent so rounding shows
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // inner padding
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // smooth edges
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw background
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw border
        g2.setColor(Color.GRAY); // change border color here
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        g2.dispose();
    }
}