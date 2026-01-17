package petFeeder;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Panel that displays a scrolling log of simulation events
 */
public class EventLogPanel extends JPanel {
    private final JTextPane textPane;
    private final StyledDocument doc;
    private final SimpleDateFormat timeFormat;

    private static final int MAX_LOG_LINES = 500;

    public EventLogPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Event Log"));

        timeFormat = new SimpleDateFormat("HH:mm:ss");

        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new Font("Monospaced", Font.PLAIN, 11));
        doc = textPane.getStyledDocument();

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Add initial message
        addEvent("Simulation initialized", new Color(100, 100, 100));
        addEvent("Waiting for events...", new Color(100, 100, 100));
    }

    public void addEvent(String message, Color color) {
        SwingUtilities.invokeLater(() -> {
            try {
                String timestamp = timeFormat.format(new Date());
                String fullMessage = String.format("[%s] %s\n", timestamp, message);

                // Create style for this message
                Style style = textPane.addStyle("Style", null);
                StyleConstants.setForeground(style, color);

                // Insert the message
                doc.insertString(doc.getLength(), fullMessage, style);

                // Limit log size
                if (doc.getLength() > MAX_LOG_LINES * 100) {
                    doc.remove(0, doc.getLength() / 2);
                }

                // Auto-scroll to bottom
                textPane.setCaretPosition(doc.getLength());

            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }

    public void clear() {
        SwingUtilities.invokeLater(() -> {
            try {
                doc.remove(0, doc.getLength());
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }
}