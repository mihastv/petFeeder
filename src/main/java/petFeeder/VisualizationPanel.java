package petFeeder;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Panel that visualizes the pet feeder simulation with animated graphics
 */
public class VisualizationPanel extends JPanel {
    private final SharedState state;
    private long animationFrame = 0;

    // Colors
    private static final Color BG_COLOR = new Color(245, 245, 250);
    private static final Color CAT_HAPPY_COLOR = new Color(255, 180, 100);
    private static final Color CAT_HUNGRY_COLOR = new Color(200, 150, 100);
    private static final Color BOWL_COLOR = new Color(100, 150, 200);
    private static final Color FOOD_COLOR = new Color(139, 90, 60);
    private static final Color FEEDER_COLOR = new Color(70, 70, 90);
    private static final Color FEEDER_JAM_COLOR = new Color(200, 50, 50);
    private static final Color REPAIRMAN_COLOR = new Color(50, 150, 50);

    public VisualizationPanel(SharedState state) {
        this.state = state;
        setBackground(BG_COLOR);
        setPreferredSize(new Dimension(600, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable antialiasing for smooth graphics
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        animationFrame++;

        int width = getWidth();
        int height = getHeight();

        // Draw title
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.setColor(new Color(50, 50, 70));
        drawCenteredString(g2d, "Smart Pet Feeder Simulation", width / 2, 40);

        // Draw components
        drawFeeder(g2d, width / 2, 120);
        drawBowl(g2d, width / 2, 320);
        drawCat(g2d, width / 2 - 150, 400);
        drawRepairman(g2d, width / 2 + 200, 350);

        // Draw status indicators
        drawStatusBox(g2d, 50, height - 100);
    }

    private void drawCat(Graphics2D g2d, int x, int y) {
        boolean isHappy = state.isCatHappy();
        Color catColor = isHappy ? CAT_HAPPY_COLOR : CAT_HUNGRY_COLOR;

        // Bobbing animation when happy
        float bob = isHappy ? (float) Math.sin(animationFrame * 0.1) * 3 : 0;
        y += bob;

        // Cat body
        g2d.setColor(catColor);
        Ellipse2D body = new Ellipse2D.Double(x - 40, y - 30, 80, 60);
        g2d.fill(body);

        // Cat head
        Ellipse2D head = new Ellipse2D.Double(x - 35, y - 60, 70, 50);
        g2d.fill(head);

        // Ears
        int[] earX1 = {x - 30, x - 25, x - 15};
        int[] earY1 = {y - 60, y - 80, y - 60};
        g2d.fillPolygon(earX1, earY1, 3);

        int[] earX2 = {x + 15, x + 25, x + 30};
        int[] earY2 = {y - 60, y - 80, y - 60};
        g2d.fillPolygon(earX2, earY2, 3);

        // Eyes
        g2d.setColor(Color.BLACK);
        if (isHappy) {
            // Happy eyes (arcs)
            g2d.setStroke(new BasicStroke(3));
            g2d.drawArc(x - 20, y - 45, 10, 10, 0, -180);
            g2d.drawArc(x + 10, y - 45, 10, 10, 0, -180);
        } else {
            // Wide eyes (circles)
            g2d.fillOval(x - 20, y - 45, 8, 8);
            g2d.fillOval(x + 12, y - 45, 8, 8);
        }

        // Nose
        g2d.fillOval(x - 3, y - 35, 6, 6);

        // Whiskers
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(x - 35, y - 40, x - 50, y - 45);
        g2d.drawLine(x - 35, y - 35, x - 50, y - 35);
        g2d.drawLine(x + 35, y - 40, x + 50, y - 45);
        g2d.drawLine(x + 35, y - 35, x + 50, y - 35);

        // Tail
        g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        float tailWag = (float) Math.sin(animationFrame * 0.15) * 10;
        QuadCurve2D tail = new QuadCurve2D.Float(
                x + 35, y,
                x + 60 + tailWag, y - 20,
                x + 50, y - 40
        );
        g2d.setColor(catColor);
        g2d.draw(tail);

        // Label
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        String status = isHappy ? "😸 Happy Cat" : "😿 Hungry Cat";
        drawCenteredString(g2d, status, x, y + 60);
    }

    private void drawBowl(Graphics2D g2d, int x, int y) {
        // Bowl
        g2d.setColor(BOWL_COLOR);
        int[] bowlX = {x - 50, x - 40, x + 40, x + 50};
        int[] bowlY = {y, y + 30, y + 30, y};
        g2d.fillPolygon(bowlX, bowlY, 4);

        // Bowl rim
        g2d.setColor(BOWL_COLOR.darker());
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(x - 50, y, x + 50, y);

        // Food in bowl
        int foodCount = state.getFoodInBowl();
        if (foodCount > 0) {
            g2d.setColor(FOOD_COLOR);
            int maxFood = 6;
            int displayFood = Math.min(foodCount, maxFood);

            for (int i = 0; i < displayFood; i++) {
                int foodX = x - 30 + (i % 3) * 20 + (int) (Math.random() * 5);
                int foodY = y + 10 + (i / 3) * 10;
                g2d.fillOval(foodX, foodY, 15, 12);
            }
        }

        // Label
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        drawCenteredString(g2d, "🍚 Food Bowl: " + foodCount + " units", x, y + 50);
    }

    private void drawFeeder(Graphics2D g2d, int x, int y) {
        boolean isJammed = state.isDispenserJammed();
        Color feederColor = isJammed ? FEEDER_JAM_COLOR : FEEDER_COLOR;

        // Dispenser body
        g2d.setColor(feederColor);
        Rectangle2D body = new Rectangle2D.Double(x - 60, y, 120, 100);
        g2d.fill(body);

        // Dispenser top (hopper)
        int[] hopperX = {x - 60, x - 40, x + 40, x + 60};
        int[] hopperY = {y, y - 40, y - 40, y};
        g2d.fillPolygon(hopperX, hopperY, 4);

        // Dispenser chute
        g2d.setColor(feederColor.darker());
        int[] chuteX = {x - 20, x - 15, x + 15, x + 20};
        int[] chuteY = {y + 100, y + 130, y + 130, y + 100};
        g2d.fillPolygon(chuteX, chuteY, 4);

        // Food particles falling (if not jammed)
        if (!isJammed && animationFrame % 20 < 10) {
            g2d.setColor(FOOD_COLOR);
            for (int i = 0; i < 3; i++) {
                int particleY = y + 130 + (int) ((animationFrame % 20) * 5);
                g2d.fillOval(x - 10 + i * 10, particleY, 6, 6);
            }
        }

        // Jam indicator
        if (isJammed) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            drawCenteredString(g2d, "⚠️", x, y + 50);

            // Blinking text
            if (animationFrame % 20 < 10) {
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                drawCenteredString(g2d, "JAMMED!", x, y + 80);
            }
        }

        // Label
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        String status = isJammed ? "🔴 Feeder (JAMMED)" : "🟢 Feeder (Working)";
        drawCenteredString(g2d, status, x, y + 150);
    }

    private void drawRepairman(Graphics2D g2d, int x, int y) {
        boolean isFixing = state.isDispenserJammed();

        // Repairman body
        g2d.setColor(REPAIRMAN_COLOR);
        Rectangle2D body = new Rectangle2D.Double(x - 20, y, 40, 60);
        g2d.fill(body);

        // Head
        g2d.setColor(new Color(255, 220, 180));
        g2d.fillOval(x - 15, y - 30, 30, 30);

        // Hard hat
        g2d.setColor(Color.YELLOW);
        g2d.fillArc(x - 18, y - 35, 36, 25, 0, 180);
        g2d.fillRect(x - 20, y - 38, 40, 5);

        // Eyes
        g2d.setColor(Color.BLACK);
        g2d.fillOval(x - 8, y - 20, 4, 4);
        g2d.fillOval(x + 4, y - 20, 4, 4);

        // Arms
        g2d.setColor(REPAIRMAN_COLOR);
        g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        if (isFixing) {
            // Animated working arms
            float armAngle = (float) Math.sin(animationFrame * 0.2) * 20;
            g2d.drawLine(x - 20, y + 20, x - 40, y + 30 + (int) armAngle);
            g2d.drawLine(x + 20, y + 20, x + 40, y + 30 - (int) armAngle);

            // Wrench
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(6));
            g2d.drawLine(x + 40, y + 30, x + 50, y + 40);
        } else {
            // Idle arms
            g2d.drawLine(x - 20, y + 20, x - 30, y + 40);
            g2d.drawLine(x + 20, y + 20, x + 30, y + 40);
        }

        // Legs
        g2d.drawLine(x - 10, y + 60, x - 15, y + 90);
        g2d.drawLine(x + 10, y + 60, x + 15, y + 90);

        // Label
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        String status = isFixing ? "🔧 Repairman (FIXING)" : "🔧 Repairman (Idle)";
        drawCenteredString(g2d, status, x, y + 110);
    }

    private void drawStatusBox(Graphics2D g2d, int x, int y) {
        g2d.setColor(new Color(255, 255, 255, 230));
        g2d.fillRoundRect(x - 10, y - 10, 250, 80, 15, 15);

        g2d.setColor(new Color(100, 100, 100));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x - 10, y - 10, 250, 80, 15, 15);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2d.drawString("Animation Frame: " + animationFrame, x, y + 10);
        g2d.drawString("Cat State: " + (state.isCatHappy() ? "Happy 😸" : "Hungry 😿"), x, y + 30);
        g2d.drawString("Food in Bowl: " + state.getFoodInBowl(), x, y + 50);
    }

    private void drawCenteredString(Graphics2D g2d, String text, int x, int y) {
        FontMetrics metrics = g2d.getFontMetrics();
        int textX = x - metrics.stringWidth(text) / 2;
        g2d.drawString(text, textX, y);
    }
}
