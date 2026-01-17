package petFeeder.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PetView extends JFrame {
    private JLabel statusLabel = new JLabel("😺 Cat is Happy!", SwingConstants.CENTER);
    private JLabel bowlLabel = new JLabel("Food in Bowl: 0", SwingConstants.CENTER);
    private JTextArea logArea = new JTextArea(10, 30);
    private JButton repairButton = new JButton("Repair Feeder");

    public PetView() {
        setTitle("MVC Pet Feeder");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        JPanel top = new JPanel(new GridLayout(2, 1));
        top.add(statusLabel);
        top.add(bowlLabel);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(logArea), BorderLayout.CENTER);
        add(repairButton, BorderLayout.SOUTH);
    }

    public void updateDisplay(int food, boolean happy, boolean jammed) {
        bowlLabel.setText("Food in Bowl: " + food);
        statusLabel.setText(happy ? "😺 Cat is Happy!" : "😿 Cat is HUNGRY!");
        statusLabel.setForeground(happy ? new Color(34, 139, 34) : Color.RED);
        repairButton.setEnabled(jammed);
    }

    public void addLog(String msg) {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public void setRepairAction(ActionListener listener) {
        repairButton.addActionListener(listener);
    }
}